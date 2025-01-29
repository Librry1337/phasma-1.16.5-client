
package dev.solo.player.clickgui;

import java.util.Iterator;
import java.awt.Color;
import com.mojang.blaze3d.matrix.MatrixStack;
import dev.solo.player.Phasma;
import dev.solo.player.Wrapper;
import dev.solo.player.fill.RoundedUtil;

import java.util.ArrayList;

public class Panel implements Wrapper
{
    private float x;
    private float y;
    private final int width;
    private final int height;
    private final int offset;
    private final char[] category;
    private final ArrayList<Button> buttons;
    
    public Panel(final int width, final int height, final int offset, final char[] category) {
        this.buttons = new ArrayList<Button>();
        this.width = width;
        this.height = height;
        this.offset = offset;
        this.category = category;
        Phasma.getInstance().getModuleManager().getModules().stream().filter(module -> module.getCategory() == category).forEach(module -> this.buttons.add(new Button(width, height, module)));
    }
    
    public void render(final MatrixStack p_230430_1_, final int p_230430_2_, final int p_230430_3_, final float p_230430_4_) {
        RoundedUtil.drawGradientRound(this.x, this.y, (float)this.width, (float)((this.height + 2) * 4), 5.0f, Color.black, Color.black, Color.darkGray, Color.black);
        RoundedUtil.drawGradientRound(this.x, this.y, (float)this.width, (float)this.height, 3.0f, Color.black, Color.black, Color.black, Color.black);
        Panel.font.drawCenteredString(p_230430_1_, this.category, this.x + this.width / 2.0f, this.y + this.height / 2.0f, -1);
        float buttonY = this.y + this.height + this.offset;
        for (final Button button : this.buttons) {
            button.setX(this.x);
            button.setY(buttonY);
            button.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
            buttonY += this.height + this.offset;
        }
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public ArrayList<Button> getButtons() {
        return this.buttons;
    }
}
