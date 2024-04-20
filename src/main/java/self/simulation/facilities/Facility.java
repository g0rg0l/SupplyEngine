package self.simulation.facilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.application.ui.ApplicationButton;
import self.map.AGISMap;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Facility {
    private final BufferedImage image;
    private final BufferedImage selectedImage;
    private final GeoPosition geoPosition;
    private final Map<Integer, Point2D> pointsDetalizationMap;
    public boolean selected;

    public Facility(BufferedImage image, BufferedImage selectedImage, GeoPosition geoPosition, AGISMap map) {
        this.image = image;
        this.selectedImage = selectedImage;
        this.geoPosition = geoPosition;
        this.pointsDetalizationMap = new HashMap<>();

        init(map);
    }


    public Facility(Facility that) {
        this.selected = that.selected;
        this.image = that.image;
        this.selectedImage = that.selectedImage;
        this.geoPosition = that.geoPosition;
        this.pointsDetalizationMap = that.pointsDetalizationMap;
    }

    public void draw(Graphics2D g2d, JXMapViewer map) {
        var point = pointsDetalizationMap.get(map.getZoom());
        var img = selected ? selectedImage : image;

        g2d.drawImage(img,
                (int) point.getX() - img.getWidth() / 2,
                (int) point.getY() - img.getHeight(),
                null);
    }

    public Point2D getPosition(int zoom) {
        return pointsDetalizationMap.get(zoom);
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public BufferedImage getImage() {
        return image;
    }

    private void init(AGISMap map) {
        for (int zoom = 0; zoom < 20; zoom++) {
            var point = map.getTileFactory().geoToPixel(geoPosition, zoom);
            pointsDetalizationMap.put(zoom, point);
        }
    }
}
