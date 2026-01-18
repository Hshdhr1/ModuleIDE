package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.fragment.R;
import androidx.fragment.app.FragmentTransition;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
class FragmentAnim {
    private FragmentAnim() {
    }

    static AnimationOrAnimator loadAnimation(Context context, Fragment fragment, boolean enter, boolean isPop) {
        int transit = fragment.getNextTransition();
        int nextAnim = getNextAnim(fragment, enter, isPop);
        fragment.setAnimations(0, 0, 0, 0);
        if (fragment.mContainer != null && fragment.mContainer.getTag(R.id.visible_removing_fragment_view_tag) != null) {
            fragment.mContainer.setTag(R.id.visible_removing_fragment_view_tag, (Object) null);
        }
        if (fragment.mContainer != null && fragment.mContainer.getLayoutTransition() != null) {
            return null;
        }
        Animation animation = fragment.onCreateAnimation(transit, enter, nextAnim);
        if (animation != null) {
            return new AnimationOrAnimator(animation);
        }
        Animator animator = fragment.onCreateAnimator(transit, enter, nextAnim);
        if (animator != null) {
            return new AnimationOrAnimator(animator);
        }
        if (nextAnim == 0 && transit != 0) {
            nextAnim = transitToAnimResourceId(transit, enter);
        }
        if (nextAnim != 0) {
            String dir = context.getResources().getResourceTypeName(nextAnim);
            boolean isAnim = "anim".equals(dir);
            boolean successfulLoad = false;
            if (isAnim) {
                try {
                    Animation animation2 = AnimationUtils.loadAnimation(context, nextAnim);
                    if (animation2 != null) {
                        return new AnimationOrAnimator(animation2);
                    }
                    successfulLoad = true;
                } catch (RuntimeException e) {
                } catch (Resources.NotFoundException e2) {
                    throw e2;
                }
            }
            if (!successfulLoad) {
                try {
                    Animator animator2 = AnimatorInflater.loadAnimator(context, nextAnim);
                    if (animator2 != null) {
                        return new AnimationOrAnimator(animator2);
                    }
                } catch (RuntimeException e3) {
                    if (isAnim) {
                        throw e3;
                    }
                    Animation animation3 = AnimationUtils.loadAnimation(context, nextAnim);
                    if (animation3 != null) {
                        return new AnimationOrAnimator(animation3);
                    }
                }
            }
        }
        return null;
    }

    private static int getNextAnim(Fragment fragment, boolean enter, boolean isPop) {
        if (isPop) {
            if (enter) {
                return fragment.getPopEnterAnim();
            }
            return fragment.getPopExitAnim();
        }
        if (enter) {
            return fragment.getEnterAnim();
        }
        return fragment.getExitAnim();
    }

    static void animateRemoveFragment(Fragment fragment, AnimationOrAnimator anim, FragmentTransition.Callback callback) {
        View viewToAnimate = fragment.mView;
        ViewGroup container = fragment.mContainer;
        container.startViewTransition(viewToAnimate);
        CancellationSignal signal = new CancellationSignal();
        signal.setOnCancelListener(new 1(fragment));
        callback.onStart(fragment, signal);
        if (anim.animation != null) {
            EndViewTransitionAnimation endViewTransitionAnimation = new EndViewTransitionAnimation(anim.animation, container, viewToAnimate);
            fragment.setAnimatingAway(fragment.mView);
            endViewTransitionAnimation.setAnimationListener(new 2(container, fragment, callback, signal));
            fragment.mView.startAnimation(endViewTransitionAnimation);
            return;
        }
        Animator animator = anim.animator;
        fragment.setAnimator(anim.animator);
        animator.addListener(new 3(container, viewToAnimate, fragment, callback, signal));
        animator.setTarget(fragment.mView);
        animator.start();
    }

    class 1 implements CancellationSignal.OnCancelListener {
        final /* synthetic */ Fragment val$fragment;

        1(Fragment fragment) {
            this.val$fragment = fragment;
        }

        public void onCancel() {
            if (this.val$fragment.getAnimatingAway() != null) {
                View v = this.val$fragment.getAnimatingAway();
                this.val$fragment.setAnimatingAway(null);
                v.clearAnimation();
            }
            this.val$fragment.setAnimator(null);
        }
    }

    class 2 implements Animation.AnimationListener {
        final /* synthetic */ FragmentTransition.Callback val$callback;
        final /* synthetic */ ViewGroup val$container;
        final /* synthetic */ Fragment val$fragment;
        final /* synthetic */ CancellationSignal val$signal;

        2(ViewGroup viewGroup, Fragment fragment, FragmentTransition.Callback callback, CancellationSignal cancellationSignal) {
            this.val$container = viewGroup;
            this.val$fragment = fragment;
            this.val$callback = callback;
            this.val$signal = cancellationSignal;
        }

        public void onAnimationStart(Animation animation) {
        }

        class 1 implements Runnable {
            1() {
            }

            public void run() {
                if (2.this.val$fragment.getAnimatingAway() != null) {
                    2.this.val$fragment.setAnimatingAway(null);
                    2.this.val$callback.onComplete(2.this.val$fragment, 2.this.val$signal);
                }
            }
        }

        public void onAnimationEnd(Animation animation) {
            this.val$container.post(new 1());
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class 3 extends AnimatorListenerAdapter {
        final /* synthetic */ FragmentTransition.Callback val$callback;
        final /* synthetic */ ViewGroup val$container;
        final /* synthetic */ Fragment val$fragment;
        final /* synthetic */ CancellationSignal val$signal;
        final /* synthetic */ View val$viewToAnimate;

        3(ViewGroup viewGroup, View view, Fragment fragment, FragmentTransition.Callback callback, CancellationSignal cancellationSignal) {
            this.val$container = viewGroup;
            this.val$viewToAnimate = view;
            this.val$fragment = fragment;
            this.val$callback = callback;
            this.val$signal = cancellationSignal;
        }

        public void onAnimationEnd(Animator anim) {
            this.val$container.endViewTransition(this.val$viewToAnimate);
            Animator animator = this.val$fragment.getAnimator();
            this.val$fragment.setAnimator(null);
            if (animator != null && this.val$container.indexOfChild(this.val$viewToAnimate) < 0) {
                this.val$callback.onComplete(this.val$fragment, this.val$signal);
            }
        }
    }

    private static int transitToAnimResourceId(int transit, boolean enter) {
        switch (transit) {
            case 4097:
                int animAttr = enter ? R.animator.fragment_open_enter : R.animator.fragment_open_exit;
                return animAttr;
            case 4099:
                int animAttr2 = enter ? R.animator.fragment_fade_enter : R.animator.fragment_fade_exit;
                return animAttr2;
            case 8194:
                int animAttr3 = enter ? R.animator.fragment_close_enter : R.animator.fragment_close_exit;
                return animAttr3;
            default:
                return -1;
        }
    }

    static class AnimationOrAnimator {
        public final Animation animation;
        public final Animator animator;

        AnimationOrAnimator(Animation animation) {
            this.animation = animation;
            this.animator = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }

        AnimationOrAnimator(Animator animator) {
            this.animation = null;
            this.animator = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }
    }

    static class EndViewTransitionAnimation extends AnimationSet implements Runnable {
        private boolean mAnimating;
        private final View mChild;
        private boolean mEnded;
        private final ViewGroup mParent;
        private boolean mTransitionEnded;

        EndViewTransitionAnimation(Animation animation, ViewGroup parent, View child) {
            super(false);
            this.mAnimating = true;
            this.mParent = parent;
            this.mChild = child;
            addAnimation(animation);
            parent.post(this);
        }

        public boolean getTransformation(long currentTime, Transformation t) {
            this.mAnimating = true;
            if (this.mEnded) {
                return true ^ this.mTransitionEnded;
            }
            boolean more = super.getTransformation(currentTime, t);
            if (!more) {
                this.mEnded = true;
                OneShotPreDrawListener.add(this.mParent, this);
            }
            return true;
        }

        public boolean getTransformation(long currentTime, Transformation outTransformation, float scale) {
            this.mAnimating = true;
            if (this.mEnded) {
                return true ^ this.mTransitionEnded;
            }
            boolean more = super.getTransformation(currentTime, outTransformation, scale);
            if (!more) {
                this.mEnded = true;
                OneShotPreDrawListener.add(this.mParent, this);
            }
            return true;
        }

        public void run() {
            if (!this.mEnded && this.mAnimating) {
                this.mAnimating = false;
                this.mParent.post(this);
            } else {
                this.mParent.endViewTransition(this.mChild);
                this.mTransitionEnded = true;
            }
        }
    }
}
