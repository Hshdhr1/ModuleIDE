package org.antlr.v4.runtime.misc;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class IntegerStack extends IntegerList {
    public IntegerStack() {
    }

    public IntegerStack(int capacity) {
        super(capacity);
    }

    public IntegerStack(IntegerStack list) {
        super(list);
    }

    public final void push(int value) {
        add(value);
    }

    public final int pop() {
        return removeAt(size() - 1);
    }

    public final int peek() {
        return get(size() - 1);
    }
}
