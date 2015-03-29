package back;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * Created by uday on 3/28/15.
 */
public class SongWave {
    public int time; // time is the duration assigned to one second
    private AudioFormat format;
    private SourceDataLine line;
    private DataLine.Info info;

    public SongWave(int time)
    {
        try {
            this.time = time;
            format = new AudioFormat(131072, 8, 1, true, false);
            info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
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
            buf[i] = (byte)(127 * Math.sin(i * (2 * Math.PI * freq / (format.getSampleRate()))));
        }
        line.write(buf, 0, buf.length);
    }

    public static void main(String[] args) {
        SongWave s = new SongWave(1000);
        while(true) {
            s.play(392, 200);
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
        }
    }
}
