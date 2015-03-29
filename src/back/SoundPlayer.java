package back;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Scanner;

public class SoundPlayer
{
    public static void main(String args[]) throws Exception
    {
        System.out.println("Press ENTER to start: ");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("sound2.rws"));

        AudioFormat format = new AudioFormat(131072, 8, 1, true, false);
        SourceDataLine line = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);

        line.start();
        int numBytesRead;
        byte buf[] = new byte[8192];
        while (true)
        {
            numBytesRead = in.read(buf, 0, buf.length);
            System.out.println(numBytesRead);
            if (numBytesRead == -1) break;
            line.write(buf, 0, numBytesRead);
        }

        /*DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(new AudioInputStream(in, format, new File("sound.rws").length()));

        clip.start();*/


    }
}

