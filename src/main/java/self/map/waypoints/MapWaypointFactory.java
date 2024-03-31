package self.map.waypoints;

import org.jxmapviewer.viewer.GeoPosition;
import self.simulation.facilities.FacilityType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapWaypointFactory {
    public static MapWaypointFactory INSTANCE = new MapWaypointFactory();
    private final Map<FacilityType, BufferedImage> imageMap;

    private MapWaypointFactory() {
        imageMap = new HashMap<>();

        try {
            imageMap.put(FacilityType.CUSTOMER, ImageIO.read(Objects.requireNonNull(getClass().getResource("/waypoints/customer_waypoint.png"))));
            imageMap.put(FacilityType.DC, ImageIO.read(Objects.requireNonNull(getClass().getResource("/waypoints/dc_waypoint.png"))));
            imageMap.put(FacilityType.FACTORY, ImageIO.read(Objects.requireNonNull(getClass().getResource("/waypoints/factory_waypoint.png"))));
            imageMap.put(FacilityType.SUPPLIER, ImageIO.read(Objects.requireNonNull(getClass().getResource("/waypoints/supplier_waypoint.png"))));
        }
        catch (Exception exception) {
            System.out.println("Error while loading resources: can not load waypoint icons.");
            System.exit(-1);
        }
    }

    public MapWaypoint create(FacilityType type, GeoPosition position) {
        BufferedImage image = imageMap.get(type);

        return new MapWaypoint(image, position);
    }

    public BufferedImage getImageByType(FacilityType type) {
        return imageMap.get(type);
    }
}
