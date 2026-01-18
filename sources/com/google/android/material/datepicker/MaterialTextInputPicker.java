package com.google.android.material.datepicker;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class MaterialTextInputPicker extends PickerFragment {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
    private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
    private CalendarConstraints calendarConstraints;
    private DateSelector dateSelector;
    private int themeResId;

    static MaterialTextInputPicker newInstance(DateSelector dateSelector, int themeResId, CalendarConstraints calendarConstraints) {
        MaterialTextInputPicker materialTextInputPicker = new MaterialTextInputPicker();
        Bundle args = new Bundle();
        args.putInt("THEME_RES_ID_KEY", themeResId);
        args.putParcelable("DATE_SELECTOR_KEY", dateSelector);
        args.putParcelable("CALENDAR_CONSTRAINTS_KEY", calendarConstraints);
        materialTextInputPicker.setArguments(args);
        return materialTextInputPicker;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("THEME_RES_ID_KEY", this.themeResId);
        bundle.putParcelable("DATE_SELECTOR_KEY", this.dateSelector);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle activeBundle = bundle == null ? getArguments() : bundle;
        this.themeResId = activeBundle.getInt("THEME_RES_ID_KEY");
        this.dateSelector = (DateSelector) activeBundle.getParcelable("DATE_SELECTOR_KEY");
        this.calendarConstraints = (CalendarConstraints) activeBundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ContextThemeWrapper themedContext = new ContextThemeWrapper(getContext(), this.themeResId);
        LayoutInflater themedInflater = layoutInflater.cloneInContext(themedContext);
        return this.dateSelector.onCreateTextInputView(themedInflater, viewGroup, bundle, this.calendarConstraints, new 1());
    }

    class 1 extends OnSelectionChangedListener {
        1() {
        }

        public void onSelectionChanged(Object obj) {
            Iterator it = MaterialTextInputPicker.this.onSelectionChangedListeners.iterator();
            while (it.hasNext()) {
                ((OnSelectionChangedListener) it.next()).onSelectionChanged(obj);
            }
        }

        public void onIncompleteSelectionChanged() {
            Iterator it = MaterialTextInputPicker.this.onSelectionChangedListeners.iterator();
            while (it.hasNext()) {
                ((OnSelectionChangedListener) it.next()).onIncompleteSelectionChanged();
            }
        }
    }

    public DateSelector getDateSelector() {
        DateSelector dateSelector = this.dateSelector;
        if (dateSelector == null) {
            throw new IllegalStateException("dateSelector should not be null. Use MaterialTextInputPicker#newInstance() to create this fragment with a DateSelector, and call this method after the fragment has been created.");
        }
        return dateSelector;
    }
}
