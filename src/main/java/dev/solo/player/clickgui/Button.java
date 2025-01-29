
package dev.solo.player.clickgui;


import java.awt.Color;
import com.mojang.blaze3d.matrix.MatrixStack;
import dev.solo.player.Wrapper;
import dev.solo.player.fill.RoundedUtil;
import dev.solo.player.module.Module;
import dev.solo.player.utils.HoverUtil;


public class Button implements Wrapper
{
    private float x;
    private float y;
    private final int width;
    private final int height;
    private final Module module;
    
    public Button(final int width, final int height, final Module module) {
        this.width = width;
        this.height = height;
        this.module = module;
    }
    
    public void render(final MatrixStack p_230430_1_, final int p_230430_2_, final int p_230430_3_, final float p_230430_4_) {
        RoundedUtil.drawGradientRound(this.x, this.y, (float)this.width, (float)this.height, 5.0f, Color.black, Color.black, Color.darkGray, Color.black);
        Button.font.drawCenteredString(p_230430_1_, this.module.getName(), this.x + this.width / 2.0f, this.y + this.height / 2.0f, -1);
    }
    
    public void mouseClicked(final double p_231044_1_, final double p_231044_3_, final int p_231044_5_) {
        if (p_231044_5_ == 0 && this.isHovered(p_231044_1_, p_231044_3_)) {
            this.module.toggle();
        }
    }
    
    private boolean isHovered(final double mouseX, final double mouseY) {
        return HoverUtil.isHovered(mouseX, mouseY, this.x, this.y, (float)this.width, (float)this.height);
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
}
