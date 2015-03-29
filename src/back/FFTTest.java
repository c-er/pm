package back;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 * Created by Mahesh on 11/9/2014.
 */
public class FFTTest {
    public static double process(byte data[], int samples) {

        byte norm[] = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            norm[i] = (byte) (data[i] - data[0]);
        }

        double maxsum = -1000000;
        double maxfreq = 0;
        for (double d = 3500.0; d > 25; d--) {
            double sum = 0.0;
            for (int i = 0; i < norm.length; i++) {
                sum += norm[i] * Math.sin(i * (d / (double) samples));
            }
            sum = Math.abs(sum);
            if (sum > maxsum) {
                maxsum = sum;
                maxfreq = d;
            }
        }
        return maxfreq / (2 * Math.PI);
    }

    public static void main(String[] args) {
        NoteDictionary.populate();
        Scanner sc = new Scanner(System.in);
        System.out.println("Press ENTER to start");
        sc.nextLine();


        System.out.println("Press ENTER to stop");
        Reader r = new Reader();
        r.start();
        sc.nextLine();
        r.stop();

        try {
            ImageIO.write(r.getImage(), "png", new FileOutputStream("music.png"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

class Reader extends Thread{

    public BufferedImage image;

    public BufferedImage getImage()
    {
        return image;
    }

    public void run()
    {
        try {
            TargetDataLine line = null;
            AudioFormat format = new AudioFormat(100000, 8, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);



            // System.out.println("Step1");

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Too high sample rate");
                System.exit(-2);
            }
            // System.out.println("Step2");

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            image = new BufferedImage(1800, 1000, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();

            g.setColor(Color.white);
            g.fillRect(0, 0, 1800, 1000);

            g.setColor(Color.black);
            int x = 90;

            for (int i = 0; i < 6; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    g.drawLine(100, x, 1700, x);
                    x += 14;
                }

                x += 14;
            }
            BufferedImage clef = ImageIO.read(new File("src/img/clef.png"));
            BufferedImage note = ImageIO.read(new File("src/img/good.png"));
            BufferedImage flat = ImageIO.read(new File("src/img/flat.png"));

            //g.drawImage(flat, 170, 32, null);
            //g.drawImage(note, 10, -82, null);


            g.drawImage(clef, 0, 0, null);



            byte buf[] = new byte[10000];
            int xPos = 10;



            while (true)
            {
                System.out.println("Stop Breathing!");
                line.read(buf, 0, buf.length);
                line.drain();
                line.flush();
                line.drain();

                System.out.println("Breath!");
                double proc = FFTTest.process(buf, 100000);

                System.out.println("Freq: " + proc + ", Note: " + NoteDictionary.getName(proc));

                switch(NoteDictionary.getName(proc) % 12)
                {
                    case 0: g.drawImage(note, xPos, -82 - 7 * 4, null); break;
                    case 1: g.drawImage(note, xPos, -82 - 7 * 5, null); g.drawImage(flat, xPos + 160, -82 - 7 * 5 + 114, null); break;
                    case 2: g.drawImage(note, xPos, -82 - 7 * 5, null); break;
                    case 3: g.drawImage(note, xPos, -82 - 7 * 6, null); g.drawImage(flat, xPos + 160, -82 - 7 * 6 + 114, null); break;
                    case 4: g.drawImage(note, xPos, -82 - 7 * 6, null); break;
                    case 5: g.drawImage(note, xPos, -82, null); break;
                    case 6: g.drawImage(note, xPos, -82 - 7 * 5, null); g.drawImage(flat, xPos + 160, -82 - 7 + 114, null); break;
                    case 7: g.drawImage(note, xPos, -82 - 7, null); break;
                    case 8: g.drawImage(note, xPos, -82 - 7 * 2, null); g.drawImage(flat, xPos + 160, -82 - 7 * 2 + 114, null); break;
                    case 9: g.drawImage(note, xPos, -82 - 7 * 2, null); break;
                    case 10: g.drawImage(note, xPos, -82 - 7 * 3, null); g.drawImage(flat, xPos + 160, -82 - 7 * 3 + 114, null); break;
                    case 11: g.drawImage(note, xPos, -82 - 7 * 3, null); break;
                }

                // g.drawImage(note, xPos, -82 - (7 * (NoteDictionary.getName(proc) % 12 - 7)), null);

                xPos += 45;
                // if NoteDictionary.getName(proc) % 12 == 5, it should be in default position
            }
        } catch (Exception e) {

        }
    }
}
