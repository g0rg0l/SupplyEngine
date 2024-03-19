package self.map;

import self.application.ApplicationMouseAdapter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import java.awt.*;
import java.util.Set;

public class GISPanel extends JXMapViewer {
    private ApplicationMouseAdapter mouseAdapter;
    private CompoundPainter<JXMapViewer> painter;

    public GISPanel() {
        this.mouseAdapter = new ApplicationMouseAdapter(this);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);

        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.LIGHT_GRAY);
        setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
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
