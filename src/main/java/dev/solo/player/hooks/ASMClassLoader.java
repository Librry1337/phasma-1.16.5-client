
package dev.solo.player.hooks;

public class ASMClassLoader extends ClassLoader
{
    private ASMClassLoader() {
        super(null);
    }
    
    @Override
    protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        return Class.forName(name, resolve, Thread.currentThread().getContextClassLoader());
    }
    
    Class<?> define(final String name, final byte[] data) {
        return this.defineClass(name, data, 0, data.length);
    }
}
