package itmo.web.demo1.beans;

import java.io.Serializable;
import java.util.List;
import itmo.web.demo1.database.models.Point;

public class AreaCalculator implements AreaCalculatorMBean, Serializable {
    private List<Point> hitPoints;

    public void setHitPoints(List<Point> points) {
        this.hitPoints = points;
    }

    @Override
    public double getArea() {
        if (hitPoints == null || hitPoints.size() < 3) return 0.0;

        double area = 0;
        for (int i = 0; i < hitPoints.size(); i++) {
            Point p1 = hitPoints.get(i);
            Point p2 = hitPoints.get((i + 1) % hitPoints.size());
            area += (p1.getX() * p2.getY()) - (p2.getX() * p1.getY());
        }
        return Math.abs(area / 2.0);
    }
}
