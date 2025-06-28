package itmo.web.demo1.beans;

import itmo.web.demo1.database.DataBaseManager;
import itmo.web.demo1.database.models.Point;
import itmo.web.demo1.beans.AreaCalculator;
import itmo.web.demo1.beans.MBeanRegistry;
import itmo.web.demo1.beans.PointStats;
import itmo.web.demo1.utils.PointHitManager;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named("tableBean")
@ApplicationScoped
public class TableBean {

    private ArrayList<Point> points = new ArrayList<>();

    private final PointStats pointStats = new PointStats();
    private final AreaCalculator areaCalculator = new AreaCalculator();



    public TableBean() {
        // Пустой — инициализация будет в @PostConstruct
    }

    @PostConstruct
    public void init() {
        // Регистрируем бины в JMX
        MBeanRegistry.registerBean(pointStats, "pointStats");
        MBeanRegistry.registerBean(areaCalculator, "areaCalculator");

        // Загружаем точки из БД и пересчитываем статистику
        points = getPoints();
        for (Point p : points) {
            boolean hit = PointHitManager.Checker.hit(p.getX(), p.getY(), p.getR());
            pointStats.update(hit);
        }
        areaCalculator.setHitPoints(points.stream().filter(p -> PointHitManager.Checker.hit(p.getX(), p.getY(), p.getR())).toList());
    }

    public void clearAllPoints() {
        try (var connection = DataBaseManager.connect()) {
            DataBaseManager.clearPoints(connection);
            points.clear();
            pointStats.reset(); // ты должен реализовать метод reset() в PointStats
            areaCalculator.setHitPoints(List.of());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Point> getPoints() {
        try (var connection = DataBaseManager.connect()) {
            DataBaseManager.createPointsTable(connection);
            points = DataBaseManager.getLastPoints(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }

    // Методы для отображения в JSF
    public int getTotalPoints() {
        return pointStats.getTotalPoints();
    }

    public int getHits() {
        return pointStats.getHits();
    }

    public double getArea() {
        return areaCalculator.getArea();
    }

    // Геттеры для использования из других бинов (например, FormBean)
    public PointStats getPointStats() {
        return pointStats;
    }

    public AreaCalculator getAreaCalculator() {
        return areaCalculator;
    }
}

