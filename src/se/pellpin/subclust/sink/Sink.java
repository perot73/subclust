package se.pellpin.subclust.sink;

import se.pellpin.subclust.Point;

import java.util.ArrayList;

public interface Sink {
    public void put(ArrayList<Point> clusterCenters);
}
