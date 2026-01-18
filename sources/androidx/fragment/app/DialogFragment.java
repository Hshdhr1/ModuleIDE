package androidx.fragment.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_INTERNAL_DIALOG_SHOWING = "android:dialogShowing";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    private int mBackStackId;
    private boolean mCancelable;
    private boolean mCreatingDialog;
    private Dialog mDialog;
    private boolean mDialogCreated;
    private Runnable mDismissRunnable;
    private boolean mDismissed;
    private Handler mHandler;
    private Observer mObserver;
    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private boolean mShownByMe;
    private boolean mShowsDialog;
    private int mStyle;
    private int mTheme;
    private boolean mViewDestroyed;

    static /* synthetic */ Dialog access$000(DialogFragment x0) {
        return x0.mDialog;
    }

    static /* synthetic */ DialogInterface.OnDismissListener access$100(DialogFragment x0) {
        return x0.mOnDismissListener;
    }

    static /* synthetic */ boolean access$200(DialogFragment x0) {
        return x0.mShowsDialog;
    }

    class 1 implements Runnable {
        1() {
        }

        public void run() {
            DialogFragment.access$100(DialogFragment.this).onDismiss(DialogFragment.access$000(DialogFragment.this));
        }
    }

    class 2 implements DialogInterface.OnCancelListener {
        2() {
        }

        public void onCancel(DialogInterface dialog) {
            if (DialogFragment.access$000(DialogFragment.this) != null) {
                DialogFragment dialogFragment = DialogFragment.this;
                dialogFragment.onCancel(DialogFragment.access$000(dialogFragment));
            }
        }
    }

    class 3 implements DialogInterface.OnDismissListener {
        3() {
        }

        public void onDismiss(DialogInterface dialog) {
            if (DialogFragment.access$000(DialogFragment.this) != null) {
                DialogFragment dialogFragment = DialogFragment.this;
                dialogFragment.onDismiss(DialogFragment.access$000(dialogFragment));
            }
        }
    }

    class 4 implements Observer {
        4() {
        }

        public void onChanged(LifecycleOwner lifecycleOwner) {
            if (lifecycleOwner != null && DialogFragment.access$200(DialogFragment.this)) {
                View view = DialogFragment.this.requireView();
                if (view.getParent() != null) {
                    throw new IllegalStateException("DialogFragment can not be attached to a container view");
                }
                if (DialogFragment.access$000(DialogFragment.this) != null) {
                    if (FragmentManager.isLoggingEnabled(3)) {
                        Log.d("FragmentManager", "DialogFragment " + this + " setting the content view on " + DialogFragment.access$000(DialogFragment.this));
                    }
                    DialogFragment.access$000(DialogFragment.this).setContentView(view);
                }
            }
        }
    }

    public DialogFragment() {
        this.mDismissRunnable = new 1();
        this.mOnCancelListener = new 2();
        this.mOnDismissListener = new 3();
        this.mStyle = 0;
        this.mTheme = 0;
        this.mCancelable = true;
        this.mShowsDialog = true;
        this.mBackStackId = -1;
        this.mObserver = new 4();
        this.mDialogCreated = false;
    }

    public DialogFragment(int contentLayoutId) {
        super(contentLayoutId);
        this.mDismissRunnable = new 1();
        this.mOnCancelListener = new 2();
        this.mOnDismissListener = new 3();
        this.mStyle = 0;
        this.mTheme = 0;
        this.mCancelable = true;
        this.mShowsDialog = true;
        this.mBackStackId = -1;
        this.mObserver = new 4();
        this.mDialogCreated = false;
    }

    public void setStyle(int style, int theme) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.d("FragmentManager", "Setting style and theme for DialogFragment " + this + " to " + style + ", " + theme);
        }
        this.mStyle = style;
        if (style == 2 || style == 3) {
            this.mTheme = 16973913;
        }
        if (theme != 0) {
            this.mTheme = theme;
        }
    }

    public void show(FragmentManager manager, String tag) {
        this.mDismissed = false;
        this.mShownByMe = true;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commit();
    }

    public int show(FragmentTransaction transaction, String tag) {
        this.mDismissed = false;
        this.mShownByMe = true;
        transaction.add(this, tag);
        this.mViewDestroyed = false;
        int commit = transaction.commit();
        this.mBackStackId = commit;
        return commit;
    }

    public void showNow(FragmentManager manager, String tag) {
        this.mDismissed = false;
        this.mShownByMe = true;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitNow();
    }

    public void dismiss() {
        dismissInternal(false, false);
    }

    public void dismissAllowingStateLoss() {
        dismissInternal(true, false);
    }

    private void dismissInternal(boolean allowStateLoss, boolean fromOnDismiss) {
        if (this.mDismissed) {
            return;
        }
        this.mDismissed = true;
        this.mShownByMe = false;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
            this.mDialog.dismiss();
            if (!fromOnDismiss) {
                if (Looper.myLooper() == this.mHandler.getLooper()) {
                    onDismiss(this.mDialog);
                } else {
                    this.mHandler.post(this.mDismissRunnable);
                }
            }
        }
        this.mViewDestroyed = true;
        if (this.mBackStackId >= 0) {
            getParentFragmentManager().popBackStack(this.mBackStackId, 1);
            this.mBackStackId = -1;
            return;
        }
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.remove(this);
        if (allowStateLoss) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public final Dialog requireDialog() {
        Dialog dialog = getDialog();
        if (dialog == null) {
            throw new IllegalStateException("DialogFragment " + this + " does not have a Dialog.");
        }
        return dialog;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void setCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setCancelable(cancelable);
        }
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    public void setShowsDialog(boolean showsDialog) {
        this.mShowsDialog = showsDialog;
    }

    public boolean getShowsDialog() {
        return this.mShowsDialog;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        getViewLifecycleOwnerLiveData().observeForever(this.mObserver);
        if (!this.mShownByMe) {
            this.mDismissed = false;
        }
    }

    public void onDetach() {
        super.onDetach();
        if (!this.mShownByMe && !this.mDismissed) {
            this.mDismissed = true;
        }
        getViewLifecycleOwnerLiveData().removeObserver(this.mObserver);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHandler = new Handler();
        this.mShowsDialog = this.mContainerId == 0;
        if (savedInstanceState != null) {
            this.mStyle = savedInstanceState.getInt("android:style", 0);
            this.mTheme = savedInstanceState.getInt("android:theme", 0);
            this.mCancelable = savedInstanceState.getBoolean("android:cancelable", true);
            this.mShowsDialog = savedInstanceState.getBoolean("android:showsDialog", this.mShowsDialog);
            this.mBackStackId = savedInstanceState.getInt("android:backStackId", -1);
        }
    }

    void performCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle dialogState;
        super.performCreateView(inflater, container, savedInstanceState);
        if (this.mView == null && this.mDialog != null && savedInstanceState != null && (dialogState = savedInstanceState.getBundle("android:savedDialogState")) != null) {
            this.mDialog.onRestoreInstanceState(dialogState);
        }
    }

    class 5 extends FragmentContainer {
        final /* synthetic */ FragmentContainer val$fragmentContainer;

        5(FragmentContainer fragmentContainer) {
            this.val$fragmentContainer = fragmentContainer;
        }

        public View onFindViewById(int id) {
            if (this.val$fragmentContainer.onHasView()) {
                return this.val$fragmentContainer.onFindViewById(id);
            }
            return DialogFragment.this.onFindViewById(id);
        }

        public boolean onHasView() {
            return this.val$fragmentContainer.onHasView() || DialogFragment.this.onHasView();
        }
    }

    FragmentContainer createFragmentContainer() {
        FragmentContainer fragmentContainer = super.createFragmentContainer();
        return new 5(fragmentContainer);
    }

    View onFindViewById(int id) {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            return dialog.findViewById(id);
        }
        return null;
    }

    boolean onHasView() {
        return this.mDialogCreated;
    }

    public LayoutInflater onGetLayoutInflater(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = super.onGetLayoutInflater(savedInstanceState);
        if (!this.mShowsDialog || this.mCreatingDialog) {
            if (FragmentManager.isLoggingEnabled(2)) {
                String message = "getting layout inflater for DialogFragment " + this;
                if (!this.mShowsDialog) {
                    Log.d("FragmentManager", "mShowsDialog = false: " + message);
                } else {
                    Log.d("FragmentManager", "mCreatingDialog = true: " + message);
                }
            }
            return layoutInflater;
        }
        prepareDialog(savedInstanceState);
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.d("FragmentManager", "get layout inflater for DialogFragment " + this + " from dialog context");
        }
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            return layoutInflater.cloneInContext(dialog.getContext());
        }
        return layoutInflater;
    }

    public void setupDialog(Dialog dialog, int style) {
        switch (style) {
            case 1:
            case 2:
                break;
            case 3:
                Window window = dialog.getWindow();
                if (window != null) {
                    window.addFlags(24);
                    break;
                }
                break;
            default:
                return;
        }
        dialog.requestWindowFeature(1);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "onCreateDialog called for DialogFragment " + this);
        }
        return new Dialog(requireContext(), getTheme());
    }

    public void onCancel(DialogInterface dialog) {
    }

    public void onDismiss(DialogInterface dialog) {
        if (!this.mViewDestroyed) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "onDismiss called for DialogFragment " + this);
            }
            dismissInternal(true, true);
        }
    }

    private void prepareDialog(Bundle savedInstanceState) {
        if (this.mShowsDialog && !this.mDialogCreated) {
            try {
                this.mCreatingDialog = true;
                Dialog onCreateDialog = onCreateDialog(savedInstanceState);
                this.mDialog = onCreateDialog;
                if (this.mShowsDialog) {
                    setupDialog(onCreateDialog, this.mStyle);
                    Activity context = getContext();
                    if (context instanceof Activity) {
                        this.mDialog.setOwnerActivity(context);
                    }
                    this.mDialog.setCancelable(this.mCancelable);
                    this.mDialog.setOnCancelListener(this.mOnCancelListener);
                    this.mDialog.setOnDismissListener(this.mOnDismissListener);
                    this.mDialogCreated = true;
                } else {
                    this.mDialog = null;
                }
            } finally {
                this.mCreatingDialog = false;
            }
        }
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        Bundle dialogState;
        super.onViewStateRestored(savedInstanceState);
        if (this.mDialog != null && savedInstanceState != null && (dialogState = savedInstanceState.getBundle("android:savedDialogState")) != null) {
            this.mDialog.onRestoreInstanceState(dialogState);
        }
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = false;
            dialog.show();
            View decorView = this.mDialog.getWindow().getDecorView();
            ViewTreeLifecycleOwner.set(decorView, this);
            ViewTreeViewModelStoreOwner.set(decorView, this);
            ViewTreeSavedStateRegistryOwner.set(decorView, this);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            Bundle dialogState = dialog.onSaveInstanceState();
            dialogState.putBoolean("android:dialogShowing", false);
            outState.putBundle("android:savedDialogState", dialogState);
        }
        int i = this.mStyle;
        if (i != 0) {
            outState.putInt("android:style", i);
        }
        int i2 = this.mTheme;
        if (i2 != 0) {
            outState.putInt("android:theme", i2);
        }
        boolean z = this.mCancelable;
        if (!z) {
            outState.putBoolean("android:cancelable", z);
        }
        boolean z2 = this.mShowsDialog;
        if (!z2) {
            outState.putBoolean("android:showsDialog", z2);
        }
        int i3 = this.mBackStackId;
        if (i3 != -1) {
            outState.putInt("android:backStackId", i3);
        }
    }

    public void onStop() {
        super.onStop();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = true;
            dialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
            this.mDialog.dismiss();
            if (!this.mDismissed) {
                onDismiss(this.mDialog);
            }
            this.mDialog = null;
            this.mDialogCreated = false;
        }
    }
}
