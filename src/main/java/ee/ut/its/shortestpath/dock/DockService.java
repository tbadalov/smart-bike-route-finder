package ee.ut.its.shortestpath.dock;

import ee.ut.its.shortestpath.dock.api.DockApi;
import ee.ut.its.shortestpath.dock.api.DockListRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DockService {

    private final DockApi dockApi;

    public DockService(DockApi dockApi) {
        this.dockApi = dockApi;
    }

    public ArrayList<Dock> all() throws IOException {
        final List<DockListRequest.DockResponse> responses = dockApi.all();
        final ArrayList<Dock> result = new ArrayList<>();
        for (DockListRequest.DockResponse response : responses) {
            result.add(toDock(response));
        }
        return result;
    }

    public Dock get(String id) throws IOException {
        final DockListRequest.DockResponse response = dockApi.get(id);
        return toDockForCount(response);
    }

    private Dock toDock(DockListRequest.DockResponse response) {
        Dock dock = new Dock();
        dock.setName(response.getName());
        dock.setAddress(response.getAddress());
        dock.setLongitude(response.getAreaCentroid().getLongitude());
        dock.setLatitude(response.getAreaCentroid().getLatitude());
        dock.setId(response.getId());
        dock.setTotalLockedCycleCount(response.getTotalLockedCycleCount());
        dock.setEmptySlots(Integer.parseInt(response.getFreeDocksCount()));
        return dock;
    }

    private Dock toDockForCount(DockListRequest.DockResponse response) {
        Dock dock = new Dock();
        dock.setMechanicalBikes(response.getManualCount());
        dock.setElectricBikes(response.getElectricCount());
        return dock;
    }
}
