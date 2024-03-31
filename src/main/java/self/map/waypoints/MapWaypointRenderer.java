package self.map.waypoints;


import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointRenderer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class MapWaypointRenderer implements WaypointRenderer<MapWaypoint> {
    @Override
    public void paintWaypoint(Graphics2D graphics2D, JXMapViewer viewer, MapWaypoint waypoint) {
        graphics2D = (Graphics2D) graphics2D.create();

        Point2D point = viewer.getTileFactory().geoToPixel(waypoint.getPosition(), viewer.getZoom());
        int x = (int) point.getX();
        int y = (int) point.getY();
        BufferedImage img = waypoint.getImage();
        graphics2D.drawImage(
                img,
                x - img.getWidth() / 2,
                y - img.getHeight(),
                null
        );

        graphics2D.dispose();
    }
}
