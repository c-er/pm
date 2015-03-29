import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.JScoreComponent;

import javax.swing.*;
public class Test {
    public static void main (String[] arg) {
        String tuneAsString = "X:2\nT:A simple scale exercise\nL:1/8\nK:C\nM:4/4\n^C2DEF|GABc|defg|gfed|cBAG|FEDC\n";
        Tune tune = new TuneParser().parse(tuneAsString);
        JScoreComponent scoreUI =new JScoreComponent();
        scoreUI.setTune(tune);
        JFrame j = new JFrame();
        j.add(scoreUI);
        j.pack();
        j.setVisible(true);
    }
}
