
package dev.solo.player.module;


import dev.solo.player.Phasma;
import dev.solo.player.Wrapper;

public class Module implements Wrapper
{
    private final char[] name;
    private final char[] category;
    private int key;
    private boolean state;
    
    public Module(final char[] name, final char[] category) {
        this.name = name;
        this.category = category;
    }
    
    public Module(final char[] name, final char[] category, final int key) {
        this.name = name;
        this.category = category;
        this.key = key;
    }
    
    public void onEnable() {
        this.state = true;
        Phasma.getInstance().getEventBusHook().register(this);
    }
    
    public void onDisable() {
        this.state = false;
        Phasma.getInstance().getEventBusHook().unregister(this);
    }
    
    public void toggle() {
        if (this.state) {
            this.onDisable();
        }
        else {
            this.onEnable();
        }
    }
    
    public char[] getName() {
        return this.name;
    }
    
    public char[] getCategory() {
        return this.category;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public boolean isState() {
        return this.state;
    }
}
