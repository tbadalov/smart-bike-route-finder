package ee.ut.its.shortestpath.dock.api;

import java.util.ArrayList;
import java.util.List;

public class DockListRequest {

    private Integer total;

    private List<DockResponse> results;

    public List<DockResponse> getResults() {
        return results;
    }

    public static class DockResponse {
        private String stationType;

        private String description;

        private String hasCCReader;

        private String primaryLockedCycleCount;

        private PointResponse areaCentroid;

        private String dockingStationType;

        private String fullCycleStockingCount;

        private String stationStatus;

        private String overFullCycleStockingCount;

        private String id;

        private Integer totalLockedCycleCount;

        private String lastUpdateCycleCountAt;

        private String secondaryLockedCycleCount;

        private String address;

        private String serialNumber;

        private String freeSpacesCount;

        private String[] cyclesInStation;

        private String stationStockingStatus;

        private String lockingStationType;

        private String name;

        private String freeDocksCount;

        private String lowCycleStockingCount;

        public String getAddress() {
            return address;
        }

        public String getName() {
            return name;
        }

        public PointResponse getAreaCentroid() {
            return areaCentroid;
        }

        public String getId() {
            return id;
        }

        public Integer getTotalLockedCycleCount() {
            return totalLockedCycleCount;
        }
    }

    public static class AreaResponse {
        private ArrayList<PointResponse> points;
    }

    public static class PointResponse {
        private Double latitude;

        private Double longitude;

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }
    }

    public static class CircleResponse {

        //private PointResponse pointResponse;

        private Double latitude;

        private Double longitude;

        private Double radius;
    }
}
