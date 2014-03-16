package se.pellpin.subclust.source;

import se.pellpin.subclust.Point;

import java.util.ArrayList;

public class SimpleSource implements Source {

        public ArrayList<Point> get() {

            ArrayList<Point> points = new ArrayList<Point>();

            points.add(new Point(1000, 1000));
            points.add(new Point(998, 998));

            points.add(new Point(2021, 2021));
            points.add(new Point(2022, 2022));
            points.add(new Point(2023, 2023));

            points.add(new Point(2060, 2060));

            return points;
        }
}
