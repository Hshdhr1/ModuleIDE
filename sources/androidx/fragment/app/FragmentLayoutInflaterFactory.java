package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.R;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes31.dex */
class FragmentLayoutInflaterFactory implements LayoutInflater.Factory2 {
    private static final String TAG = "FragmentManager";
    final FragmentManager mFragmentManager;

    FragmentLayoutInflaterFactory(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        FragmentStateManager fragmentStateManager;
        if (FragmentContainerView.class.getName().equals(name)) {
            return new FragmentContainerView(context, attrs, this.mFragmentManager);
        }
        if (!"fragment".equals(name)) {
            return null;
        }
        String fname = attrs.getAttributeValue((String) null, "class");
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Fragment);
        if (fname == null) {
            fname = a.getString(R.styleable.Fragment_android_name);
        }
        int id = a.getResourceId(R.styleable.Fragment_android_id, -1);
        String tag = a.getString(R.styleable.Fragment_android_tag);
        a.recycle();
        if (fname == null || !FragmentFactory.isFragmentClass(context.getClassLoader(), fname)) {
            return null;
        }
        int containerId = parent != null ? parent.getId() : 0;
        if (containerId != -1 || id != -1 || tag != null) {
            Fragment fragment = id != -1 ? this.mFragmentManager.findFragmentById(id) : null;
            if (fragment == null && tag != null) {
                fragment = this.mFragmentManager.findFragmentByTag(tag);
            }
            if (fragment == null && containerId != -1) {
                fragment = this.mFragmentManager.findFragmentById(containerId);
            }
            if (fragment == null) {
                fragment = this.mFragmentManager.getFragmentFactory().instantiate(context.getClassLoader(), fname);
                fragment.mFromLayout = true;
                fragment.mFragmentId = id != 0 ? id : containerId;
                fragment.mContainerId = containerId;
                fragment.mTag = tag;
                fragment.mInLayout = true;
                fragment.mFragmentManager = this.mFragmentManager;
                fragment.mHost = this.mFragmentManager.getHost();
                fragment.onInflate(this.mFragmentManager.getHost().getContext(), attrs, fragment.mSavedFragmentState);
                fragmentStateManager = this.mFragmentManager.addFragment(fragment);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Fragment " + fragment + " has been inflated via the <fragment> tag: id=0x" + Integer.toHexString(id));
                }
            } else {
                if (fragment.mInLayout) {
                    throw new IllegalArgumentException(attrs.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(id) + ", tag " + tag + ", or parent id 0x" + Integer.toHexString(containerId) + " with another fragment for " + fname);
                }
                fragment.mInLayout = true;
                fragment.mFragmentManager = this.mFragmentManager;
                fragment.mHost = this.mFragmentManager.getHost();
                fragment.onInflate(this.mFragmentManager.getHost().getContext(), attrs, fragment.mSavedFragmentState);
                fragmentStateManager = this.mFragmentManager.createOrGetFragmentStateManager(fragment);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Retained Fragment " + fragment + " has been re-attached via the <fragment> tag: id=0x" + Integer.toHexString(id));
                }
            }
            fragment.mContainer = (ViewGroup) parent;
            fragmentStateManager.moveToExpectedState();
            fragmentStateManager.ensureInflatedView();
            if (fragment.mView == null) {
                throw new IllegalStateException("Fragment " + fname + " did not create a view.");
            }
            if (id != 0) {
                fragment.mView.setId(id);
            }
            if (fragment.mView.getTag() == null) {
                fragment.mView.setTag(tag);
            }
            fragment.mView.addOnAttachStateChangeListener(new 1(fragmentStateManager));
            return fragment.mView;
        }
        throw new IllegalArgumentException(attrs.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + fname);
    }

    class 1 implements View.OnAttachStateChangeListener {
        final /* synthetic */ FragmentStateManager val$fragmentStateManager;

        1(FragmentStateManager fragmentStateManager) {
            this.val$fragmentStateManager = fragmentStateManager;
        }

        public void onViewAttachedToWindow(View v) {
            Fragment fragment = this.val$fragmentStateManager.getFragment();
            this.val$fragmentStateManager.moveToExpectedState();
            SpecialEffectsController controller = SpecialEffectsController.getOrCreateController(fragment.mView.getParent(), FragmentLayoutInflaterFactory.this.mFragmentManager);
            controller.forceCompleteAllOperations();
        }

        public void onViewDetachedFromWindow(View v) {
        }
    }
}
