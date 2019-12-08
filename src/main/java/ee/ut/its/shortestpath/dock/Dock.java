package ee.ut.its.shortestpath.dock;

import java.util.Objects;

public class Dock {

    private String id;
    private Double longitude;
    private Double latitude;
    private String name;
    private String address;
    private Integer totalLockedCycleCount;
    private Integer electricBikes;
    private Integer mechanicalBikes;
    private Integer emptySlots;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalLockedCycleCount() {
        return totalLockedCycleCount;
    }

    public void setTotalLockedCycleCount(Integer totalLockedCycleCount) {
        this.totalLockedCycleCount = totalLockedCycleCount;
    }

    public Integer getElectricBikes() {
        return electricBikes;
    }

    public void setElectricBikes(Integer electricBikes) {
        this.electricBikes = electricBikes;
    }

    public Integer getMechanicalBikes() {
        return mechanicalBikes;
    }

    public void setMechanicalBikes(Integer mechanicalBikes) {
        this.mechanicalBikes = mechanicalBikes;
    }

    public Integer getEmptySlots() {
        return emptySlots;
    }

    public void setEmptySlots(Integer emptySlots) {
        this.emptySlots = emptySlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dock dock = (Dock) o;
        return id.equals(dock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
