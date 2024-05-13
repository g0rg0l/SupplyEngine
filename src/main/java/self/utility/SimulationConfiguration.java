package self.utility;

import lombok.Getter;
import lombok.Setter;
import self.map.routing.RouteManager;
import self.simulation.facilities.FacilityManager;
import self.simulation.products.Product;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

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

    @Getter
    private final List<Product> products = new ArrayList<>(List.of(new Product("Product #1", "pcs", "10", "$")));
}
