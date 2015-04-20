package back;

/**
 * Created by uday on 3/28/15.
 */

import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.JScoreComponent;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.StringTokenizer;

public class SoundRecorder extends JFrame implements ActionListener {


    static Recorder r;
    JLabel lab;
    public SoundRecorder()
    {
        NoteDictionary.populate();
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Project Marmalade");

        lab = new JLabel();
        lab.setSize(300, 25);

        JButton butt1 = new JButton("Song1");
        JButton butt2 = new JButton("Song2");
        butt1.setSize(100, 50);
        butt2.setSize(100, 50);
        this.setLayout(null);
        this.add(butt1);
        this.add(butt2);
        this.add(lab);
        butt1.setLocation(100, 150);
        butt2.setLocation(300, 150);
        lab.setLocation(100, 250);
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        lab.setText("Waiting for thine input");
        butt1.addActionListener(this);
        butt2.addActionListener(this);
    }



    public static void main(String args[]) throws Exception
    {
        //r = new Recorder();
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
        r = new Recorder();
        if(e.getSource() instanceof JButton)
        {
            b = (JButton)e.getSource();
            if(b.getText().equals("Song1"))
            {
                lab.setText("Playing/Capturing");
                lab.repaint();
                r.start();
                SongWave s = new SongWave(1250);


                s.song1();
                s.song1();
                //s.song2();
                try {
                    s.out.close();
                } catch (Exception a) {}
                r.go = false;
            }
            else if(b.getText().equals("Song2"))
            {
                lab.setText("Playing/Capturing");
                lab.repaint();
                r.start();
                SongWave s = new SongWave(1250);

                //s.song1();
                //s.song1();

                s.song2();
                try {
                    s.out.close();
                } catch (Exception a) {}
                r.go = false;
            }
            try {
                lab.setText("FFTing");
                System.out.println("FFTING");
                lab.repaint();
                FFTThread.doWork();
                //new FFTPerformer().doWork();
                lab.setText("Smoothing");
                System.out.println("Smoothing");
                lab.repaint();
                BufferedReader in = new BufferedReader(new FileReader("sound.fftr"));
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
                } // 40
                out.println(prev + " x " + count);
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
                in.close();

                in = new BufferedReader(new FileReader("sound.fftf"));

                lab.setText("Scoring");

                String tuneAsString = "X:2\nT:A simple scale exercise\nK:C\nL:1/8\nM:4/4\n";
                String line;
                int notes = 0;
                while(true)
                {
                    notes++;
                    line = in.readLine();
                    if(line == null)
                    {
                        break;
                    }
                    StringTokenizer st = new StringTokenizer(line);
                    String note = st.nextToken();
                    int num = Integer.parseInt(st.nextToken());
                    char n = note.charAt(0);
                    String app = "";
                    if(note.charAt(2) == '5')
                    {
                        n = Character.toLowerCase(n);
                    }
                    if(note.charAt(1) == 'N')
                    {
                        app = "=";
                    }
                    else if(note.charAt(1) == 'S')
                    {
                        app = "^";
                    }
                    app += "" + n + num;
                    if(notes == 40)
                    {
                        app += "\n";
                    }
                    tuneAsString += app;
                }
                tuneAsString += "\n";
                System.out.println(tuneAsString);
                Tune tune = new TuneParser().parse(tuneAsString);
                JScoreComponent scoreUI =new JScoreComponent();
                scoreUI.setTune(tune);
                JFrame j = new JFrame();
                j.add(scoreUI);
                j.pack();
                j.setVisible(true);

            } catch (Exception a) {}
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

