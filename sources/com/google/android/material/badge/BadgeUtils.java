package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.internal.ToolbarUtils;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class BadgeUtils {
    private static final String LOG_TAG = "BadgeUtils";
    public static final boolean USE_COMPAT_PARENT;

    static /* synthetic */ void access$000(BadgeDrawable x0, View x1) {
        attachBadgeContentDescription(x0, x1);
    }

    static {
        USE_COMPAT_PARENT = Build.VERSION.SDK_INT < 18;
    }

    private BadgeUtils() {
    }

    public static void updateBadgeBounds(Rect rect, float centerX, float centerY, float halfWidth, float halfHeight) {
        rect.set((int) (centerX - halfWidth), (int) (centerY - halfHeight), (int) (centerX + halfWidth), (int) (centerY + halfHeight));
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, View anchor) {
        attachBadgeDrawable(badgeDrawable, anchor, (FrameLayout) null);
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, View anchor, FrameLayout customBadgeParent) {
        setBadgeDrawableBounds(badgeDrawable, anchor, customBadgeParent);
        if (badgeDrawable.getCustomBadgeParent() != null) {
            badgeDrawable.getCustomBadgeParent().setForeground(badgeDrawable);
        } else {
            if (USE_COMPAT_PARENT) {
                throw new IllegalArgumentException("Trying to reference null customBadgeParent");
            }
            anchor.getOverlay().add(badgeDrawable);
        }
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, Toolbar toolbar, int menuItemId) {
        attachBadgeDrawable(badgeDrawable, toolbar, menuItemId, null);
    }

    class 1 implements Runnable {
        final /* synthetic */ BadgeDrawable val$badgeDrawable;
        final /* synthetic */ FrameLayout val$customBadgeParent;
        final /* synthetic */ int val$menuItemId;
        final /* synthetic */ Toolbar val$toolbar;

        1(Toolbar toolbar, int i, BadgeDrawable badgeDrawable, FrameLayout frameLayout) {
            this.val$toolbar = toolbar;
            this.val$menuItemId = i;
            this.val$badgeDrawable = badgeDrawable;
            this.val$customBadgeParent = frameLayout;
        }

        public void run() {
            ActionMenuItemView menuItemView = ToolbarUtils.getActionMenuItemView(this.val$toolbar, this.val$menuItemId);
            if (menuItemView != null) {
                BadgeUtils.setToolbarOffset(this.val$badgeDrawable, this.val$toolbar.getResources());
                BadgeUtils.attachBadgeDrawable(this.val$badgeDrawable, (View) menuItemView, this.val$customBadgeParent);
                BadgeUtils.access$000(this.val$badgeDrawable, menuItemView);
            }
        }
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, Toolbar toolbar, int menuItemId, FrameLayout customBadgeParent) {
        toolbar.post(new 1(toolbar, menuItemId, badgeDrawable, customBadgeParent));
    }

    private static void attachBadgeContentDescription(BadgeDrawable badgeDrawable, View view) {
        if (Build.VERSION.SDK_INT >= 29 && ViewCompat.hasAccessibilityDelegate(view)) {
            ViewCompat.setAccessibilityDelegate(view, new 2(view.getAccessibilityDelegate(), badgeDrawable));
        } else {
            ViewCompat.setAccessibilityDelegate(view, new 3(badgeDrawable));
        }
    }

    class 2 extends AccessibilityDelegateCompat {
        final /* synthetic */ BadgeDrawable val$badgeDrawable;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(View.AccessibilityDelegate arg0, BadgeDrawable badgeDrawable) {
            super(arg0);
            this.val$badgeDrawable = badgeDrawable;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setContentDescription(this.val$badgeDrawable.getContentDescription());
        }
    }

    class 3 extends AccessibilityDelegateCompat {
        final /* synthetic */ BadgeDrawable val$badgeDrawable;

        3(BadgeDrawable badgeDrawable) {
            this.val$badgeDrawable = badgeDrawable;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setContentDescription(this.val$badgeDrawable.getContentDescription());
        }
    }

    public static void detachBadgeDrawable(BadgeDrawable badgeDrawable, View anchor) {
        if (badgeDrawable == null) {
            return;
        }
        if (USE_COMPAT_PARENT || badgeDrawable.getCustomBadgeParent() != null) {
            badgeDrawable.getCustomBadgeParent().setForeground((Drawable) null);
        } else {
            anchor.getOverlay().remove(badgeDrawable);
        }
    }

    public static void detachBadgeDrawable(BadgeDrawable badgeDrawable, Toolbar toolbar, int menuItemId) {
        if (badgeDrawable == null) {
            return;
        }
        ActionMenuItemView menuItemView = ToolbarUtils.getActionMenuItemView(toolbar, menuItemId);
        if (menuItemView != null) {
            removeToolbarOffset(badgeDrawable);
            detachBadgeDrawable(badgeDrawable, menuItemView);
            detachBadgeContentDescription(menuItemView);
            return;
        }
        Log.w("BadgeUtils", "Trying to remove badge from a null menuItemView: " + menuItemId);
    }

    private static void detachBadgeContentDescription(View view) {
        if (Build.VERSION.SDK_INT >= 29 && ViewCompat.hasAccessibilityDelegate(view)) {
            ViewCompat.setAccessibilityDelegate(view, new 4(view.getAccessibilityDelegate()));
        } else {
            ViewCompat.setAccessibilityDelegate(view, (AccessibilityDelegateCompat) null);
        }
    }

    class 4 extends AccessibilityDelegateCompat {
        4(View.AccessibilityDelegate arg0) {
            super(arg0);
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setContentDescription((CharSequence) null);
        }
    }

    static void setToolbarOffset(BadgeDrawable badgeDrawable, Resources resources) {
        badgeDrawable.setAdditionalHorizontalOffset(resources.getDimensionPixelOffset(R.dimen.mtrl_badge_toolbar_action_menu_item_horizontal_offset));
        badgeDrawable.setAdditionalVerticalOffset(resources.getDimensionPixelOffset(R.dimen.mtrl_badge_toolbar_action_menu_item_vertical_offset));
    }

    static void removeToolbarOffset(BadgeDrawable badgeDrawable) {
        badgeDrawable.setAdditionalHorizontalOffset(0);
        badgeDrawable.setAdditionalVerticalOffset(0);
    }

    public static void setBadgeDrawableBounds(BadgeDrawable badgeDrawable, View anchor, FrameLayout compatBadgeParent) {
        Rect badgeBounds = new Rect();
        anchor.getDrawingRect(badgeBounds);
        badgeDrawable.setBounds(badgeBounds);
        badgeDrawable.updateBadgeCoordinates(anchor, compatBadgeParent);
    }

    public static ParcelableSparseArray createParcelableBadgeStates(SparseArray sparseArray) {
        ParcelableSparseArray badgeStates = new ParcelableSparseArray();
        for (int i = 0; i < sparseArray.size(); i++) {
            int key = sparseArray.keyAt(i);
            BadgeDrawable badgeDrawable = (BadgeDrawable) sparseArray.valueAt(i);
            if (badgeDrawable == null) {
                throw new IllegalArgumentException("badgeDrawable cannot be null");
            }
            badgeStates.put(key, badgeDrawable.getSavedState());
        }
        return badgeStates;
    }

    public static SparseArray createBadgeDrawablesFromSavedStates(Context context, ParcelableSparseArray badgeStates) {
        SparseArray<BadgeDrawable> badgeDrawables = new SparseArray<>(badgeStates.size());
        for (int i = 0; i < badgeStates.size(); i++) {
            int key = badgeStates.keyAt(i);
            BadgeState.State savedState = (BadgeState.State) badgeStates.valueAt(i);
            if (savedState == null) {
                throw new IllegalArgumentException("BadgeDrawable's savedState cannot be null");
            }
            BadgeDrawable badgeDrawable = BadgeDrawable.createFromSavedState(context, savedState);
            badgeDrawables.put(key, badgeDrawable);
        }
        return badgeDrawables;
    }
}
