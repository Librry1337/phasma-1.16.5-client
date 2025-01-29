package dev.solo.player.animations.animations;


import dev.solo.player.animations.easing.Easing;

/* loaded from: phasma.jar:ua/leansani/phasma/animations/animation/AbstractAnimation.class */
public abstract class AbstractAnimation {
    private double value;
    private long start;
    private double duration;
    private double from;
    private double to;
    private Easing easing;
    private boolean debug;

    public boolean update() {
        boolean alive = isAlive();
        if (alive) {
            this.value = interpolate(getFrom(), getTo(), getEasing().ease(calculatePart()));
        } else {
            setStart(0L);
            this.value = getTo();
        }
        return alive;
    }

    public boolean isAlive() {
        return !isDone();
    }

    public boolean isDone() {
        return calculatePart() >= 1.0d;
    }

    public double calculatePart() {
        return (System.currentTimeMillis() - getStart()) / getDuration();
    }

    public double interpolate(double start, double end, double pct) {
        return start + ((end - start) * pct);
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getStart() {
        return this.start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getFrom() {
        return this.from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return this.to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public Easing getEasing() {
        return this.easing;
    }

    public void setEasing(Easing easing) {
        this.easing = easing;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}