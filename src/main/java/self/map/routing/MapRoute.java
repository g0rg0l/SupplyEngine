package self.map.routing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.MapUtilities;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapRoute {
    private final List<GeoPosition> originalPoints;
    private final double originalDistance;
    private final long originalTime;
    private final Map<Integer, List<GeoPosition>> geoPointsDetalizationMap;
    private final Map<Integer, List<Point2D>> points2DDetalizationMap;
    private final Map<Integer, Double> distanceDetalizationMap;


    public MapRoute(JXMapViewer map, Path path) {
        this.originalPoints = path.getGeoPositions();
        this.originalDistance = path.getDistance();
        this.originalTime = path.getTime();
        this.geoPointsDetalizationMap = new HashMap<>();
        this.points2DDetalizationMap = new HashMap<>();
        this.distanceDetalizationMap = new HashMap<>();

        init(map);
    }

    /**
     * Метод, в котором инициализируется рут карты:
     *  в нём просчитываются все детализации для уровней приближения карты, а, соответственно, и
     *  дистанции упрощённых маршрутов (время остаётся неизменным) для того, чтобы потом, в зависимости
     *  от текущего уровня приближения карты, отображать маршрут в нужной детализации.
     */
    private void init(JXMapViewer map) {
        for (int zoom = 0; zoom < 20; zoom++) {

            List<GeoPosition> geoDetalizationList = new ArrayList<>();
            List<Point2D> pointsDetalizationList = new ArrayList<>();
            double distance = 0;
            int gap = MapUtilities.getGapForZoomLevel(zoom);

            geoDetalizationList.add(originalPoints.get(0));
            pointsDetalizationList.add(map.getTileFactory().geoToPixel(originalPoints.get(0), zoom));
            for (int i = 1; i < originalPoints.size() - 1; i += gap) {

                var p1 = geoDetalizationList.get(geoDetalizationList.size() - 1);
                var p2 = originalPoints.get(i);

                double distanceBetweenPoints = MapUtilities.calculateDistanceByHaversine(
                    p1.getLatitude(), p1.getLongitude(),
                    p2.getLatitude(), p2.getLongitude()
                );

                distance += distanceBetweenPoints;
                geoDetalizationList.add(p2);
                pointsDetalizationList.add(map.getTileFactory().geoToPixel(p2, zoom));
            }
            geoDetalizationList.add(originalPoints.get(originalPoints.size() - 1));
            pointsDetalizationList.add(map.getTileFactory().geoToPixel(originalPoints.get(originalPoints.size() - 1), zoom));

            distanceDetalizationMap.put(zoom, distance * 1000);
            geoPointsDetalizationMap.put(zoom, geoDetalizationList);
            points2DDetalizationMap.put(zoom, pointsDetalizationList);
        }
    }

    public List<GeoPosition> getGeoPoints(int zoom) {
        return geoPointsDetalizationMap.get(zoom);
    }

    public List<Point2D> getPoints2D(int zoom) { return points2DDetalizationMap.get(zoom); }

    public List<GeoPosition> getOriginalPoints() {
        return originalPoints;
    }

    public double getOriginalDistance() {
        return originalDistance;
    }

    public long getOriginalTime() {
        return originalTime;
    }
}
