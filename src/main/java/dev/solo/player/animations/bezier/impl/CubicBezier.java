
package dev.solo.player.animations.bezier.impl;

import dev.solo.player.animations.bezier.AbstractBezier;
import dev.solo.player.animations.bezier.Point;

import java.util.Collections;
import java.util.Iterator;
import java.util.AbstractMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CubicBezier extends AbstractBezier
{
    private final Point firstPoint;
    private final Point secondPoint;
    private final List<Point> points;
    private int pointsAmount;
    
    public CubicBezier() {
        this.points = new ArrayList<Point>();
        this.pointsAmount = 0;
        this.firstPoint = new Point(0.0, 0.0);
        this.secondPoint = new Point(1.0, 1.0);
        this.setPointsAmount(30);
    }
    
    public CubicBezier(final int pointsAmount) {
        this();
        this.setPointsAmount(pointsAmount);
    }
    
    public CubicBezier(final Point firstPoint, final Point secondPoint) {
        this.points = new ArrayList<Point>();
        this.pointsAmount = 0;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.setPointsAmount(30);
    }
    
    public CubicBezier(final Point firstPoint, final Point secondPoint, final int pointsAmount) {
        this(firstPoint, secondPoint);
        this.setPointsAmount(pointsAmount);
    }
    
    public CubicBezier(final CubicBezier bezier) {
        this.points = new ArrayList<Point>();
        this.pointsAmount = 0;
        this.firstPoint = bezier.getFirstPoint();
        this.secondPoint = bezier.getSecondPoint();
        this.setPointsAmount(30);
    }
    
    public CubicBezier(final CubicBezier bezier, final int pointsAmount) {
        this(bezier);
        this.setPointsAmount(pointsAmount);
    }
    
    public CubicBezier(final String str) {
        this.points = new ArrayList<Point>();
        this.pointsAmount = 0;
        final String[] splitted = str.replace(" ", "").split(",");
        if (splitted.length != 4) {
            throw new IllegalArgumentException("Couldn't parse " + str + ", please follow this format: x1,y1,x2,y2");
        }
        this.firstPoint = new Point(splitted[0] + "," + splitted[1]);
        this.secondPoint = new Point(splitted[2] + "," + splitted[3]);
        this.setPointsAmount(30);
    }
    
    public CubicBezier(final String str, final int pointsAmount) {
        this(str);
        this.setPointsAmount(pointsAmount);
    }
    
    private void setupPoints() {
        if (this.firstPoint == null || this.secondPoint == null) {
            return;
        }
        this.points.clear();
        for (double increment = 0.03333333333333333, t = 0.0; t <= 1.0; t += increment) {
            final Point point = this.getPoint(t);
            this.points.add(new Point(point.x, 1.0).subtract(0.0, point.y));
        }
        this.points.add(new Point(1.0, 0.0));
    }
    
    private Point getPoint(final double time) {
        if (this.firstPoint == null || this.secondPoint == null) {
            throw new NullPointerException("firstPoint or secondPoint is null");
        }
        final Point controlPoint1 = this.firstPoint.copy();
        final Point controlPoint2 = this.secondPoint.copy();
        final double oneMinusT = 1.0 - time;
        return new Point(this.start.x * Math.pow(oneMinusT, 3.0) + 3.0 * controlPoint1.x * time * Math.pow(oneMinusT, 2.0) + 3.0 * controlPoint2.x * Math.pow(time, 2.0) * oneMinusT + this.end.x * Math.pow(time, 3.0), this.start.y * Math.pow(oneMinusT, 3.0) + 3.0 * controlPoint1.y * time * Math.pow(oneMinusT, 2.0) + 3.0 * controlPoint2.y * Math.pow(time, 2.0) * oneMinusT + this.end.y * Math.pow(time, 3.0));
    }
    
    private Map.Entry<Point, Point> getBoundingPoints(final double x) {
        if (this.points.isEmpty()) {
            return new AbstractMap.SimpleEntry<Point, Point>(new Point(0.0, 0.0), new Point(0.0, 0.0));
        }
        Point lowerPoint = this.points.get(0);
        Point upperPoint = this.points.get(this.points.size() - 1);
        for (final Point point : this.points) {
            if (point.x < x) {
                lowerPoint = point;
            }
            else {
                if (point.x > x && upperPoint.x >= point.x) {
                    upperPoint = point;
                    break;
                }
                continue;
            }
        }
        if (upperPoint.x < x) {
            upperPoint = lowerPoint;
        }
        if (lowerPoint.x > x) {
            lowerPoint = upperPoint;
        }
        return new AbstractMap.SimpleEntry<Point, Point>(lowerPoint, upperPoint);
    }
    
    @Override
    public double ease(final double time) {
        if (this.firstPoint == null || this.secondPoint == null) {
            return 0.0;
        }
        final Map.Entry<Point, Point> points = this.getBoundingPoints(time);
        final Point lowerPoint = points.getKey();
        final Point upperPoint = points.getValue();
        if (lowerPoint.equals(upperPoint)) {
            return 1.0 - lowerPoint.y;
        }
        final double interpolatedY = (upperPoint.y - lowerPoint.y) / (upperPoint.x - lowerPoint.x) * (time - lowerPoint.x) + lowerPoint.y;
        return 1.0 - interpolatedY;
    }
    
    public Point getFirstPoint() {
        return this.firstPoint.copy();
    }
    
    public Point getSecondPoint() {
        return this.secondPoint.copy();
    }
    
    public List<Point> getPoints() {
        return Collections.unmodifiableList((List<? extends Point>)this.points);
    }
    
    public int getPointsAmount() {
        return this.pointsAmount;
    }
    
    public void setPointsAmount(final int pointsAmount) {
        if (this.pointsAmount == pointsAmount) {
            return;
        }
        this.pointsAmount = pointsAmount;
        this.setupPoints();
    }
    
    public CubicBezier copy() {
        return new CubicBezier(this);
    }
}
