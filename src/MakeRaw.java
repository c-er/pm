import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 * Created by uday on 5/14/15.
 */
public class MakeRaw {
    public static void main(String[] args) {
        try {
            System.out.print("Enter filename: ");
            AudioInputStream in = AudioSystem.getAudioInputStream(new File(new Scanner(System.in).nextLine()));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("sound.rws"));
            int bytesFrame = in.getFormat().getFrameSize();
            AudioFormat format = in.getFormat();
            System.out.println(format.getFrameRate());
            System.out.println(format.getFrameSize());
            /*if(bytesFrame == AudioSystem.NOT_SPECIFIED)
            {
                bytesFrame = 1;
            }
            byte buf[] = new byte[bytesFrame * 4096];
            int read;
            while((read = in.read(buf)) != -1)
            {

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
