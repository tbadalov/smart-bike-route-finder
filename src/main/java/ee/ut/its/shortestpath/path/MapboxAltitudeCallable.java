package ee.ut.its.shortestpath.path;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.tilequery.MapboxTilequery;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import ee.ut.its.shortestpath.Route;
import ee.ut.its.shortestpath.altitude.Altitude;
import ee.ut.its.shortestpath.dock.Dock;
import retrofit2.Response;

import java.util.List;
import java.util.concurrent.Callable;


public class MapboxAltitudeCallable implements Callable<Altitude> {

    private Dock origin;
    private String accessKey;

    public MapboxAltitudeCallable(Dock origin, String accessKey) {
        this.origin = origin;
        this.accessKey = accessKey;
    }

    @Override
    public Altitude call() throws Exception {
        MapboxTilequery elevationQuery = MapboxTilequery.builder()
                .accessToken(accessKey)
                .mapIds("mapbox.mapbox-terrain-v2")
                .query(Point.fromLngLat(origin.getLongitude(), origin.getLatitude()))
                .geometry("polygon")
                .layers("contour")
                .limit(10)
                .build();
        Response<FeatureCollection> response = elevationQuery.executeCall();
        if (response.body().features() != null) {
            List<Feature> featureList = response.body().features();
            String elevation = "";
            for (Feature singleFeature : featureList) {
                if (singleFeature.getStringProperty("ele") != null)
                    elevation = singleFeature.getStringProperty("ele");
            }
            return new Altitude(origin.getId(), elevation);
        }
        return null;

    }

    private Point toPoint(Dock dock) {
        return Point.fromLngLat(dock.getLongitude(), dock.getLatitude());
    }
}
