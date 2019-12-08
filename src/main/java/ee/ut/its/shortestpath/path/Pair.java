package ee.ut.its.shortestpath.path;

import ee.ut.its.shortestpath.dock.Dock;

public class Pair {

    Point point1;
    Dock dock;
    double distance;

    public Pair(Point point1, Dock dock, double distance) {
        this.point1 = point1;
        this.dock = dock;
        this.distance = distance;
    }
}
