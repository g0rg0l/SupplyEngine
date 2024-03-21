package self.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import java.util.Set;

public class GISMap extends JXMapViewer {
    private AGISMapMouseAdapter mouseAdapter;
    private CompoundPainter<JXMapViewer> painter;

    public GISMap() {
        setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
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

    /**
     * Метод, инициализирующий карту - отображение локаций с их иконками, создание путей между локациями.
     * Вызывается после инициализации сценария, предполагается, что 1 раз.
     *
     * @param waypoints - множество точек для отображение
     * TODO: Передавать не только 1 список, но столько, сколько разных типов объектов на карте
     */
    public void initMapLocations(Set<Waypoint> waypoints) {
        painter = new CompoundPainter<>();

        /* Waypoints painter */
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        painter.addPainter(waypointPainter);

        setOverlayPainter(painter);
    }
}
