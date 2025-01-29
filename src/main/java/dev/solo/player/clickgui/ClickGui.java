
package dev.solo.player.clickgui;

import java.util.Iterator;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.matrix.MatrixStack;
import dev.solo.player.Phasma;
import dev.solo.player.Wrapper;
import dev.solo.player.animations.animations.Animation;
import dev.solo.player.animations.easing.Easings;
import dev.solo.player.clickgui.Panel;
import net.minecraft.util.text.ITextComponent;
import java.util.ArrayList;
import net.minecraft.client.gui.screen.Screen;

public class ClickGui extends Screen implements Wrapper
{
    private final int width = 100;
    private final int height = 16;
    private final int panelOffset = 20;
    private final int buttonOffset = 2;
    private final ArrayList<Panel> panels;
    private final Animation animation;

    public ClickGui() {
        super(ITextComponent.nullToEmpty(""));
        this.panels = new ArrayList<Panel>();
        this.animation = new Animation();
        Phasma.getInstance().getCategoryManager().getCategories().forEach(category -> this.panels.add(new Panel(100, 16, 2, category)));
    }

    @Override
    public void onClose() {
        this.animation.animate(0.0, 0.3, Easings.BACK_IN);
    }

    protected void init() {
        this.animation.animate(1.0, 0.3, Easings.BACK_OUT);
        super.init();
    }

    @Override
    public void render(final MatrixStack p_230430_1_, final int p_230430_2_, final int p_230430_3_, final float p_230430_4_) {
        this.animation.update();
        if (this.animation.getValue() == 0.0) {
            super.onClose();
        }
        this.renderBackground(p_230430_1_);
        RenderSystem.pushMatrix();
        RenderSystem.translated(ClickGui.mc.getWindow().getGuiScaledWidth() / 2.0, ClickGui.mc.getWindow().getGuiScaledHeight() / 2.0, 0.0);
        RenderSystem.scaled(this.animation.getValue(), this.animation.getValue(), 0.0);
        RenderSystem.translated(-ClickGui.mc.getWindow().getGuiScaledWidth() / 2.0, -ClickGui.mc.getWindow().getGuiScaledHeight() / 2.0, 0.0);
        float panelX = ClickGui.mc.getWindow().getGuiScaledWidth() / 2.0f - (120 * this.panels.size() - 20) / 2.0f;
        final float panelY = ClickGui.mc.getWindow().getGuiScaledHeight() / 2.0f - 36.0f;
        for (final Panel panel : this.panels) {
            panel.setX(panelX);
            panel.setY(panelY);
            panel.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
            panelX += 120.0f;
        }
        RenderSystem.popMatrix();
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    @Override
    public boolean mouseClicked(final double p_231044_1_, final double p_231044_3_, final int p_231044_5_) {
        this.panels.forEach(panel -> panel.getButtons().forEach(button -> button.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_)));
        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }
}
