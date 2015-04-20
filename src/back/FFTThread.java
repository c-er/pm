package back;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by uday on 4/19/15.
 */
public class FFTThread extends Thread {
    byte data[];
    String note;
    int len;
    public FFTThread(byte data[], int len)
    {
        this.data = new byte[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);
        note = null;
        this.len = len;
    }

    public void run()
    {
        Complex dat[] = new Complex[this.len];
        for(int i = 0; i < dat.length; i++)
        {
            if(i >= data.length)
            {
                dat[i] = new Complex(0, 0);
            }
            else
            {
                dat[i] = new Complex(this.data[i] / 128.0, 0);
            }
        }
        try {
            note = doFFT(dat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String doFFT(Complex[] data) throws Exception
    {
        Complex[] fftRes = new FastFourierTransformer(DftNormalization.STANDARD).transform(data, TransformType.FORWARD);
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

    public static void doWork()
    {
        try {
            PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("sound.fftr")));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("sound.rws"));

            byte[] buf = new byte[4096];

            int numRead;
            String prev = "";
            String tot = "";
            int count = 1;
            ArrayList<FFTThread> arr = new ArrayList<>();
            System.out.println("Starting thread init");
            while (true)
            {
                numRead = in.read(buf, 0, 4096);
                if (numRead == -1) break;
                FFTThread t = new FFTThread(buf, 65536);
                t.start();
                arr.add(t);
            }
            System.out.println("Ending thread init");
            for(int i = 0; i < arr.size(); i++)
            {
                if(arr.get(i).note == null)
                {
                    System.out.println("Sleeping");
                    Thread.sleep(100);
                }
                else
                {
                    out2.print(filter(arr.get(i).note));
                    out2.flush();
                }
            }

            out2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String filter(String note)
    {
        if(note.equals("CN0"))
        {
            return "";
        }
        return note + "\n";
    }
}
