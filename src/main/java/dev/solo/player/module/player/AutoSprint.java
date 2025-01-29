
package dev.solo.player.module.player;

import dev.solo.player.module.Module;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

public class AutoSprint extends Module
{
    public AutoSprint() {
        super(new char[] { 'A', 'u', 't', 'o', 'S', 'p', 'r', 'i', 'n', 't' }, Category.player);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent tickEvent) {
        if (AutoSprint.mc.player != null && AutoSprint.mc.level != null) {
            AutoSprint.mc.options.keySprint.setDown(true);
        }
    }
}
