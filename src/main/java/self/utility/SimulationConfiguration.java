package self.utility;

import lombok.Getter;
import lombok.Setter;
import self.map.routing.RouteManager;
import self.simulation.facilities.FacilityManager;

import java.awt.geom.Point2D;

public class SimulationConfiguration {
    public static final SimulationConfiguration INSTANCE = new SimulationConfiguration();
    private SimulationConfiguration() {  }

    @Getter
    @Setter
    private int mapZoomLevel;

    @Getter
    @Setter
    private Point2D mapCenterPoint;

    @Getter
    @Setter
    private FacilityManager facilityManager;

    @Getter
    @Setter
    private RouteManager routeManager;

    @Getter
    @Setter
    private double vehicleSpeed = 40;
}
