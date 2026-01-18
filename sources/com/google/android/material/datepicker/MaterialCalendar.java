package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
public final class MaterialCalendar extends PickerFragment {
    private static final String CALENDAR_CONSTRAINTS_KEY = "CALENDAR_CONSTRAINTS_KEY";
    private static final String CURRENT_MONTH_KEY = "CURRENT_MONTH_KEY";
    private static final String GRID_SELECTOR_KEY = "GRID_SELECTOR_KEY";
    private static final int SMOOTH_SCROLL_MAX = 3;
    private static final String THEME_RES_ID_KEY = "THEME_RES_ID_KEY";
    private CalendarConstraints calendarConstraints;
    private CalendarSelector calendarSelector;
    private CalendarStyle calendarStyle;
    private Month current;
    private DateSelector dateSelector;
    private View dayFrame;
    private RecyclerView recyclerView;
    private int themeResId;
    private View yearFrame;
    private RecyclerView yearSelector;
    static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
    static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
    static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";

    enum CalendarSelector {
        DAY,
        YEAR
    }

    interface OnDayClickListener {
        void onDayClick(long j);
    }

    static /* synthetic */ RecyclerView access$000(MaterialCalendar x0) {
        return x0.recyclerView;
    }

    static /* synthetic */ CalendarConstraints access$100(MaterialCalendar x0) {
        return x0.calendarConstraints;
    }

    static /* synthetic */ DateSelector access$200(MaterialCalendar x0) {
        return x0.dateSelector;
    }

    static /* synthetic */ RecyclerView access$300(MaterialCalendar x0) {
        return x0.yearSelector;
    }

    static /* synthetic */ CalendarStyle access$400(MaterialCalendar x0) {
        return x0.calendarStyle;
    }

    static /* synthetic */ View access$500(MaterialCalendar x0) {
        return x0.dayFrame;
    }

    static /* synthetic */ Month access$602(MaterialCalendar x0, Month x1) {
        x0.current = x1;
        return x1;
    }

    public static MaterialCalendar newInstance(DateSelector dateSelector, int themeResId, CalendarConstraints calendarConstraints) {
        MaterialCalendar materialCalendar = new MaterialCalendar();
        Bundle args = new Bundle();
        args.putInt("THEME_RES_ID_KEY", themeResId);
        args.putParcelable("GRID_SELECTOR_KEY", dateSelector);
        args.putParcelable("CALENDAR_CONSTRAINTS_KEY", calendarConstraints);
        args.putParcelable("CURRENT_MONTH_KEY", calendarConstraints.getOpenAt());
        materialCalendar.setArguments(args);
        return materialCalendar;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("THEME_RES_ID_KEY", this.themeResId);
        bundle.putParcelable("GRID_SELECTOR_KEY", this.dateSelector);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
        bundle.putParcelable("CURRENT_MONTH_KEY", this.current);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle activeBundle = bundle == null ? getArguments() : bundle;
        this.themeResId = activeBundle.getInt("THEME_RES_ID_KEY");
        this.dateSelector = (DateSelector) activeBundle.getParcelable("GRID_SELECTOR_KEY");
        this.calendarConstraints = (CalendarConstraints) activeBundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.current = (Month) activeBundle.getParcelable("CURRENT_MONTH_KEY");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int layout;
        int orientation;
        ContextThemeWrapper themedContext = new ContextThemeWrapper(getContext(), this.themeResId);
        this.calendarStyle = new CalendarStyle(themedContext);
        LayoutInflater themedInflater = layoutInflater.cloneInContext(themedContext);
        Month earliestMonth = this.calendarConstraints.getStart();
        if (MaterialDatePicker.isFullscreen(themedContext)) {
            int layout2 = R.layout.mtrl_calendar_vertical;
            layout = layout2;
            orientation = 1;
        } else {
            int layout3 = R.layout.mtrl_calendar_horizontal;
            layout = layout3;
            orientation = 0;
        }
        View root = themedInflater.inflate(layout, viewGroup, false);
        root.setMinimumHeight(getDialogPickerHeight(requireContext()));
        GridView daysHeader = root.findViewById(R.id.mtrl_calendar_days_of_week);
        ViewCompat.setAccessibilityDelegate(daysHeader, new 1());
        daysHeader.setAdapter(new DaysOfWeekAdapter());
        daysHeader.setNumColumns(earliestMonth.daysInWeek);
        daysHeader.setEnabled(false);
        this.recyclerView = root.findViewById(R.id.mtrl_calendar_months);
        SmoothCalendarLayoutManager layoutManager = new 2(getContext(), orientation, false, orientation);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
        MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(themedContext, this.dateSelector, this.calendarConstraints, new 3());
        this.recyclerView.setAdapter(monthsPagerAdapter);
        int columns = themedContext.getResources().getInteger(R.integer.mtrl_calendar_year_selector_span);
        RecyclerView findViewById = root.findViewById(R.id.mtrl_calendar_year_selector_frame);
        this.yearSelector = findViewById;
        if (findViewById != null) {
            findViewById.setHasFixedSize(true);
            this.yearSelector.setLayoutManager(new GridLayoutManager(themedContext, columns, 1, false));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(createItemDecoration());
        }
        if (root.findViewById(R.id.month_navigation_fragment_toggle) != null) {
            addActionsToMonthNavigation(root, monthsPagerAdapter);
        }
        if (!MaterialDatePicker.isFullscreen(themedContext)) {
            new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        this.recyclerView.scrollToPosition(monthsPagerAdapter.getPosition(this.current));
        return root;
    }

    class 1 extends AccessibilityDelegateCompat {
        1() {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setCollectionInfo((Object) null);
        }
    }

    class 2 extends SmoothCalendarLayoutManager {
        final /* synthetic */ int val$orientation;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        2(Context context, int orientation, boolean reverseLayout, int i) {
            super(context, orientation, reverseLayout);
            this.val$orientation = i;
        }

        protected void calculateExtraLayoutSpace(RecyclerView.State state, int[] ints) {
            if (this.val$orientation == 0) {
                ints[0] = MaterialCalendar.access$000(MaterialCalendar.this).getWidth();
                ints[1] = MaterialCalendar.access$000(MaterialCalendar.this).getWidth();
            } else {
                ints[0] = MaterialCalendar.access$000(MaterialCalendar.this).getHeight();
                ints[1] = MaterialCalendar.access$000(MaterialCalendar.this).getHeight();
            }
        }
    }

    class 3 implements OnDayClickListener {
        3() {
        }

        public void onDayClick(long day) {
            if (MaterialCalendar.access$100(MaterialCalendar.this).getDateValidator().isValid(day)) {
                MaterialCalendar.access$200(MaterialCalendar.this).select(day);
                Iterator it = MaterialCalendar.this.onSelectionChangedListeners.iterator();
                while (it.hasNext()) {
                    ((OnSelectionChangedListener) it.next()).onSelectionChanged(MaterialCalendar.access$200(MaterialCalendar.this).getSelection());
                }
                MaterialCalendar.access$000(MaterialCalendar.this).getAdapter().notifyDataSetChanged();
                if (MaterialCalendar.access$300(MaterialCalendar.this) != null) {
                    MaterialCalendar.access$300(MaterialCalendar.this).getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    class 4 extends RecyclerView.ItemDecoration {
        private final Calendar startItem = UtcDates.getUtcCalendar();
        private final Calendar endItem = UtcDates.getUtcCalendar();

        4() {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            YearGridAdapter adapter;
            int width;
            GridLayoutManager layoutManager;
            Iterator it;
            Pair<Long, Long> range;
            int firstHighlightPosition;
            if (!(recyclerView.getAdapter() instanceof YearGridAdapter) || !(recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
                return;
            }
            YearGridAdapter adapter2 = (YearGridAdapter) recyclerView.getAdapter();
            GridLayoutManager layoutManager2 = recyclerView.getLayoutManager();
            Iterator it2 = MaterialCalendar.access$200(MaterialCalendar.this).getSelectedRanges().iterator();
            while (it2.hasNext()) {
                Pair<Long, Long> range2 = (Pair) it2.next();
                if (range2.first != null && range2.second != null) {
                    this.startItem.setTimeInMillis(((Long) range2.first).longValue());
                    this.endItem.setTimeInMillis(((Long) range2.second).longValue());
                    int firstHighlightPosition2 = adapter2.getPositionForYear(this.startItem.get(1));
                    int lastHighlightPosition = adapter2.getPositionForYear(this.endItem.get(1));
                    View firstView = layoutManager2.findViewByPosition(firstHighlightPosition2);
                    View lastView = layoutManager2.findViewByPosition(lastHighlightPosition);
                    int firstRow = firstHighlightPosition2 / layoutManager2.getSpanCount();
                    int lastRow = lastHighlightPosition / layoutManager2.getSpanCount();
                    int row = firstRow;
                    while (row <= lastRow) {
                        int firstPositionInRow = layoutManager2.getSpanCount() * row;
                        View viewInRow = layoutManager2.findViewByPosition(firstPositionInRow);
                        if (viewInRow != null) {
                            int top = viewInRow.getTop() + MaterialCalendar.access$400(MaterialCalendar.this).year.getTopInset();
                            adapter = adapter2;
                            int bottom = viewInRow.getBottom() - MaterialCalendar.access$400(MaterialCalendar.this).year.getBottomInset();
                            int left = row == firstRow ? firstView.getLeft() + (firstView.getWidth() / 2) : 0;
                            if (row == lastRow) {
                                width = lastView.getLeft() + (lastView.getWidth() / 2);
                            } else {
                                width = recyclerView.getWidth();
                            }
                            int right = width;
                            layoutManager = layoutManager2;
                            it = it2;
                            range = range2;
                            firstHighlightPosition = firstHighlightPosition2;
                            canvas.drawRect(left, top, right, bottom, MaterialCalendar.access$400(MaterialCalendar.this).rangeFill);
                        } else {
                            adapter = adapter2;
                            layoutManager = layoutManager2;
                            it = it2;
                            range = range2;
                            firstHighlightPosition = firstHighlightPosition2;
                        }
                        row++;
                        adapter2 = adapter;
                        layoutManager2 = layoutManager;
                        range2 = range;
                        it2 = it;
                        firstHighlightPosition2 = firstHighlightPosition;
                    }
                }
            }
        }
    }

    private RecyclerView.ItemDecoration createItemDecoration() {
        return new 4();
    }

    Month getCurrentMonth() {
        return this.current;
    }

    CalendarConstraints getCalendarConstraints() {
        return this.calendarConstraints;
    }

    void setCurrentMonth(Month moveTo) {
        MonthsPagerAdapter adapter = (MonthsPagerAdapter) this.recyclerView.getAdapter();
        int moveToPosition = adapter.getPosition(moveTo);
        int distance = moveToPosition - adapter.getPosition(this.current);
        boolean jump = Math.abs(distance) > 3;
        boolean isForward = distance > 0;
        this.current = moveTo;
        if (jump && isForward) {
            this.recyclerView.scrollToPosition(moveToPosition - 3);
            postSmoothRecyclerViewScroll(moveToPosition);
        } else if (jump) {
            this.recyclerView.scrollToPosition(moveToPosition + 3);
            postSmoothRecyclerViewScroll(moveToPosition);
        } else {
            postSmoothRecyclerViewScroll(moveToPosition);
        }
    }

    public DateSelector getDateSelector() {
        return this.dateSelector;
    }

    CalendarStyle getCalendarStyle() {
        return this.calendarStyle;
    }

    static int getDayHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.mtrl_calendar_day_height);
    }

    void setSelector(CalendarSelector selector) {
        this.calendarSelector = selector;
        if (selector == CalendarSelector.YEAR) {
            this.yearSelector.getLayoutManager().scrollToPosition(((YearGridAdapter) this.yearSelector.getAdapter()).getPositionForYear(this.current.year));
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
        } else if (selector == CalendarSelector.DAY) {
            this.yearFrame.setVisibility(8);
            this.dayFrame.setVisibility(0);
            setCurrentMonth(this.current);
        }
    }

    void toggleVisibleSelector() {
        if (this.calendarSelector == CalendarSelector.YEAR) {
            setSelector(CalendarSelector.DAY);
        } else if (this.calendarSelector == CalendarSelector.DAY) {
            setSelector(CalendarSelector.YEAR);
        }
    }

    private void addActionsToMonthNavigation(View root, MonthsPagerAdapter monthsPagerAdapter) {
        MaterialButton monthDropSelect = root.findViewById(R.id.month_navigation_fragment_toggle);
        monthDropSelect.setTag(SELECTOR_TOGGLE_TAG);
        ViewCompat.setAccessibilityDelegate(monthDropSelect, new 5());
        MaterialButton monthPrev = root.findViewById(R.id.month_navigation_previous);
        monthPrev.setTag(NAVIGATION_PREV_TAG);
        MaterialButton monthNext = root.findViewById(R.id.month_navigation_next);
        monthNext.setTag(NAVIGATION_NEXT_TAG);
        this.yearFrame = root.findViewById(R.id.mtrl_calendar_year_selector_frame);
        this.dayFrame = root.findViewById(R.id.mtrl_calendar_day_selector_frame);
        setSelector(CalendarSelector.DAY);
        monthDropSelect.setText(this.current.getLongName());
        this.recyclerView.addOnScrollListener(new 6(monthsPagerAdapter, monthDropSelect));
        monthDropSelect.setOnClickListener(new 7());
        monthNext.setOnClickListener(new 8(monthsPagerAdapter));
        monthPrev.setOnClickListener(new 9(monthsPagerAdapter));
    }

    class 5 extends AccessibilityDelegateCompat {
        5() {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            String string;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            if (MaterialCalendar.access$500(MaterialCalendar.this).getVisibility() == 0) {
                string = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_year_selection);
            } else {
                string = MaterialCalendar.this.getString(R.string.mtrl_picker_toggle_to_day_selection);
            }
            accessibilityNodeInfoCompat.setHintText(string);
        }
    }

    class 6 extends RecyclerView.OnScrollListener {
        final /* synthetic */ MaterialButton val$monthDropSelect;
        final /* synthetic */ MonthsPagerAdapter val$monthsPagerAdapter;

        6(MonthsPagerAdapter monthsPagerAdapter, MaterialButton materialButton) {
            this.val$monthsPagerAdapter = monthsPagerAdapter;
            this.val$monthDropSelect = materialButton;
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int currentItem;
            if (dx < 0) {
                currentItem = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
            } else {
                currentItem = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
            }
            MaterialCalendar.access$602(MaterialCalendar.this, this.val$monthsPagerAdapter.getPageMonth(currentItem));
            this.val$monthDropSelect.setText(this.val$monthsPagerAdapter.getPageTitle(currentItem));
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 0) {
                CharSequence announcementText = this.val$monthDropSelect.getText();
                if (Build.VERSION.SDK_INT >= 16) {
                    recyclerView.announceForAccessibility(announcementText);
                } else {
                    recyclerView.sendAccessibilityEvent(2048);
                }
            }
        }
    }

    class 7 implements View.OnClickListener {
        7() {
        }

        public void onClick(View view) {
            MaterialCalendar.this.toggleVisibleSelector();
        }
    }

    class 8 implements View.OnClickListener {
        final /* synthetic */ MonthsPagerAdapter val$monthsPagerAdapter;

        8(MonthsPagerAdapter monthsPagerAdapter) {
            this.val$monthsPagerAdapter = monthsPagerAdapter;
        }

        public void onClick(View view) {
            int currentItem = MaterialCalendar.this.getLayoutManager().findFirstVisibleItemPosition();
            if (currentItem + 1 < MaterialCalendar.access$000(MaterialCalendar.this).getAdapter().getItemCount()) {
                MaterialCalendar.this.setCurrentMonth(this.val$monthsPagerAdapter.getPageMonth(currentItem + 1));
            }
        }
    }

    class 9 implements View.OnClickListener {
        final /* synthetic */ MonthsPagerAdapter val$monthsPagerAdapter;

        9(MonthsPagerAdapter monthsPagerAdapter) {
            this.val$monthsPagerAdapter = monthsPagerAdapter;
        }

        public void onClick(View view) {
            int currentItem = MaterialCalendar.this.getLayoutManager().findLastVisibleItemPosition();
            if (currentItem - 1 >= 0) {
                MaterialCalendar.this.setCurrentMonth(this.val$monthsPagerAdapter.getPageMonth(currentItem - 1));
            }
        }
    }

    class 10 implements Runnable {
        final /* synthetic */ int val$position;

        10(int i) {
            this.val$position = i;
        }

        public void run() {
            MaterialCalendar.access$000(MaterialCalendar.this).smoothScrollToPosition(this.val$position);
        }
    }

    private void postSmoothRecyclerViewScroll(int position) {
        this.recyclerView.post(new 10(position));
    }

    private static int getDialogPickerHeight(Context context) {
        Resources resources = context.getResources();
        int navigationHeight = resources.getDimensionPixelSize(R.dimen.mtrl_calendar_navigation_height) + resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_top_padding) + resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_navigation_bottom_padding);
        int daysOfWeekHeight = resources.getDimensionPixelSize(R.dimen.mtrl_calendar_days_of_week_height);
        int calendarHeight = (MonthAdapter.MAXIMUM_WEEKS * resources.getDimensionPixelSize(R.dimen.mtrl_calendar_day_height)) + ((MonthAdapter.MAXIMUM_WEEKS - 1) * resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_month_vertical_padding));
        int calendarPadding = resources.getDimensionPixelOffset(R.dimen.mtrl_calendar_bottom_padding);
        return navigationHeight + daysOfWeekHeight + calendarHeight + calendarPadding;
    }

    LinearLayoutManager getLayoutManager() {
        return this.recyclerView.getLayoutManager();
    }

    public boolean addOnSelectionChangedListener(OnSelectionChangedListener onSelectionChangedListener) {
        return super.addOnSelectionChangedListener(onSelectionChangedListener);
    }
}
