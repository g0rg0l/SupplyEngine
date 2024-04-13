package self.simulation.facilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Facility {
    private final BufferedImage image;
    private final GeoPosition geoPosition;
    private final Map<Integer, Point2D> pointsDetalizationMap;

    public Facility(BufferedImage image, GeoPosition geoPosition, AGISMap map) {
        this.image = image;
        this.geoPosition = geoPosition;
        this.pointsDetalizationMap = new HashMap<>();

        init(map);
    }


    public Facility(Facility that) {
        this.image = that.image;
        this.geoPosition = that.geoPosition;
        this.pointsDetalizationMap = that.pointsDetalizationMap;
    }

    public void draw(Graphics2D g2d, JXMapViewer map) {
        var point = pointsDetalizationMap.get(map.getZoom());
        System.out.println(map.getZoom());

        g2d.drawImage(image,
                (int) point.getX() - image.getWidth() / 2,
                (int) point.getY() - image.getHeight(),
                null);
    }

    private void init(AGISMap map) {
        for (int zoom = 0; zoom < 20; zoom++) {
            var point = map.getTileFactory().geoToPixel(geoPosition, zoom);
            pointsDetalizationMap.put(zoom, point);
        }
    }
}
