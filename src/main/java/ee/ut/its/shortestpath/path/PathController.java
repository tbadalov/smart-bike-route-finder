package ee.ut.its.shortestpath.path;

import ee.ut.its.shortestpath.Route;
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
    public List<Route> findPath(@RequestBody PostRequest req) throws InterruptedException, ExecutionException, IOException {
        List<Route> directionsRoutes = pathService.bestRoutes(req.getSrc(), req.getDest());

        return directionsRoutes;
    }
}
