// Decompiled with: Procyon 0.6.0
// Class Version: 8
package dev.solo.player;

import dev.solo.player.clickgui.ClickGui;
import dev.solo.player.hooks.EventBusHook;
import dev.solo.player.module.CategoryManager;
import dev.solo.player.module.Module;
import dev.solo.player.module.ModuleManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.util.Iterator;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("examplemod")
public class Phasma implements Wrapper
{
    private static Phasma instance;
    private final EventBusHook eventBusHook;
    private final CategoryManager categoryManager;
    private final ModuleManager moduleManager;
    private final ClickGui clickGui;

    public Phasma() {
        Phasma.instance = this;
        this.eventBusHook = new EventBusHook();
        this.categoryManager = new CategoryManager();
        this.moduleManager = new ModuleManager();
        this.clickGui = new ClickGui();
        this.eventBusHook.register(this);
    }

    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent keyInputEvent) {
        if (keyInputEvent.getAction() == 1 && Phasma.mc.screen == null) {
            for (final Module module : this.moduleManager.getModules()) {
                if (module.getKey() == keyInputEvent.getKey()) {
                    module.toggle();
                }
            }
        }
    }

    public static Phasma getInstance() {
        return Phasma.instance;
    }

    public EventBusHook getEventBusHook() {
        return this.eventBusHook;
    }

    public CategoryManager getCategoryManager() {
        return this.categoryManager;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public ClickGui getClickGui() {
        return this.clickGui;
    }
}
