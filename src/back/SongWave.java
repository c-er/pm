package back;

import javax.sound.sampled.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;


public class SongWave {
    public int time; // time is the duration assigned to one second
    public AudioFormat format;
    public SourceDataLine line;
    public DataLine.Info info;
    public BufferedOutputStream out;

    public SongWave(int time)
    {
        try {
            this.time = time;
            format = new AudioFormat(131072, 8, 1, true, false);
            info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            out = new BufferedOutputStream(new FileOutputStream("sound2.rws"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(double freq, int dur)  //plays note with frequency freq for duration dur
    {
        int samps = (int)(((double)dur/time) * format.getSampleRate());
        System.out.println(samps);
        byte buf[] = new byte[samps];
        double a = 2 * Math.PI * freq;
        System.out.println(a);
        for(int i = 0; i < samps; i++)
        {
            buf[i] = (byte)(127 * Math.cos(i * (2 * Math.PI * freq / (format.getSampleRate()))));
        }
        line.write(buf, 0, buf.length);
    }

    public void writeToFile(double freq, int dur) throws Exception
    {
        int samps = (int)(((double)dur/time) * format.getSampleRate());
        System.out.println(samps);
        byte buf[] = new byte[samps];
        double a = 2 * Math.PI * freq;
        System.out.println(a);
        for(int i = 0; i < samps; i++)
        {
            buf[i] = (byte)(127 * Math.cos(i * (2 * Math.PI * freq / (format.getSampleRate()))));
        }
        out.write(buf, 0, buf.length);
        out.flush();
    }

    public void song3()
    {

    }

    public void song2()
    {
        this.play(392, 200);
        this.play(440, 200);
        this.play(466, 200);
        this.play(523, 200);
        this.play(587, 400);
        this.play(784, 400);
        this.play(698, 400);
        this.play(587, 400);
        this.play(659, 800);

        this.play(698, 400);
        this.play(587, 200);
        this.play(659, 200);
        this.play(698, 400);
        this.play(659, 200);
        this.play(587, 200);
        this.play(440, 400);
        this.play(466, 400);
        this.play(440, 800);

        this.play(392, 200);
        this.play(440, 200);
        this.play(466, 200);
        this.play(523, 200);
        this.play(466, 400);
        this.play(440, 200);
        this.play(466, 200);
        this.play(523, 400);
        this.play(466, 200);
        this.play(440, 200);
        this.play(392, 800);

        this.play(523, 400);
        this.play(466, 200);
        this.play(440, 200);
        this.play(392, 400);
        this.play(440, 200);
        this.play(466, 200);
        this.play(523, 400);
        this.play(466, 200);
        this.play(523, 200);
        this.play(587, 200);
        this.play(523, 200);
        this.play(466, 200);
        this.play(440, 200);

        // bruh

        this.play(392, 200);
        this.play(440, 200);
        this.play(466, 200);
        this.play(523, 200);
        this.play(587, 400);
        this.play(784, 400);
        this.play(698, 400);
        this.play(587, 400);
        this.play(659, 800);

        this.play(698, 400);
        this.play(587, 200);
        this.play(659, 200);
        this.play(698, 400);
        this.play(659, 200);
        this.play(587, 200);
        this.play(440, 400);
        this.play(466, 400);
        this.play(440, 800);

        this.play(392, 200);
        this.play(440, 200);
        this.play(466, 200);
        this.play(523, 200);
        this.play(466, 400);
        this.play(440, 200);
        this.play(466, 200);
        this.play(523, 400);
        this.play(466, 200);
        this.play(440, 200);
        this.play(392, 800);

        this.play(523, 400);
        this.play(466, 200);
        this.play(440, 200);
        this.play(392, 400);
        this.play(440, 200);
        this.play(466, 200);
        this.play(523, 400);
        this.play(466, 200);
        this.play(523, 200);
        this.play(587, 800);
    }

    public void song1()
    {
        this.play(392, 400);
        //this.play(392, 200);
        this.play(587, 200);
        this.play(698, 200);
        this.play(784, 200);
        this.play(523, 200);
        this.play(587, 200);
        this.play(392, 600);
        //this.play(392, 200);
        this.play(523, 200);
        this.play(392, 200);
        this.play(466, 200);
        this.play(392, 200);
        this.play(440, 200);
        this.play(466, 200);
        this.play(349, 200);
        this.play(466, 200);
        this.play(523, 200);
        this.play(698, 400);
        this.play(659, 200);
        this.play(589, 200);
        this.play(659, 400);
        this.play(466, 200);
        this.play(523, 200);
        this.play(659, 400);
        this.play(587, 200);
        this.play(523, 200);
        this.play(466, 200);
    }

    public static void main(String[] args) throws Exception {
        SongWave s = new SongWave(1250);



        /*s.play(392, 200);
        s.play(392, 200);
        s.play(587, 200);
        s.play(698, 200);
        s.play(784, 200);
        s.play(523, 200);
        s.play(587, 200);
        s.play(392, 400);
        s.play(392, 200);
        s.play(523, 200);
        s.play(392, 200);
        s.play(466, 200);
        s.play(392, 200);
        s.play(440, 200);
        s.play(466, 200);
        s.play(349, 200);
        s.play(466, 200);
        s.play(523, 200);
        s.play(698, 400);
        s.play(659, 200);
        s.play(589, 200);
        s.play(659, 400);
        s.play(466, 200);
        s.play(523, 200);
        s.play(659, 400);
        s.play(587, 200);
        s.play(523, 200);
        s.play(466, 200);

        s.out.close();*/
        //s.song2();
        s.song1();
        s.song1();
        TargetDataLine line = null;
        AudioFormat format = new AudioFormat(131072, 8, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        // System.out.println("Step1");

        /*if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Too high sample rate");
            System.exit(-2);
        }
        // System.out.println("Step2");

        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

        System.out.println("Buf: " + line.getBufferSize());

        byte[] buf = new byte[line.getBufferSize() / 5];


        while(true)
        {
            line.read(buf, 0, buf.length);
            for(int i = 0; i < buf.length; i++)
            {
                System.out.println(buf[i]);
            }
        }*/

    }
}
