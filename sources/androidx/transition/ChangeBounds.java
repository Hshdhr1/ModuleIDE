package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import androidx.transition.Transition;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
public class ChangeBounds extends Transition {
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private boolean mReparent;
    private boolean mResizeClip;
    private int[] mTempLocation;
    private static final String[] sTransitionProperties = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    private static final Property DRAWABLE_ORIGIN_PROPERTY = new 1(PointF.class, "boundsOrigin");
    private static final Property TOP_LEFT_PROPERTY = new 2(PointF.class, "topLeft");
    private static final Property BOTTOM_RIGHT_PROPERTY = new 3(PointF.class, "bottomRight");
    private static final Property BOTTOM_RIGHT_ONLY_PROPERTY = new 4(PointF.class, "bottomRight");
    private static final Property TOP_LEFT_ONLY_PROPERTY = new 5(PointF.class, "topLeft");
    private static final Property POSITION_PROPERTY = new 6(PointF.class, "position");
    private static RectEvaluator sRectEvaluator = new RectEvaluator();

    class 1 extends Property {
        private Rect mBounds;

        1(Class cls, String arg1) {
            super(cls, arg1);
            this.mBounds = new Rect();
        }

        public void set(Drawable object, PointF value) {
            object.copyBounds(this.mBounds);
            this.mBounds.offsetTo(Math.round(value.x), Math.round(value.y));
            object.setBounds(this.mBounds);
        }

        public PointF get(Drawable object) {
            object.copyBounds(this.mBounds);
            return new PointF(this.mBounds.left, this.mBounds.top);
        }
    }

    class 2 extends Property {
        2(Class cls, String arg1) {
            super(cls, arg1);
        }

        public void set(ViewBounds viewBounds, PointF topLeft) {
            viewBounds.setTopLeft(topLeft);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    }

    class 3 extends Property {
        3(Class cls, String arg1) {
            super(cls, arg1);
        }

        public void set(ViewBounds viewBounds, PointF bottomRight) {
            viewBounds.setBottomRight(bottomRight);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    }

    class 4 extends Property {
        4(Class cls, String arg1) {
            super(cls, arg1);
        }

        public void set(View view, PointF bottomRight) {
            int left = view.getLeft();
            int top = view.getTop();
            int right = Math.round(bottomRight.x);
            int bottom = Math.round(bottomRight.y);
            ViewUtils.setLeftTopRightBottom(view, left, top, right, bottom);
        }

        public PointF get(View view) {
            return null;
        }
    }

    class 5 extends Property {
        5(Class cls, String arg1) {
            super(cls, arg1);
        }

        public void set(View view, PointF topLeft) {
            int left = Math.round(topLeft.x);
            int top = Math.round(topLeft.y);
            int right = view.getRight();
            int bottom = view.getBottom();
            ViewUtils.setLeftTopRightBottom(view, left, top, right, bottom);
        }

        public PointF get(View view) {
            return null;
        }
    }

    class 6 extends Property {
        6(Class cls, String arg1) {
            super(cls, arg1);
        }

        public void set(View view, PointF topLeft) {
            int left = Math.round(topLeft.x);
            int top = Math.round(topLeft.y);
            int right = left + view.getWidth();
            int bottom = top + view.getHeight();
            ViewUtils.setLeftTopRightBottom(view, left, top, right, bottom);
        }

        public PointF get(View view) {
            return null;
        }
    }

    public ChangeBounds() {
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
    }

    @SuppressLint({"RestrictedApi"})
    public ChangeBounds(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.CHANGE_BOUNDS);
        boolean resizeClip = TypedArrayUtils.getNamedBoolean(a, (XmlResourceParser) attrs, "resizeClip", 0, false);
        a.recycle();
        setResizeClip(resizeClip);
    }

    @NonNull
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean resizeClip) {
        this.mResizeClip = resizeClip;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            values.values.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            values.values.put("android:changeBounds:parent", values.view.getParent());
            if (this.mReparent) {
                values.view.getLocationInWindow(this.mTempLocation);
                values.values.put("android:changeBounds:windowX", Integer.valueOf(this.mTempLocation[0]));
                values.values.put("android:changeBounds:windowY", Integer.valueOf(this.mTempLocation[1]));
            }
            if (this.mResizeClip) {
                values.values.put("android:changeBounds:clip", ViewCompat.getClipBounds(view));
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private boolean parentMatches(View startParent, View endParent) {
        if (!this.mReparent) {
            return true;
        }
        TransitionValues endValues = getMatchedTransitionValues(startParent, true);
        return endValues == null ? startParent == endParent : endParent == endValues.view;
    }

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        ObjectAnimator mergeAnimators;
        if (startValues == null || endValues == null) {
            return null;
        }
        Map<String, Object> startParentVals = startValues.values;
        Map<String, Object> endParentVals = endValues.values;
        ViewGroup startParent = (ViewGroup) startParentVals.get("android:changeBounds:parent");
        ViewGroup endParent = (ViewGroup) endParentVals.get("android:changeBounds:parent");
        if (startParent == null || endParent == null) {
            return null;
        }
        View view = endValues.view;
        if (parentMatches(startParent, endParent)) {
            Rect startBounds = (Rect) startValues.values.get("android:changeBounds:bounds");
            Rect endBounds = (Rect) endValues.values.get("android:changeBounds:bounds");
            int startLeft = startBounds.left;
            int endLeft = endBounds.left;
            int startTop = startBounds.top;
            int endTop = endBounds.top;
            int startRight = startBounds.right;
            int endRight = endBounds.right;
            int startBottom = startBounds.bottom;
            int endBottom = endBounds.bottom;
            int startWidth = startRight - startLeft;
            int startHeight = startBottom - startTop;
            int endWidth = endRight - endLeft;
            int endHeight = endBottom - endTop;
            Rect startClip = (Rect) startValues.values.get("android:changeBounds:clip");
            Rect endClip = (Rect) endValues.values.get("android:changeBounds:clip");
            if ((startWidth != 0 && startHeight != 0) || (endWidth != 0 && endHeight != 0)) {
                numChanges = (startLeft == endLeft && startTop == endTop) ? 0 : 0 + 1;
                if (startRight != endRight || startBottom != endBottom) {
                    numChanges++;
                }
            }
            if ((startClip != null && !startClip.equals(endClip)) || (startClip == null && endClip != null)) {
                numChanges++;
            }
            if (numChanges > 0) {
                if (!this.mResizeClip) {
                    ViewUtils.setLeftTopRightBottom(view, startLeft, startTop, startRight, startBottom);
                    if (numChanges == 2) {
                        if (startWidth == endWidth && startHeight == endHeight) {
                            Path topLeftPath = getPathMotion().getPath(startLeft, startTop, endLeft, endTop);
                            mergeAnimators = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, topLeftPath);
                        } else {
                            ViewBounds viewBounds = new ViewBounds(view);
                            Path topLeftPath2 = getPathMotion().getPath(startLeft, startTop, endLeft, endTop);
                            Animator ofPointF = ObjectAnimatorUtils.ofPointF(viewBounds, TOP_LEFT_PROPERTY, topLeftPath2);
                            Path bottomRightPath = getPathMotion().getPath(startRight, startBottom, endRight, endBottom);
                            Animator ofPointF2 = ObjectAnimatorUtils.ofPointF(viewBounds, BOTTOM_RIGHT_PROPERTY, bottomRightPath);
                            ObjectAnimator animatorSet = new AnimatorSet();
                            animatorSet.playTogether(new Animator[]{ofPointF, ofPointF2});
                            mergeAnimators = animatorSet;
                            animatorSet.addListener(new 7(viewBounds));
                        }
                    } else if (startLeft != endLeft || startTop != endTop) {
                        Path topLeftPath3 = getPathMotion().getPath(startLeft, startTop, endLeft, endTop);
                        mergeAnimators = ObjectAnimatorUtils.ofPointF(view, TOP_LEFT_ONLY_PROPERTY, topLeftPath3);
                    } else {
                        Path bottomRight = getPathMotion().getPath(startRight, startBottom, endRight, endBottom);
                        mergeAnimators = ObjectAnimatorUtils.ofPointF(view, BOTTOM_RIGHT_ONLY_PROPERTY, bottomRight);
                    }
                } else {
                    int maxWidth = Math.max(startWidth, endWidth);
                    int maxHeight = Math.max(startHeight, endHeight);
                    ViewUtils.setLeftTopRightBottom(view, startLeft, startTop, startLeft + maxWidth, startTop + maxHeight);
                    Animator animator = null;
                    if (startLeft != endLeft || startTop != endTop) {
                        Path topLeftPath4 = getPathMotion().getPath(startLeft, startTop, endLeft, endTop);
                        animator = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, topLeftPath4);
                    }
                    if (startClip == null) {
                        startClip = new Rect(0, 0, startWidth, startHeight);
                    }
                    if (endClip == null) {
                        endClip = new Rect(0, 0, endWidth, endHeight);
                    }
                    ObjectAnimator clipAnimator = null;
                    if (!startClip.equals(endClip)) {
                        ViewCompat.setClipBounds(view, startClip);
                        clipAnimator = ObjectAnimator.ofObject(view, "clipBounds", sRectEvaluator, new Object[]{startClip, endClip});
                        clipAnimator.addListener(new 8(view, endClip, endLeft, endTop, endRight, endBottom));
                    }
                    mergeAnimators = TransitionUtils.mergeAnimators(animator, clipAnimator);
                }
                if (view.getParent() instanceof ViewGroup) {
                    ViewGroup parent = view.getParent();
                    ViewGroupUtils.suppressLayout(parent, true);
                    Transition.TransitionListener transitionListener = new 9(parent);
                    addListener(transitionListener);
                    return mergeAnimators;
                }
                return mergeAnimators;
            }
        } else {
            int startX = ((Integer) startValues.values.get("android:changeBounds:windowX")).intValue();
            int startY = ((Integer) startValues.values.get("android:changeBounds:windowY")).intValue();
            int endX = ((Integer) endValues.values.get("android:changeBounds:windowX")).intValue();
            int endY = ((Integer) endValues.values.get("android:changeBounds:windowY")).intValue();
            if (startX != endX || startY != endY) {
                sceneRoot.getLocationInWindow(this.mTempLocation);
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
                Drawable bitmapDrawable = new BitmapDrawable(bitmap);
                float transitionAlpha = ViewUtils.getTransitionAlpha(view);
                ViewUtils.setTransitionAlpha(view, 0.0f);
                ViewUtils.getOverlay(sceneRoot).add(bitmapDrawable);
                Path topLeftPath5 = getPathMotion().getPath(startX - this.mTempLocation[0], startY - this.mTempLocation[1], endX - this.mTempLocation[0], endY - this.mTempLocation[1]);
                PropertyValuesHolder origin = PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, topLeftPath5);
                ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, new PropertyValuesHolder[]{origin});
                anim.addListener(new 10(sceneRoot, bitmapDrawable, view, transitionAlpha));
                return anim;
            }
        }
        return null;
    }

    class 7 extends AnimatorListenerAdapter {
        private ViewBounds mViewBounds;
        final /* synthetic */ ViewBounds val$viewBounds;

        7(ViewBounds viewBounds) {
            this.val$viewBounds = viewBounds;
            this.mViewBounds = this.val$viewBounds;
        }
    }

    class 8 extends AnimatorListenerAdapter {
        private boolean mIsCanceled;
        final /* synthetic */ int val$endBottom;
        final /* synthetic */ int val$endLeft;
        final /* synthetic */ int val$endRight;
        final /* synthetic */ int val$endTop;
        final /* synthetic */ Rect val$finalClip;
        final /* synthetic */ View val$view;

        8(View view, Rect rect, int i, int i2, int i3, int i4) {
            this.val$view = view;
            this.val$finalClip = rect;
            this.val$endLeft = i;
            this.val$endTop = i2;
            this.val$endRight = i3;
            this.val$endBottom = i4;
        }

        public void onAnimationCancel(Animator animation) {
            this.mIsCanceled = true;
        }

        public void onAnimationEnd(Animator animation) {
            if (!this.mIsCanceled) {
                ViewCompat.setClipBounds(this.val$view, this.val$finalClip);
                ViewUtils.setLeftTopRightBottom(this.val$view, this.val$endLeft, this.val$endTop, this.val$endRight, this.val$endBottom);
            }
        }
    }

    class 9 extends TransitionListenerAdapter {
        boolean mCanceled = false;
        final /* synthetic */ ViewGroup val$parent;

        9(ViewGroup viewGroup) {
            this.val$parent = viewGroup;
        }

        public void onTransitionCancel(@NonNull Transition transition) {
            ViewGroupUtils.suppressLayout(this.val$parent, false);
            this.mCanceled = true;
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            if (!this.mCanceled) {
                ViewGroupUtils.suppressLayout(this.val$parent, false);
            }
            transition.removeListener(this);
        }

        public void onTransitionPause(@NonNull Transition transition) {
            ViewGroupUtils.suppressLayout(this.val$parent, false);
        }

        public void onTransitionResume(@NonNull Transition transition) {
            ViewGroupUtils.suppressLayout(this.val$parent, true);
        }
    }

    class 10 extends AnimatorListenerAdapter {
        final /* synthetic */ BitmapDrawable val$drawable;
        final /* synthetic */ ViewGroup val$sceneRoot;
        final /* synthetic */ float val$transitionAlpha;
        final /* synthetic */ View val$view;

        10(ViewGroup viewGroup, BitmapDrawable bitmapDrawable, View view, float f) {
            this.val$sceneRoot = viewGroup;
            this.val$drawable = bitmapDrawable;
            this.val$view = view;
            this.val$transitionAlpha = f;
        }

        public void onAnimationEnd(Animator animation) {
            ViewUtils.getOverlay(this.val$sceneRoot).remove(this.val$drawable);
            ViewUtils.setTransitionAlpha(this.val$view, this.val$transitionAlpha);
        }
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        void setTopLeft(PointF topLeft) {
            this.mLeft = Math.round(topLeft.x);
            this.mTop = Math.round(topLeft.y);
            this.mTopLeftCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        void setBottomRight(PointF bottomRight) {
            this.mRight = Math.round(bottomRight.x);
            this.mBottom = Math.round(bottomRight.y);
            this.mBottomRightCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }
    }
}
