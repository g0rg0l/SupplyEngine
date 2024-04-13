package self.simulation.facilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MapFacilityPainter implements Painter<JXMapViewer> {
    private final FacilityManager waypointManager;

    public MapFacilityPainter(FacilityManager waypointManager) {
        this.waypointManager = waypointManager;
    }

    @Override
    public void paint(Graphics2D g2d, JXMapViewer map, int w, int h) {
        Graphics2D g = (Graphics2D) g2d.create();
        Rectangle2D rect = map.getViewportBounds();
        g.translate(-rect.getX(), -rect.getY());

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        waypointManager.getObjects().forEach(o -> o.draw(g, map));

        g.dispose();
    }
}
