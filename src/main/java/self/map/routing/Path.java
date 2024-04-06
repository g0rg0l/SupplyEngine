package self.map.routing;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.List;

public class Path {
    private double distance;
    private long time;
    private List<GeoPosition> geoPositions;

    public Path(double distance, long time, List<GeoPosition> geoPositions) {
        this.distance = distance;
        this.time = time;
        this.geoPositions = geoPositions;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<GeoPosition> getGeoPositions() {
        return geoPositions;
    }

    public void setGeoPositions(List<GeoPosition> geoPositions) {
        this.geoPositions = geoPositions;
    }
}
