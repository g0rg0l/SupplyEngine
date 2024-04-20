package self.map.routing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;

import java.awt.*;
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
        g.setStroke(new BasicStroke(3));

        var routes = routeManager.getObjects();
        for (int i = 0; i < routes.size(); i++) {
            var route = routes.get(i);
            var movables = route.getMovables();

            route.draw(g, map);
            for (int j = 0; j < movables.size(); j++) {
                movables.get(j).draw(g);
            }
        }

        g.dispose();
    }
}
