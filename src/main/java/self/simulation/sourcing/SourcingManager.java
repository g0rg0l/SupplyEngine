package self.simulation.sourcing;

import self.map.MapUtilities;
import self.map.routing.RouteManager;
import self.simulation.demand.Order;
import self.simulation.facilities.FacilityManager;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;

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

    public DC getSource(Order order) {
        switch (order.getDestination().getSourcingType()) {
            case CLOSEST -> {
                var dcsByDistance = facilityManager.getDcs()
                        .stream()
                        .filter(dc -> routeManager.getRouteBetween(order.getDestination(), dc) != null)
                        .sorted((dc1, dc2) -> {
                            double distToDC1 = MapUtilities.calculateDistanceByHaversine(
                                    dc1.getGeoPosition().getLatitude(),
                                    dc1.getGeoPosition().getLongitude(),
                                    order.getDestination().getGeoPosition().getLatitude(),
                                    order.getDestination().getGeoPosition().getLongitude()
                            );
                            double distToDC2 = MapUtilities.calculateDistanceByHaversine(
                                    dc2.getGeoPosition().getLatitude(),
                                    dc2.getGeoPosition().getLongitude(),
                                    order.getDestination().getGeoPosition().getLatitude(),
                                    order.getDestination().getGeoPosition().getLongitude()
                            );

                            return Double.compare(distToDC1, distToDC2);
                        })
                        .toList();

                return dcsByDistance.stream().findFirst().orElse(null);
            }

            case FASTEST -> {
                double minimalTravelTime = Double.MAX_VALUE;
                DC dcWithMinimalTravelTime = null;

                for (int i = 0; i < facilityManager.getDcs().size(); i++) {
                    var dc = facilityManager.getDcs().get(i);
                    var route = routeManager.getRouteBetween(order.getDestination(), dc);

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

    public void initPath(Order order) {
        if (order.getDestination() != null && order.getSource() != null) {
            order.setRoute(routeManager.getRouteBetween(order.getDestination(), order.getSource()));
        }
    }
}
