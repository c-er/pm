package back;
import java.util.ArrayList;

/**l
 * Created by mahesh on 11/8/14.
 */
public class NoteDictionary
{
    static ArrayList<Note> notes;
    public static void populate()
    {
        notes = new ArrayList<Note>();
        for(byte i = 0; i <= 96; i++) {
            // c0 = 16.35159783

            notes.add(new Note(i, 16.35159783 * Math.pow(2, i/12.0)));
            // System.out.println(i + ": " + 16.35159783 * Math.pow(2, i/12.0));
        }

    }

    public static String getNote(double frequency)
    {
        byte name = getName(frequency);
        String ret = "";
        switch(name % 12)
        {
            case 0: ret = "CN"; break;
            case 1: ret = "CS"; break;
            case 2: ret = "DN"; break;
            case 3: ret = "DS"; break;
            case 4: ret = "EN"; break;
            case 5: ret = "FN"; break;
            case 6: ret = "FS"; break;
            case 7: ret = "GN"; break;
            case 8: ret = "GS"; break;
            case 9: ret = "AN"; break;
            case 10: ret = "AS"; break;
            case 11: ret = "BN"; break;
        }
        ret += name/12;
        return ret;
    }

    public static void main(String[] args) {
        NoteDictionary.populate();
        System.out.println(getNote(399));
    }

    public static byte getName(double frequency)
    {
        if(frequency == -1)
        {
            return -1;
        }
        int i = 0;
        for(; i < notes.size(); i++)
        {
            if(notes.get(i).freq >= frequency)
            {
                //System.out.println(notes.get(i).freq);
                break;
            }
        }
        if(i == 0) {
            return notes.get(i).name;
        }
        double diffbelow = Math.abs(notes.get(i - 1).freq - frequency);
        double diffabove = Math.abs(notes.get(i).freq - frequency);

        if(Math.min(diffbelow, diffabove) == diffbelow)
        {
            return notes.get(i - 1).name;
        }
        else
        {
            return notes.get(i).name;
        }


    }

}

class Note implements Comparable<Note>
{
    public byte name;
    public double freq;

    public Note(byte name, double freq)
    {
        this.name = name;
        this.freq = freq;
    }

    public int compareTo(Note n)
    {
        if(this.freq - n.freq < 0)
        {
            return -1;
        }
        else if(this.freq == n.freq)
        {
            return 0;
        }
        return 1;
    }
}