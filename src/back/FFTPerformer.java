package back;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class FFTPerformer
{
    public FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);

    public static void main(String[] args) throws Exception
    {
        new FFTPerformer().doWork();
    }

    public void doWork() throws Exception
    {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("sound2.rws"));
        byte[] buf = new byte[131072];

        int numRead;
        while (true)
        {

            numRead = in.read(buf, 0, 4096);
            if (numRead == -1) break;

            Complex[] data = makeComplex(buf);
            int pos = 0;

            doFFT(data);
        }
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

    public void doFFT(Complex[] data)
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
        for(int i = 0; i < 5; i++)
        {
            Data dat = list.get(i);
            if (dat.index >= 5000)
                continue;

            //System.out.println(dat.index);
        }

        //System.out.println();
    }
}
