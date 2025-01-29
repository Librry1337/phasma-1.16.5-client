
package dev.solo.player.module.visual;

import dev.solo.player.module.Module;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class NoBadOverlay extends Module
{
    public NoBadOverlay() {
        super(new char[] { 'N', 'o', 'B', 'a', 'd', 'O', 'v', 'e', 'r', 'l', 'a', 'y' }, Category.visual);
    }
    
    @SubscribeEvent
    public void onRenderBlockOverlay(final RenderBlockOverlayEvent renderBlockOverlayEvent) {
        renderBlockOverlayEvent.setCanceled(true);
    }
}
