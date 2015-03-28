package back;

/**
 * Created by mahesh on 3/28/15.
 */
public class Data implements Comparable<Data>
{
    public double magnitude;
    public int index;

    public Data(double mag, int i)
    {
        magnitude = mag;
        index = i;
    }

    public int compareTo(Data data)
    {
        if(data.magnitude > this.magnitude)
        {
            return 1;
        }

        if(this.magnitude > data.magnitude)
        {
            return -1;
        }

        return 0;
    }
}
