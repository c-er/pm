import back.Data;
import back.NoteDictionary;
import org.apache.commons.math3.complex.Complex;
import org.jtransforms.fft.DoubleFFT_1D;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Test {

    public static void main(String[] args) {
        try {
            AudioFormat format = new AudioFormat(131072, 8, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            System.out.println(Integer.MAX_VALUE);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("sound.rws"));

            int numBytesRead;
            byte buf[] = new byte[2048];
            ArrayList<Freq> freqs = new ArrayList<>();
            while (true)
            {
                numBytesRead = in.read(buf, 0, buf.length);
                if (numBytesRead == -1) break;
                byte b2[] = help(buf);
                Complex fft[] = doFFT(b2);
                ArrayList<Data> a = new ArrayList<Data>();

                for(int i = 0; i < fft.length; i++)
                {
                    a.add(new Data(fft[i].abs(), i));
                }

                Collections.sort(a);
                int f = 0;
                for(int i = 0; i < 10; i++)
                {
                    if (a.get(i).index < 25000)
                    {
                        f = a.get(i).index;
                        break;
                    }
                }
                freqs.add(new Freq(f, 1));
                System.out.println(f);
            }

            smooth(freqs);

            System.out.println();

            for(int i = 0; i < freqs.size(); i++)
            {
                System.out.println(freqs.get(i));
            }

            byte play[] = new byte[freqs.size() * 2048];
            int j = 0;
            for(int i = 0; i < freqs.size(); i++)
            {
                if(freqs.get(i).dur == 0)
                {
                    continue;
                }
                byte temp[] = createSineWave(freqs.get(i).freq, 2048 * freqs.get(i).dur, format);
                System.arraycopy(temp, 0, play, j, temp.length);
                j += temp.length;
            }
            line.start();
            line.write(play, 0, play.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void smooth(ArrayList<Freq> freqs)
    {
        for(int i = 0; i < freqs.size(); i++)
        {
            int count = 1;
            if(freqs.get(i).dur == 0)
            {
                continue;
            }
            double f = NoteDictionary.getClosest(freqs.get(i).freq);
            for(int j = i + 1; j < freqs.size(); j++)
            {
                if(f == NoteDictionary.getClosest(freqs.get(j).freq))
                {
                    count++;
                    freqs.get(j).dur = 0;
                }
                else
                {
                    break;
                }
            }
            freqs.get(i).freq = (int)f;
            freqs.get(i).dur = count;
        }
    }

    public static byte[] createSineWave(double freq, int len, AudioFormat format)
    {
        byte ret[] = new byte[len];
        double w = Math.PI * 2.0 * freq;
        for(int i = 0; i < ret.length; i++)
        {
            ret[i] = (byte)(127 * Math.sin(w * i / format.getSampleRate()));
        }
        return ret;
    }

    public static Complex[] makeComplex(byte b[])
    {
        Complex ret[] = new Complex[b.length];
        for(int i  = 0; i < b.length; i++)
        {
            ret[i] = new Complex(b[i], 0);
        }
        return ret;
    }

    public static double[] makeGay(Complex b[])
    {
        double ret[] = new double[b.length * 2];
        for(int i = 0; i < b.length; i++)
        {
            ret[2 * i] = b[i].getReal();
            ret[2 * i + 1] = b[i].getImaginary();
        }
        return ret;
    }

    public static Complex[] makeStraight(double b[])
    {
        Complex ret[] = new Complex[b.length / 2];
        for(int i = 0; i < ret.length; i++)
        {
            ret[i] = new Complex(b[2 * i], b[2 * i + 1]);
        }
        return ret;
    }

    public static Complex[] doFFT(byte input[])
    {
        Complex fft[] = makeComplex(input);
        double fft2[] = makeGay(fft);
        new DoubleFFT_1D(input.length).complexForward(fft2);
        return makeStraight(fft2);
    }

    public static byte[] help(byte[] a)
    {
        byte ret[] = new byte[131072];
        for(int i = 0; i < a.length; i++)
        {
            ret[i] = a[i];
        }
        return ret;
    }

}

class Freq {
    public int freq;
    public int dur;

    public Freq(int freq, int dur)
    {
        this.freq = freq;
        this.dur = dur;
    }

    public String toString()
    {
        return "Frequency: " + freq + " Duration: " + dur;
    }
}
