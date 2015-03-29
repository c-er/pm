package back;

/**
 * Created by uday on 3/28/15.
 */

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class SoundRecorder extends JFrame implements ActionListener {


    static Recorder r;
    public SoundRecorder()
    {
        NoteDictionary.populate();
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("RawSoundRecorder");

        JButton butt = new JButton("Start");
        butt.setSize(100, 50);
        this.setLayout(null);
        this.add(butt);
        butt.setLocation(150, 125);
        butt.addActionListener(this);
    }



    public static void main(String args[]) throws Exception
    {
        r = new Recorder();
        new SoundRecorder();

        /*System.out.println("Press ENTER to start: ");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.println("Press ENTER to end: ");
        Recorder r = new Recorder();
        r.start();
        sc.nextLine();
        r.go = false;*/

    }


    public void actionPerformed(ActionEvent e) {
        JButton b;
        if(e.getSource() instanceof JButton)
        {
            b = (JButton)e.getSource();
            if(b.getText().equals("Start"))
            {
                b.setText("Stop");
                r.start();
                SongWave s = new SongWave(1250);

                s.song1();
                try {
                    s.out.close();
                } catch (Exception a) {}
                r.go = false;
                b.setText("Start");

                try {
                    new FFTPerformer().doWork();
                } catch (Exception a) {}


            }
            else
            {
                b.setText("Start");
                r.go = false;
            }
        }
    }
}

class Recorder extends Thread {

    boolean go;
    BufferedOutputStream out;
    public void cleanup() throws Exception
    {
        out.flush();
        out.close();
    }

    public Recorder()
    {
        go = true;
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
            line.start();

            System.out.println("Buf: " + line.getBufferSize());

            byte[] buf = new byte[line.getBufferSize() / 5];

            out = new BufferedOutputStream(new FileOutputStream("sound.rws"));

            while(go)
            {
                line.read(buf, 0, buf.length);
                out.write(buf);
            }

            System.out.println("Stopped, draining");
            System.out.println("Avail: " + line.available());


            line.drain();
            buf = new byte[line.available()];
            line.read(buf, 0, buf.length);
            out.write(buf);
            /*while(line.available() != 0)
            {
                System.out.println("Avail: " + line.available());
                line.drain();
                line.read(buf, 0, buf.length);
                out.write(buf);
            }*/

            System.out.println("Drained, cleaning up");

            this.cleanup();


        } catch (Exception e) {

        }

    }
}

