package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import java.util.ArrayList;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class StateListAnimator {
    private final ArrayList tuples = new ArrayList();
    private Tuple lastMatch = null;
    ValueAnimator runningAnimator = null;
    private final Animator.AnimatorListener animationListener = new 1();

    class 1 extends AnimatorListenerAdapter {
        1() {
        }

        public void onAnimationEnd(Animator animator) {
            if (StateListAnimator.this.runningAnimator == animator) {
                StateListAnimator.this.runningAnimator = null;
            }
        }
    }

    public void addState(int[] specs, ValueAnimator animator) {
        Tuple tuple = new Tuple(specs, animator);
        animator.addListener(this.animationListener);
        this.tuples.add(tuple);
    }

    public void setState(int[] state) {
        Tuple match = null;
        int count = this.tuples.size();
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            }
            Tuple tuple = (Tuple) this.tuples.get(i);
            if (!StateSet.stateSetMatches(tuple.specs, state)) {
                i++;
            } else {
                match = tuple;
                break;
            }
        }
        Tuple tuple2 = this.lastMatch;
        if (match == tuple2) {
            return;
        }
        if (tuple2 != null) {
            cancel();
        }
        this.lastMatch = match;
        if (match != null) {
            start(match);
        }
    }

    private void start(Tuple match) {
        ValueAnimator valueAnimator = match.animator;
        this.runningAnimator = valueAnimator;
        valueAnimator.start();
    }

    private void cancel() {
        ValueAnimator valueAnimator = this.runningAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.runningAnimator = null;
        }
    }

    public void jumpToCurrentState() {
        ValueAnimator valueAnimator = this.runningAnimator;
        if (valueAnimator != null) {
            valueAnimator.end();
            this.runningAnimator = null;
        }
    }

    static class Tuple {
        final ValueAnimator animator;
        final int[] specs;

        Tuple(int[] specs, ValueAnimator animator) {
            this.specs = specs;
            this.animator = animator;
        }
    }
}
