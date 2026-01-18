package com.google.android.material.datepicker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.datepicker.MaterialCalendar;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
class YearGridAdapter extends RecyclerView.Adapter {
    private final MaterialCalendar materialCalendar;

    static /* synthetic */ MaterialCalendar access$000(YearGridAdapter x0) {
        return x0.materialCalendar;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(TextView view) {
            super(view);
            this.textView = view;
        }
    }

    YearGridAdapter(MaterialCalendar materialCalendar) {
        this.materialCalendar = materialCalendar;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        TextView yearTextView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mtrl_calendar_year, viewGroup, false);
        return new ViewHolder(yearTextView);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        int year = getYearForPosition(position);
        String navigateYear = viewHolder.textView.getContext().getString(R.string.mtrl_picker_navigate_to_year_description);
        viewHolder.textView.setText(String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(year)}));
        viewHolder.textView.setContentDescription(String.format(navigateYear, new Object[]{Integer.valueOf(year)}));
        CalendarStyle styles = this.materialCalendar.getCalendarStyle();
        Calendar calendar = UtcDates.getTodayCalendar();
        CalendarItemStyle style = calendar.get(1) == year ? styles.todayYear : styles.year;
        for (Long day : this.materialCalendar.getDateSelector().getSelectedDays()) {
            calendar.setTimeInMillis(day.longValue());
            if (calendar.get(1) == year) {
                style = styles.selectedYear;
            }
        }
        style.styleItem(viewHolder.textView);
        viewHolder.textView.setOnClickListener(createYearClickListener(year));
    }

    class 1 implements View.OnClickListener {
        final /* synthetic */ int val$year;

        1(int i) {
            this.val$year = i;
        }

        public void onClick(View view) {
            Month current = Month.create(this.val$year, YearGridAdapter.access$000(YearGridAdapter.this).getCurrentMonth().month);
            CalendarConstraints calendarConstraints = YearGridAdapter.access$000(YearGridAdapter.this).getCalendarConstraints();
            Month moveTo = calendarConstraints.clamp(current);
            YearGridAdapter.access$000(YearGridAdapter.this).setCurrentMonth(moveTo);
            YearGridAdapter.access$000(YearGridAdapter.this).setSelector(MaterialCalendar.CalendarSelector.DAY);
        }
    }

    private View.OnClickListener createYearClickListener(int year) {
        return new 1(year);
    }

    public int getItemCount() {
        return this.materialCalendar.getCalendarConstraints().getYearSpan();
    }

    int getPositionForYear(int year) {
        return year - this.materialCalendar.getCalendarConstraints().getStart().year;
    }

    int getYearForPosition(int position) {
        return this.materialCalendar.getCalendarConstraints().getStart().year + position;
    }
}
