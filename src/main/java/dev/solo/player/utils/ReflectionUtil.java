
package dev.solo.player.utils;

import java.lang.reflect.Field;

public class ReflectionUtil
{
    public static <T> T getField(final Class<?> clazz, final Object object, final int index) {
        try {
            final Field field = clazz.getDeclaredFields()[index];
            field.setAccessible(true);
            return (T)field.get(object);
        }
        catch (final Exception ex) {
            return null;
        }
    }
    
    public static void setField(final Class<?> clazz, final Object object, final int index, final Object value) {
        try {
            final Field field = clazz.getDeclaredFields()[index];
            field.setAccessible(true);
            field.set(object, value);
        }
        catch (final Exception ex) {}
    }
}
