package se.pellpin.subclust;

import java.util.ArrayList;

public class Statistics {
    double xMin = Double.MAX_VALUE;
    double xMax = -Double.MAX_VALUE;
    double xAvg = 0;
    double yMin = Double.MAX_VALUE;
    double yMax = -Double.MAX_VALUE;
    double yAvg = 0;
    double pMin = Double.MAX_VALUE;
    double pMax = -Double.MAX_VALUE;
    double pAvg = 0;


    public static Statistics getStatistics(ArrayList<Point> points) {
        Statistics stats = new Statistics();

        for (Point p : points) {

            if(p.x > stats.xMax)
                stats.xMax = p.x;
            if(p.y > stats.yMax)
                stats.yMax = p.y;
            if(p.p > stats.pMax)
                stats.pMax = p.p;

            if(p.x < stats.xMin)
                stats.xMin = p.x;
            if(p.y < stats.yMin)
                stats.yMin = p.y;
            if(p.p < stats.pMin)
                stats.pMin = p.p;

            stats.xAvg += p.x;
            stats.yAvg += p.y;
            stats.pAvg += p.p;
        }

        stats.xAvg = stats.xAvg / points.size();
        stats.yAvg = stats.yAvg / points.size();
        stats.pAvg = stats.pAvg / points.size();

        return stats;
    }
}
