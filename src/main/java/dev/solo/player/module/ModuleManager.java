
package dev.solo.player.module;

import dev.solo.player.clickgui.ClickGui;
import dev.solo.player.module.client.Overlay;
import dev.solo.player.module.combat.Aura;
import dev.solo.player.module.move.NoJumpDelay;
import dev.solo.player.module.player.NoClickDelay;
import dev.solo.player.module.visual.Gamma;
import dev.solo.player.module.visual.NoBadOverlay;

import java.util.Arrays;

import java.util.ArrayList;

public class ModuleManager
{
    private final ArrayList<Module> modules;
    
    public ModuleManager() {
        (this.modules = new ArrayList<Module>()).addAll(Arrays.asList(new ClickGui(), new Overlay(), new Aura(), new NoJumpDelay(), new NoClickDelay(), new Gamma(), new NoBadOverlay(), new Tracers()));
    }
    
    public ArrayList<Module> getModules() {
        return this.modules;
    }
}
