package self.map.routing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;

import java.awt.*;
import java.awt.geom.Point2D;

public class MapRoutePainter implements Painter<JXMapViewer> {
    private final MapRouteManager routeManager;

    public MapRoutePainter(MapRouteManager routeManager) {
        this.routeManager = routeManager;
    }

    @Override
    public void paint(Graphics2D g2d, JXMapViewer map, int w, int h) {
        Rectangle rect = map.getViewportBounds();

        Graphics2D g = (Graphics2D) g2d.create();
        g.translate(-rect.x, -rect.y);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));

        routeManager.getObjects().forEach(route -> drawRoute(route, g, map));

        g.dispose();
    }

    private void drawRoute(MapRoute route, Graphics2D g, JXMapViewer map) {
        if (route.getPoints().size() < 2) return;
        var tileFactory = map.getTileFactory();
        var zoom = map.getZoom();
        var points = route.getPoints();

        var first = tileFactory.geoToPixel(points.get(0), zoom);
        var last = tileFactory.geoToPixel(points.get(points.size() - 1), zoom);

        int lastX = (int) first.getX();
        int lastY = (int) first.getY();

        for (int i = 1; i < points.size() - 1; i++) {
            Point2D pt = tileFactory.geoToPixel(points.get(i), zoom);
            int x = (int) pt.getX();
            int y = (int) pt.getY();

            g.drawLine(lastX, lastY, x, y);

            lastX = x; lastY = y;
        }

        g.drawLine(lastX, lastY, (int) last.getX(), (int) last.getY());
    }
}
