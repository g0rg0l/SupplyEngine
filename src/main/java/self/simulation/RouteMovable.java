package self.simulation;


import self.map.routing.MapRoute;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ListIterator;

public class RouteMovable {
    private Point2D currentPosition;
    private Point2D targetPosition;
    private ListIterator<Point2D> next;
    private MapRoute route;
    private double time;
    private double speed;
    private boolean isStraight;
    private boolean isFinished;

    public void update(float dt) {
        time += dt;

        double distToMove = dt * speed;
        double distToNext = currentPosition.distance(targetPosition);
        while (distToMove > distToNext) {
            distToMove -= distToNext;

            currentPosition = targetPosition;
            if (isStraight) {
                if (next.hasNext()) {
                    targetPosition = (Point2D) next.next().clone();
                }
                else {
                    isFinished = true;
                    return;
                }
            }
            else {
                if (next.hasPrevious()) {
                    targetPosition = (Point2D) next.previous().clone();
                }
                else {
                    isFinished = true;
                    return;
                }
            }

            distToNext = currentPosition.distance(targetPosition);
        }

        move(distToMove);
    }

    public void setup(MapRoute route, ListIterator<Point2D> currentPoint, double speed, boolean isStraight) {
        if (isStraight) {
            this.currentPosition = (Point2D) currentPoint.next().clone();
            this.targetPosition = (Point2D) currentPoint.next().clone();
        }
        else {
            this.currentPosition = (Point2D) currentPoint.previous().clone();
            this.targetPosition = (Point2D) currentPoint.previous().clone();
        }
        this.next = currentPoint;
        this.speed = speed;
        this.route = route;
        this.isStraight = isStraight;
    }

    public void recalculatePositionOnNewZoom(int zoom) {
        double routeCompleteRatio = time / route.getOriginalTime();

        double dist = 0;
        var newPoints = route.getPoints2D(zoom);

        if (isStraight) {
            for (int i = 1; i < newPoints.size(); i++) {
                double d = newPoints.get(i - 1).distance(newPoints.get(i));

                if ((dist + d) / route.getPixelDistance(zoom) >= routeCompleteRatio) {
                    double speedInPixelsInSec = route.getPixelDistance(zoom) / route.getOriginalTime();
                    setup(route, newPoints.listIterator(i - 1), speedInPixelsInSec, isStraight);

                    double afterStartDistInPixels = routeCompleteRatio * route.getPixelDistance(zoom) - dist;
                    move(afterStartDistInPixels);

                    return;
                }
                else dist += d;
            }

        }
        else {
            for (int i = newPoints.size() - 2; i >= 0; i--) {
                double d = newPoints.get(i + 1).distance(newPoints.get(i));

                if ((dist + d) / route.getPixelDistance(zoom) >= routeCompleteRatio) {
                    double speedInPixelsInSec = route.getPixelDistance(zoom) / route.getOriginalTime();
                    setup(route, newPoints.listIterator(i + 2), speedInPixelsInSec, isStraight);

                    double afterStartDistInPixels = routeCompleteRatio * route.getPixelDistance(zoom) - dist;
                    move(afterStartDistInPixels);

                    return;
                }
                else dist += d;
            }
        }
    }

    private void move(double accelerationValue) {
        var ds = new Point2D.Double(
                targetPosition.getX() - currentPosition.getX(),
                targetPosition.getY() - currentPosition.getY()
        );

        var ds_length = currentPosition.distance(targetPosition);

        var ds_normalized = new Point2D.Double(
                ds.getX() / ds_length,
                ds.getY() / ds_length
        );

        currentPosition.setLocation(
                currentPosition.getX() + ds_normalized.getX() * accelerationValue,
                currentPosition.getY() + ds_normalized.getY() * accelerationValue
        );
    }

    public void draw(Graphics2D g2d) {
        final int size = 12;
        g2d.setColor(Color.MAGENTA.darker());

        g2d.fillOval(
                (int) currentPosition.getX() - size / 2,
                (int) currentPosition.getY() - size / 2,
                size, size
        );
    }

    public boolean isFinished() {
        return isFinished;
    }
}
