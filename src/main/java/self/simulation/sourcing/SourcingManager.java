package self.simulation.sourcing;

import self.map.MapUtilities;
import self.map.routing.RouteManager;
import self.simulation.demand.Order;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityManager;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;
import self.simulation.facilities.objects.ISourceFacility;

import java.util.Comparator;

public class SourcingManager {
    public static SourcingManager INSTANCE = new SourcingManager();
    private RouteManager routeManager;
    private FacilityManager facilityManager;

    private SourcingManager() {  }

    public void init(RouteManager routeManager, FacilityManager facilityManager) {
        this.routeManager = routeManager;
        this.facilityManager = facilityManager;
    }

    public ISourceFacility getSource(Order order) {
        if (order.getDestination() instanceof Customer destination) {
            switch (destination.getSourcingType()) {
                case CLOSEST -> {
                    var dcToReplenish = facilityManager.getDcs()
                            .stream()
                            .filter(dc -> routeManager.getRouteBetween(destination, dc) != null)
                            .filter(dc -> dc.getInventories().containsKey(order.getProduct()))
                            .min((dc1, dc2) -> {
                                var distToDc1 = MapUtilities.calculateDistanceByHaversine(dc1.getGeoPosition(), destination.getGeoPosition());
                                var distToDc2 = MapUtilities.calculateDistanceByHaversine(dc2.getGeoPosition(), destination.getGeoPosition());
                                return Double.compare(distToDc1, distToDc2);
                            })
                            .orElse(null);

                    return dcToReplenish;
                }

                case FASTEST -> {
                    double minimalTravelTime = Double.MAX_VALUE;
                    DC dcWithMinimalTravelTime = null;

                    for (int i = 0; i < facilityManager.getDcs().size(); i++) {
                        var dc = facilityManager.getDcs().get(i);
                        var route = routeManager.getRouteBetween(destination, dc);

                        if (route != null) {
                            double travelTime = route.getOriginalTime();
                            if (travelTime < minimalTravelTime) {
                                minimalTravelTime = travelTime;
                                dcWithMinimalTravelTime = dc;
                            }
                        }
                    }

                    return dcWithMinimalTravelTime;
                }

                case CHEAPEST -> {
                    return null;
                }

                default -> { return null; }
            }
        }
        else if (order.getDestination() instanceof DC destination) {
            var supplierToReplenish = facilityManager.getSuppliers()
                    .stream()
                    .filter(s -> routeManager.getRouteBetween(destination, s) != null)
                    .filter(s -> s.getInventories().containsKey(order.getProduct()))
                    .min((s1, s2) -> {
                        var timeToTravelToS1 = routeManager.getRouteBetween(destination, s1).getOriginalTime();
                        var timeToTravelToS2 = routeManager.getRouteBetween(destination, s2).getOriginalTime();

                        return Double.compare(timeToTravelToS1, timeToTravelToS2);
                    })
                    .orElse(null);

            if (supplierToReplenish != null) return supplierToReplenish;

            var DCToReplenish = facilityManager.getDcs()
                    .stream()
                    .filter(s -> routeManager.getRouteBetween(destination, s) != null)
                    .filter(s -> s.getInventories().containsKey(order.getProduct()))
                    .min((s1, s2) -> {
                        var timeToTravelToS1 = routeManager.getRouteBetween(destination, s1).getOriginalTime();
                        var timeToTravelToS2 = routeManager.getRouteBetween(destination, s2).getOriginalTime();

                        return Double.compare(timeToTravelToS1, timeToTravelToS2);
                    })
                    .orElse(null);

            return DCToReplenish;
        }

        return null;
    }

    public void initPath(Order order) {
        if (order.getDestination() != null && order.getSource() != null) {
            order.setRoute(routeManager.getRouteBetween(
                    (Facility) order.getDestination(),
                    (Facility) order.getSource())
            );
        }
    }
}
