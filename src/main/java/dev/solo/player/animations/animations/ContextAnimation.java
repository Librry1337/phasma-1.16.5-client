package dev.solo.player.animations.animations;


import dev.solo.player.animations.AnimationContext;

/* loaded from: phasma.jar:ua/leansani/phasma/animations/animation/ContextAnimation.class */
public class ContextAnimation extends AbstractAnimation {
    private final String name;
    private final AnimationContext context;

    public ContextAnimation(String name, AnimationContext context) {
        this.name = name;
        this.context = context;
    }

    @Override // ua.leansani.phasma.animations.animation.AbstractAnimation
    public void setValue(double valueTo) {
        if (valueTo == getValue()) {
            return;
        }
        setDebug(getContext().isDebug());
        if (isAlive() && (valueTo == getFrom() || valueTo == getTo() || valueTo == getValue())) {
            if (isDebug()) {
                System.out.println("Animating " + this.name + " cancelled due to valueTo equals fromValue");
                return;
            }
            return;
        }
        setEasing(getContext().getEasing());
        setDuration(getContext().getDuration() * 1000.0d);
        setStart(System.currentTimeMillis());
        setFrom(getValue());
        setTo(valueTo);
        if (isDebug()) {
            System.out.println(this.name + "#animate {\n    to value: " + getTo() + "\n    from value: " + getValue() + "\n    duration: " + getDuration() + "\n}");
        }
    }

    public AnimationContext getContext() {
        return this.context;
    }

    public String getName() {
        return this.name;
    }
}