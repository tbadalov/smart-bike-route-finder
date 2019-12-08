package ee.ut.its.shortestpath;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import ee.ut.its.shortestpath.dock.Dock;

public class Route {

    private Dock src;
    private Dock dest;
    private DirectionsRoute directionsRoute;
    private Boolean electricOnly;

    public Route(Dock src, Dock dest, DirectionsRoute directionsRoute) {
        this.src = src;
        this.dest = dest;
        this.directionsRoute = directionsRoute;
    }

    public Dock getSrc() {
        return src;
    }

    public Dock getDest() {
        return dest;
    }

    public DirectionsRoute getDirectionsRoute() {
        return directionsRoute;
    }

    public Boolean getElectricOnly() {
        return electricOnly;
    }

    public void setElectricOnly(Boolean electricOnly) {
        this.electricOnly = electricOnly;
    }
}
