package self.application;

import self.map.routing.MapRouteFactory;
import self.simulation.facilities.Facility;
import self.simulation.facilities.FacilityFactory;
import self.simulation.facilities.FacilityType;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ApplicationAddController {
    private final Application application;
    private ApplicationGISMap map;
    private FacilityType addingType;
    private boolean isAddingRoute;
    private Facility a = null;
    private Facility b = null;

    public ApplicationAddController(Application application) {
        this.application = application;
    }

    public void selectFacility(FacilityType type) {
        addingType = type;
        cancelAddingRoute();

        if (map != null) {
            BufferedImage facilityIcon = FacilityFactory.getImageByType(type);

            Cursor facilityCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    facilityIcon,
                    new Point(0, 0), type.name()
            );

            map.setCursor(facilityCursor);
            map.repaint();
        }
    }

    public void selectRoute() {
        cancelAddingRoute();
        cancelAddingFacility();
        isAddingRoute = true;
        map.repaint();
    }

    public void cancel() {
        if (addingType != null) {
            cancelAddingFacility();
        }
        if (isAddingRoute) {
            cancelAddingRoute();
        }
        map.repaint();
    }

    private void cancelAddingFacility() {
        addingType = null;
        map.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void cancelAddingRoute() {
        isAddingRoute = false;

        if (a != null) {
            a.selected = false;
            a = null;
        }
        if (b != null) {
            b.selected = false;
            b = null;
        }
    }

    public void addFacility(Point mousePoint) {
        if (addingType != null) {
            Rectangle viewportBounds = map.getViewportBounds();
            Point fixedPoint = new Point(
                    viewportBounds.x + mousePoint.x + 16,
                    viewportBounds.y + mousePoint.y + 32
            );

            var location = map.getTileFactory().pixelToGeo(fixedPoint, map.getZoom());
            Facility facility = FacilityFactory.create(addingType, location, map);
            map.addFacility(facility);

            cancel();
            map.repaint();
        }
    }

    public void addRoute(Point mousePoint) {
        if (isAddingRoute) {
            Rectangle viewportBounds = map.getViewportBounds();
            Point fixedPoint = new Point(
                    viewportBounds.x + mousePoint.x,
                    viewportBounds.y + mousePoint.y
            );
            var rects = map.getFacilityManager().getHitBoxes(map.getZoom());

            if (a == null) {
                for (Rectangle2D rect : rects) {
                    if (rect.contains(fixedPoint)) {
                        a = map.getFacilityManager().getObjects().get(rects.indexOf(rect));
                        a.selected = true;
                        break;
                    }
                }
            }
            else if (b == null) {
                for (Rectangle2D rect : rects) {
                    if (rect.contains(fixedPoint)) {
                        b = map.getFacilityManager().getObjects().get(rects.indexOf(rect));
                        a.selected = false;
                        isAddingRoute = false;

                        map.addRoute(MapRouteFactory.createRoute(a.getGeoPosition(), b.getGeoPosition(), map));
                        break;
                    }
                }
            }
            map.repaint();
        }
    }

    public void setMap(ApplicationGISMap map) {
        this.map = map;
    }
}
