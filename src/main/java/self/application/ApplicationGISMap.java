package self.application;

import self.map.AGISMap;
import self.map.routing.MapRoute;
import self.map.routing.MapRouteFactory;
import self.simulation.facilities.Facility;
import self.simulation.facilities.objects.Customer;
import self.simulation.facilities.objects.DC;
import self.simulation.facilities.objects.Factory;
import self.simulation.facilities.objects.Supplier;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public final class ApplicationGISMap extends AGISMap {
    public ApplicationGISMap() {
        super();
        setupPaintersByManagers();
    }

    public void addFacility(Facility facility) {
        facilityManager.addObject(facility);
        repaint();
    }

    public void addRoute(Facility from, Facility to) {
        var route = MapRouteFactory.createRoute(from, to, this);
        routeManager.addObject(route);
        repaint();
    }
}
