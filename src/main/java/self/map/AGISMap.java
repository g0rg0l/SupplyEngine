package self.map;

import lombok.Getter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import self.map.routing.RouteManager;
import self.map.routing.MapRoutePainter;
import self.simulation.facilities.FacilityManager;
import self.simulation.facilities.MapFacilityPainter;

public abstract class AGISMap extends JXMapViewer {
    public static final int MAX_ZOOM_LEVEL = 18;
    public static final long MIN_TIME_BETWEEN_ZOOMS_NANOS = 150_000_000;
    protected long lastZoomTime = 0;
    public boolean isInitialized = false;
    protected AGISMapMouseAdapter mouseAdapter;
    protected CompoundPainter<JXMapViewer> painter;

    @Getter
    protected RouteManager routeManager;

    @Getter
    protected FacilityManager facilityManager;


    public AGISMap() {
        super();

        setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));

        this.painter = new CompoundPainter<>();
        this.routeManager = new RouteManager();
        this.facilityManager = new FacilityManager();
    }

    protected void setupPaintersByManagers() {
        MapRoutePainter routePainter = new MapRoutePainter(routeManager);
        painter.addPainter(routePainter);

        MapFacilityPainter facilityPainter = new MapFacilityPainter(facilityManager);
        painter.addPainter(facilityPainter);

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


    @Override
    public void setZoom(int zoom) {
        if (zoom < MAX_ZOOM_LEVEL && System.nanoTime() - lastZoomTime >= MIN_TIME_BETWEEN_ZOOMS_NANOS || !isInitialized) {
            super.setZoom(zoom);
            lastZoomTime = System.nanoTime();
        }
    }
}
