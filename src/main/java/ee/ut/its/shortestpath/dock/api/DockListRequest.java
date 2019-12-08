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

        private DockResponse station;

        private String popup;

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

        public DockResponse getStation() {
            return station;
        }

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

        public Integer getManualCount() {
            final String KEYWORD = "<span class=\"station-bikes\"><b>";
            String popup = getPopup();
            int wordPosition = popup.indexOf(KEYWORD);
            return getCountAfterKeyword(wordPosition, KEYWORD);
        }

        public Integer getElectricCount() {
            final String KEYWORD = "<span class=\"station-bikes\"><b>";
            String popup = getPopup();
            int wordPosition = popup.indexOf(KEYWORD);
            wordPosition = popup.indexOf(KEYWORD, wordPosition +KEYWORD.length());
            return getCountAfterKeyword(wordPosition, KEYWORD);
        }

        public String getFreeDocksCount() {
            return freeDocksCount;
        }

        private Integer getCountAfterKeyword(int start, String keyword) {
            int endIndex = popup.indexOf("</b>", start + keyword.length());
            String count = popup.substring(start + keyword.length(), endIndex);
            return Integer.parseInt(count);
        }

        private String getPopup() {
            return popup;
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
