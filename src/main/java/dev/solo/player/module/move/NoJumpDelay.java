
package dev.solo.player.module.move;

import dev.solo.player.module.Module;
import dev.solo.player.utils.ReflectionUtil;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
public class NoJumpDelay extends Module
{
    public NoJumpDelay() {
        super(new char[] { 'N', 'o', 'J', 'u', 'm', 'p', 'D', 'e', 'l', 'a', 'y' }, Category.move);
    }

    @SubscribeEvent
    public void onTick(final TickEvent tickEvent) {
        if (NoJumpDelay.mc.player != null && NoJumpDelay.mc.level != null) {
            ReflectionUtil.setField(LivingEntity.class, NoJumpDelay.mc.player, 70, 0);
        }
    }
}
