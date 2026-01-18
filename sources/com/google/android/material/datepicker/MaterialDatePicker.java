package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.EdgeToEdgeUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class MaterialDatePicker extends DialogFragment {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String DATE_SELECTOR_KEY = "DATE_SELECTOR_KEY";
    public static final int INPUT_MODE_CALENDAR = 0;
    private static final String INPUT_MODE_KEY = "INPUT_MODE_KEY";
    public static final int INPUT_MODE_TEXT = 1;
    private static final String NEGATIVE_BUTTON_TEXT_KEY = "NEGATIVE_BUTTON_TEXT_KEY";
    private static final String NEGATIVE_BUTTON_TEXT_RES_ID_KEY = "NEGATIVE_BUTTON_TEXT_RES_ID_KEY";
    private static final String OVERRIDE_THEME_RES_ID = "OVERRIDE_THEME_RES_ID";
    private static final String POSITIVE_BUTTON_TEXT_KEY = "POSITIVE_BUTTON_TEXT_KEY";
    private static final String POSITIVE_BUTTON_TEXT_RES_ID_KEY = "POSITIVE_BUTTON_TEXT_RES_ID_KEY";
    private static final String TITLE_TEXT_KEY = "TITLE_TEXT_KEY";
    private static final String TITLE_TEXT_RES_ID_KEY = "TITLE_TEXT_RES_ID_KEY";
    private MaterialShapeDrawable background;
    private MaterialCalendar calendar;
    private CalendarConstraints calendarConstraints;
    private Button confirmButton;
    private DateSelector dateSelector;
    private boolean edgeToEdgeEnabled;
    private boolean fullscreen;
    private TextView headerSelectionText;
    private CheckableImageButton headerToggleButton;
    private int inputMode;
    private CharSequence negativeButtonText;
    private int negativeButtonTextResId;
    private int overrideThemeResId;
    private PickerFragment pickerFragment;
    private CharSequence positiveButtonText;
    private int positiveButtonTextResId;
    private CharSequence titleText;
    private int titleTextResId;
    static final Object CONFIRM_BUTTON_TAG = "CONFIRM_BUTTON_TAG";
    static final Object CANCEL_BUTTON_TAG = "CANCEL_BUTTON_TAG";
    static final Object TOGGLE_BUTTON_TAG = "TOGGLE_BUTTON_TAG";
    private final LinkedHashSet onPositiveButtonClickListeners = new LinkedHashSet();
    private final LinkedHashSet onNegativeButtonClickListeners = new LinkedHashSet();
    private final LinkedHashSet onCancelListeners = new LinkedHashSet();
    private final LinkedHashSet onDismissListeners = new LinkedHashSet();

    @Retention(RetentionPolicy.SOURCE)
    public @interface InputMode {
    }

    static /* synthetic */ LinkedHashSet access$000(MaterialDatePicker x0) {
        return x0.onPositiveButtonClickListeners;
    }

    static /* synthetic */ LinkedHashSet access$100(MaterialDatePicker x0) {
        return x0.onNegativeButtonClickListeners;
    }

    static /* synthetic */ void access$200(MaterialDatePicker x0) {
        x0.updateHeader();
    }

    static /* synthetic */ DateSelector access$300(MaterialDatePicker x0) {
        return x0.getDateSelector();
    }

    static /* synthetic */ Button access$400(MaterialDatePicker x0) {
        return x0.confirmButton;
    }

    static /* synthetic */ CheckableImageButton access$500(MaterialDatePicker x0) {
        return x0.headerToggleButton;
    }

    static /* synthetic */ void access$600(MaterialDatePicker x0, CheckableImageButton x1) {
        x0.updateToggleContentDescription(x1);
    }

    static /* synthetic */ void access$700(MaterialDatePicker x0) {
        x0.startPickerFragment();
    }

    public static long todayInUtcMilliseconds() {
        return UtcDates.getTodayCalendar().getTimeInMillis();
    }

    public static long thisMonthInUtcMilliseconds() {
        return Month.current().timeInMillis;
    }

    public String getHeaderText() {
        return getDateSelector().getSelectionDisplayString(getContext());
    }

    static MaterialDatePicker newInstance(Builder builder) {
        MaterialDatePicker materialDatePicker = new MaterialDatePicker();
        Bundle args = new Bundle();
        args.putInt("OVERRIDE_THEME_RES_ID", builder.overrideThemeResId);
        args.putParcelable("DATE_SELECTOR_KEY", builder.dateSelector);
        args.putParcelable("CALENDAR_CONSTRAINTS_KEY", builder.calendarConstraints);
        args.putInt("TITLE_TEXT_RES_ID_KEY", builder.titleTextResId);
        args.putCharSequence("TITLE_TEXT_KEY", builder.titleText);
        args.putInt("INPUT_MODE_KEY", builder.inputMode);
        args.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", builder.positiveButtonTextResId);
        args.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", builder.positiveButtonText);
        args.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", builder.negativeButtonTextResId);
        args.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", builder.negativeButtonText);
        materialDatePicker.setArguments(args);
        return materialDatePicker;
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("OVERRIDE_THEME_RES_ID", this.overrideThemeResId);
        bundle.putParcelable("DATE_SELECTOR_KEY", this.dateSelector);
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder(this.calendarConstraints);
        if (this.calendar.getCurrentMonth() != null) {
            constraintsBuilder.setOpenAt(this.calendar.getCurrentMonth().timeInMillis);
        }
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", constraintsBuilder.build());
        bundle.putInt("TITLE_TEXT_RES_ID_KEY", this.titleTextResId);
        bundle.putCharSequence("TITLE_TEXT_KEY", this.titleText);
        bundle.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", this.positiveButtonTextResId);
        bundle.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", this.positiveButtonText);
        bundle.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", this.negativeButtonTextResId);
        bundle.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", this.negativeButtonText);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle activeBundle = bundle == null ? getArguments() : bundle;
        this.overrideThemeResId = activeBundle.getInt("OVERRIDE_THEME_RES_ID");
        this.dateSelector = (DateSelector) activeBundle.getParcelable("DATE_SELECTOR_KEY");
        this.calendarConstraints = (CalendarConstraints) activeBundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.titleTextResId = activeBundle.getInt("TITLE_TEXT_RES_ID_KEY");
        this.titleText = activeBundle.getCharSequence("TITLE_TEXT_KEY");
        this.inputMode = activeBundle.getInt("INPUT_MODE_KEY");
        this.positiveButtonTextResId = activeBundle.getInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY");
        this.positiveButtonText = activeBundle.getCharSequence("POSITIVE_BUTTON_TEXT_KEY");
        this.negativeButtonTextResId = activeBundle.getInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY");
        this.negativeButtonText = activeBundle.getCharSequence("NEGATIVE_BUTTON_TEXT_KEY");
    }

    private int getThemeResId(Context context) {
        int i = this.overrideThemeResId;
        if (i != 0) {
            return i;
        }
        return getDateSelector().getDefaultThemeResId(context);
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog = new Dialog(requireContext(), getThemeResId(requireContext()));
        Context context = dialog.getContext();
        this.fullscreen = isFullscreen(context);
        int surfaceColor = MaterialAttributes.resolveOrThrow(context, R.attr.colorSurface, MaterialDatePicker.class.getCanonicalName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(context, null, R.attr.materialCalendarStyle, R.style.Widget_MaterialComponents_MaterialCalendar);
        this.background = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(context);
        this.background.setFillColor(ColorStateList.valueOf(surfaceColor));
        this.background.setElevation(ViewCompat.getElevation(dialog.getWindow().getDecorView()));
        return dialog;
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int layout = this.fullscreen ? R.layout.mtrl_picker_fullscreen : R.layout.mtrl_picker_dialog;
        View root = layoutInflater.inflate(layout, viewGroup);
        Context context = root.getContext();
        if (this.fullscreen) {
            View frame = root.findViewById(R.id.mtrl_calendar_frame);
            frame.setLayoutParams(new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -2));
        } else {
            View pane = root.findViewById(R.id.mtrl_calendar_main_pane);
            pane.setLayoutParams(new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -1));
        }
        TextView findViewById = root.findViewById(R.id.mtrl_picker_header_selection_text);
        this.headerSelectionText = findViewById;
        ViewCompat.setAccessibilityLiveRegion(findViewById, 1);
        this.headerToggleButton = root.findViewById(R.id.mtrl_picker_header_toggle);
        TextView titleTextView = root.findViewById(R.id.mtrl_picker_title_text);
        CharSequence charSequence = this.titleText;
        if (charSequence != null) {
            titleTextView.setText(charSequence);
        } else {
            titleTextView.setText(this.titleTextResId);
        }
        initHeaderToggle(context);
        this.confirmButton = root.findViewById(R.id.confirm_button);
        if (getDateSelector().isSelectionComplete()) {
            this.confirmButton.setEnabled(true);
        } else {
            this.confirmButton.setEnabled(false);
        }
        this.confirmButton.setTag(CONFIRM_BUTTON_TAG);
        CharSequence charSequence2 = this.positiveButtonText;
        if (charSequence2 != null) {
            this.confirmButton.setText(charSequence2);
        } else {
            int i = this.positiveButtonTextResId;
            if (i != 0) {
                this.confirmButton.setText(i);
            }
        }
        this.confirmButton.setOnClickListener(new 1());
        Button cancelButton = root.findViewById(R.id.cancel_button);
        cancelButton.setTag(CANCEL_BUTTON_TAG);
        CharSequence charSequence3 = this.negativeButtonText;
        if (charSequence3 != null) {
            cancelButton.setText(charSequence3);
        } else {
            int i2 = this.negativeButtonTextResId;
            if (i2 != 0) {
                cancelButton.setText(i2);
            }
        }
        cancelButton.setOnClickListener(new 2());
        return root;
    }

    class 1 implements View.OnClickListener {
        1() {
        }

        public void onClick(View v) {
            Iterator it = MaterialDatePicker.access$000(MaterialDatePicker.this).iterator();
            while (it.hasNext()) {
                ((MaterialPickerOnPositiveButtonClickListener) it.next()).onPositiveButtonClick(MaterialDatePicker.this.getSelection());
            }
            MaterialDatePicker.this.dismiss();
        }
    }

    class 2 implements View.OnClickListener {
        2() {
        }

        public void onClick(View v) {
            Iterator it = MaterialDatePicker.access$100(MaterialDatePicker.this).iterator();
            while (it.hasNext()) {
                View.OnClickListener listener = (View.OnClickListener) it.next();
                listener.onClick(v);
            }
            MaterialDatePicker.this.dismiss();
        }
    }

    public void onStart() {
        super.onStart();
        Window window = requireDialog().getWindow();
        if (this.fullscreen) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable(this.background);
            enableEdgeToEdgeIfNeeded(window);
        } else {
            window.setLayout(-2, -2);
            int inset = getResources().getDimensionPixelOffset(R.dimen.mtrl_calendar_dialog_background_inset);
            Rect insets = new Rect(inset, inset, inset, inset);
            window.setBackgroundDrawable(new InsetDrawable(this.background, inset, inset, inset, inset));
            window.getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(requireDialog(), insets));
        }
        startPickerFragment();
    }

    public void onStop() {
        this.pickerFragment.clearOnSelectionChangedListeners();
        super.onStop();
    }

    public final void onCancel(DialogInterface dialogInterface) {
        Iterator it = this.onCancelListeners.iterator();
        while (it.hasNext()) {
            DialogInterface.OnCancelListener listener = (DialogInterface.OnCancelListener) it.next();
            listener.onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        Iterator it = this.onDismissListeners.iterator();
        while (it.hasNext()) {
            DialogInterface.OnDismissListener listener = (DialogInterface.OnDismissListener) it.next();
            listener.onDismiss(dialogInterface);
        }
        ViewGroup viewGroup = getView();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    public final Object getSelection() {
        return getDateSelector().getSelection();
    }

    private void enableEdgeToEdgeIfNeeded(Window window) {
        if (this.edgeToEdgeEnabled) {
            return;
        }
        View headerLayout = requireView().findViewById(R.id.fullscreen_header);
        EdgeToEdgeUtils.applyEdgeToEdge(window, true, ViewUtils.getBackgroundColor(headerLayout), null);
        int originalPaddingTop = headerLayout.getPaddingTop();
        int originalHeaderHeight = headerLayout.getLayoutParams().height;
        ViewCompat.setOnApplyWindowInsetsListener(headerLayout, new 3(originalHeaderHeight, headerLayout, originalPaddingTop));
        this.edgeToEdgeEnabled = true;
    }

    class 3 implements OnApplyWindowInsetsListener {
        final /* synthetic */ View val$headerLayout;
        final /* synthetic */ int val$originalHeaderHeight;
        final /* synthetic */ int val$originalPaddingTop;

        3(int i, View view, int i2) {
            this.val$originalHeaderHeight = i;
            this.val$headerLayout = view;
            this.val$originalPaddingTop = i2;
        }

        public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
            int topInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            if (this.val$originalHeaderHeight >= 0) {
                this.val$headerLayout.getLayoutParams().height = this.val$originalHeaderHeight + topInset;
                View view = this.val$headerLayout;
                view.setLayoutParams(view.getLayoutParams());
            }
            View view2 = this.val$headerLayout;
            view2.setPadding(view2.getPaddingLeft(), this.val$originalPaddingTop + topInset, this.val$headerLayout.getPaddingRight(), this.val$headerLayout.getPaddingBottom());
            return insets;
        }
    }

    private void updateHeader() {
        String headerText = getHeaderText();
        this.headerSelectionText.setContentDescription(String.format(getString(R.string.mtrl_picker_announce_current_selection), new Object[]{headerText}));
        this.headerSelectionText.setText(headerText);
    }

    private void startPickerFragment() {
        PickerFragment pickerFragment;
        int themeResId = getThemeResId(requireContext());
        this.calendar = MaterialCalendar.newInstance(getDateSelector(), themeResId, this.calendarConstraints);
        if (this.headerToggleButton.isChecked()) {
            pickerFragment = MaterialTextInputPicker.newInstance(getDateSelector(), themeResId, this.calendarConstraints);
        } else {
            pickerFragment = this.calendar;
        }
        this.pickerFragment = pickerFragment;
        updateHeader();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mtrl_calendar_frame, this.pickerFragment);
        fragmentTransaction.commitNow();
        this.pickerFragment.addOnSelectionChangedListener(new 4());
    }

    class 4 extends OnSelectionChangedListener {
        4() {
        }

        public void onSelectionChanged(Object obj) {
            MaterialDatePicker.access$200(MaterialDatePicker.this);
            MaterialDatePicker.access$400(MaterialDatePicker.this).setEnabled(MaterialDatePicker.access$300(MaterialDatePicker.this).isSelectionComplete());
        }

        public void onIncompleteSelectionChanged() {
            MaterialDatePicker.access$400(MaterialDatePicker.this).setEnabled(false);
        }
    }

    private void initHeaderToggle(Context context) {
        this.headerToggleButton.setTag(TOGGLE_BUTTON_TAG);
        this.headerToggleButton.setImageDrawable(createHeaderToggleDrawable(context));
        this.headerToggleButton.setChecked(this.inputMode != 0);
        ViewCompat.setAccessibilityDelegate(this.headerToggleButton, (AccessibilityDelegateCompat) null);
        updateToggleContentDescription(this.headerToggleButton);
        this.headerToggleButton.setOnClickListener(new 5());
    }

    class 5 implements View.OnClickListener {
        5() {
        }

        public void onClick(View v) {
            MaterialDatePicker.access$400(MaterialDatePicker.this).setEnabled(MaterialDatePicker.access$300(MaterialDatePicker.this).isSelectionComplete());
            MaterialDatePicker.access$500(MaterialDatePicker.this).toggle();
            MaterialDatePicker materialDatePicker = MaterialDatePicker.this;
            MaterialDatePicker.access$600(materialDatePicker, MaterialDatePicker.access$500(materialDatePicker));
            MaterialDatePicker.access$700(MaterialDatePicker.this);
        }
    }

    private void updateToggleContentDescription(CheckableImageButton toggle) {
        String contentDescription;
        if (this.headerToggleButton.isChecked()) {
            contentDescription = toggle.getContext().getString(R.string.mtrl_picker_toggle_to_calendar_input_mode);
        } else {
            contentDescription = toggle.getContext().getString(R.string.mtrl_picker_toggle_to_text_input_mode);
        }
        this.headerToggleButton.setContentDescription(contentDescription);
    }

    private DateSelector getDateSelector() {
        if (this.dateSelector == null) {
            this.dateSelector = (DateSelector) getArguments().getParcelable("DATE_SELECTOR_KEY");
        }
        return this.dateSelector;
    }

    private static Drawable createHeaderToggleDrawable(Context context) {
        StateListDrawable toggleDrawable = new StateListDrawable();
        toggleDrawable.addState(new int[]{16842912}, AppCompatResources.getDrawable(context, R.drawable.material_ic_calendar_black_24dp));
        toggleDrawable.addState(new int[0], AppCompatResources.getDrawable(context, R.drawable.material_ic_edit_black_24dp));
        return toggleDrawable;
    }

    static boolean isFullscreen(Context context) {
        return readMaterialCalendarStyleBoolean(context, 16843277);
    }

    static boolean isNestedScrollable(Context context) {
        return readMaterialCalendarStyleBoolean(context, R.attr.nestedScrollable);
    }

    static boolean readMaterialCalendarStyleBoolean(Context context, int attributeResId) {
        int calendarStyle = MaterialAttributes.resolveOrThrow(context, R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName());
        int[] attrs = {attributeResId};
        TypedArray a = context.obtainStyledAttributes(calendarStyle, attrs);
        boolean attributeValue = a.getBoolean(0, false);
        a.recycle();
        return attributeValue;
    }

    private static int getPaddedPickerWidth(Context context) {
        Resources resources = context.getResources();
        int padding = resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_content_padding);
        int daysInWeek = Month.current().daysInWeek;
        int dayWidth = resources.getDimensionPixelSize(R.dimen.mtrl_calendar_day_width);
        int horizontalSpace = resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_horizontal_padding);
        return (padding * 2) + (daysInWeek * dayWidth) + ((daysInWeek - 1) * horizontalSpace);
    }

    public boolean addOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener materialPickerOnPositiveButtonClickListener) {
        return this.onPositiveButtonClickListeners.add(materialPickerOnPositiveButtonClickListener);
    }

    public boolean removeOnPositiveButtonClickListener(MaterialPickerOnPositiveButtonClickListener materialPickerOnPositiveButtonClickListener) {
        return this.onPositiveButtonClickListeners.remove(materialPickerOnPositiveButtonClickListener);
    }

    public void clearOnPositiveButtonClickListeners() {
        this.onPositiveButtonClickListeners.clear();
    }

    public boolean addOnNegativeButtonClickListener(View.OnClickListener onNegativeButtonClickListener) {
        return this.onNegativeButtonClickListeners.add(onNegativeButtonClickListener);
    }

    public boolean removeOnNegativeButtonClickListener(View.OnClickListener onNegativeButtonClickListener) {
        return this.onNegativeButtonClickListeners.remove(onNegativeButtonClickListener);
    }

    public void clearOnNegativeButtonClickListeners() {
        this.onNegativeButtonClickListeners.clear();
    }

    public boolean addOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return this.onCancelListeners.add(onCancelListener);
    }

    public boolean removeOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return this.onCancelListeners.remove(onCancelListener);
    }

    public void clearOnCancelListeners() {
        this.onCancelListeners.clear();
    }

    public boolean addOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return this.onDismissListeners.add(onDismissListener);
    }

    public boolean removeOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return this.onDismissListeners.remove(onDismissListener);
    }

    public void clearOnDismissListeners() {
        this.onDismissListeners.clear();
    }

    public static final class Builder {
        CalendarConstraints calendarConstraints;
        final DateSelector dateSelector;
        int overrideThemeResId = 0;
        int titleTextResId = 0;
        CharSequence titleText = null;
        int positiveButtonTextResId = 0;
        CharSequence positiveButtonText = null;
        int negativeButtonTextResId = 0;
        CharSequence negativeButtonText = null;
        Object selection = null;
        int inputMode = 0;

        private Builder(DateSelector dateSelector) {
            this.dateSelector = dateSelector;
        }

        public static Builder customDatePicker(DateSelector dateSelector) {
            return new Builder(dateSelector);
        }

        public static Builder datePicker() {
            return new Builder(new SingleDateSelector());
        }

        public static Builder dateRangePicker() {
            return new Builder(new RangeDateSelector());
        }

        public Builder setSelection(Object obj) {
            this.selection = obj;
            return this;
        }

        public Builder setTheme(int themeResId) {
            this.overrideThemeResId = themeResId;
            return this;
        }

        public Builder setCalendarConstraints(CalendarConstraints bounds) {
            this.calendarConstraints = bounds;
            return this;
        }

        public Builder setTitleText(int titleTextResId) {
            this.titleTextResId = titleTextResId;
            this.titleText = null;
            return this;
        }

        public Builder setTitleText(CharSequence charSequence) {
            this.titleText = charSequence;
            this.titleTextResId = 0;
            return this;
        }

        public Builder setPositiveButtonText(int textId) {
            this.positiveButtonTextResId = textId;
            this.positiveButtonText = null;
            return this;
        }

        public Builder setPositiveButtonText(CharSequence text) {
            this.positiveButtonText = text;
            this.positiveButtonTextResId = 0;
            return this;
        }

        public Builder setNegativeButtonText(int textId) {
            this.negativeButtonTextResId = textId;
            this.negativeButtonText = null;
            return this;
        }

        public Builder setNegativeButtonText(CharSequence text) {
            this.negativeButtonText = text;
            this.negativeButtonTextResId = 0;
            return this;
        }

        public Builder setInputMode(int inputMode) {
            this.inputMode = inputMode;
            return this;
        }

        public MaterialDatePicker build() {
            if (this.calendarConstraints == null) {
                this.calendarConstraints = new CalendarConstraints.Builder().build();
            }
            if (this.titleTextResId == 0) {
                this.titleTextResId = this.dateSelector.getDefaultTitleResId();
            }
            Object obj = this.selection;
            if (obj != null) {
                this.dateSelector.setSelection(obj);
            }
            if (this.calendarConstraints.getOpenAt() == null) {
                this.calendarConstraints.setOpenAt(createDefaultOpenAt());
            }
            return MaterialDatePicker.newInstance(this);
        }

        private Month createDefaultOpenAt() {
            if (!this.dateSelector.getSelectedDays().isEmpty()) {
                Month firstSelectedMonth = Month.create(((Long) this.dateSelector.getSelectedDays().iterator().next()).longValue());
                if (monthInValidRange(firstSelectedMonth, this.calendarConstraints)) {
                    return firstSelectedMonth;
                }
            }
            Month thisMonth = Month.current();
            return monthInValidRange(thisMonth, this.calendarConstraints) ? thisMonth : this.calendarConstraints.getStart();
        }

        private static boolean monthInValidRange(Month month, CalendarConstraints constraints) {
            return month.compareTo(constraints.getStart()) >= 0 && month.compareTo(constraints.getEnd()) <= 0;
        }
    }
}
