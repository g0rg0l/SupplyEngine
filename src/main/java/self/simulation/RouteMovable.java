package self.simulation;


import self.map.routing.MapRoute;

import java.awt.*;
import java.awt.geom.Point2D;

public class RouteMovable {
    private Point2D current;
    private Point2D next;
    private MapRoute route;
    private double time;
    private double speed;
    private boolean isFinished;

    public void update(float dt) {
        time += dt;
        var distToMove = dt * speed;

        while (distToMove > current.distance(next)) {
            distToMove -= current.distance(next);

            current.setLocation(next);
            var newNext = getNextPointToMove();
            if (newNext != null) next = newNext;
            else {
                isFinished = true;
                return;
            }
        }

        move(current, next, distToMove);
    }

    public void setup(MapRoute route, Point2D current, Point2D next, double speed) {
        this.current = (Point2D) current.clone();
        this.next = (Point2D) next.clone();
        this.speed = speed;
        this.route = route;
    }

    public void recalculatePositionOnNewZoom(int zoom) {
        double routeCompleteRatio = time / route.getOriginalTime();

        double dist = 0;
        var newPoints = route.getPoints2D(zoom);
        for (int i = 1; i < newPoints.size(); i++) {
            double d = newPoints.get(i - 1).distance(newPoints.get(i));

            if ((dist + d) / route.getPixelDistance(zoom) >= routeCompleteRatio) {
                var newStartPoint = (Point2D) route.getPoints2D(zoom).get(i - 1).clone();
                var newTargetPoint = (Point2D) route.getPoints2D(zoom).get(i).clone();
                double speedInPixelsInSec = route.getPixelDistance(zoom) / route.getOriginalTime();

                double afterStartDistInPixels = routeCompleteRatio * route.getPixelDistance(zoom) - dist;
                move(newStartPoint, newTargetPoint, afterStartDistInPixels);

                setup(route, newStartPoint, newTargetPoint, speedInPixelsInSec);
                return;
            }
            else dist += d;
        }
    }

    private void move(Point2D from, Point2D to, double accelerationValue) {
        var ds = new Point2D.Double(
                to.getX() - from.getX(),
                to.getY() - from.getY()
        );
        var ds_length = from.distance(to);
        var ds_normalized = new Point2D.Double(
                ds.getX() / ds_length,
                ds.getY() / ds_length
        );

        from.setLocation(
                from.getX() + ds_normalized.getX() * accelerationValue,
                from.getY() + ds_normalized.getY() * accelerationValue
        );
    }

    public void draw(Graphics2D g2d) {
        final int size = 12;
        g2d.setColor(Color.MAGENTA.darker());

        g2d.fillOval(
                (int) current.getX() - size / 2,
                (int) current.getY() - size / 2,
                size, size
        );
    }

    private Point2D getNextPointToMove() {


        var points = route.getPoints2D();

        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).equals(next) && i != points.size() - 1)
                return points.get(i + 1);
        }

        return null;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
