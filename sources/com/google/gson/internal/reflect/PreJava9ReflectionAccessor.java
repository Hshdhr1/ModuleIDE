package com.google.gson.internal.reflect;

import java.lang.reflect.AccessibleObject;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes53.dex */
final class PreJava9ReflectionAccessor extends ReflectionAccessor {
    PreJava9ReflectionAccessor() {
    }

    public void makeAccessible(AccessibleObject ao) {
        ao.setAccessible(true);
    }
}
