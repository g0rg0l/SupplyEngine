package self.map.routing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;


public class MapRouteFactory {
    public static MapRoute createRoute(GeoPosition from, GeoPosition to, JXMapViewer map) {
        var path = MapPathProvider.INSTANCE.getPath(from, to);
        return new MapRoute(map, path);
    }
}
