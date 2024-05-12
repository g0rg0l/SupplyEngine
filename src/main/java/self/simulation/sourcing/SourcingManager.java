package self.simulation.sourcing;

import self.map.routing.RouteManager;
import self.simulation.demand.Order;
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

    public DC getSource(Order order) {
        switch (order.getDestination().getSourcingType()) {
            case CLOSEST -> {
                double minimalDistance = Double.MAX_VALUE;
                DC dcWithMinimalDistance = null;

                for (int i = 0; i < facilityManager.getDcs().size(); i++) {
                    var dc = facilityManager.getDcs().get(i);
                    var route = routeManager.getRouteBetween(order.getDestination(), dc);

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

            case FASTEST -> {
                return null;
            }

            case CHEAPEST -> {
                return null;
            }

            default -> { return null; }
        }
    }

    public void initPath(Order order) {
        if (order.getDestination() != null && order.getSource() != null) {
            order.setRoute(routeManager.getRouteBetween(order.getDestination(), order.getSource()));
        }
    }
}
