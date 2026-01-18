package com.google.android.material.internal;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.internal.MaterialCheckable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public class CheckableGroup {
    private final Map checkables = new HashMap();
    private final Set checkedIds = new HashSet();
    private OnCheckedStateChangeListener onCheckedStateChangeListener;
    private boolean selectionRequired;
    private boolean singleSelection;

    public interface OnCheckedStateChangeListener {
        void onCheckedStateChanged(Set set);
    }

    static /* synthetic */ boolean access$000(CheckableGroup x0, MaterialCheckable x1) {
        return x0.checkInternal(x1);
    }

    static /* synthetic */ boolean access$100(CheckableGroup x0) {
        return x0.selectionRequired;
    }

    static /* synthetic */ boolean access$200(CheckableGroup x0, MaterialCheckable x1, boolean x2) {
        return x0.uncheckInternal(x1, x2);
    }

    static /* synthetic */ void access$300(CheckableGroup x0) {
        x0.onCheckedStateChanged();
    }

    public void setSingleSelection(boolean singleSelection) {
        if (this.singleSelection != singleSelection) {
            this.singleSelection = singleSelection;
            clearCheck();
        }
    }

    public boolean isSingleSelection() {
        return this.singleSelection;
    }

    public void setSelectionRequired(boolean selectionRequired) {
        this.selectionRequired = selectionRequired;
    }

    public boolean isSelectionRequired() {
        return this.selectionRequired;
    }

    public void setOnCheckedStateChangeListener(OnCheckedStateChangeListener listener) {
        this.onCheckedStateChangeListener = listener;
    }

    public void addCheckable(MaterialCheckable materialCheckable) {
        this.checkables.put(Integer.valueOf(materialCheckable.getId()), materialCheckable);
        if (materialCheckable.isChecked()) {
            checkInternal(materialCheckable);
        }
        materialCheckable.setInternalOnCheckedChangeListener(new 1());
    }

    class 1 implements MaterialCheckable.OnCheckedChangeListener {
        1() {
        }

        public void onCheckedChanged(MaterialCheckable materialCheckable, boolean isChecked) {
            CheckableGroup checkableGroup = CheckableGroup.this;
            if (isChecked) {
                if (!CheckableGroup.access$000(checkableGroup, materialCheckable)) {
                    return;
                }
            } else if (!CheckableGroup.access$200(checkableGroup, materialCheckable, CheckableGroup.access$100(checkableGroup))) {
                return;
            }
            CheckableGroup.access$300(CheckableGroup.this);
        }
    }

    public void removeCheckable(MaterialCheckable materialCheckable) {
        materialCheckable.setInternalOnCheckedChangeListener(null);
        this.checkables.remove(Integer.valueOf(materialCheckable.getId()));
        this.checkedIds.remove(Integer.valueOf(materialCheckable.getId()));
    }

    public void check(int id) {
        MaterialCheckable materialCheckable = (MaterialCheckable) this.checkables.get(Integer.valueOf(id));
        if (materialCheckable != null && checkInternal(materialCheckable)) {
            onCheckedStateChanged();
        }
    }

    public void uncheck(int id) {
        MaterialCheckable materialCheckable = (MaterialCheckable) this.checkables.get(Integer.valueOf(id));
        if (materialCheckable != null && uncheckInternal(materialCheckable, this.selectionRequired)) {
            onCheckedStateChanged();
        }
    }

    public void clearCheck() {
        boolean checkedStateChanged = !this.checkedIds.isEmpty();
        Iterator it = this.checkables.values().iterator();
        while (it.hasNext()) {
            uncheckInternal((MaterialCheckable) it.next(), false);
        }
        if (checkedStateChanged) {
            onCheckedStateChanged();
        }
    }

    public int getSingleCheckedId() {
        if (!this.singleSelection || this.checkedIds.isEmpty()) {
            return -1;
        }
        return ((Integer) this.checkedIds.iterator().next()).intValue();
    }

    public Set getCheckedIds() {
        return new HashSet(this.checkedIds);
    }

    public List getCheckedIdsSortedByChildOrder(ViewGroup parent) {
        Set<Integer> checkedIds = getCheckedIds();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if ((child instanceof MaterialCheckable) && checkedIds.contains(Integer.valueOf(child.getId()))) {
                arrayList.add(Integer.valueOf(child.getId()));
            }
        }
        return arrayList;
    }

    private boolean checkInternal(MaterialCheckable materialCheckable) {
        int id = materialCheckable.getId();
        if (this.checkedIds.contains(Integer.valueOf(id))) {
            return false;
        }
        MaterialCheckable materialCheckable2 = (MaterialCheckable) this.checkables.get(Integer.valueOf(getSingleCheckedId()));
        if (materialCheckable2 != null) {
            uncheckInternal(materialCheckable2, false);
        }
        boolean checkedStateChanged = this.checkedIds.add(Integer.valueOf(id));
        if (!materialCheckable.isChecked()) {
            materialCheckable.setChecked(true);
        }
        return checkedStateChanged;
    }

    private boolean uncheckInternal(MaterialCheckable materialCheckable, boolean selectionRequired) {
        int id = materialCheckable.getId();
        if (!this.checkedIds.contains(Integer.valueOf(id))) {
            return false;
        }
        if (selectionRequired && this.checkedIds.size() == 1 && this.checkedIds.contains(Integer.valueOf(id))) {
            materialCheckable.setChecked(true);
            return false;
        }
        boolean checkedStateChanged = this.checkedIds.remove(Integer.valueOf(id));
        if (materialCheckable.isChecked()) {
            materialCheckable.setChecked(false);
        }
        return checkedStateChanged;
    }

    private void onCheckedStateChanged() {
        OnCheckedStateChangeListener onCheckedStateChangeListener = this.onCheckedStateChangeListener;
        if (onCheckedStateChangeListener != null) {
            onCheckedStateChangeListener.onCheckedStateChanged(getCheckedIds());
        }
    }
}
