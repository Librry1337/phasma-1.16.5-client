
package dev.solo.player.hooks;

import java.util.Iterator;

import dev.solo.player.utils.ReflectionUtil;
import net.minecraftforge.eventbus.ListenerList;
import java.util.Collections;
import java.util.ArrayList;
import net.minecraftforge.eventbus.api.EventListenerHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import java.lang.reflect.InvocationTargetException;
import net.minecraftforge.eventbus.api.IGenericEvent;
import net.minecraftforge.eventbus.api.Event;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventListener;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EventBusHook
{
    private ConcurrentHashMap<Object, List<IEventListener>> listeners;
    private final int busID;
    
    public EventBusHook() {
        this.listeners = ReflectionUtil.getField(EventBus.class, MinecraftForge.EVENT_BUS, 4);
        this.busID = ReflectionUtil.getField(EventBus.class, MinecraftForge.EVENT_BUS, 5);
    }
    
    private void registerClass(final Class<?> clazz) {
        Arrays.stream(clazz.getMethods()).filter(m -> Modifier.isStatic(m.getModifiers())).filter(m -> m.isAnnotationPresent(SubscribeEvent.class)).forEach(m -> this.registerListener(clazz, m, m));
    }
    
    private Optional<Method> getDeclMethod(final Class<?> clz, final Method in) {
        try {
            return Optional.of(clz.getDeclaredMethod(in.getName(), in.getParameterTypes()));
        }
        catch (final NoSuchMethodException var4) {
            return Optional.empty();
        }
    }
    
    private void registerObject(final Object obj) {
        final HashSet<Class<?>> classes = new HashSet<Class<?>>();
        this.typesFor(obj.getClass(), classes);
        Arrays.stream(obj.getClass().getMethods()).filter(m -> !Modifier.isStatic(m.getModifiers())).forEach(m -> classes.stream().map(c -> this.getDeclMethod(c, m)).filter(rm -> rm.isPresent() && rm.get().isAnnotationPresent(SubscribeEvent.class)).findFirst().ifPresent(rm -> this.registerListener(obj, m, rm.get())));
    }
    
    private void typesFor(final Class<?> clz, final Set<Class<?>> visited) {
        if (clz.getSuperclass() != null) {
            this.typesFor(clz.getSuperclass(), visited);
            Arrays.stream(clz.getInterfaces()).forEach(i -> this.typesFor(i, visited));
            visited.add(clz);
        }
    }
    
    public void register(final Object target) {
        if (!this.listeners.containsKey(target)) {
            if (target.getClass() == Class.class) {
                this.registerClass((Class<?>)target);
            }
            else {
                this.registerObject(target);
            }
        }
    }
    
    private void registerListener(final Object target, final Method method, final Method real) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation. It has " + parameterTypes.length + " arguments, but event handler methods require a single argument only.");
        }
        final Class<?> eventType = parameterTypes[0];
        if (!Event.class.isAssignableFrom(eventType)) {
            throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but takes an argument that is not an Event subtype : " + eventType);
        }
        this.register(eventType, target, real);
    }
    
    private void register(final Class<?> eventType, final Object target, final Method method) {
        try {
            final ASMEventHandlerHook asm = new ASMEventHandlerHook(target, method, IGenericEvent.class.isAssignableFrom(eventType));
            this.addToListeners(target, eventType, asm, asm.getPriority());
        }
        catch (final InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException var5) {}
    }
    
    private void addToListeners(final Object target, final Class<?> eventType, final IEventListener listener, final EventPriority priority) {
        final ListenerList listenerList = EventListenerHelper.getListenerList(eventType);
        listenerList.register(this.busID, priority, listener);
        final List<IEventListener> others = this.listeners.computeIfAbsent(target, k -> Collections.synchronizedList(new ArrayList<Object>()));
        others.add(listener);
    }
    
    public void unregister(final Object object) {
        final List<IEventListener> list = this.listeners.remove(object);
        if (list != null) {
            for (final IEventListener listener : list) {
                ListenerList.unregisterAll(this.busID, listener);
            }
        }
    }
}
