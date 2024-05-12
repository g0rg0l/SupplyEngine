package self.map.routing;

import lombok.Getter;
import lombok.Setter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.MapUtilities;
import self.simulation.RouteMovable;
import self.simulation.facilities.Facility;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class MapRoute {
    public final int fromID;
    public final int toID;

    @Getter
    private final double originalDistance;

    @Getter
    @Setter
    private long originalTime;

    @Getter
    private final List<RouteMovable> movables;

    private final List<GeoPosition> originalPoints;
    private final Map<Integer, List<GeoPosition>> geoPointsDetalizationMap;
    private final Map<Integer, List<Point2D>> points2DDetalizationMap;
    private final Map<Integer, Double> distanceInMetersDetalizationMap;
    private final Map<Integer, Double> distanceInPixelsDetalizationMap;
    private int zoom;


    public MapRoute(JXMapViewer map, Path path, Facility from, Facility to) {
        this.originalPoints = path.getGeoPositions();
        this.originalDistance = path.getDistance();
        this.originalTime = path.getTime();
        this.geoPointsDetalizationMap = new HashMap<>();
        this.points2DDetalizationMap = new HashMap<>();
        this.distanceInMetersDetalizationMap = new HashMap<>();
        this.distanceInPixelsDetalizationMap = new HashMap<>();
        this.movables = new ArrayList<>();
        this.zoom = map.getZoom();
        this.fromID = from.id;
        this.toID = to.id;

        init(map);
    }

    public MapRoute(MapRoute that) {
        this.originalPoints = that.originalPoints;
        this.originalDistance = that.originalDistance;
        this.originalTime = that.originalTime;
        this.geoPointsDetalizationMap = that.geoPointsDetalizationMap;
        this.points2DDetalizationMap = that.points2DDetalizationMap;
        this.distanceInMetersDetalizationMap = that.distanceInMetersDetalizationMap;
        this.distanceInPixelsDetalizationMap = that.distanceInPixelsDetalizationMap;
        this.movables = new ArrayList<>();
        this.zoom = that.zoom;
        this.fromID = that.fromID;
        this.toID = that.toID;
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
            double geoDistance = 0;
            double pixelDistance = 0;
            int gap = MapUtilities.getGapForZoomLevel(zoom);

            geoDetalizationList.add(originalPoints.get(0));
            pointsDetalizationList.add(map.getTileFactory().geoToPixel(originalPoints.get(0), zoom));
            for (int i = 1; i < originalPoints.size() - 1; i += gap) {

                var p1Geo = geoDetalizationList.get(geoDetalizationList.size() - 1);
                var p2Geo = originalPoints.get(i);

                double distanceBetweenPoints = MapUtilities.calculateDistanceByHaversine(
                    p1Geo.getLatitude(), p1Geo.getLongitude(),
                    p2Geo.getLatitude(), p2Geo.getLongitude()
                );

                geoDistance += distanceBetweenPoints;
                geoDetalizationList.add(p2Geo);
                pointsDetalizationList.add(map.getTileFactory().geoToPixel(p2Geo, zoom));

                var p1_2D = pointsDetalizationList.get(pointsDetalizationList.size() - 2);
                var p2_2D = pointsDetalizationList.get(pointsDetalizationList.size() - 1);
                pixelDistance += p1_2D.distance(p2_2D);
            }
            geoDetalizationList.add(originalPoints.get(originalPoints.size() - 1));
            pointsDetalizationList.add(map.getTileFactory().geoToPixel(originalPoints.get(originalPoints.size() - 1), zoom));

            distanceInMetersDetalizationMap.put(zoom, geoDistance);
            distanceInPixelsDetalizationMap.put(zoom, pixelDistance);
            geoPointsDetalizationMap.put(zoom, geoDetalizationList);
            points2DDetalizationMap.put(zoom, pointsDetalizationList);
        }
    }

    /**
     * Метод, который принимает какой-то IMovable объект для его дальнейшего движения.
     * Тут объект выставляется на координаты начала движения и ему задаётся скорость в текущем приближении.
     *
     * @param movable - Принимаемый для движения объект.
     */
    public void move(RouteMovable movable, boolean isStraight) {
        var targetToNext = isStraight
                ? points2DDetalizationMap.get(zoom).listIterator(0)
                : points2DDetalizationMap.get(zoom).listIterator(points2DDetalizationMap.get(zoom).size());
        var speedInPixelsInSec = distanceInPixelsDetalizationMap.get(zoom) / originalTime;

        movable.setup(this, targetToNext, speedInPixelsInSec, isStraight);
        movables.add(movable);
    }

    public void update(float dt) {
        var finished = new ArrayList<RouteMovable>();

        for (int i = 0; i < movables.size(); i++) {
            var movable = movables.get(i);

            movable.update(dt);
            if (movable.isFinished()) {
                finished.add(movable);
            }
        }
        movables.removeAll(finished);
    }

    /**
     * Метод, вызываемый, когда детализация карты, на которой расположен рут, была обновлена.
     * Здесь необходимо рассчитать новые координаты всех объектов, которые едут по руту,
     * задать им новый таргет и скорость.
     *
     * @param zoom Новый уровень детализации карты
     */
    public void setZoom(int zoom) {
        for (int i = 0; i < movables.size(); i++) {
            movables.get(i).recalculatePositionOnNewZoom(zoom);
        }
        this.zoom = zoom;
    }

    public void draw(Graphics2D g2d, JXMapViewer map) {
        var rectangle = map.getViewportBounds();
        var points2D = getPoints2D(map.getZoom());

        g2d.setColor(new Color(100,100,100));

        for (int i = 1; i < points2D.size(); i++) {
            Line2D line2D = new Line2D.Double(points2D.get(i - 1), points2D.get(i));

            if (line2D.intersects(rectangle))
                g2d.draw(line2D);
        }
    }

    public List<GeoPosition> getGeoPoints(int zoom) {
        return geoPointsDetalizationMap.get(zoom);
    }

    public List<Point2D> getPoints2D(int zoom) {
        return points2DDetalizationMap.get(zoom);
    }

    public double getPixelDistance(int zoom) {
        return distanceInPixelsDetalizationMap.get(zoom);
    }
}
