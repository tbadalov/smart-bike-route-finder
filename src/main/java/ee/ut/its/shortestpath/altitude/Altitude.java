package ee.ut.its.shortestpath.altitude;

public class Altitude {
    private String id;
    private String altitude;

    public Altitude(String id, String altitude) {
        this.id = id;
        this.altitude = altitude;
    }

    public Altitude() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "Altitude{" +
                "id='" + id + '\'' +
                ", altitude='" + altitude + '\'' +
                '}';
    }
}
