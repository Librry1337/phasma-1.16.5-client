// Decompiled with: Procyon 0.6.0
// Class Version: 8
package dev.solo.player.animations.bezier;

public class Point
{
    public double x;
    public double y;
    
    public Point() {
        this.x = 0.0;
        this.y = 0.0;
    }
    
    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point(final Point point) {
        this.x = point.x;
        this.y = point.y;
    }
    
    public Point(String str) {
        str = str.replace(" ", "");
        if (!str.contains(",")) {
            return;
        }
        final String[] splitted = str.split(",");
        if (splitted.length <= 1) {
            return;
        }
        final String first = splitted[0];
        final String second = splitted[1];
        this.x = Double.parseDouble(first.trim().replace("﻿", ""));
        this.y = Double.parseDouble(second.trim().replace("﻿", ""));
    }
    
    public Point copy() {
        return new Point(this);
    }
    
    public Point multiply(final double x, final double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Point multiply(final double factor) {
        return this.multiply(factor, factor);
    }
    
    public Point multiply(final Point point) {
        return this.multiply(point.x, point.y);
    }
    
    public Point add(final double x, final double y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Point add(final Point point) {
        return this.add(point.x, point.y);
    }
    
    public Point add(final double addition) {
        return this.add(addition, addition);
    }
    
    public Point subtract(final double x, final double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Point subtract(final Point point) {
        return this.add(point.x, point.y);
    }
    
    public Point subtract(final double subtraction) {
        return this.add(subtraction, subtraction);
    }
    
    public Point divide(final double x, final double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Point divide(final Point point) {
        return this.divide(point.x, point.y);
    }
    
    public Point divide(final double division) {
        return this.divide(division, division);
    }
    
    public Point set(final double x, final double y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public Point set(final Point point) {
        return this.set(point.x, point.y);
    }
}
