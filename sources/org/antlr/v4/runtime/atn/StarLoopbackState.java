package org.antlr.v4.runtime.atn;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public final class StarLoopbackState extends ATNState {
    public final StarLoopEntryState getLoopEntryState() {
        return (StarLoopEntryState) transition(0).target;
    }

    public int getStateType() {
        return 9;
    }
}
