package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.datepicker.MaterialCalendar;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class MonthsPagerAdapter extends RecyclerView.Adapter {
    private final CalendarConstraints calendarConstraints;
    private final DateSelector dateSelector;
    private final int itemHeight;
    private final MaterialCalendar.OnDayClickListener onDayClickListener;

    static /* synthetic */ MaterialCalendar.OnDayClickListener access$000(MonthsPagerAdapter x0) {
        return x0.onDayClickListener;
    }

    MonthsPagerAdapter(Context context, DateSelector dateSelector, CalendarConstraints calendarConstraints, MaterialCalendar.OnDayClickListener onDayClickListener) {
        Month firstPage = calendarConstraints.getStart();
        Month lastPage = calendarConstraints.getEnd();
        Month currentPage = calendarConstraints.getOpenAt();
        if (firstPage.compareTo(currentPage) > 0) {
            throw new IllegalArgumentException("firstPage cannot be after currentPage");
        }
        if (currentPage.compareTo(lastPage) > 0) {
            throw new IllegalArgumentException("currentPage cannot be after lastPage");
        }
        int daysHeight = MonthAdapter.MAXIMUM_WEEKS * MaterialCalendar.getDayHeight(context);
        int labelHeight = MaterialDatePicker.isFullscreen(context) ? MaterialCalendar.getDayHeight(context) : 0;
        this.itemHeight = daysHeight + labelHeight;
        this.calendarConstraints = calendarConstraints;
        this.dateSelector = dateSelector;
        this.onDayClickListener = onDayClickListener;
        setHasStableIds(true);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final MaterialCalendarGridView monthGrid;
        final TextView monthTitle;

        ViewHolder(LinearLayout container, boolean showLabel) {
            super(container);
            TextView findViewById = container.findViewById(R.id.month_title);
            this.monthTitle = findViewById;
            ViewCompat.setAccessibilityHeading(findViewById, true);
            this.monthGrid = container.findViewById(R.id.month_grid);
            if (!showLabel) {
                findViewById.setVisibility(8);
            }
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LinearLayout container = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mtrl_calendar_month_labeled, viewGroup, false);
        if (MaterialDatePicker.isFullscreen(viewGroup.getContext())) {
            container.setLayoutParams(new RecyclerView.LayoutParams(-1, this.itemHeight));
            return new ViewHolder(container, true);
        }
        return new ViewHolder(container, false);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Month month = this.calendarConstraints.getStart().monthsLater(position);
        viewHolder.monthTitle.setText(month.getLongName());
        MaterialCalendarGridView monthGrid = viewHolder.monthGrid.findViewById(R.id.month_grid);
        if (monthGrid.getAdapter() != null && month.equals(monthGrid.getAdapter().month)) {
            monthGrid.invalidate();
            monthGrid.getAdapter().updateSelectedStates(monthGrid);
        } else {
            MonthAdapter monthAdapter = new MonthAdapter(month, this.dateSelector, this.calendarConstraints);
            monthGrid.setNumColumns(month.daysInWeek);
            monthGrid.setAdapter((ListAdapter) monthAdapter);
        }
        monthGrid.setOnItemClickListener(new 1(monthGrid));
    }

    class 1 implements AdapterView.OnItemClickListener {
        final /* synthetic */ MaterialCalendarGridView val$monthGrid;

        1(MaterialCalendarGridView materialCalendarGridView) {
            this.val$monthGrid = materialCalendarGridView;
        }

        public void onItemClick(AdapterView adapterView, View view, int position, long id) {
            if (this.val$monthGrid.getAdapter().withinMonth(position)) {
                MonthsPagerAdapter.access$000(MonthsPagerAdapter.this).onDayClick(this.val$monthGrid.getAdapter().getItem(position).longValue());
            }
        }
    }

    public long getItemId(int position) {
        return this.calendarConstraints.getStart().monthsLater(position).getStableId();
    }

    public int getItemCount() {
        return this.calendarConstraints.getMonthSpan();
    }

    CharSequence getPageTitle(int position) {
        return getPageMonth(position).getLongName();
    }

    Month getPageMonth(int position) {
        return this.calendarConstraints.getStart().monthsLater(position);
    }

    int getPosition(Month month) {
        return this.calendarConstraints.getStart().monthsUntil(month);
    }
}
