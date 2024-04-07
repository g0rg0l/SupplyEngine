package self.utility;

import self.map.routing.MapRoute;
import self.map.waypoints.MapWaypoint;

import java.awt.geom.Point2D;
import java.util.List;

public class SimulationConfiguration {
    public static final SimulationConfiguration INSTANCE = new SimulationConfiguration();
    private SimulationConfiguration() {  }

    private int mapZoomLevel;
    private Point2D mapCenterPoint;
    private List<MapRoute> routes;
    private List<MapWaypoint> waypoints;

    public List<MapRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<MapRoute> routes) {
        this.routes = routes;
    }

    public List<MapWaypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<MapWaypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public int getMapZoomLevel() {
        return mapZoomLevel;
    }

    public void setMapZoomLevel(int mapZoomLevel) {
        this.mapZoomLevel = mapZoomLevel;
    }

    public Point2D getMapCenterPoint() {
        return mapCenterPoint;
    }

    public void setMapCenterPoint(Point2D mapCenterPoint) {
        this.mapCenterPoint = mapCenterPoint;
    }
}
