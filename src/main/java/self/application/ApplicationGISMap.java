package self.application;

import org.jxmapviewer.viewer.WaypointPainter;
import self.map.AGISMap;
import self.map.waypoints.MapWaypoint;
import self.map.waypoints.MapWaypointRenderer;

import java.util.HashSet;
import java.util.Set;


public class ApplicationGISMap extends AGISMap {
    private WaypointPainter<MapWaypoint> waypointPainter;

    public ApplicationGISMap() {
        super();
        resetWaypointPainter();
        setOverlayPainter(painter);
    }

    public void addWaypoint(MapWaypoint waypoint) {
        Set<MapWaypoint> waypoints = new HashSet<>(waypointPainter.getWaypoints());
        waypoints.add(waypoint);
        resetWaypointPainter(waypoints);
    }

    private void resetWaypointPainter() {
        painter.removePainter(waypointPainter);

        waypointPainter = new WaypointPainter<>();
        waypointPainter.setRenderer(new MapWaypointRenderer());

        painter.addPainter(waypointPainter);
    }

    private void resetWaypointPainter(Set<MapWaypoint> waypoints) {
        painter.removePainter(waypointPainter);

        waypointPainter = new WaypointPainter<>();
        waypointPainter.setRenderer(new MapWaypointRenderer());
        waypointPainter.setWaypoints(waypoints);

        painter.addPainter(waypointPainter);
    }
}
