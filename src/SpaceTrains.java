import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by uday on 5/20/15.
 */
public class SpaceTrains {

    ArrayList<Node2> ref;

    public void solve(ArrayList<Node> graph)
    {
        ArrayList<Node> sptSet = new ArrayList<>();
        long siz = graph.size();
        while(sptSet.size() < siz)
        {
            Collections.sort(graph);
            Node cur = graph.get(0);
            sptSet.add(cur);
            graph.remove(cur);
            for(int i = 0; i < cur.goTo.size(); i++)
            {
                cur.goTo.get(i).dest.dist = Math.min(cur.goTo.get(i).dest.dist, cur.dist + cur.goTo.get(i).len);
            }
        }
    }
}

class Node implements Comparable<Node> {
    public int visited;
    public int id;
    public int dist;
    public ArrayList<Train> goTo;

    public int compareTo(Node n)
    {
        return this.dist - n.dist;
    }
}

class Node2 implements Comparable<Node2> {
    public int visited;
    public int id;
    public int dist;
    public ArrayList<Train> goTo;

    public int compareTo(Node2 n)
    {
        return this.id - n.id;
    }
}

class Train {
    public Node dest;
    public int len;
    public int cost;
}