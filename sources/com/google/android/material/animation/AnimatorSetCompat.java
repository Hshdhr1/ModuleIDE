package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class AnimatorSetCompat {
    public static void playTogether(AnimatorSet animatorSet, List list) {
        long totalDuration = 0;
        int count = list.size();
        for (int i = 0; i < count; i++) {
            Animator animator = (Animator) list.get(i);
            totalDuration = Math.max(totalDuration, animator.getStartDelay() + animator.getDuration());
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 0});
        ofInt.setDuration(totalDuration);
        list.add(0, ofInt);
        animatorSet.playTogether(list);
    }
}
