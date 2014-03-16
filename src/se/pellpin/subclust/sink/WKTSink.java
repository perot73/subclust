package se.pellpin.subclust.sink;

import se.pellpin.subclust.Point;

import java.util.ArrayList;

/**
 * Created by seponr on 2014-03-16.
 */
public class WKTSink implements Sink {
    @Override
    public void put(ArrayList<Point> clusterCenters) {
        System.out.print("MULTIPOINT(");
        for(int i=0; i<clusterCenters.size(); i++) {
            if (i>0) {
               System.out.print(",");
            }
            Point p = clusterCenters.get(i);
            System.out.print (p.x + " " + p.y);
        }
        System.out.print(")");
    }
}
