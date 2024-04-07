package self.simulation;


import java.awt.*;
import java.awt.geom.Point2D;

public class Shipment {
    private double x, y;
    private Point2D target;
    private double speed;
    private double time = 0;

    public boolean update(float dt) {
        time += dt;

        var v = new Point2D.Double(target.getX() - x, target.getY() - y);
        double l = Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
        var vNorm = new Point2D.Double(v.x / l, v.y / l);

        x += (vNorm.x * speed);
        y += (vNorm.y * speed);

        return l < 1;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.MAGENTA.darker());
        g2d.fillOval((int) (x - 6), (int) (y - 6), 12, 12);
    }

    public void setup(Point2D position, Point2D target, double speed) {
        x = position.getX();
        y = position.getY();
        this.target = target;
        this.speed = speed;
    }

    public Point2D getTarget() {
        return target;
    }

    public void setTarget(Point2D target) {
        this.target = target;
    }
}
