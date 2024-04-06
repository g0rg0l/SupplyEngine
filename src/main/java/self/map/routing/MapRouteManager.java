package self.map.routing;

import self.map.AManager;



public class MapRouteManager extends AManager<MapRoute> {

    public void onZoomUpdated(int zoom) {
        System.out.println("New zoom level: " + zoom);
    }
}