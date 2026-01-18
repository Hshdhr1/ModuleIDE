package com.google.android.material.transition;

import android.graphics.RectF;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class FitModeEvaluators {
    private static final FitModeEvaluator WIDTH = new 1();
    private static final FitModeEvaluator HEIGHT = new 2();

    class 1 implements FitModeEvaluator {
        1() {
        }

        public FitModeResult evaluate(float progress, float scaleStartFraction, float scaleEndFraction, float startWidth, float startHeight, float endWidth, float endHeight) {
            float currentWidth = TransitionUtils.lerp(startWidth, endWidth, scaleStartFraction, scaleEndFraction, progress, true);
            float startScale = currentWidth / startWidth;
            float endScale = currentWidth / endWidth;
            float currentStartHeight = startHeight * startScale;
            float currentEndHeight = endHeight * endScale;
            return new FitModeResult(startScale, endScale, currentWidth, currentStartHeight, currentWidth, currentEndHeight);
        }

        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartHeight > fitModeResult.currentEndHeight;
        }

        public void applyMask(RectF maskBounds, float maskMultiplier, FitModeResult fitModeResult) {
            float currentHeightDiff = Math.abs(fitModeResult.currentEndHeight - fitModeResult.currentStartHeight);
            maskBounds.bottom -= currentHeightDiff * maskMultiplier;
        }
    }

    class 2 implements FitModeEvaluator {
        2() {
        }

        public FitModeResult evaluate(float progress, float scaleStartFraction, float scaleEndFraction, float startWidth, float startHeight, float endWidth, float endHeight) {
            float currentHeight = TransitionUtils.lerp(startHeight, endHeight, scaleStartFraction, scaleEndFraction, progress, true);
            float startScale = currentHeight / startHeight;
            float endScale = currentHeight / endHeight;
            float currentStartWidth = startWidth * startScale;
            float currentEndWidth = endWidth * endScale;
            return new FitModeResult(startScale, endScale, currentStartWidth, currentHeight, currentEndWidth, currentHeight);
        }

        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartWidth > fitModeResult.currentEndWidth;
        }

        public void applyMask(RectF maskBounds, float maskMultiplier, FitModeResult fitModeResult) {
            float currentWidthDiff = Math.abs(fitModeResult.currentEndWidth - fitModeResult.currentStartWidth);
            maskBounds.left += (currentWidthDiff / 2.0f) * maskMultiplier;
            maskBounds.right -= (currentWidthDiff / 2.0f) * maskMultiplier;
        }
    }

    static FitModeEvaluator get(int fitMode, boolean entering, RectF startBounds, RectF endBounds) {
        switch (fitMode) {
            case 0:
                return shouldAutoFitToWidth(entering, startBounds, endBounds) ? WIDTH : HEIGHT;
            case 1:
                return WIDTH;
            case 2:
                return HEIGHT;
            default:
                throw new IllegalArgumentException("Invalid fit mode: " + fitMode);
        }
    }

    private static boolean shouldAutoFitToWidth(boolean entering, RectF startBounds, RectF endBounds) {
        float startWidth = startBounds.width();
        float startHeight = startBounds.height();
        float endWidth = endBounds.width();
        float endHeight = endBounds.height();
        float endHeightFitToWidth = (endHeight * startWidth) / endWidth;
        float startHeightFitToWidth = (startHeight * endWidth) / startWidth;
        if (entering) {
            if (endHeightFitToWidth >= startHeight) {
                return true;
            }
        } else if (startHeightFitToWidth >= endHeight) {
            return true;
        }
        return false;
    }

    private FitModeEvaluators() {
    }
}
