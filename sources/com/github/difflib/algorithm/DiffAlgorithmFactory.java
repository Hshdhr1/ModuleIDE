package com.github.difflib.algorithm;

import java.util.function.BiPredicate;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public interface DiffAlgorithmFactory {
    DiffAlgorithmI create();

    DiffAlgorithmI create(BiPredicate biPredicate);
}
