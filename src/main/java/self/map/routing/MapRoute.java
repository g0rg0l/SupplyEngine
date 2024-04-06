package self.map.routing;

import org.jxmapviewer.viewer.GeoPosition;

import java.util.List;

public class MapRoute {
    private List<GeoPosition> points;
    private double distance;
    private long time;

    public MapRoute(List<GeoPosition> points, double distance, long time) {
        this.points = points;
        this.distance = distance;
        this.time = time;
    }

    public List<GeoPosition> getPoints() {
        return points;
    }

    public void setPoints(List<GeoPosition> points) {
        this.points = points;
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
}
