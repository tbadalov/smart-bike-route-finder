package ee.ut.its.shortestpath.path_finder;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class PathController {

    @Autowired
    private PathService pathService;

    @PostMapping(value = "/findRoutes", consumes = "application/json", produces = "application/json")
    public List<DirectionsRoute> findPath(@RequestBody PostRequest req) throws InterruptedException, ExecutionException, IOException {
        List<DirectionsRoute> directionsRoutes = pathService.bestRoutes(req.getSrc(), req.getDest());
        return directionsRoutes;
    }
}
