package dev.solo.player.module.client;

import dev.solo.player.fill.RoundedUtil;
import dev.solo.player.module.Module;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.awt.Color;
import net.minecraftforge.client.event.RenderGameOverlayEvent;


public class Overlay extends Module
{
    float watermarkX;
    float watermarkY;
    float arrayListX;
    float arrayListY;
    
    public Overlay() {
        super(new char[] { 'O', 'v', 'e', 'r', 'l', 'a', 'y' }, Category.client);
    }
    
    @SubscribeEvent
    public void onChat(final RenderGameOverlayEvent.Chat chat) {
        this.watermarkX = 0.0f;
        this.watermarkY = 0.0f;
        final char[] text = { 'p', 'h', 'a', 's', 'm', 'a' };
        RoundedUtil.drawRound(this.watermarkX + 1.0f, this.watermarkY + 2.0f, (float)Overlay.font.getStringWidth(text), (float)Overlay.font.getFontHeight(), 0.0f, Color.black);
        Overlay.font.drawString(chat.getMatrixStack(), text, this.watermarkX, this.watermarkY, -1);
    }
}
