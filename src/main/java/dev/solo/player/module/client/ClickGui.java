
package dev.solo.player.module.client;


import dev.solo.player.Phasma;
import dev.solo.player.module.Module;

public class ClickGui extends Module
{
    public ClickGui() {
        super(new char[] { 'C', 'l', 'i', 'c', 'k', 'G', 'u', 'i' }, Category.client, 344);
    }

    @Override
    public void onEnable() {
        ClickGui.mc.setScreen(Phasma.getInstance().getClickGui());
    }
}
