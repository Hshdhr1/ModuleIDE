package com.google.android.material.bottomsheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class BottomSheetDialogFragment extends AppCompatDialogFragment {
    private boolean waitingForDismissAllowingStateLoss;

    static /* synthetic */ void access$100(BottomSheetDialogFragment x0) {
        x0.dismissAfterAnimation();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), getTheme());
    }

    public void dismiss() {
        if (!tryDismissWithAnimation(false)) {
            super.dismiss();
        }
    }

    public void dismissAllowingStateLoss() {
        if (!tryDismissWithAnimation(true)) {
            super.dismissAllowingStateLoss();
        }
    }

    private boolean tryDismissWithAnimation(boolean allowingStateLoss) {
        BottomSheetDialog dialog = getDialog();
        if (dialog instanceof BottomSheetDialog) {
            BottomSheetDialog dialog2 = dialog;
            BottomSheetBehavior<?> behavior = dialog2.getBehavior();
            if (behavior.isHideable() && dialog2.getDismissWithAnimation()) {
                dismissWithAnimation(behavior, allowingStateLoss);
                return true;
            }
            return false;
        }
        return false;
    }

    private void dismissWithAnimation(BottomSheetBehavior bottomSheetBehavior, boolean allowingStateLoss) {
        this.waitingForDismissAllowingStateLoss = allowingStateLoss;
        if (bottomSheetBehavior.getState() == 5) {
            dismissAfterAnimation();
            return;
        }
        if (getDialog() instanceof BottomSheetDialog) {
            getDialog().removeDefaultCallback();
        }
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetDismissCallback(this, null));
        bottomSheetBehavior.setState(5);
    }

    private void dismissAfterAnimation() {
        if (this.waitingForDismissAllowingStateLoss) {
            super.dismissAllowingStateLoss();
        } else {
            super.dismiss();
        }
    }

    private class BottomSheetDismissCallback extends BottomSheetBehavior.BottomSheetCallback {
        private BottomSheetDismissCallback() {
        }

        /* synthetic */ BottomSheetDismissCallback(BottomSheetDialogFragment x0, 1 x1) {
            this();
        }

        public void onStateChanged(View bottomSheet, int newState) {
            if (newState == 5) {
                BottomSheetDialogFragment.access$100(BottomSheetDialogFragment.this);
            }
        }

        public void onSlide(View bottomSheet, float slideOffset) {
        }
    }
}
