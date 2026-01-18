package okhttp3.internal.platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes54.dex */
class OptionalMethod {
    private final String methodName;
    private final Class[] methodParams;
    private final Class returnType;

    OptionalMethod(Class cls, String methodName, Class... methodParams) {
        this.returnType = cls;
        this.methodName = methodName;
        this.methodParams = methodParams;
    }

    public boolean isSupported(Object obj) {
        return getMethod(obj.getClass()) != null;
    }

    public Object invokeOptional(Object obj, Object... args) throws InvocationTargetException {
        Method m = getMethod(obj.getClass());
        if (m == null) {
            return null;
        }
        try {
            return m.invoke(obj, args);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public Object invokeOptionalWithoutCheckedException(Object obj, Object... args) {
        try {
            return invokeOptional(obj, args);
        } catch (InvocationTargetException e) {
            RuntimeException targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw targetException;
            }
            AssertionError error = new AssertionError("Unexpected exception");
            error.initCause(targetException);
            throw error;
        }
    }

    public Object invoke(Object obj, Object... args) throws InvocationTargetException {
        Method m = getMethod(obj.getClass());
        if (m == null) {
            throw new AssertionError("Method " + this.methodName + " not supported for object " + obj);
        }
        try {
            return m.invoke(obj, args);
        } catch (IllegalAccessException e) {
            AssertionError error = new AssertionError("Unexpectedly could not call: " + m);
            error.initCause(e);
            throw error;
        }
    }

    public Object invokeWithoutCheckedException(Object obj, Object... args) {
        try {
            return invoke(obj, args);
        } catch (InvocationTargetException e) {
            RuntimeException targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw targetException;
            }
            AssertionError error = new AssertionError("Unexpected exception");
            error.initCause(targetException);
            throw error;
        }
    }

    private Method getMethod(Class cls) {
        if (this.methodName == null) {
            return null;
        }
        Method method = getPublicMethod(cls, this.methodName, this.methodParams);
        if (method != null && this.returnType != null && !this.returnType.isAssignableFrom(method.getReturnType())) {
            return null;
        }
        return method;
    }

    private static Method getPublicMethod(Class cls, String methodName, Class[] parameterTypes) {
        Method method = null;
        try {
            method = cls.getMethod(methodName, parameterTypes);
            if ((method.getModifiers() & 1) == 0) {
                return null;
            }
            return method;
        } catch (NoSuchMethodException e) {
            return method;
        }
    }
}
