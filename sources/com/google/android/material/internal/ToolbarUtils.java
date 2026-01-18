package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class ToolbarUtils {
    private static final Comparator VIEW_TOP_COMPARATOR = new 1();

    class 1 implements Comparator {
        1() {
        }

        public int compare(View view1, View view2) {
            return view1.getTop() - view2.getTop();
        }
    }

    private ToolbarUtils() {
    }

    public static TextView getTitleTextView(Toolbar toolbar) {
        List<TextView> textViews = getTextViewsWithText(toolbar, toolbar.getTitle());
        if (textViews.isEmpty()) {
            return null;
        }
        return (TextView) Collections.min(textViews, VIEW_TOP_COMPARATOR);
    }

    public static TextView getSubtitleTextView(Toolbar toolbar) {
        List<TextView> textViews = getTextViewsWithText(toolbar, toolbar.getSubtitle());
        if (textViews.isEmpty()) {
            return null;
        }
        return (TextView) Collections.max(textViews, VIEW_TOP_COMPARATOR);
    }

    private static List getTextViewsWithText(Toolbar toolbar, CharSequence text) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            TextView childAt = toolbar.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = childAt;
                if (TextUtils.equals(textView.getText(), text)) {
                    arrayList.add(textView);
                }
            }
        }
        return arrayList;
    }

    public static ImageView getLogoImageView(Toolbar toolbar) {
        return getImageView(toolbar, toolbar.getLogo());
    }

    private static ImageView getImageView(Toolbar toolbar, Drawable content) {
        ImageView imageView;
        Drawable drawable;
        if (content == null) {
            return null;
        }
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            ImageView childAt = toolbar.getChildAt(i);
            if ((childAt instanceof ImageView) && (drawable = (imageView = childAt).getDrawable()) != null && drawable.getConstantState() != null && drawable.getConstantState().equals(content.getConstantState())) {
                return imageView;
            }
        }
        return null;
    }

    public static View getSecondaryActionMenuItemView(Toolbar toolbar) {
        ActionMenuView actionMenuView = getActionMenuView(toolbar);
        if (actionMenuView != null && actionMenuView.getChildCount() > 1) {
            return actionMenuView.getChildAt(0);
        }
        return null;
    }

    public static ActionMenuView getActionMenuView(Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            ActionMenuView childAt = toolbar.getChildAt(i);
            if (childAt instanceof ActionMenuView) {
                return childAt;
            }
        }
        return null;
    }

    public static ImageButton getNavigationIconButton(Toolbar toolbar) {
        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon == null) {
            return null;
        }
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            ImageButton childAt = toolbar.getChildAt(i);
            if (childAt instanceof ImageButton) {
                ImageButton imageButton = childAt;
                if (imageButton.getDrawable() == navigationIcon) {
                    return imageButton;
                }
            }
        }
        return null;
    }

    public static ActionMenuItemView getActionMenuItemView(Toolbar toolbar, int menuItemId) {
        ActionMenuView actionMenuView = getActionMenuView(toolbar);
        if (actionMenuView != null) {
            for (int i = 0; i < actionMenuView.getChildCount(); i++) {
                ActionMenuItemView childAt = actionMenuView.getChildAt(i);
                if (childAt instanceof ActionMenuItemView) {
                    ActionMenuItemView actionMenuItemView = childAt;
                    if (actionMenuItemView.getItemData().getItemId() == menuItemId) {
                        return actionMenuItemView;
                    }
                }
            }
            return null;
        }
        return null;
    }
}
