package self.application;

import org.jxmapviewer.viewer.GeoPosition;
import self.map.AGISMap;
import self.map.waypoints.MapWaypoint;
import self.map.waypoints.MapWaypointFactory;
import self.simulation.facilities.FacilityType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ApplicationAddController {
    private final Application application;
    private ApplicationGISMap map;
    private FacilityType addingType;

    public ApplicationAddController(Application application) {
        this.application = application;
    }

    public void select(FacilityType type) {
        addingType = type;

        if (map != null) {
            BufferedImage facilityIcon = MapWaypointFactory.INSTANCE.getImageByType(type);

            Cursor facilityCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    facilityIcon,
                    new Point(0, 0), type.name()
            );

            map.setCursor(facilityCursor);
        }
    }

    public void cancel() {
        if (addingType != null) {
            addingType = null;
            map.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void add(Point mousePoint) {
        if (addingType != null) {
            Rectangle viewportBounds = map.getViewportBounds();
            Point fixedPoint = new Point(
                    viewportBounds.x + mousePoint.x + 16,
                    viewportBounds.y + mousePoint.y + 32
            );

            GeoPosition position = map.getTileFactory().pixelToGeo(fixedPoint, map.getZoom());

            MapWaypoint newWaypoint = MapWaypointFactory.INSTANCE.create(addingType, position);
            // TODO: Facility newFacility = FacilityFactory.INSTANCE.create(addingType, position);

            map.addWaypoint(newWaypoint);
            // TODO: application.addFacility(newFacility);

            cancel();
        }
    }

    public void setMap(ApplicationGISMap map) {
        this.map = map;
    }
}
