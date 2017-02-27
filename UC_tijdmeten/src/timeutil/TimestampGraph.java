package timeutil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.toIntExact;


/**
 * Created by charl on 2/25/2017.
 */
public class TimestampGraph {
    public static void createGraph(String path, String title, int width, int height, TimeStamp ts, List<Integer> samples) {
        List<Pair<Long, String> > results = new ArrayList();
        long maxDuration = 0L;
        for (int i = 0; i < ts.getPeriodTimes().size(); i++) {
            long from = ts.getPeriodTimes().get(i).getLeft();
            long to = ts.getPeriodTimes().get(i).getRight();
            long duration = to - from;
            if (duration > maxDuration) maxDuration = duration; // check for the maximum duration to scale the graph
            int desc = samples.get(i);
            results.add(new Pair(to - from, "" + desc));
        }
        StringBuilder builder = new StringBuilder();
        builder.append("\"<svg\" class=\"chart\" width=\"" + width + "\" height=\"" + height + "\">" );
        int elements = ts.getPeriodTimes().size();
        int margin_left = (int) (width * 0.1);
        int margin_right = margin_left;
        int margin_top = (int) (height * 0.1);
        int margin_bottom = 2 * margin_top;
        int x_step = (int) (((width - (margin_left + margin_right))  / elements));
        long yScale = maxDuration / (height - (margin_bottom + margin_top));
        // Create the axis
        builder.append("<g>");
        builder.append("<polyline points=\"");
        for (int i = 0; i < results.size(); i++) {
            // draw the line
            int x_pos = margin_left + (i * x_step);
            int value = toIntExact(results.get(i).getLeft() / yScale) ;
            builder.append("" + x_pos + " " + value );
            if (i != results.size()) builder.append(",");
        }
        builder.append("\"/>");
        builder.append("</g>");
        builder.append("</svg>");
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            fw.write(builder.toString());
            fw.close();
        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }

    }

}
