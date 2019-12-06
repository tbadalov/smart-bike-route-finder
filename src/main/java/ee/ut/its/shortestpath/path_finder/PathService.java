
package ee.ut.its.shortestpath.path_finder;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import ee.ut.its.shortestpath.dock.Dock;
import ee.ut.its.shortestpath.dock.DockService;
import ee.ut.its.shortestpath.dock.api.DockApi;
import ee.ut.its.shortestpath.route.RouteService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class PathService {

    @Value("${mapbox_key}")
    private String accessKey;

    private ExecutorService executorService = Executors.newFixedThreadPool(6);

    public List<DirectionsRoute> bestRoutes(Point src, Point dest) throws IOException, ExecutionException, InterruptedException {
        List<Dock> srcDocks = closestDocks(src);
        List<Dock> destDocks = closestDocks(dest);

        srcDocks.removeAll(destDocks);
        List<Future<DirectionsRoute>> routeCalls = new ArrayList<>();
        for ( Dock srcDock : srcDocks ) {
            for ( Dock destDock : destDocks ) {
                Future<DirectionsRoute> route = executorService.submit(
                        new RouteService(
                                com.mapbox.geojson.Point.fromLngLat(srcDock.getLongitude(), srcDock.getLatitude()),
                                com.mapbox.geojson.Point.fromLngLat(destDock.getLongitude(), destDock.getLatitude()),
                                accessKey
                        )
                );
                routeCalls.add(route);
            }
        }
        List<DirectionsRoute> result = new ArrayList<>(routeCalls.size());
        for (Future<DirectionsRoute> routeFuture : routeCalls) {
            result.add(routeFuture.get());
        }
        result.sort(Comparator.comparingDouble(DirectionsRoute::distance));
        return result;
    }

    private List<Dock> closestDocks(Point point) throws IOException {
        ArrayList<Dock> allDocks = new DockService(new DockApi(new OkHttpClient())).all();
        List<Pair> collect = allDocks.stream()
                .map(dock -> new Pair(point, dock, distance(point, dock)))
                .sorted(Comparator.comparingDouble(pair -> pair.distance))
                .collect(Collectors.toList());
        List<Dock> result = new ArrayList<>();

        int checkedUntil = 0;
        for (int i = 0; i < collect.size() && result.size() != 3 && checkedUntil != allDocks.size(); i++) {
            Dock dock = collect.get(i).dock;
            if (dock.getTotalLockedCycleCount() != 0) result.add(dock);
        }

        return result;
    }

    private double distance(Point p1, Dock dock) {
        double latitude = p1.getLat();
        double longitude = p1.getLng();
        double e = dock.getLatitude();
        double f = dock.getLongitude();
        double d2r = Math.PI / 180;

        double dlong = (longitude - f) * d2r;
        double dlat = (latitude - e) * d2r;
        double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(e * d2r)
                * Math.cos(latitude * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6367 * c;
        return d;

    }
}
