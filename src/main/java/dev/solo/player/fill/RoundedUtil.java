
package dev.solo.player.fill;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.solo.player.Wrapper;
import dev.solo.player.fill.ShaderUtil;

import java.awt.Color;

public class RoundedUtil implements Wrapper
{
    public static ShaderUtil roundedShader;
    public static ShaderUtil roundedOutlineShader;
    private static final ShaderUtil roundedTexturedShader;
    private static final ShaderUtil roundedGradientShader;

    public static void drawRoundScale(final float x, final float y, final float width, final float height, final float radius, final Color color, final float scale) {
        drawRound(x + width - width * scale, y + height / 2.0f - height / 2.0f * scale, width * scale, height * scale, radius, color);
    }

    public static void drawGradientHorizontal(final float x, final float y, final float width, final float height, final float radius, final Color left, final Color right) {
        drawGradientRound(x, y, width, height, radius, left, left, right, right);
    }

    public static void drawGradientVertical(final float x, final float y, final float width, final float height, final float radius, final Color top, final Color bottom) {
        drawGradientRound(x, y, width, height, radius, bottom, top, bottom, top);
    }

    public static void drawGradientRound(final float x, final float y, final float width, final float height, final float radius, final Color bottomLeft, final Color topLeft, final Color bottomRight, final Color topRight) {
        resetColor();
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(770, 771);
        RoundedUtil.roundedGradientShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtil.roundedGradientShader);
        RoundedUtil.roundedGradientShader.setUniformf("color1", bottomLeft.getRed() / 255.0f, bottomLeft.getGreen() / 255.0f, bottomLeft.getBlue() / 255.0f, bottomLeft.getAlpha() / 255.0f);
        RoundedUtil.roundedGradientShader.setUniformf("color2", topLeft.getRed() / 255.0f, topLeft.getGreen() / 255.0f, topLeft.getBlue() / 255.0f, topLeft.getAlpha() / 255.0f);
        RoundedUtil.roundedGradientShader.setUniformf("color3", bottomRight.getRed() / 255.0f, bottomRight.getGreen() / 255.0f, bottomRight.getBlue() / 255.0f, bottomRight.getAlpha() / 255.0f);
        RoundedUtil.roundedGradientShader.setUniformf("color4", topRight.getRed() / 255.0f, topRight.getGreen() / 255.0f, topRight.getBlue() / 255.0f, topRight.getAlpha() / 255.0f);
        ShaderUtil.drawQuads(x, y, width, height);
        RoundedUtil.roundedGradientShader.unload();
        GlStateManager._disableBlend();
    }

    public static void drawRound(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        resetColor();
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(770, 771);
        RoundedUtil.roundedShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtil.roundedShader);
        RoundedUtil.roundedShader.setUniformf("color", color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        ShaderUtil.drawQuads(x, y, width, height);
        RoundedUtil.roundedShader.unload();
        GlStateManager._disableBlend();
    }

    public static void drawRoundOutline(final float x, final float y, final float width, final float height, final float radius, final float outlineThickness, final Color color, final Color outlineColor) {
        resetColor();
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(770, 771);
        RoundedUtil.roundedOutlineShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtil.roundedOutlineShader);
        RoundedUtil.roundedOutlineShader.setUniformf("outlineThickness", (float)(outlineThickness * RoundedUtil.mc.getWindow().getGuiScale()));
        RoundedUtil.roundedOutlineShader.setUniformf("color", color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        RoundedUtil.roundedOutlineShader.setUniformf("outlineColor", outlineColor.getRed() / 255.0f, outlineColor.getGreen() / 255.0f, outlineColor.getBlue() / 255.0f, outlineColor.getAlpha() / 255.0f);
        ShaderUtil.drawQuads(x - outlineThickness, y - outlineThickness, width + outlineThickness * 2.0f, height + outlineThickness * 2.0f);
        RoundedUtil.roundedOutlineShader.unload();
        GlStateManager._disableBlend();
    }

    public static void drawRoundTextured(final float x, final float y, final float width, final float height, final float radius, final float alpha) {
        resetColor();
        RoundedUtil.roundedTexturedShader.init();
        RoundedUtil.roundedTexturedShader.setUniformi("textureIn", 0);
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtil.roundedTexturedShader);
        RoundedUtil.roundedTexturedShader.setUniformf("alpha", alpha);
        ShaderUtil.drawQuads(x, y, width, height);
        RoundedUtil.roundedTexturedShader.unload();
        GlStateManager._disableBlend();
    }

    private static void resetColor() {
        GlStateManager._color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private static void setupRoundedRectUniforms(final float x, final float y, final float width, final float height, final float radius, final ShaderUtil roundedTexturedShader) {
        roundedTexturedShader.setUniformf("location", (float)(x * RoundedUtil.mc.getWindow().getGuiScale()), (float)(RoundedUtil.mc.getWindow().getHeight() - height * RoundedUtil.mc.getWindow().getGuiScale() - y * RoundedUtil.mc.getWindow().getGuiScale()));
        roundedTexturedShader.setUniformf("rectSize", (float)(width * RoundedUtil.mc.getWindow().getGuiScale()), (float)(height * RoundedUtil.mc.getWindow().getGuiScale()));
        roundedTexturedShader.setUniformf("radius", (float)(radius * RoundedUtil.mc.getWindow().getGuiScale()));
    }

    static {
        RoundedUtil.roundedShader = new ShaderUtil("roundedRect");
        RoundedUtil.roundedOutlineShader = new ShaderUtil("roundRectOutline");
        roundedTexturedShader = new ShaderUtil("roundRectTextured");
        roundedGradientShader = new ShaderUtil("roundedRectGradient");
    }
}
