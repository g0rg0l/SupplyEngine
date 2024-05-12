package self.simulation.sourcing;

import self.map.routing.RouteManager;
import self.simulation.facilities.FacilityManager;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;

public class SourcingManager {
    public static SourcingManager INSTANCE = new SourcingManager();
    private RouteManager routeManager;
    private FacilityManager facilityManager;

    private SourcingManager() {  }

    public void init(RouteManager routeManager, FacilityManager facilityManager) {
        this.routeManager = routeManager;
        this.facilityManager = facilityManager;
    }

    public DC getSource(Customer destination) {
        if (destination.getSourcingType() == SourcingType.CLOSEST) {
            double minimalDistance = Double.MAX_VALUE;
            DC dcWithMinimalDistance = null;

            for (int i = 0; i < facilityManager.getDcs().size(); i++) {
                var dc = facilityManager.getDcs().get(i);
                var route = routeManager.getRouteBetween(destination, dc);

                if (route != null) {
                    double dist = route.getOriginalDistance();
                    if (dist < minimalDistance) {
                        minimalDistance = dist;
                        dcWithMinimalDistance = dc;
                    }
                }
            }

            return dcWithMinimalDistance;
        }

        return null;
    }
}
