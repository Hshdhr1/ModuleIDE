package com.google.android.material.bottomnavigation;

import android.content.Context;
import com.google.android.material.R;
import com.google.android.material.navigation.NavigationBarItemView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class BottomNavigationItemView extends NavigationBarItemView {
    public BottomNavigationItemView(Context context) {
        super(context);
    }

    protected int getItemLayoutResId() {
        return R.layout.design_bottom_navigation_item;
    }

    protected int getItemDefaultMarginResId() {
        return R.dimen.design_bottom_navigation_margin;
    }
}
