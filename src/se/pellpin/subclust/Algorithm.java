package se.pellpin.subclust;

import se.pellpin.subclust.sink.Sink;
import se.pellpin.subclust.sink.WKTSink;
import se.pellpin.subclust.source.GPXSource;
import se.pellpin.subclust.source.Source;

import java.util.ArrayList;

public class Algorithm {

    /** */
    public static double RADIUS_A = 0.001;
    public static double RADIUS_B = 1.5d * RADIUS_A;

    public static double ALPHA = 4.0d / Math.pow(RADIUS_A, 2);
    public static double BETA = 4.0d / Math.pow(RADIUS_B, 2);

    public static double ACCEPT_RATIO = 0.98d;
    public static double REJECT_RATIO = 0.16d;


    public void process(Source source, Sink sink) {
        ArrayList<Point> points = source.get();

        ArrayList<Point> clusterCenters = new ArrayList<Point>();
        Point p1 = null;
        calculatePotentials(points);
        Statistics stats = Statistics.getStatistics(points);
        p1 = maxPotential(points);
        normalizePotential(points, p1.p);
        Point pc = p1;
        while (true) {

            if(pc.p >= ACCEPT_RATIO) {
                clusterCenters.add(pc);
                updatePotential(points, pc);
            } else if (pc.p <= REJECT_RATIO){
                break;
            } else {
                double dMin = minDistance(clusterCenters, pc);
                double a = (dMin / RADIUS_A);
                double b = (pc.p / stats.pMax);
                if( ( a + b ) >= 1) {
                    clusterCenters.add(pc);
                    updatePotential(points, pc);
                } else {
                    pc.p = 0;
                }
            }
            pc = maxPotential(points);
        }

        sink.put(clusterCenters);
    }

    public void calculatePotentials(ArrayList<Point> points) {
        for (Point pi : points) {
            for (Point pj : points) {
                pi.p += Math.exp(-ALPHA * (Math.pow(pi.x - pj.x, 2) + Math.pow(pi.y - pj.x, 2)));
            }
        }
    }

    public void updatePotential(ArrayList<Point> points, Point pc) {
        for (Point p : points) {
            p.p -= pc.p * Math.exp( -BETA * (Math.pow(p.x - pc.x, 2 ) + Math.pow(p.y - pc.y, 2 ) ) );
        }
    }

    public void normalizePotential(ArrayList<Point> points, double refVal) {
        for (Point point : points) {
            point.p = point.p / refVal;
        }
    }

    public Point maxPotential(ArrayList<Point> points) {
        Point maxPoint = points.get(0);
        for (Point currPoint : points) {
            if(currPoint.p > maxPoint.p)
                maxPoint = currPoint;
        }
        return maxPoint;
    }
    
    public double minDistance(ArrayList<Point> points, Point ref) {
        double minDist = Double.MAX_VALUE;
        double dist;
        for (Point p : points) {
            dist = Math.sqrt(Math.pow(ref.x - p.x,2) + Math.pow(ref.y - p.y, 2));
            if(dist<minDist) {
                minDist = dist;
            }
        }
        return  minDist;
    }

    public static void main(String[] args) {
        Algorithm a = new Algorithm();

        Source source = new GPXSource("/Users/seponr/Downloads/tracks.gpx");
        Sink sink = new WKTSink();

        a.process(source, sink);
    }

}
