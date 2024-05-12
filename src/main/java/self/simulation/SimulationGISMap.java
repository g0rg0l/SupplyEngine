package self.simulation;

import self.map.AGISMap;
import self.map.routing.RouteManager;
import self.simulation.facilities.FacilityManager;

public final class SimulationGISMap extends AGISMap {
    /**
     * Когда мы создаём симуляцию, мы хотим скопировать все объекты из Application'а
     * и передать их в SimulationGisMap посредством копирования менеджеров.
     * Копирование менеджеров создаёт копии агрегируемых ими объектов.
     */
    public SimulationGISMap(FacilityManager facilityManager, RouteManager routeManager) {
        super();

        this.facilityManager = new FacilityManager(facilityManager);
        this.routeManager = new RouteManager(routeManager);

        setupPaintersByManagers();
    }
}
