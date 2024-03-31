package self.map.waypoints;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.image.BufferedImage;

public class MapWaypoint extends DefaultWaypoint {
    private final BufferedImage image;

    public MapWaypoint(BufferedImage image, GeoPosition position) {
        super(position);

        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
