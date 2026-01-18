package androidx.transition;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import java.util.ArrayList;

@SuppressLint({"ViewConstructor"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes51.dex */
class GhostViewHolder extends FrameLayout {
    private boolean mAttached;

    @NonNull
    private ViewGroup mParent;

    GhostViewHolder(ViewGroup parent) {
        super(parent.getContext());
        setClipChildren(false);
        this.mParent = parent;
        this.mParent.setTag(R.id.ghost_view_holder, this);
        ViewGroupUtils.getOverlay(this.mParent).add((View) this);
        this.mAttached = true;
    }

    public void onViewAdded(View child) {
        if (!this.mAttached) {
            throw new IllegalStateException("This GhostViewHolder is detached!");
        }
        super.onViewAdded(child);
    }

    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if ((getChildCount() == 1 && getChildAt(0) == child) || getChildCount() == 0) {
            this.mParent.setTag(R.id.ghost_view_holder, (Object) null);
            ViewGroupUtils.getOverlay(this.mParent).remove((View) this);
            this.mAttached = false;
        }
    }

    static GhostViewHolder getHolder(@NonNull ViewGroup parent) {
        return (GhostViewHolder) parent.getTag(R.id.ghost_view_holder);
    }

    void popToOverlayTop() {
        if (!this.mAttached) {
            throw new IllegalStateException("This GhostViewHolder is detached!");
        }
        ViewGroupUtils.getOverlay(this.mParent).remove((View) this);
        ViewGroupUtils.getOverlay(this.mParent).add((View) this);
    }

    void addGhostView(GhostViewPort ghostView) {
        ArrayList<View> viewParents = new ArrayList<>();
        getParents(ghostView.mView, viewParents);
        int index = getInsertIndex(viewParents);
        if (index < 0 || index >= getChildCount()) {
            addView(ghostView);
        } else {
            addView(ghostView, index);
        }
    }

    private int getInsertIndex(ArrayList arrayList) {
        ArrayList<View> tempParents = new ArrayList<>();
        int low = 0;
        int high = getChildCount() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            GhostViewPort midView = getChildAt(mid);
            getParents(midView.mView, tempParents);
            if (isOnTop(arrayList, tempParents)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
            tempParents.clear();
        }
        return low;
    }

    private static boolean isOnTop(ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList.isEmpty() || arrayList2.isEmpty() || arrayList.get(0) != arrayList2.get(0)) {
            return true;
        }
        int depth = Math.min(arrayList.size(), arrayList2.size());
        for (int i = 1; i < depth; i++) {
            View viewParent = (View) arrayList.get(i);
            View comparedWithParent = (View) arrayList2.get(i);
            if (viewParent != comparedWithParent) {
                return isOnTop(viewParent, comparedWithParent);
            }
        }
        return arrayList2.size() == depth;
    }

    private static void getParents(View view, ArrayList arrayList) {
        View parent = view.getParent();
        if (parent instanceof ViewGroup) {
            getParents(parent, arrayList);
        }
        arrayList.add(view);
    }

    private static boolean isOnTop(View view, View comparedWith) {
        ViewGroup parent = view.getParent();
        int childrenCount = parent.getChildCount();
        if (Build.VERSION.SDK_INT >= 21 && view.getZ() != comparedWith.getZ()) {
            return view.getZ() > comparedWith.getZ();
        }
        boolean isOnTop = true;
        int i = 0;
        while (true) {
            if (i >= childrenCount) {
                break;
            }
            int childIndex = ViewGroupUtils.getChildDrawingOrder(parent, i);
            View child = parent.getChildAt(childIndex);
            if (child == view) {
                isOnTop = false;
                break;
            }
            if (child != comparedWith) {
                i++;
            } else {
                isOnTop = true;
                break;
            }
        }
        return isOnTop;
    }
}
