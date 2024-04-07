package self.map.routing;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import self.map.MapUtilities;
import self.simulation.Shipment;

import java.awt.*;
import java.awt.geom.Line2D;
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
    private final List<Shipment> movables;
    private Map<Shipment, Integer> movableZooms;
    private int zoom;


    public MapRoute(JXMapViewer map, Path path) {
        this.originalPoints = path.getGeoPositions();
        this.originalDistance = path.getDistance();
        this.originalTime = path.getTime();
        this.geoPointsDetalizationMap = new HashMap<>();
        this.points2DDetalizationMap = new HashMap<>();
        this.distanceDetalizationMap = new HashMap<>();
        this.movables = new ArrayList<>();
        this.movableZooms = new HashMap<>();
        this.zoom = map.getZoom();

        init(map);
    }

    public MapRoute(MapRoute that) {
        this.originalPoints = that.originalPoints;
        this.originalDistance = that.originalDistance;
        this.originalTime = that.originalTime;
        this.geoPointsDetalizationMap = that.geoPointsDetalizationMap;
        this.points2DDetalizationMap = that.points2DDetalizationMap;
        this.distanceDetalizationMap = that.distanceDetalizationMap;
        this.movables = new ArrayList<>();
        this.movableZooms = new HashMap<>();
        this.zoom = that.zoom;
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

    public void draw(Graphics2D g2d, JXMapViewer map) {
        movables.forEach(m -> m.draw(g2d));

        var rectangle = map.getViewportBounds();
        var points2D = getPoints2D(map.getZoom());

        g2d.setColor(Color.BLUE);
        for (int i = 1; i < points2D.size(); i++) {
            Line2D line2D = new Line2D.Double(points2D.get(i - 1), points2D.get(i));

            if (line2D.intersects(rectangle))
                g2d.draw(line2D);
        }
    }

    /**
     * Метод, который принимает какой-то IMovable объект для его дальнейшего движения.
     * Тут объект выставляется на координаты начала движения и ему задаётся скорость в текущем приближении.
     *
     * @param movable - Принимаемый для движения объект.
     */
    public void move(Shipment movable) {
        var startPoint = points2DDetalizationMap.get(zoom).get(0);
        var targetPoint = points2DDetalizationMap.get(zoom).get(1);

        var speed = distanceDetalizationMap.get(zoom) / originalTime / Math.pow(2, zoom);

        movable.setup(startPoint, targetPoint, speed);
        movables.add(movable);
        movableZooms.put(movable, zoom);
    }

    public void update(float dt) {
        for (Shipment shipment : movables) {
            if (shipment.update(dt)) {
                recalculate(shipment);
            }
        }
    }

    /**
     * Как только объект дошёл до своего таргета - точки в руте, вызывается этот метод:
     * в нём задаётся новый таргет для объекта.
     *
     * @param shipment Объект, который достиг таргета движения
     */
    private void recalculate(Shipment shipment) {
        int oldTargetIndex = points2DDetalizationMap.get(movableZooms.get(shipment)).indexOf(shipment.getTarget());

        if (oldTargetIndex + 1 < points2DDetalizationMap.get(movableZooms.get(shipment)).size()) {
            var newTarget = points2DDetalizationMap.get(movableZooms.get(shipment)).get(oldTargetIndex + 1);
            shipment.setTarget(newTarget);
        }
    }

    public List<GeoPosition> getGeoPoints(int zoom) {
        return geoPointsDetalizationMap.get(zoom);
    }

    public List<Point2D> getPoints2D(int zoom) {
        return points2DDetalizationMap.get(zoom);
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}
