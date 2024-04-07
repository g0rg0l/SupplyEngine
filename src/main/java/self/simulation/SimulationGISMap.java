package self.simulation;

import self.map.AGISMap;
import self.map.routing.MapRoute;
import self.map.waypoints.MapWaypoint;

import java.util.List;

public class SimulationGISMap extends AGISMap {
    public void setWaypoints(List<MapWaypoint> waypoints) {
        waypointManager.setObjects(waypoints);
    }

    public void setRoutes(List<MapRoute> routes) {
        routeManager.setObjects(routes);
    }
}
