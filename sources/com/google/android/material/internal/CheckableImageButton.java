package com.google.android.material.internal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Checkable;
import androidx.appcompat.R;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class CheckableImageButton extends AppCompatImageButton implements Checkable {
    private static final int[] DRAWABLE_STATE_CHECKED = {16842912};
    private boolean checkable;
    private boolean checked;
    private boolean pressable;

    public CheckableImageButton(Context context) {
        this(context, null);
    }

    public CheckableImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.imageButtonStyle);
    }

    public CheckableImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.checkable = true;
        this.pressable = true;
        ViewCompat.setAccessibilityDelegate(this, new 1());
    }

    class 1 extends AccessibilityDelegateCompat {
        1() {
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setChecked(CheckableImageButton.this.isChecked());
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setCheckable(CheckableImageButton.this.isCheckable());
            info.setChecked(CheckableImageButton.this.isChecked());
        }
    }

    public void setChecked(boolean checked) {
        if (this.checkable && this.checked != checked) {
            this.checked = checked;
            refreshDrawableState();
            sendAccessibilityEvent(2048);
        }
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void toggle() {
        setChecked(!this.checked);
    }

    public void setPressed(boolean pressed) {
        if (this.pressable) {
            super.setPressed(pressed);
        }
    }

    public int[] onCreateDrawableState(int extraSpace) {
        if (this.checked) {
            int[] iArr = DRAWABLE_STATE_CHECKED;
            return mergeDrawableStates(super.onCreateDrawableState(iArr.length + extraSpace), iArr);
        }
        return super.onCreateDrawableState(extraSpace);
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.checked = this.checked;
        return savedState;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
    }

    public void setCheckable(boolean checkable) {
        if (this.checkable != checkable) {
            this.checkable = checkable;
            sendAccessibilityEvent(0);
        }
    }

    public boolean isCheckable() {
        return this.checkable;
    }

    public void setPressable(boolean pressable) {
        this.pressable = pressable;
    }

    public boolean isPressable() {
        return this.pressable;
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new 1();
        boolean checked;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            readFromParcel(source);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.checked ? 1 : 0);
        }

        private void readFromParcel(Parcel in) {
            this.checked = in.readInt() == 1;
        }

        class 1 implements Parcelable.ClassLoaderCreator {
            1() {
            }

            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }
    }
}
