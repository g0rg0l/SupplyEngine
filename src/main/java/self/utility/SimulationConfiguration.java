package self.utility;

import java.awt.geom.Point2D;

public class SimulationConfiguration {
    public static final SimulationConfiguration INSTANCE = new SimulationConfiguration();
    private SimulationConfiguration() {  }

    private int mapZoomLevel;
    private Point2D mapCenterPoint;

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
