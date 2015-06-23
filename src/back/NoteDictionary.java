package back;
import java.util.ArrayList;

/**l
 * Created by mahesh on 11/8/14.
 */
public class NoteDictionary
{
    static ArrayList<Note> notes = populate();
    private static ArrayList<Note> populate()
    {
        ArrayList<Note> ret = new ArrayList<>();
        for(byte i = 0; i <= 96; i++) {
            // c0 = 16.35159783

            ret.add(new Note(i, 16.35159783 * Math.pow(2, i/12.0)));
            // System.out.println(i + ": " + 16.35159783 * Math.pow(2, i/12.0));
        }
        return ret;
    }

    public static String getNote(double frequency)
    {
        byte name = getName(frequency);
        String ret = "";
        switch(name % 12)
        {
            case 0: ret = "=C"; break;
            case 1: ret = "^C"; break;
            case 2: ret = "=D"; break;
            case 3: ret = "^D"; break;
            case 4: ret = "=E"; break;
            case 5: ret = "=F"; break;
            case 6: ret = "^F"; break;
            case 7: ret = "=G"; break;
            case 8: ret = "^G"; break;
            case 9: ret = "=A"; break;
            case 10: ret = "^A"; break;
            case 11: ret = "=B"; break;
        }
        ret += name/12;
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(getClosest(555));
    }

    public static double getClosest(double freq)
    {
        for(int i = 0; i < notes.size(); i++)
        {
            if(notes.get(i).freq > freq)
            {
                if(i == 0)
                {
                    return notes.get(i).freq;
                }
                else
                {
                    double diffabove = Math.abs(notes.get(i).freq - freq);
                    double diffbelow = Math.abs(notes.get(i - 1).freq - freq);
                    if(Math.min(diffabove, diffbelow) == diffbelow)
                    {
                        return notes.get(i - 1).freq;
                    }
                    else
                    {
                        return notes.get(i).freq;
                    }
                }
            }
        }
        return notes.get(notes.size() - 1).freq;
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