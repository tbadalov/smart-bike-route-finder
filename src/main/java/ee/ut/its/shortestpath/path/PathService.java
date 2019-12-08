
package ee.ut.its.shortestpath.path;

import ee.ut.its.shortestpath.Route;
import ee.ut.its.shortestpath.dock.Dock;
import ee.ut.its.shortestpath.dock.DockService;
import ee.ut.its.shortestpath.dock.api.DockApi;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class PathService {

    @Value("${mapbox_key}")
    private String accessKey;

    private final DockService dockService = new DockService(new DockApi(new OkHttpClient()));

    private ExecutorService executorService = Executors.newFixedThreadPool(6);

    public List<Route> bestRoutes(Point src, Point dest) throws IOException, ExecutionException, InterruptedException {
        List<Dock> srcDocks = closestDocks(src);
        List<Dock> destDocks = closestDocks(dest);
        srcDocks.removeAll(destDocks);

        findBikeCount(srcDocks);
        findBikeCount(destDocks);

        return findPossibleRoutes(srcDocks, destDocks);
    }

    private void findBikeCount(List<Dock> docks) {
        docks.parallelStream().forEach(dock -> {
            int electricalCount = -1;
            int mechanicalCount = -1;
            try {
                Dock response = dockService.get(dock.getId());
                electricalCount = response.getElectricBikes();
                mechanicalCount= response.getMechanicalBikes();
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            } finally {
                dock.setElectricBikes(electricalCount);
                dock.setMechanicalBikes(mechanicalCount);
            }
        });
    }

    private List<Route> findPossibleRoutes(List<Dock> srcDocks, List<Dock> destDocks) throws ExecutionException, InterruptedException {
        List<Future<Route>> routeCalls = new ArrayList<>();
        for ( Dock srcDock : srcDocks ) {
            for ( Dock destDock : destDocks ) {
                Future<Route> route = executorService.submit(new MapboxCallable(srcDock, destDock, accessKey));
                routeCalls.add(route);
            }
        }
        List<Route> result = new ArrayList<>(routeCalls.size());
        for (Future<Route> routeFuture : routeCalls) {
            result.add(routeFuture.get());
        }

        return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<Dock> closestDocks(Point point) throws IOException {
        ArrayList<Dock> allDocks = dockService.all();
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
