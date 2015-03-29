import java.io.*;

/**
 * Created by gov on 3/28/2015.
 */

public class GUI {

    public String NoteReader(int noteNumber) throws IOException {
        String note = null;

        BufferedReader nReader = new BufferedReader(new FileReader("C:\\Users\\gov\\Documents\\test.txt"));
        for(int i = 0; i < noteNumber; i++) {
            note = nReader.readLine();
        }
        return(note);
    }

    public int AmtOfLines() throws IOException{
        int len;

        LineNumberReader length = new LineNumberReader(new FileReader(new File("C:\\Users\\gov\\Documents\\test.txt")));
        length.skip(Long.MAX_VALUE);

        len = length.getLineNumber() + 1;
        length.close();

        return(len);
    }


    public int OctAvg(){
        int     oct ;
        int     amt = 0;
        int     sum = 0;
        int     nNumb = 1;
        String  mNote = "You Shouldn't see this!";

        try{
            amt = AmtOfLines();
        }
        catch (IOException e){
            System.out.println("OctAvg L assignment error");
        }

        for(int i = 1; i <= amt; i++){

            try{
                mNote = NoteReader(nNumb);
                nNumb++;
            }
            catch (IOException e){
                System.out.println("Oops");
            }

            sum += (int)mNote.charAt(2) - '0';
        }
        oct = sum / amt;
        return(oct);
    }

    public void NoteLocation(){
        int     octAvg;
        int     oct;
        int     yVal;
        int     lines = 0;
        int     noteNum = 1;
        String  noteInfo;
        char    musicNote = 0;

        try{
            lines = AmtOfLines();

        }
        catch (IOException e){
            System.out.println("Oops in reading line amount in NoteLocation");
            e.printStackTrace();
        }

        for(int i = 1; i <= lines; i++){
            try{
                noteInfo = NoteReader(noteNum);
                musicNote = noteInfo.charAt(0);
                oct = noteInfo.charAt(2) - '0';
                noteNum++;
            }
            catch (IOException e){
                System.out.println("Oops in setting value of musicNote in NoteLocation");
                e.printStackTrace();
            }

            switch(musicNote){
                case 'A':
                    break;
                case 'B':
                    break;
                case 'C':
                    break;
                case 'D':
                    break;
                case 'F':
                    break;
                case 'G':
                    break;
                default:
                    System.out.println("NO!");
                    break;
            }
        }
    }

}

