package self.map.routing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class MapRoutePainter implements Painter<JXMapViewer> {
    private final MapRouteManager routeManager;

    public MapRoutePainter(MapRouteManager routeManager) {
        this.routeManager = routeManager;
    }

    @Override
    public void paint(Graphics2D g2d, JXMapViewer map, int w, int h) {
        Graphics2D g = (Graphics2D) g2d.create();
        Rectangle2D rect = map.getViewportBounds();
        g.translate(-rect.getX(), -rect.getY());

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));

        routeManager.getObjects().forEach(r -> drawRoute(g, map, r));

        g.dispose();
    }

    private void drawRoute(Graphics2D g2d, JXMapViewer map, MapRoute route) {
        var rectangle = map.getViewportBounds();

        var points2D = route.getPoints2D(map.getZoom());

        g2d.setColor(Color.BLUE);
        for (int i = 1; i < points2D.size(); i++) {
            Line2D line2D = new Line2D.Double(points2D.get(i - 1), points2D.get(i));

            if (line2D.intersects(rectangle))
                g2d.draw(line2D);
        }

        g2d.setColor(Color.RED);
        g2d.draw(rectangle);
    }
}
