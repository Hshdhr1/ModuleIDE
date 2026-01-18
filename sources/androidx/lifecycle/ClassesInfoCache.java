package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes13.dex */
final class ClassesInfoCache {
    private static final int CALL_TYPE_NO_ARG = 0;
    private static final int CALL_TYPE_PROVIDER = 1;
    private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
    static ClassesInfoCache sInstance = new ClassesInfoCache();
    private final Map mCallbackMap = new HashMap();
    private final Map mHasLifecycleMethods = new HashMap();

    ClassesInfoCache() {
    }

    boolean hasLifecycleMethods(Class cls) {
        Boolean hasLifecycleMethods = (Boolean) this.mHasLifecycleMethods.get(cls);
        if (hasLifecycleMethods != null) {
            return hasLifecycleMethods.booleanValue();
        }
        Method[] methods = getDeclaredMethods(cls);
        for (Method method : methods) {
            OnLifecycleEvent annotation = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class);
            if (annotation != null) {
                createInfo(cls, methods);
                return true;
            }
        }
        this.mHasLifecycleMethods.put(cls, false);
        return false;
    }

    private Method[] getDeclaredMethods(Class cls) {
        try {
            return cls.getDeclaredMethods();
        } catch (NoClassDefFoundError e) {
            throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e);
        }
    }

    CallbackInfo getInfo(Class cls) {
        CallbackInfo existing = (CallbackInfo) this.mCallbackMap.get(cls);
        if (existing != null) {
            return existing;
        }
        return createInfo(cls, null);
    }

    private void verifyAndPutHandler(Map map, MethodReference newHandler, Lifecycle.Event newEvent, Class cls) {
        Lifecycle.Event event = (Lifecycle.Event) map.get(newHandler);
        if (event != null && newEvent != event) {
            Method method = newHandler.mMethod;
            throw new IllegalArgumentException("Method " + method.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous value " + event + ", new value " + newEvent);
        }
        if (event == null) {
            map.put(newHandler, newEvent);
        }
    }

    private CallbackInfo createInfo(Class cls, Method[] declaredMethods) {
        CallbackInfo superInfo;
        Class<?> superclass = cls.getSuperclass();
        HashMap hashMap = new HashMap();
        if (superclass != null && (superInfo = getInfo(superclass)) != null) {
            hashMap.putAll(superInfo.mHandlerToEvent);
        }
        Class<?>[] interfaces = cls.getInterfaces();
        char c = 0;
        for (Class<?> intrfc : interfaces) {
            for (Map.Entry<MethodReference, Lifecycle.Event> entry : getInfo(intrfc).mHandlerToEvent.entrySet()) {
                verifyAndPutHandler(hashMap, (MethodReference) entry.getKey(), (Lifecycle.Event) entry.getValue(), cls);
            }
        }
        Method[] methods = declaredMethods != null ? declaredMethods : getDeclaredMethods(cls);
        boolean hasLifecycleMethods = false;
        int length = methods.length;
        int i = 0;
        while (i < length) {
            Method method = methods[i];
            OnLifecycleEvent annotation = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class);
            if (annotation != null) {
                hasLifecycleMethods = true;
                Class<?>[] params = method.getParameterTypes();
                int callType = 0;
                if (params.length > 0) {
                    callType = 1;
                    if (!params[c].isAssignableFrom(LifecycleOwner.class)) {
                        throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    }
                }
                Lifecycle.Event event = annotation.value();
                if (params.length > 1) {
                    callType = 2;
                    if (!params[1].isAssignableFrom(Lifecycle.Event.class)) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    }
                    if (event != Lifecycle.Event.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                }
                if (params.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                MethodReference methodReference = new MethodReference(callType, method);
                verifyAndPutHandler(hashMap, methodReference, event, cls);
            }
            i++;
            c = 0;
        }
        CallbackInfo info = new CallbackInfo(hashMap);
        this.mCallbackMap.put(cls, info);
        this.mHasLifecycleMethods.put(cls, Boolean.valueOf(hasLifecycleMethods));
        return info;
    }

    @Deprecated
    static class CallbackInfo {
        final Map mEventToHandlers = new HashMap();
        final Map mHandlerToEvent;

        CallbackInfo(Map map) {
            this.mHandlerToEvent = map;
            for (Map.Entry<MethodReference, Lifecycle.Event> entry : map.entrySet()) {
                Lifecycle.Event event = (Lifecycle.Event) entry.getValue();
                List<MethodReference> methodReferences = (List) this.mEventToHandlers.get(event);
                if (methodReferences == null) {
                    methodReferences = new ArrayList<>();
                    this.mEventToHandlers.put(event, methodReferences);
                }
                methodReferences.add((MethodReference) entry.getKey());
            }
        }

        void invokeCallbacks(LifecycleOwner source, Lifecycle.Event event, Object target) {
            invokeMethodsForEvent((List) this.mEventToHandlers.get(event), source, event, target);
            invokeMethodsForEvent((List) this.mEventToHandlers.get(Lifecycle.Event.ON_ANY), source, event, target);
        }

        private static void invokeMethodsForEvent(List list, LifecycleOwner source, Lifecycle.Event event, Object mWrapped) {
            if (list != null) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    ((MethodReference) list.get(i)).invokeCallback(source, event, mWrapped);
                }
            }
        }
    }

    @Deprecated
    static final class MethodReference {
        final int mCallType;
        final Method mMethod;

        MethodReference(int callType, Method method) {
            this.mCallType = callType;
            this.mMethod = method;
            method.setAccessible(true);
        }

        void invokeCallback(LifecycleOwner source, Lifecycle.Event event, Object target) {
            try {
                switch (this.mCallType) {
                    case 0:
                        this.mMethod.invoke(target, new Object[0]);
                        return;
                    case 1:
                        this.mMethod.invoke(target, new Object[]{source});
                        return;
                    case 2:
                        this.mMethod.invoke(target, new Object[]{source, event});
                        return;
                    default:
                        return;
                }
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Failed to call observer method", e.getCause());
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof MethodReference)) {
                return false;
            }
            MethodReference that = (MethodReference) o;
            return this.mCallType == that.mCallType && this.mMethod.getName().equals(that.mMethod.getName());
        }

        public int hashCode() {
            return (this.mCallType * 31) + this.mMethod.getName().hashCode();
        }
    }
}
