// Decompiled with: Procyon 0.6.0
// Class Version: 8
package dev.solo.player.animations.bezier;


import dev.solo.player.animations.easing.Easing;

public abstract class AbstractBezier implements Easing
{
    public final Point start;
    public final Point end;

    public AbstractBezier() {
        this.start = new Point(0.0, 0.0);
        this.end = new Point(1.0, 1.0);
    }
}
