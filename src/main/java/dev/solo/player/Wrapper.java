// Decompiled with: Procyon 0.6.0
// Class Version: 8
package dev.solo.player;

import java.util.Random;

import dev.solo.player.font.GlyphPageFontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.Minecraft;

public interface Wrapper
{
    public static final Minecraft mc = Minecraft.getInstance();
    public static final GlyphPageFontRenderer font = GlyphPageFontRenderer.create("https://www.dropbox.com/scl/fi/esio22sgog59avti21sye/Montserrat-Bold.ttf?rlkey=0zv597k729ui4krimo2luxppt&st=k59q1czo&dl=1", 14, false, false, false);
    public static final Tessellator tessellator = Tessellator.getInstance();
    public static final BufferBuilder bufferBuilder = Wrapper.tessellator.getBuilder();
    public static final Random random = new Random();
}
