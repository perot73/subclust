package se.pellpin.subclust.sink;

import se.pellpin.subclust.Point;

import java.util.ArrayList;

/**
 * Created by seponr on 2014-03-16.
 */
public class SimpleSink implements Sink{

    public void put(ArrayList<Point> clusterCenters) {
        System.out.println("Points sent to sink");
        for (Point p : clusterCenters) {
            System.out.println(p);
        }
    }

}
