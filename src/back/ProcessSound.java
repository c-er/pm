package back;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by uday on 3/29/15.
 */
public class ProcessSound {

    public BufferedImage image;
    public Graphics2D g;

    public ProcessSound()
    {
        image = new BufferedImage(1800, 1000, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
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
    }
    public static void main(String[] args) throws Exception {
        new ProcessSound().drawNote("AAA");
        /*BufferedReader in = new BufferedReader(new FileReader("sound.fftr"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sound.fft2")));
        String prev = in.readLine();
        String str;
        int count = 1;
        while(true)
        {
            str = in.readLine();
            if(str == null)
                break;
            if(!str.equals(prev))
            {
                out.println(prev + " x " + count);
                count = 1;
            }
            else
            {
                count++;
            }

            prev = str;
        }
        out.close();
        in.close();

        in = new BufferedReader(new FileReader("sound.fft2"));
        out = new PrintWriter(new BufferedWriter(new FileWriter("sound.fftf")));

        while(true)
        {
            str = in.readLine();
            if(str == null)
                break;
            StringTokenizer st = new StringTokenizer(str);
            String n = st.nextToken();
            st.nextToken();
            int i = Integer.parseInt(st.nextToken());
            if(i != 1)
            {
                i = i + 1;
                if(i / 5 == 0)
                {
                }
                else
                {
                    out.println(n + " " + i / 5);
                }
            }
        }

        out.close();
        in.close();*/

    }

    public void drawNote(String note)
    {
        try {
            Graphics gg;
            BufferedImage clef = ImageIO.read(new File("src/back/img/clef.png"));
            BufferedImage qnote = ImageIO.read(new File("src/back/img/good.png"));
            BufferedImage sharpraw = ImageIO.read(new File("src/back/img/sharp.png"));
            BufferedImage dqnote = ImageIO.read(new File("src/back/img/dqnote.png"));
            BufferedImage hnoteraw = ImageIO.read(new File("src/back/img/hnote.png"));
            BufferedImage hnote = null;
            gg = hnoteraw.createGraphics();
            gg.drawImage(hnote, 0, 0, 488, 495, null);
            gg.dispose();
            BufferedImage enote = ImageIO.read(new File("src/back/img/enote.png"));
            BufferedImage sharp = null;
            gg = sharpraw.createGraphics();
            gg.drawImage(sharp, 0, 0, 132, 152, null);
            gg.dispose();

            g.drawImage(clef, 0, 0, null);
            g.drawImage(hnote, 45, 0, null);

            try {
                ImageIO.write(image, "png", new FileOutputStream("music.png"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }

    }
}
