
package dev.solo.player.hooks;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassWriter;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IGenericEvent;
import net.minecraftforge.eventbus.api.Event;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.WildcardType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraftforge.eventbus.api.IEventListener;

public class ASMEventHandlerHook implements IEventListener
{
    private static final AtomicInteger IDs;
    private static final String HANDLER_DESC;
    private static final String HANDLER_FUNC_DESC;
    private static final ASMClassLoader LOADER;
    private static final HashMap<Method, Class<?>> cache;
    private final IEventListener handler;
    private final SubscribeEvent subInfo;
    private Type filter;
    
    public ASMEventHandlerHook(final Object target, final Method method, final boolean isGeneric) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        this.filter = null;
        if (Modifier.isStatic(method.getModifiers())) {
            this.handler = (IEventListener)this.createWrapper(method).newInstance();
        }
        else {
            this.handler = (IEventListener)this.createWrapper(method).getConstructor(Object.class).newInstance(target);
        }
        this.subInfo = method.getAnnotation(SubscribeEvent.class);
        if (isGeneric) {
            final Type type = method.getGenericParameterTypes()[0];
            if (type instanceof ParameterizedType) {
                this.filter = ((ParameterizedType)type).getActualTypeArguments()[0];
                if (this.filter instanceof ParameterizedType) {
                    this.filter = ((ParameterizedType)this.filter).getRawType();
                }
                else if (this.filter instanceof WildcardType) {
                    final WildcardType wfilter = (WildcardType)this.filter;
                    if (wfilter.getUpperBounds().length == 1 && wfilter.getUpperBounds()[0] == Object.class && wfilter.getLowerBounds().length == 0) {
                        this.filter = null;
                    }
                }
            }
        }
    }
    
    @Override
    public void invoke(final Event event) {
        if (this.handler != null && (!event.isCancelable() || !event.isCanceled() || this.subInfo.receiveCanceled()) && (this.filter == null || this.filter == ((IGenericEvent)event).getGenericType())) {
            this.handler.invoke(event);
        }
    }
    
    public EventPriority getPriority() {
        return this.subInfo.priority();
    }
    
    public Class<?> createWrapper(final Method callback) {
        if (ASMEventHandlerHook.cache.containsKey(callback)) {
            return ASMEventHandlerHook.cache.get(callback);
        }
        final ClassWriter cw = new ClassWriter(0);
        final boolean isStatic = Modifier.isStatic(callback.getModifiers());
        final String name = this.getUniqueName(callback);
        final String desc = name.replace('.', '/');
        final String instType = org.objectweb.asm.Type.getInternalName(callback.getDeclaringClass());
        final String eventType = org.objectweb.asm.Type.getInternalName(callback.getParameterTypes()[0]);
        cw.visit(50, 33, desc, null, "java/lang/Object", new String[] { ASMEventHandlerHook.HANDLER_DESC });
        cw.visitSource(".dynamic", null);
        if (!isStatic) {
            cw.visitField(1, "instance", "Ljava/lang/Object;", null, null).visitEnd();
        }
        MethodVisitor mv = cw.visitMethod(1, "<init>", isStatic ? "()V" : "(Ljava/lang/Object;)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, "java/lang/Object", "<init>", "()V", false);
        if (!isStatic) {
            mv.visitVarInsn(25, 0);
            mv.visitVarInsn(25, 1);
            mv.visitFieldInsn(181, desc, "instance", "Ljava/lang/Object;");
        }
        mv.visitInsn(177);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        mv = cw.visitMethod(1, "invoke", ASMEventHandlerHook.HANDLER_FUNC_DESC, null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        if (!isStatic) {
            mv.visitFieldInsn(180, desc, "instance", "Ljava/lang/Object;");
            mv.visitTypeInsn(192, instType);
        }
        mv.visitVarInsn(25, 1);
        mv.visitTypeInsn(192, eventType);
        mv.visitMethodInsn(isStatic ? 184 : 182, instType, callback.getName(), org.objectweb.asm.Type.getMethodDescriptor(callback), false);
        mv.visitInsn(177);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        cw.visitEnd();
        final Class<?> ret = ASMEventHandlerHook.LOADER.define(name, cw.toByteArray());
        ASMEventHandlerHook.cache.put(callback, ret);
        return ret;
    }
    
    private String getUniqueName(final Method callback) {
        return String.format("%s_%d_%s_%s_%s", this.getClass().getName(), ASMEventHandlerHook.IDs.getAndIncrement(), callback.getDeclaringClass().getSimpleName(), callback.getName(), callback.getParameterTypes()[0].getSimpleName());
    }
    
    @Override
    public String toString() {
        return null;
    }
    
    static {
        IDs = new AtomicInteger();
        HANDLER_DESC = org.objectweb.asm.Type.getInternalName(IEventListener.class);
        HANDLER_FUNC_DESC = org.objectweb.asm.Type.getMethodDescriptor(org.objectweb.asm.Type.VOID_TYPE, org.objectweb.asm.Type.getType(Event.class));
        LOADER = new ASMClassLoader();
        cache = new HashMap<Method, Class<?>>();
    }
    
    private static class ASMClassLoader extends ClassLoader
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
}
