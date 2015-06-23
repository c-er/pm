package back;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by uday on 4/20/15.
 */

// blaze it
public class DynamicRecorder extends Thread {

    public boolean go;
    public int window;
    private String fftname;
    public ArrayList<FFTThread> fft = new ArrayList<>();
    // bitesize is the fft window size you want it to do
    public DynamicRecorder(int bitesize, String fftname)
    {
        this.window = bitesize;
        this.fftname = fftname;
    }

    public void printResults() throws Exception
    {
        if(this.isAlive())
        {
            return;
        }

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fftname)));
        for(int i = 0; i < fft.size(); i++)
        {
            if(fft.get(i).note != null)
            {
                out.println(fft.get(i).note);
            }
        }
    }

    public void run()
    {
        try {

            TargetDataLine line = null;
            AudioFormat format = new AudioFormat(131072, 8, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // System.out.println("Step1");

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Too high sample rate");
                System.exit(-2);
            }
            // System.out.println("Step2");

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);

            byte buf[] = new byte[window];

            line.start();

            while(go)
            {
                line.read(buf, 0, window);
                FFTThread t = new FFTThread(buf, 131072);
                fft.add(t);
                t.start();
            }

            int av = line.available();

            System.out.println("Draining start");
            line.drain();
            line.close();
            System.out.println("Draining ended");

            for(int i = 0; i <= av; i += window)
            {
                line.read(buf, 0, window);
                FFTThread t = new FFTThread(buf, 131072);
                fft.add(t);
                t.start();
            }

        } catch (Exception e) {
            System.out.println("Caught exception in DynamicRecorder Thread");
            e.printStackTrace();
        }

    }
}
