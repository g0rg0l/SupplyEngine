package self.utility;

import self.map.routing.MapRoute;
import self.simulation.facilities.Facility;

import java.awt.geom.Point2D;
import java.util.List;

public class SimulationConfiguration {
    public static final SimulationConfiguration INSTANCE = new SimulationConfiguration();
    private SimulationConfiguration() {  }

    private int mapZoomLevel;
    private Point2D mapCenterPoint;
    private List<MapRoute> routes;
    private List<Facility> facilities;

    public List<MapRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<MapRoute> routes) {
        this.routes = routes;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
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
