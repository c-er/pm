package back;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class FFTPerformer
{
    public FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
    public PrintWriter out;
    public PrintWriter out2;

    public static void main(String[] args) throws Exception
    {
        NoteDictionary.populate();

        new FFTPerformer().doWork();
    }

    public String filter(String note)
    {
        if(note.equals("CN0"))
        {
            return "";
        }
        return note + "\n";
    }


    public void doWork() throws Exception
    {
        //out = new PrintWriter(new BufferedWriter(new FileWriter("sound.fft")));
        out2 = new PrintWriter(new BufferedWriter(new FileWriter("sound.fftr")));
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("sound.rws"));
        byte[] buf = new byte[65536];

        int numRead;
        String prev = "";
        String tot = "";
        int count = 1;
        while (true)
        {

            numRead = in.read(buf, 0, 4096);
            if (numRead == -1) break;

            Complex[] data = makeComplex(buf);
            int pos = 0;

            String freq = doFFT(data);
            if(!freq.equals(""))
            {
                System.out.println(freq);
                out2.print(filter(freq));
                out2.flush();
            }

            if(!prev.equals(freq))
            {
                //out.print(filter(freq));
            }

            prev = freq;
        }

        out2.close();
        //out.close();

    }

    public Complex[] makeComplex(byte[] data)
    {
        Complex[] complex = new Complex[data.length];

        for(int i = 0; i < data.length; i++)
        {
            complex[i] = new Complex((data[i] / 128.0) /* hammingWindow(i, data.length)*/, 0);
        }

        return complex;
    }

    private static double hammingWindow(int n, int frameSize)
    {
        return 0.54 - 0.46 * Math.cos(2.0 * Math.PI * (double) n / (double) (frameSize - 1));
    }

    public String doFFT(Complex[] data) throws Exception
    {
        Complex[] fftRes = fft.transform(data, TransformType.FORWARD);
        ArrayList<Data> list = new ArrayList<Data>();

        for(int i = 0; i < fftRes.length; i++)
        {
            Complex complex = fftRes[i];
            double mag = Math.sqrt(Math.pow(complex.getReal(), 2) + Math.pow(complex.getImaginary(), 2));

            list.add(new Data(mag, i));
        }



        Collections.sort(list);
        String j = "";
        for(int i = 0; i < 5; i++)
        {
            Data dat = list.get(i);
            if (dat.index >= 5000)
                continue;
            else {
                //out.println(NoteDictionary.getNote(dat.index));
                //System.out.println(NoteDictionary.getNote(dat.index));
                j = NoteDictionary.getNote(dat.index);
                break;
            }
        }
        //out.flush();
        return j;
    }
}
