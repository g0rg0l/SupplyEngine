package self.map.routing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.simulation.facilities.Facility;


public class MapRouteFactory {
    public static MapRoute createRoute(Facility from, Facility to, JXMapViewer map) {
        return new MapRoute(
                map,
                MapPathProvider.INSTANCE.getPath(from.getGeoPosition(), to.getGeoPosition()),
                from, to
        );
    }
}
