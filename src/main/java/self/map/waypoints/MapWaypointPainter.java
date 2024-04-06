package self.map.waypoints;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class MapWaypointPainter implements Painter<JXMapViewer> {
    private final MapWaypointManager waypointManager;

    public MapWaypointPainter(MapWaypointManager waypointManager) {
        this.waypointManager = waypointManager;
    }

    @Override
    public void paint(Graphics2D g2d, JXMapViewer map, int w, int h) {
        Graphics2D g = (Graphics2D) g2d.create();
        Rectangle viewportBounds = map.getViewportBounds();
        g.translate(-viewportBounds.getX(), -viewportBounds.getY());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (MapWaypoint waypoint : waypointManager.getObjects()) {
            Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
            BufferedImage img = waypoint.getImage();

            g.drawImage(img,
                    (int) point.getX() - img.getWidth() / 2,
                    (int) point.getY() - img.getHeight(),
                    null);
        }

        g.dispose();
    }
}
