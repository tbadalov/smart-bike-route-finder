package ee.ut.its.shortestpath.path;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.geojson.Point;
import ee.ut.its.shortestpath.Route;
import ee.ut.its.shortestpath.dock.Dock;
import retrofit2.Response;

import java.util.concurrent.Callable;


public class MapboxCallable implements Callable<Route> {

    private Dock origin;
    private Dock destination;
    private String accessKey;

    public MapboxCallable(Dock origin, Dock destination, String accessKey) {
        this.origin = origin;
        this.destination = destination;
        this.accessKey = accessKey;
    }

    @Override
    public Route call() throws Exception {
        MapboxDirections client = MapboxDirections.builder()
                .origin(toPoint(origin))
                .destination(toPoint(destination))
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_CYCLING)
                .accessToken(accessKey)
                .build();
        Response<DirectionsResponse> response = client.executeCall();
        if (response.body() == null || response.body().routes().size() < 1)  return null;

        return new Route(origin, destination, response.body().routes().get(0));
    }

    private Point toPoint(Dock dock) {
        return Point.fromLngLat(dock.getLongitude(), dock.getLatitude());
    }
}
