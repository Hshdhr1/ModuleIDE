package com.google.android.material.chip;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.CheckableGroup;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class ChipGroup extends FlowLayout {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ChipGroup;
    private final CheckableGroup checkableGroup;
    private int chipSpacingHorizontal;
    private int chipSpacingVertical;
    private final int defaultCheckedId;
    private OnCheckedStateChangeListener onCheckedStateChangeListener;
    private final PassThroughHierarchyChangeListener passThroughListener;

    @Deprecated
    public interface OnCheckedChangeListener {
        void onCheckedChanged(ChipGroup chipGroup, int i);
    }

    public interface OnCheckedStateChangeListener {
        void onCheckedChanged(ChipGroup chipGroup, List list);
    }

    static /* synthetic */ OnCheckedStateChangeListener access$100(ChipGroup x0) {
        return x0.onCheckedStateChangeListener;
    }

    static /* synthetic */ CheckableGroup access$200(ChipGroup x0) {
        return x0.checkableGroup;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
    }

    public ChipGroup(Context context) {
        this(context, null);
    }

    public ChipGroup(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.chipGroupStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ChipGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        int i = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, i), attrs, defStyleAttr);
        CheckableGroup checkableGroup = new CheckableGroup();
        this.checkableGroup = checkableGroup;
        PassThroughHierarchyChangeListener passThroughHierarchyChangeListener = new PassThroughHierarchyChangeListener(this, null);
        this.passThroughListener = passThroughHierarchyChangeListener;
        Context context2 = getContext();
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.ChipGroup, defStyleAttr, i, new int[0]);
        int chipSpacing = a.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacing, 0);
        setChipSpacingHorizontal(a.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingHorizontal, chipSpacing));
        setChipSpacingVertical(a.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingVertical, chipSpacing));
        setSingleLine(a.getBoolean(R.styleable.ChipGroup_singleLine, false));
        setSingleSelection(a.getBoolean(R.styleable.ChipGroup_singleSelection, false));
        setSelectionRequired(a.getBoolean(R.styleable.ChipGroup_selectionRequired, false));
        this.defaultCheckedId = a.getResourceId(R.styleable.ChipGroup_checkedChip, -1);
        a.recycle();
        checkableGroup.setOnCheckedStateChangeListener(new 1());
        super.setOnHierarchyChangeListener(passThroughHierarchyChangeListener);
        ViewCompat.setImportantForAccessibility(this, 1);
    }

    class 1 implements CheckableGroup.OnCheckedStateChangeListener {
        1() {
        }

        public void onCheckedStateChanged(Set set) {
            if (ChipGroup.access$100(ChipGroup.this) != null) {
                OnCheckedStateChangeListener access$100 = ChipGroup.access$100(ChipGroup.this);
                ChipGroup chipGroup = ChipGroup.this;
                access$100.onCheckedChanged(chipGroup, ChipGroup.access$200(chipGroup).getCheckedIdsSortedByChildOrder(ChipGroup.this));
            }
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        int i;
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        int columnCount = isSingleLine() ? getChipCount() : -1;
        int rowCount = getRowCount();
        if (isSingleSelection()) {
            i = 1;
        } else {
            i = 2;
        }
        infoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(rowCount, columnCount, false, i));
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && (p instanceof LayoutParams);
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener listener) {
        PassThroughHierarchyChangeListener.access$302(this.passThroughListener, listener);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        int i = this.defaultCheckedId;
        if (i != -1) {
            this.checkableGroup.check(i);
        }
    }

    @Deprecated
    public void setDividerDrawableHorizontal(Drawable divider) {
        throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setDividerDrawableVertical(Drawable divider) {
        throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setShowDividerHorizontal(int dividerMode) {
        throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setShowDividerVertical(int dividerMode) {
        throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setFlexWrap(int flexWrap) {
        throw new UnsupportedOperationException("Changing flex wrap not allowed. ChipGroup exposes a singleLine attribute instead.");
    }

    public void check(int id) {
        this.checkableGroup.check(id);
    }

    public int getCheckedChipId() {
        return this.checkableGroup.getSingleCheckedId();
    }

    public List getCheckedChipIds() {
        return this.checkableGroup.getCheckedIdsSortedByChildOrder(this);
    }

    public void clearCheck() {
        this.checkableGroup.clearCheck();
    }

    @Deprecated
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        if (listener == null) {
            setOnCheckedStateChangeListener(null);
        } else {
            setOnCheckedStateChangeListener(new 2(listener));
        }
    }

    class 2 implements OnCheckedStateChangeListener {
        final /* synthetic */ OnCheckedChangeListener val$listener;

        2(OnCheckedChangeListener onCheckedChangeListener) {
            this.val$listener = onCheckedChangeListener;
        }

        public void onCheckedChanged(ChipGroup group, List list) {
            if (!ChipGroup.access$200(ChipGroup.this).isSingleSelection()) {
                return;
            }
            this.val$listener.onCheckedChanged(group, ChipGroup.this.getCheckedChipId());
        }
    }

    public void setOnCheckedStateChangeListener(OnCheckedStateChangeListener listener) {
        this.onCheckedStateChangeListener = listener;
    }

    private int getChipCount() {
        int count = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof Chip) {
                count++;
            }
        }
        return count;
    }

    int getIndexOfChip(View child) {
        if (!(child instanceof Chip)) {
            return -1;
        }
        int index = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof Chip) {
                Chip chip = getChildAt(i);
                if (chip == child) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public void setChipSpacing(int chipSpacing) {
        setChipSpacingHorizontal(chipSpacing);
        setChipSpacingVertical(chipSpacing);
    }

    public void setChipSpacingResource(int id) {
        setChipSpacing(getResources().getDimensionPixelOffset(id));
    }

    public int getChipSpacingHorizontal() {
        return this.chipSpacingHorizontal;
    }

    public void setChipSpacingHorizontal(int chipSpacingHorizontal) {
        if (this.chipSpacingHorizontal != chipSpacingHorizontal) {
            this.chipSpacingHorizontal = chipSpacingHorizontal;
            setItemSpacing(chipSpacingHorizontal);
            requestLayout();
        }
    }

    public void setChipSpacingHorizontalResource(int id) {
        setChipSpacingHorizontal(getResources().getDimensionPixelOffset(id));
    }

    public int getChipSpacingVertical() {
        return this.chipSpacingVertical;
    }

    public void setChipSpacingVertical(int chipSpacingVertical) {
        if (this.chipSpacingVertical != chipSpacingVertical) {
            this.chipSpacingVertical = chipSpacingVertical;
            setLineSpacing(chipSpacingVertical);
            requestLayout();
        }
    }

    public void setChipSpacingVerticalResource(int id) {
        setChipSpacingVertical(getResources().getDimensionPixelOffset(id));
    }

    public boolean isSingleLine() {
        return super.isSingleLine();
    }

    public void setSingleLine(boolean singleLine) {
        super.setSingleLine(singleLine);
    }

    public void setSingleLine(int id) {
        setSingleLine(getResources().getBoolean(id));
    }

    public boolean isSingleSelection() {
        return this.checkableGroup.isSingleSelection();
    }

    public void setSingleSelection(boolean singleSelection) {
        this.checkableGroup.setSingleSelection(singleSelection);
    }

    public void setSingleSelection(int id) {
        setSingleSelection(getResources().getBoolean(id));
    }

    public void setSelectionRequired(boolean selectionRequired) {
        this.checkableGroup.setSelectionRequired(selectionRequired);
    }

    public boolean isSelectionRequired() {
        return this.checkableGroup.isSelectionRequired();
    }

    private class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        /* synthetic */ PassThroughHierarchyChangeListener(ChipGroup x0, 1 x1) {
            this();
        }

        static /* synthetic */ ViewGroup.OnHierarchyChangeListener access$302(PassThroughHierarchyChangeListener x0, ViewGroup.OnHierarchyChangeListener x1) {
            x0.onHierarchyChangeListener = x1;
            return x1;
        }

        public void onChildViewAdded(View parent, View child) {
            if (parent == ChipGroup.this && (child instanceof Chip)) {
                int id = child.getId();
                if (id == -1) {
                    int id2 = ViewCompat.generateViewId();
                    child.setId(id2);
                }
                ChipGroup.access$200(ChipGroup.this).addCheckable((Chip) child);
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.onHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        public void onChildViewRemoved(View parent, View child) {
            ChipGroup chipGroup = ChipGroup.this;
            if (parent == chipGroup && (child instanceof Chip)) {
                ChipGroup.access$200(chipGroup).removeCheckable((Chip) child);
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.onHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}
