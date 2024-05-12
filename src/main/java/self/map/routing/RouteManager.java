package self.map.routing;

import self.map.AManager;
import self.simulation.facilities.Facility;

import java.util.HashMap;
import java.util.Map;


public class RouteManager extends AManager<MapRoute> {
    private final Map<Integer, Map<Integer, MapRoute>> facilityIDtoRouteBetween;

    public RouteManager() {
        super();
        this.facilityIDtoRouteBetween = new HashMap<>();
    }

    public RouteManager(RouteManager that) {
        this.facilityIDtoRouteBetween = new HashMap<>();
        that.objects.forEach(o -> addObject(new MapRoute(o)));
    }

    public void onZoomUpdated(int zoom) {
        objects.forEach(r -> r.setZoom(zoom));
    }

    @Override
    public void addObject(MapRoute object) {
        super.addObject(object);

        facilityIDtoRouteBetween.computeIfAbsent(object.fromID, o -> new HashMap<>()).put(object.toID, object);
        facilityIDtoRouteBetween.computeIfAbsent(object.toID, o -> new HashMap<>()).put(object.fromID, object);
    }

    public MapRoute getRouteBetween(Facility a, Facility b) {
        var connectedWithA = facilityIDtoRouteBetween.get(a.id);
        return connectedWithA != null ? connectedWithA.get(b.id) : null;
    }
}