package se.pellpin.subclust;

import java.sql.Timestamp;
import java.util.Date;

public class Point {
    public double x;
    public double y;
    public double p;
    Date time;
    float heading;
    float speed;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public String toString() {
        return "[" + x + " , " + y + " , " + p + "]";
    }
}