package com.google.android.material.transition;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class FadeModeEvaluators {
    private static final FadeModeEvaluator IN = new 1();
    private static final FadeModeEvaluator OUT = new 2();
    private static final FadeModeEvaluator CROSS = new 3();
    private static final FadeModeEvaluator THROUGH = new 4();

    class 1 implements FadeModeEvaluator {
        1() {
        }

        public FadeModeResult evaluate(float progress, float fadeStartFraction, float fadeEndFraction, float threshold) {
            int endAlpha = TransitionUtils.lerp(0, 255, fadeStartFraction, fadeEndFraction, progress);
            return FadeModeResult.endOnTop(255, endAlpha);
        }
    }

    class 2 implements FadeModeEvaluator {
        2() {
        }

        public FadeModeResult evaluate(float progress, float fadeStartFraction, float fadeEndFraction, float threshold) {
            int startAlpha = TransitionUtils.lerp(255, 0, fadeStartFraction, fadeEndFraction, progress);
            return FadeModeResult.startOnTop(startAlpha, 255);
        }
    }

    class 3 implements FadeModeEvaluator {
        3() {
        }

        public FadeModeResult evaluate(float progress, float fadeStartFraction, float fadeEndFraction, float threshold) {
            int startAlpha = TransitionUtils.lerp(255, 0, fadeStartFraction, fadeEndFraction, progress);
            int endAlpha = TransitionUtils.lerp(0, 255, fadeStartFraction, fadeEndFraction, progress);
            return FadeModeResult.startOnTop(startAlpha, endAlpha);
        }
    }

    class 4 implements FadeModeEvaluator {
        4() {
        }

        public FadeModeResult evaluate(float progress, float fadeStartFraction, float fadeEndFraction, float threshold) {
            float fadeFractionDiff = fadeEndFraction - fadeStartFraction;
            float fadeFractionThreshold = (fadeFractionDiff * threshold) + fadeStartFraction;
            int startAlpha = TransitionUtils.lerp(255, 0, fadeStartFraction, fadeFractionThreshold, progress);
            int endAlpha = TransitionUtils.lerp(0, 255, fadeFractionThreshold, fadeEndFraction, progress);
            return FadeModeResult.startOnTop(startAlpha, endAlpha);
        }
    }

    static FadeModeEvaluator get(int fadeMode, boolean entering) {
        switch (fadeMode) {
            case 0:
                return entering ? IN : OUT;
            case 1:
                return entering ? OUT : IN;
            case 2:
                return CROSS;
            case 3:
                return THROUGH;
            default:
                throw new IllegalArgumentException("Invalid fade mode: " + fadeMode);
        }
    }

    private FadeModeEvaluators() {
    }
}
