package ee.ut.its.shortestpath.route;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import retrofit2.Response;

import java.util.concurrent.Callable;


public class RouteService implements Callable<DirectionsRoute> {

    private Point origin;
    private Point destination;
    private String accessKey;

    public RouteService(Point origin, Point destination, String accessKey) {
        this.origin = origin;
        this.destination = destination;
        this.accessKey = accessKey;
    }

    @Override
    public DirectionsRoute call() throws Exception {
        MapboxDirections client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_CYCLING)
                .accessToken(accessKey)
                .build();
        Response<DirectionsResponse> response = client.executeCall();
        if (response.body() == null || response.body().routes().size() < 1)  return null;

        return response.body().routes().get(0);
    }
}
