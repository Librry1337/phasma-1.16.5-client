
package dev.solo.player.utils;

public class HoverUtil
{
    public static boolean isHovered(final double mouseX, final double mouseY, final float x, final float y, final float width, final float height) {
        return mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height;
    }
}
