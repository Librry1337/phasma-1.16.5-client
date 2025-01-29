package dev.solo.player.animations.animations;


import dev.solo.player.animations.easing.Easing;
import dev.solo.player.animations.easing.Easings;

/* loaded from: phasma.jar:ua/leansani/phasma/animations/animation/Animation.class */
public class Animation extends AbstractAnimation {
    public void animate(double valueTo, double duration) {
        animate(valueTo, duration, Easings.LINEAR);
    }

    public void animate(double valueTo, double duration, Easing easing) {
        animate(valueTo, duration, easing, false);
    }

    public void animate(double valueTo, double duration, Easing easing, boolean debug) {
        setDebug(debug);
        if (isAlive() && (valueTo == getFrom() || valueTo == getTo() || valueTo == getValue())) {
            if (isDebug()) {
                System.out.println("Animate cancelled due to valueTo equals fromValue");
                return;
            }
            return;
        }
        setEasing(easing);
        setDuration(duration * 1000.0d);
        setStart(System.currentTimeMillis());
        setFrom(getValue());
        setTo(valueTo);
        if (isDebug()) {
            System.out.println("#animate {\n    to value: " + getTo() + "\n    from value: " + getValue() + "\n    duration: " + getDuration() + "\n}");
        }
    }
}