package back;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
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

    public void play2(double f1, double f2, int dur)
    {
        int samps = (int)(((double)dur/time) * format.getSampleRate());
        System.out.println(samps);
        byte buf[] = new byte[samps];
        double a1 = 2 * Math.PI * f1;
        double a2 = 2 * Math.PI * f2;
        // System.out.println(a);
        for(int i = 0; i < samps; i++)
        {
            buf[i] = (byte)(((byte)(127 * Math.cos(i * (2 * Math.PI * f1 / (format.getSampleRate())))) + (byte)(127 * Math.cos(i * (2 * Math.PI * f2 / (format.getSampleRate()))))) / 2);
        }
        line.write(buf, 0, buf.length);
    }

    public void playChord(Frequency freq[], int dur)
    {
        int samps = (int)(((double)dur/time) * format.getSampleRate());
        byte buf[] = new byte[samps];
        double maxweight = 0;
        double totweight = 0;
        for(int i = 0; i < freq.length; i++)
        {
            totweight += freq[i].weight;
            if(freq[i].weight > maxweight)
            {
                maxweight = freq[i].weight;
            }
        }
        for(int i = 0; i < samps; i++)
        {
            double val = 0;
            for(int j = 0; j < freq.length; j++)
            {
                val += (freq[j].weight / maxweight) * (127 * Math.cos(i * (2 * Math.PI * freq[j].freq / (format.getSampleRate()))));
            }
            buf[i] = (byte)(val / (totweight / maxweight));
        }
        line.write(buf, 0, buf.length);
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

    public void song2() throws Exception
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



        this.writeToFile(392, 200);
        this.writeToFile(440, 200);
        this.writeToFile(466, 200);
        this.writeToFile(523, 200);
        this.writeToFile(587, 400);
        this.writeToFile(784, 400);
        this.writeToFile(698, 400);
        this.writeToFile(587, 400);
        this.writeToFile(659, 800);

        this.writeToFile(698, 400);
        this.writeToFile(587, 200);
        this.writeToFile(659, 200);
        this.writeToFile(698, 400);
        this.writeToFile(659, 200);
        this.writeToFile(587, 200);
        this.writeToFile(440, 400);
        this.writeToFile(466, 400);
        this.writeToFile(440, 800);

        this.writeToFile(392, 200);
        this.writeToFile(440, 200);
        this.writeToFile(466, 200);
        this.writeToFile(523, 200);
        this.writeToFile(466, 400);
        this.writeToFile(440, 200);
        this.writeToFile(466, 200);
        this.writeToFile(523, 400);
        this.writeToFile(466, 200);
        this.writeToFile(440, 200);
        this.writeToFile(392, 800);

        this.writeToFile(523, 400);
        this.writeToFile(466, 200);
        this.writeToFile(440, 200);
        this.writeToFile(392, 400);
        this.writeToFile(440, 200);
        this.writeToFile(466, 200);
        this.writeToFile(523, 400);
        this.writeToFile(466, 200);
        this.writeToFile(523, 200);
        this.writeToFile(587, 200);
        this.writeToFile(523, 200);
        this.writeToFile(466, 200);
        this.writeToFile(440, 200);

        // bruh

        /*this.play(392, 200);
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
        this.play(587, 800);*/
    }

    public void song1() throws Exception
    {
        this.play(392, 652 * 2);
        //this.play(392, 652);
        this.play(587, 652);
        this.play(698, 652);
        this.play(784, 652);
        this.play(523, 652);
        this.play(587, 652);
        this.play(392, 652 * 3);
        //this.play(392, 652);
        this.play(523, 652);
        this.play(392, 652);
        this.play(466, 652);
        this.play(392, 652);
        this.play(440, 652);
        this.play(466, 652);
        this.play(349, 652);
        this.play(466, 652);
        this.play(523, 652);
        this.play(698, 652 * 2);
        this.play(659, 652);
        this.play(589, 652);
        this.play(659, 652 * 2);
        this.play(466, 652);
        this.play(523, 652);
        this.play(659, 652 * 2);
        this.play(587, 652);
        this.play(523, 652);
        this.play(466, 652);

        this.writeToFile(392, 652 * 2);
        //this.writeToFile(392, 652);
        this.writeToFile(587, 652);
        this.writeToFile(698, 652);
        this.writeToFile(784, 652);
        this.writeToFile(523, 652);
        this.writeToFile(587, 652);
        this.writeToFile(392, 652 * 3);
        //this.writeToFile(392, 652);
        this.writeToFile(523, 652);
        this.writeToFile(392, 652);
        this.writeToFile(466, 652);
        this.writeToFile(392, 652);
        this.writeToFile(440, 652);
        this.writeToFile(466, 652);
        this.writeToFile(349, 652);
        this.writeToFile(466, 652);
        this.writeToFile(523, 652);
        this.writeToFile(698, 652 * 2);
        this.writeToFile(659, 652);
        this.writeToFile(589, 652);
        this.writeToFile(659, 652 * 2);
        this.writeToFile(466, 652);
        this.writeToFile(523, 652);
        this.writeToFile(659, 652 * 2);
        this.writeToFile(587, 652);
        this.writeToFile(523, 652);
        this.writeToFile(466, 652);
    }

    public static void main(String[] args) throws Exception {
        SongWave s = new SongWave(1250);
        
        s.out = new BufferedOutputStream(new FileOutputStream("sound.rws"));

        s.song1();
        

        //s.play2(392, 587.33, 1250);
        //Frequency[] f = new Frequency[15];
        //f[0] = new Frequency(392, Math.random());
        //f[1] = new Frequency(466.16, Math.random());
        //f[2] = new Frequency(587.33, Math.random());
        //for(int i = 0; i < 15; i++)
        //{
        //    f[i] = new Frequency(16.35 * Math.pow(2, i), Math.random());
        //}
       // s.playChord(f, 2500);



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
        /*s.song1();
        s.song1();
        TargetDataLine line = null;
        AudioFormat format = new AudioFormat(131072, 8, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);*/

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

class Frequency {
    double freq;
    double weight;

    public Frequency(double freq, double weight)
    {
        this.freq = freq;
        this.weight = weight;
    }
}