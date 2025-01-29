package dev.solo.player.animations;

import java.util.LinkedList;
import java.util.Optional;

import dev.solo.player.animations.animations.ContextAnimation;
import dev.solo.player.animations.easing.Easing;
import dev.solo.player.animations.easing.Easings;

/* loaded from: AnimationContext.class */
public class AnimationContext {
    private final LinkedList<ContextAnimation> animations = new LinkedList<>();
    private double duration = 1.0d;
    private Easing easing = Easings.LINEAR;
    private boolean debug = false;

    public ContextAnimation getAnimation(String name) {
        ContextAnimation animation;
        Optional<ContextAnimation> optional = this.animations.stream().filter(it -> {
            return it.getName().equalsIgnoreCase(name);
        }).findFirst();
        if (!optional.isPresent()) {
            animation = new ContextAnimation(name, this);
            this.animations.add(animation);
        } else {
            animation = optional.get();
        }
        return animation;
    }

    public AnimationContext animate(String name, double value, double duration) {
        return animate(name, value, duration, Easings.LINEAR);
    }

    public AnimationContext animate(String name, double value, double duration, Easing easing) {
        return animate(name, value, duration, easing, false);
    }

    private AnimationContext animate(String name, double value, double duration, Easing easing, boolean debug) {
        setDuration(duration);
        setEasing(easing);
        setDebug(debug);
        getAnimation(name).setValue(value);
        return this;
    }

    public AnimationContext animate(double duration, Runnable runnable) {
        return animate(duration, this.easing, runnable);
    }

    public AnimationContext animate(double duration, Easing easing, Runnable runnable) {
        return animate(duration, easing, false, runnable);
    }

    private AnimationContext animate(double duration, Easing easing, boolean debug, Runnable runnable) {
        setDuration(duration);
        setEasing(easing);
        setDebug(debug);
        runnable.run();
        return this;
    }

    public boolean update() {
        getAnimations().forEach((v0) -> {
            v0.update();
        });
        return getAnimations().stream().anyMatch((v0) -> {
            return v0.isAlive();
        });
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setEasing(Easing easing) {
        this.easing = easing;
    }

    public double getDuration() {
        return this.duration;
    }

    public LinkedList<ContextAnimation> getAnimations() {
        return this.animations;
    }

    public Easing getEasing() {
        return this.easing;
    }

    public boolean isDebug() {
        return this.debug;
    }
}