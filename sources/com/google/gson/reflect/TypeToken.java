package com.google.gson.reflect;

import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
public class TypeToken {
    final int hashCode;
    final Class rawType;
    final Type type;

    protected TypeToken() {
        this.type = getSuperclassTypeParameter(getClass());
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    TypeToken(Type type) {
        this.type = $Gson$Types.canonicalize((Type) $Gson$Preconditions.checkNotNull(type));
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    static Type getSuperclassTypeParameter(Class cls) {
        ParameterizedType genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = genericSuperclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public final Class getRawType() {
        return this.rawType;
    }

    public final Type getType() {
        return this.type;
    }

    @Deprecated
    public boolean isAssignableFrom(Class cls) {
        return isAssignableFrom((Type) cls);
    }

    @Deprecated
    public boolean isAssignableFrom(Type from) {
        if (from == null) {
            return false;
        }
        if (this.type.equals(from)) {
            return true;
        }
        if (this.type instanceof Class) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(from));
        }
        if (this.type instanceof ParameterizedType) {
            return isAssignableFrom(from, this.type, new HashMap());
        }
        if (this.type instanceof GenericArrayType) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(from)) && isAssignableFrom(from, this.type);
        }
        throw buildUnexpectedTypeError(this.type, Class.class, ParameterizedType.class, GenericArrayType.class);
    }

    @Deprecated
    public boolean isAssignableFrom(TypeToken typeToken) {
        return isAssignableFrom(typeToken.getType());
    }

    private static boolean isAssignableFrom(Type from, GenericArrayType to) {
        ParameterizedType genericComponentType = to.getGenericComponentType();
        if (genericComponentType instanceof ParameterizedType) {
            Type t = from;
            if (from instanceof GenericArrayType) {
                t = ((GenericArrayType) from).getGenericComponentType();
            } else if (from instanceof Class) {
                Type type = (Class) from;
                while (type.isArray()) {
                    type = type.getComponentType();
                }
                t = type;
            }
            return isAssignableFrom(t, genericComponentType, new HashMap());
        }
        return true;
    }

    private static boolean isAssignableFrom(Type from, ParameterizedType to, Map map) {
        if (from == null) {
            return false;
        }
        if (to.equals(from)) {
            return true;
        }
        Class<?> clazz = $Gson$Types.getRawType(from);
        ParameterizedType ptype = null;
        if (from instanceof ParameterizedType) {
            ptype = (ParameterizedType) from;
        }
        if (ptype != null) {
            Type[] tArgs = ptype.getActualTypeArguments();
            TypeVariable<?>[] tParams = clazz.getTypeParameters();
            for (int i = 0; i < tArgs.length; i++) {
                Type arg = tArgs[i];
                TypeVariable<?> var = tParams[i];
                while (arg instanceof TypeVariable) {
                    TypeVariable<?> v = (TypeVariable) arg;
                    arg = (Type) map.get(v.getName());
                }
                map.put(var.getName(), arg);
            }
            if (typeEquals(ptype, to, map)) {
                return true;
            }
        }
        for (Type itype : clazz.getGenericInterfaces()) {
            if (isAssignableFrom(itype, to, new HashMap(map))) {
                return true;
            }
        }
        Type sType = clazz.getGenericSuperclass();
        return isAssignableFrom(sType, to, new HashMap(map));
    }

    private static boolean typeEquals(ParameterizedType from, ParameterizedType to, Map map) {
        if (!from.getRawType().equals(to.getRawType())) {
            return false;
        }
        Type[] fromArgs = from.getActualTypeArguments();
        Type[] toArgs = to.getActualTypeArguments();
        for (int i = 0; i < fromArgs.length; i++) {
            if (!matches(fromArgs[i], toArgs[i], map)) {
                return false;
            }
        }
        return true;
    }

    private static AssertionError buildUnexpectedTypeError(Type token, Class... clsArr) {
        StringBuilder exceptionMessage = new StringBuilder("Unexpected type. Expected one of: ");
        for (Class cls : clsArr) {
            exceptionMessage.append(cls.getName()).append(", ");
        }
        exceptionMessage.append("but got: ").append(token.getClass().getName()).append(", for type token: ").append(token.toString()).append('.');
        return new AssertionError(exceptionMessage.toString());
    }

    private static boolean matches(Type from, Type to, Map map) {
        return to.equals(from) || ((from instanceof TypeVariable) && to.equals(map.get(((TypeVariable) from).getName())));
    }

    public final int hashCode() {
        return this.hashCode;
    }

    public final boolean equals(Object o) {
        return (o instanceof TypeToken) && $Gson$Types.equals(this.type, ((TypeToken) o).type);
    }

    public final String toString() {
        return $Gson$Types.typeToString(this.type);
    }

    public static TypeToken get(Type type) {
        return new TypeToken(type);
    }

    public static TypeToken get(Class cls) {
        return new TypeToken(cls);
    }

    public static TypeToken getParameterized(Type rawType, Type... typeArguments) {
        return new TypeToken($Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments));
    }

    public static TypeToken getArray(Type componentType) {
        return new TypeToken($Gson$Types.arrayOf(componentType));
    }
}
