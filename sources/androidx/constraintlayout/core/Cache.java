package androidx.constraintlayout.core;

import androidx.constraintlayout.core.Pools;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes40.dex */
public class Cache {
    Pools.Pool optimizedArrayRowPool = new Pools.SimplePool(256);
    Pools.Pool arrayRowPool = new Pools.SimplePool(256);
    Pools.Pool solverVariablePool = new Pools.SimplePool(256);
    SolverVariable[] mIndexedVariables = new SolverVariable[32];
}
