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

    public void doWork() throws Exception
    {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("sound.rws"));
        byte[] buf = new byte[131072];

        int numRead;
        while (true)
        {
            numRead = in.read(buf, 0, buf.length);
            if (numRead == -1) break;

            Complex[] data = makeComplex(buf);
            doFFT(data);
        }
    }

    public Complex[] makeComplex(byte[] data)
    {
        Complex[] complex = new Complex[data.length];

        for(int i = 0; i < data.length; i++)
        {
            complex[i] = new Complex(data[i] / 128.0, 0);
        }

        return complex;
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
        for(int i = 0; i < 10; i++)
        {
            Data dat = list.get(i);
            System.out.println(dat.index);
        }

    }
}
