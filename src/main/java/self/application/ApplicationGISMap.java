package self.application;

import self.map.AGISMap;
import self.map.routing.MapRoute;
import self.map.waypoints.MapWaypoint;


public class ApplicationGISMap extends AGISMap {
    public void addWaypoint(MapWaypoint waypoint) {
        waypointManager.addObject(waypoint);
    }

    public void addRoute(MapRoute route) {
        routeManager.addObject(route);
    }
}
