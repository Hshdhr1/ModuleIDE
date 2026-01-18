package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.shape.MaterialShapeDrawable;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class BottomSheetDialog extends AppCompatDialog {
    private BottomSheetBehavior behavior;
    private FrameLayout bottomSheet;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    boolean cancelable;
    private boolean canceledOnTouchOutside;
    private boolean canceledOnTouchOutsideSet;
    private FrameLayout container;
    private CoordinatorLayout coordinator;
    boolean dismissWithAnimation;
    private BottomSheetBehavior.BottomSheetCallback edgeToEdgeCallback;
    private boolean edgeToEdgeEnabled;

    static /* synthetic */ BottomSheetBehavior.BottomSheetCallback access$000(BottomSheetDialog x0) {
        return x0.edgeToEdgeCallback;
    }

    static /* synthetic */ BottomSheetBehavior.BottomSheetCallback access$002(BottomSheetDialog x0, BottomSheetBehavior.BottomSheetCallback x1) {
        x0.edgeToEdgeCallback = x1;
        return x1;
    }

    static /* synthetic */ BottomSheetBehavior access$100(BottomSheetDialog x0) {
        return x0.behavior;
    }

    static /* synthetic */ FrameLayout access$200(BottomSheetDialog x0) {
        return x0.bottomSheet;
    }

    public BottomSheetDialog(Context context) {
        this(context, 0);
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    public BottomSheetDialog(Context context, int theme) {
        super(context, getThemeResId(context, theme));
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new 5();
        supportRequestWindowFeature(1);
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    protected BottomSheetDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new 5();
        supportRequestWindowFeature(1);
        this.cancelable = cancelable;
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    public void setContentView(int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.setStatusBarColor(0);
                window.addFlags(Integer.MIN_VALUE);
                if (Build.VERSION.SDK_INT < 23) {
                    window.addFlags(67108864);
                }
            }
            window.setLayout(-1, -1);
        }
    }

    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (this.cancelable != cancelable) {
            this.cancelable = cancelable;
            BottomSheetBehavior bottomSheetBehavior = this.behavior;
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.setHideable(cancelable);
            }
        }
    }

    protected void onStart() {
        super.onStart();
        BottomSheetBehavior bottomSheetBehavior = this.behavior;
        if (bottomSheetBehavior != null && bottomSheetBehavior.getState() == 5) {
            this.behavior.setState(4);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        if (window != null && Build.VERSION.SDK_INT >= 21) {
            boolean drawEdgeToEdge = this.edgeToEdgeEnabled && Color.alpha(window.getNavigationBarColor()) < 255;
            FrameLayout frameLayout = this.container;
            if (frameLayout != null) {
                frameLayout.setFitsSystemWindows(!drawEdgeToEdge);
            }
            CoordinatorLayout coordinatorLayout = this.coordinator;
            if (coordinatorLayout != null) {
                coordinatorLayout.setFitsSystemWindows(drawEdgeToEdge ? false : true);
            }
            if (drawEdgeToEdge) {
                window.getDecorView().setSystemUiVisibility(768);
            }
        }
    }

    public void cancel() {
        BottomSheetBehavior<FrameLayout> behavior = getBehavior();
        if (!this.dismissWithAnimation || behavior.getState() == 5) {
            super.cancel();
        } else {
            behavior.setState(5);
        }
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !this.cancelable) {
            this.cancelable = true;
        }
        this.canceledOnTouchOutside = cancel;
        this.canceledOnTouchOutsideSet = true;
    }

    public BottomSheetBehavior getBehavior() {
        if (this.behavior == null) {
            ensureContainerAndBehavior();
        }
        return this.behavior;
    }

    public void setDismissWithAnimation(boolean dismissWithAnimation) {
        this.dismissWithAnimation = dismissWithAnimation;
    }

    public boolean getDismissWithAnimation() {
        return this.dismissWithAnimation;
    }

    public boolean getEdgeToEdgeEnabled() {
        return this.edgeToEdgeEnabled;
    }

    private FrameLayout ensureContainerAndBehavior() {
        if (this.container == null) {
            FrameLayout inflate = View.inflate(getContext(), R.layout.design_bottom_sheet_dialog, (ViewGroup) null);
            this.container = inflate;
            this.coordinator = inflate.findViewById(R.id.coordinator);
            FrameLayout findViewById = this.container.findViewById(R.id.design_bottom_sheet);
            this.bottomSheet = findViewById;
            BottomSheetBehavior from = BottomSheetBehavior.from(findViewById);
            this.behavior = from;
            from.addBottomSheetCallback(this.bottomSheetCallback);
            this.behavior.setHideable(this.cancelable);
        }
        return this.container;
    }

    private View wrapInBottomSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinator = this.container.findViewById(R.id.coordinator);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        if (this.edgeToEdgeEnabled) {
            ViewCompat.setOnApplyWindowInsetsListener(this.bottomSheet, new 1());
        }
        this.bottomSheet.removeAllViews();
        if (params == null) {
            this.bottomSheet.addView(view);
        } else {
            this.bottomSheet.addView(view, params);
        }
        coordinator.findViewById(R.id.touch_outside).setOnClickListener(new 2());
        ViewCompat.setAccessibilityDelegate(this.bottomSheet, new 3());
        this.bottomSheet.setOnTouchListener(new 4());
        return this.container;
    }

    class 1 implements OnApplyWindowInsetsListener {
        1() {
        }

        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
            if (BottomSheetDialog.access$000(BottomSheetDialog.this) != null) {
                BottomSheetDialog.access$100(BottomSheetDialog.this).removeBottomSheetCallback(BottomSheetDialog.access$000(BottomSheetDialog.this));
            }
            if (insets != null) {
                BottomSheetDialog bottomSheetDialog = BottomSheetDialog.this;
                BottomSheetDialog.access$002(bottomSheetDialog, new EdgeToEdgeCallback(BottomSheetDialog.access$200(bottomSheetDialog), insets, null));
                BottomSheetDialog.access$100(BottomSheetDialog.this).addBottomSheetCallback(BottomSheetDialog.access$000(BottomSheetDialog.this));
            }
            return insets;
        }
    }

    class 2 implements View.OnClickListener {
        2() {
        }

        public void onClick(View view) {
            if (BottomSheetDialog.this.cancelable && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
                BottomSheetDialog.this.cancel();
            }
        }
    }

    class 3 extends AccessibilityDelegateCompat {
        3() {
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (BottomSheetDialog.this.cancelable) {
                info.addAction(1048576);
                info.setDismissable(true);
            } else {
                info.setDismissable(false);
            }
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (action == 1048576 && BottomSheetDialog.this.cancelable) {
                BottomSheetDialog.this.cancel();
                return true;
            }
            return super.performAccessibilityAction(host, action, args);
        }
    }

    class 4 implements View.OnTouchListener {
        4() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            return true;
        }
    }

    boolean shouldWindowCloseOnTouchOutside() {
        if (!this.canceledOnTouchOutsideSet) {
            TypedArray a = getContext().obtainStyledAttributes(new int[]{16843611});
            this.canceledOnTouchOutside = a.getBoolean(0, true);
            a.recycle();
            this.canceledOnTouchOutsideSet = true;
        }
        return this.canceledOnTouchOutside;
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId == 0) {
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, outValue, true)) {
                return outValue.resourceId;
            }
            return R.style.Theme_Design_Light_BottomSheetDialog;
        }
        return themeId;
    }

    void removeDefaultCallback() {
        this.behavior.removeBottomSheetCallback(this.bottomSheetCallback);
    }

    class 5 extends BottomSheetBehavior.BottomSheetCallback {
        5() {
        }

        public void onStateChanged(View bottomSheet, int newState) {
            if (newState == 5) {
                BottomSheetDialog.this.cancel();
            }
        }

        public void onSlide(View bottomSheet, float slideOffset) {
        }
    }

    private static class EdgeToEdgeCallback extends BottomSheetBehavior.BottomSheetCallback {
        private final WindowInsetsCompat insetsCompat;
        private final boolean lightBottomSheet;
        private final boolean lightStatusBar;

        /* synthetic */ EdgeToEdgeCallback(View x0, WindowInsetsCompat x1, 1 x2) {
            this(x0, x1);
        }

        private EdgeToEdgeCallback(View bottomSheet, WindowInsetsCompat insetsCompat) {
            ColorStateList backgroundTint;
            this.insetsCompat = insetsCompat;
            boolean z = Build.VERSION.SDK_INT >= 23 && (bottomSheet.getSystemUiVisibility() & 8192) != 0;
            this.lightStatusBar = z;
            MaterialShapeDrawable msd = BottomSheetBehavior.from(bottomSheet).getMaterialShapeDrawable();
            if (msd != null) {
                backgroundTint = msd.getFillColor();
            } else {
                backgroundTint = ViewCompat.getBackgroundTintList(bottomSheet);
            }
            if (backgroundTint != null) {
                this.lightBottomSheet = MaterialColors.isColorLight(backgroundTint.getDefaultColor());
            } else if (bottomSheet.getBackground() instanceof ColorDrawable) {
                this.lightBottomSheet = MaterialColors.isColorLight(bottomSheet.getBackground().getColor());
            } else {
                this.lightBottomSheet = z;
            }
        }

        public void onStateChanged(View bottomSheet, int newState) {
            setPaddingForPosition(bottomSheet);
        }

        public void onSlide(View bottomSheet, float slideOffset) {
            setPaddingForPosition(bottomSheet);
        }

        void onLayout(View bottomSheet) {
            setPaddingForPosition(bottomSheet);
        }

        private void setPaddingForPosition(View bottomSheet) {
            if (bottomSheet.getTop() < this.insetsCompat.getSystemWindowInsetTop()) {
                BottomSheetDialog.setLightStatusBar(bottomSheet, this.lightBottomSheet);
                bottomSheet.setPadding(bottomSheet.getPaddingLeft(), this.insetsCompat.getSystemWindowInsetTop() - bottomSheet.getTop(), bottomSheet.getPaddingRight(), bottomSheet.getPaddingBottom());
            } else if (bottomSheet.getTop() != 0) {
                BottomSheetDialog.setLightStatusBar(bottomSheet, this.lightStatusBar);
                bottomSheet.setPadding(bottomSheet.getPaddingLeft(), 0, bottomSheet.getPaddingRight(), bottomSheet.getPaddingBottom());
            }
        }
    }

    public static void setLightStatusBar(View view, boolean isLight) {
        int flags;
        if (Build.VERSION.SDK_INT >= 23) {
            int flags2 = view.getSystemUiVisibility();
            if (isLight) {
                flags = flags2 | 8192;
            } else {
                flags = flags2 & (-8193);
            }
            view.setSystemUiVisibility(flags);
        }
    }
}
