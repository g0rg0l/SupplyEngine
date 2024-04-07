package self.map.routing;

import self.map.AManager;



public class MapRouteManager extends AManager<MapRoute> {

    public void onZoomUpdated(int zoom) {
        objects.forEach(r -> r.setZoom(zoom));
    }
}