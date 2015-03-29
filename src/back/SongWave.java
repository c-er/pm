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
            buf[i] = (byte)(127 * Math.sin(i * (2 * Math.PI * freq / (format.getSampleRate()))));
        }
        out.write(buf, 0, buf.length);
        out.flush();
    }

    public static void main(String[] args) throws Exception {
        SongWave s = new SongWave(131072);
        //s.writeToFile(0, 25);
        s.writeToFile(440, 2048);
        s.writeToFile(250, 6144);

        s.out.close();
        /*while(true) {
            s.play(392, 200);
            s.play(392, 200);
            s.play(587, 200);
            s.play(698, 200);
            s.play(784, 200);
            s.play(523, 200);
            s.play(587, 200);c
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
        }*/
    }
}
