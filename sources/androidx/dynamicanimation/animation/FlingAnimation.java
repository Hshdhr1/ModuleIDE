package androidx.dynamicanimation.animation;

import androidx.annotation.FloatRange;
import androidx.dynamicanimation.animation.DynamicAnimation;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes41.dex */
public final class FlingAnimation extends DynamicAnimation {
    private final DragForce mFlingForce;

    public FlingAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        this.mFlingForce = new DragForce();
        this.mFlingForce.setValueThreshold(getValueThreshold());
    }

    public FlingAnimation(Object obj, FloatPropertyCompat floatPropertyCompat) {
        super(obj, floatPropertyCompat);
        this.mFlingForce = new DragForce();
        this.mFlingForce.setValueThreshold(getValueThreshold());
    }

    public FlingAnimation setFriction(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Friction must be positive");
        }
        this.mFlingForce.setFrictionScalar(f);
        return this;
    }

    public float getFriction() {
        return this.mFlingForce.getFrictionScalar();
    }

    public FlingAnimation setMinValue(float f) {
        super.setMinValue(f);
        return this;
    }

    public FlingAnimation setMaxValue(float f) {
        super.setMaxValue(f);
        return this;
    }

    public FlingAnimation setStartVelocity(float f) {
        super.setStartVelocity(f);
        return this;
    }

    boolean updateValueAndVelocity(long j) {
        DynamicAnimation.MassState updateValueAndVelocity = this.mFlingForce.updateValueAndVelocity(this.mValue, this.mVelocity, j);
        this.mValue = updateValueAndVelocity.mValue;
        this.mVelocity = updateValueAndVelocity.mVelocity;
        if (this.mValue < this.mMinValue) {
            this.mValue = this.mMinValue;
            return true;
        }
        if (this.mValue > this.mMaxValue) {
            this.mValue = this.mMaxValue;
            return true;
        }
        if (isAtEquilibrium(this.mValue, this.mVelocity)) {
            return true;
        }
        return false;
    }

    float getAcceleration(float f, float f2) {
        return this.mFlingForce.getAcceleration(f, f2);
    }

    boolean isAtEquilibrium(float f, float f2) {
        return f >= this.mMaxValue || f <= this.mMinValue || this.mFlingForce.isAtEquilibrium(f, f2);
    }

    void setValueThreshold(float f) {
        this.mFlingForce.setValueThreshold(f);
    }

    static final class DragForce implements Force {
        private static final float DEFAULT_FRICTION = -4.2f;
        private static final float VELOCITY_THRESHOLD_MULTIPLIER = 62.5f;
        private float mFriction = -4.2f;
        private final DynamicAnimation.MassState mMassState = new DynamicAnimation.MassState();
        private float mVelocityThreshold;

        DragForce() {
        }

        void setFrictionScalar(float f) {
            this.mFriction = f * (-4.2f);
        }

        float getFrictionScalar() {
            return this.mFriction / (-4.2f);
        }

        DynamicAnimation.MassState updateValueAndVelocity(float f, float f2, long j) {
            this.mMassState.mVelocity = (float) (f2 * Math.exp((j / 1000.0f) * this.mFriction));
            this.mMassState.mValue = (float) ((f - (f2 / this.mFriction)) + ((f2 / this.mFriction) * Math.exp((this.mFriction * j) / 1000.0f)));
            if (isAtEquilibrium(this.mMassState.mValue, this.mMassState.mVelocity)) {
                this.mMassState.mVelocity = 0.0f;
            }
            return this.mMassState;
        }

        public float getAcceleration(float f, float f2) {
            return f2 * this.mFriction;
        }

        public boolean isAtEquilibrium(float f, float f2) {
            return Math.abs(f2) < this.mVelocityThreshold;
        }

        void setValueThreshold(float f) {
            this.mVelocityThreshold = f * 62.5f;
        }
    }
}
