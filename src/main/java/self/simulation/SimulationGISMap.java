package self.simulation;

import self.map.AGISMap;
import self.map.routing.RouteManager;
import self.simulation.facilities.FacilityManager;

public final class SimulationGISMap extends AGISMap {
    public SimulationGISMap(FacilityManager facilityManager, RouteManager routeManager) {
        super();

        this.facilityManager = new FacilityManager(facilityManager);
        this.routeManager = new RouteManager(routeManager);

        setupPaintersByManagers();
    }
}
