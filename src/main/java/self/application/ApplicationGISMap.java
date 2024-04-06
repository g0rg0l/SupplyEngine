package self.application;

import self.map.AGISMap;
import self.map.routing.MapRoute;
import self.map.routing.MapRoutePainter;
import self.map.waypoints.MapWaypoint;
import self.map.waypoints.MapWaypointPainter;


public class ApplicationGISMap extends AGISMap {

    public ApplicationGISMap() {
        super();

        MapWaypointPainter waypointPainter = new MapWaypointPainter(waypointManager);
        painter.addPainter(waypointPainter);

        MapRoutePainter routePainter = new MapRoutePainter(routeManager);
        painter.addPainter(routePainter);

        setOverlayPainter(painter);
    }

    public void addWaypoint(MapWaypoint waypoint) {
        waypointManager.addObject(waypoint);
    }

    public void addRoute(MapRoute route) {
        routeManager.addObject(route);
    }

    @Override
    public void onZoomUpdated() {
        routeManager.onZoomUpdated(getZoom());
    }
}
