import back.Data;
import org.apache.commons.math3.complex.Complex;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by uday on 5/14/15.
 */
public class Test2 {
    public static void main(String[] args) {
        try {
            AudioFormat format = new AudioFormat(131072, 8, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            System.out.print("Enter BPM: ");
            int bpm = Integer.parseInt(new Scanner(System.in).nextLine());
            int len = (int)((60.0 / (bpm * 4.0)) * 131072.0);
            System.out.println(len);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("sound.rws"));

            int numBytesRead;
            byte buf[] = new byte[len];
            ArrayList<Freq> freqs = new ArrayList<>();
            while (true)
            {
                numBytesRead = in.read(buf, 0, buf.length);
                if (numBytesRead == -1) break;
                byte b2[] = Test.help(buf);
                Complex fft[] = Test.doFFT(b2);
                ArrayList<Data> a = new ArrayList<>();

                for(int i = 0; i < fft.length; i++)
                {
                    Data d = new Data(fft[i].abs(), i);
                    //System.out.println(d);
                    a.add(d);
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
                //System.out.println(f);
            }

            Test.smooth(freqs);

            System.out.println();

            for(int i = 0; i < freqs.size(); i++)
            {
                System.out.println(freqs.get(i));
            }

            byte play[] = new byte[freqs.size() * len];
            int j = 0;
            for(int i = 0; i < freqs.size(); i++)
            {
                if(freqs.get(i).dur == 0)
                {
                    continue;
                }
                byte temp[] = Test.createSineWave(freqs.get(i).freq, len * freqs.get(i).dur, format);
                System.arraycopy(temp, 0, play, j, temp.length);
                j += temp.length;
            }
            line.start();
            line.write(play, 0, play.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
