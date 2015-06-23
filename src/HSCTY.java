import org.apache.commons.math3.transform.DctNormalization;
import org.apache.commons.math3.transform.FastCosineTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.StringTokenizer;

/**
 * Created by uday on 5/19/15.
 */
public class HSCTY {
    public static void main(String[] args) {
        String data = "1653.0 0.13916760914832338 58.72838425104149 -29.800988375644806 -64.39696961966996 77.24677622083472 29.83855931789015 -62.70175235211251 42.999999999999986 65.1382409674247 -21.49541356738255 -67.39554551772046 54.396969619669974 40.92032042100787 -39.07153000154913 -15.546218972937488 65.0";
        StringTokenizer st = new StringTokenizer(data);
        int count = 0;
        System.out.println("got here1");
        while(st.hasMoreTokens())
        {
            count++;
            st.nextToken();
        }
        System.out.println("count: " + count);
        st = new StringTokenizer(data);
        double d[] = new double[count];
        int b = 0;
        System.out.println("got here1");
        while(st.hasMoreTokens())
        {
            d[b++] = Double.parseDouble(st.nextToken());
        }

        System.out.println("before");


        /*double maxval = -10000;
        for(int i = 0; i < d.length; i++)
        {
            maxval = Math.max(d[i], maxval);
        }
        for(int i = 0; i < d.length; i++)
        {
            d[i]/=maxval;
        }*/

        /*double d2[] = new double[d.length * 2 - 1];
        int j = 0;
        for(int i = d.length - 1; i >= 0; i--)
        {
            d2[j++] = d[i];
        }
        for(int i = 1; i < d.length; i++)
        {
            d2[j + i - 1] = d[i];
        }

        for(int i = 0; i < d2.length; i++)
        {
            System.out.println(d2[i]);
        }*/


        d = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I).transform(d, TransformType.INVERSE);
        //a.inverse(d, false);

        System.out.println("after");
        for(int i = 0; i < d.length; i++)
        {
            System.out.println(d[i]);
        }

        for(int i = 0; i < d.length; i++)
        {
            System.out.print((char) (d[i] + 0.5 + 0));
        }

    }
}
