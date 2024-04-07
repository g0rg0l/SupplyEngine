package self.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import self.map.routing.MapRouteManager;
import self.map.routing.MapRoutePainter;
import self.map.waypoints.MapWaypointManager;
import self.map.waypoints.MapWaypointPainter;

public abstract class AGISMap extends JXMapViewer {
    protected AGISMapMouseAdapter mouseAdapter;
    protected final CompoundPainter<JXMapViewer> painter;
    protected final MapRouteManager routeManager;
    protected final MapWaypointManager waypointManager;

    public AGISMap() {
        setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
        this.painter = new CompoundPainter<>();
        this.routeManager = new MapRouteManager();
        this.waypointManager = new MapWaypointManager();

        MapWaypointPainter waypointPainter = new MapWaypointPainter(waypointManager);
        painter.addPainter(waypointPainter);

        MapRoutePainter routePainter = new MapRoutePainter(routeManager);
        painter.addPainter(routePainter);

        setOverlayPainter(painter);
    }

    public void setMouseAdapter(AGISMapMouseAdapter mouseAdapter) {
        if (this.mouseAdapter != null) {
            removeMouseListener(this.mouseAdapter);
            removeMouseMotionListener(this.mouseAdapter);
            removeMouseWheelListener(this.mouseAdapter);
        }

        this.mouseAdapter = mouseAdapter;
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    public void onZoomUpdated() {
        routeManager.onZoomUpdated(getZoom());
    }

    public MapRouteManager getRouteManager() {
        return routeManager;
    }

    public MapWaypointManager getWaypointManager() {
        return waypointManager;
    }
}
