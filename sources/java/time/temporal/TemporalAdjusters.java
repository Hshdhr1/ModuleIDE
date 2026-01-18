package java.time.temporal;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.function.UnaryOperator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class TemporalAdjusters {
    private TemporalAdjusters() {
    }

    public static TemporalAdjuster ofDateAdjuster(UnaryOperator unaryOperator) {
        TemporalAdjusters$$ExternalSyntheticBackport0.m(unaryOperator, "dateBasedAdjuster");
        return new TemporalAdjusters$$ExternalSyntheticLambda6(unaryOperator);
    }

    static /* synthetic */ Temporal lambda$ofDateAdjuster$0(UnaryOperator unaryOperator, Temporal temporal) {
        return temporal.with((LocalDate) unaryOperator.apply(LocalDate.from(temporal)));
    }

    public static TemporalAdjuster firstDayOfMonth() {
        return new TemporalAdjusters$$ExternalSyntheticLambda4();
    }

    static /* synthetic */ Temporal lambda$firstDayOfMonth$1(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_MONTH, 1L);
    }

    static /* synthetic */ Temporal lambda$lastDayOfMonth$2(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_MONTH, temporal.range(ChronoField.DAY_OF_MONTH).getMaximum());
    }

    public static TemporalAdjuster lastDayOfMonth() {
        return new TemporalAdjusters$$ExternalSyntheticLambda10();
    }

    public static TemporalAdjuster firstDayOfNextMonth() {
        return new TemporalAdjusters$$ExternalSyntheticLambda13();
    }

    static /* synthetic */ Temporal lambda$firstDayOfNextMonth$3(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_MONTH, 1L).plus(1L, ChronoUnit.MONTHS);
    }

    public static TemporalAdjuster firstDayOfYear() {
        return new TemporalAdjusters$$ExternalSyntheticLambda8();
    }

    static /* synthetic */ Temporal lambda$firstDayOfYear$4(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_YEAR, 1L);
    }

    static /* synthetic */ Temporal lambda$lastDayOfYear$5(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_YEAR, temporal.range(ChronoField.DAY_OF_YEAR).getMaximum());
    }

    public static TemporalAdjuster lastDayOfYear() {
        return new TemporalAdjusters$$ExternalSyntheticLambda11();
    }

    public static TemporalAdjuster firstDayOfNextYear() {
        return new TemporalAdjusters$$ExternalSyntheticLambda5();
    }

    static /* synthetic */ Temporal lambda$firstDayOfNextYear$6(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_YEAR, 1L).plus(1L, ChronoUnit.YEARS);
    }

    public static TemporalAdjuster firstInMonth(DayOfWeek dayOfWeek) {
        return dayOfWeekInMonth(1, dayOfWeek);
    }

    public static TemporalAdjuster lastInMonth(DayOfWeek dayOfWeek) {
        return dayOfWeekInMonth(-1, dayOfWeek);
    }

    public static TemporalAdjuster dayOfWeekInMonth(int i, DayOfWeek dayOfWeek) {
        TemporalAdjusters$$ExternalSyntheticBackport0.m(dayOfWeek, "dayOfWeek");
        int value = dayOfWeek.getValue();
        if (i >= 0) {
            return new TemporalAdjusters$$ExternalSyntheticLambda1(value, i);
        }
        return new TemporalAdjusters$$ExternalSyntheticLambda2(value, i);
    }

    static /* synthetic */ Temporal lambda$dayOfWeekInMonth$7(int i, int i2, Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_MONTH, 1L).plus((int) ((((i - r7.get(ChronoField.DAY_OF_WEEK)) + 7) % 7) + ((i2 - 1) * 7)), ChronoUnit.DAYS);
    }

    static /* synthetic */ Temporal lambda$dayOfWeekInMonth$8(int i, int i2, Temporal temporal) {
        Temporal with = temporal.with(ChronoField.DAY_OF_MONTH, temporal.range(ChronoField.DAY_OF_MONTH).getMaximum());
        int i3 = i - with.get(ChronoField.DAY_OF_WEEK);
        if (i3 == 0) {
            i3 = 0;
        } else if (i3 > 0) {
            i3 -= 7;
        }
        return with.plus((int) (i3 - (((-i2) - 1) * 7)), ChronoUnit.DAYS);
    }

    public static TemporalAdjuster next(DayOfWeek dayOfWeek) {
        return new TemporalAdjusters$$ExternalSyntheticLambda12(dayOfWeek.getValue());
    }

    static /* synthetic */ Temporal lambda$next$9(int i, Temporal temporal) {
        return temporal.plus(temporal.get(ChronoField.DAY_OF_WEEK) - i >= 0 ? 7 - r0 : -r0, ChronoUnit.DAYS);
    }

    public static TemporalAdjuster nextOrSame(DayOfWeek dayOfWeek) {
        return new TemporalAdjusters$$ExternalSyntheticLambda7(dayOfWeek.getValue());
    }

    static /* synthetic */ Temporal lambda$nextOrSame$10(int i, Temporal temporal) {
        int i2 = temporal.get(ChronoField.DAY_OF_WEEK);
        if (i2 == i) {
            return temporal;
        }
        return temporal.plus(i2 - i >= 0 ? 7 - r0 : -r0, ChronoUnit.DAYS);
    }

    public static TemporalAdjuster previous(DayOfWeek dayOfWeek) {
        return new TemporalAdjusters$$ExternalSyntheticLambda3(dayOfWeek.getValue());
    }

    static /* synthetic */ Temporal lambda$previous$11(int i, Temporal temporal) {
        return temporal.minus(i - temporal.get(ChronoField.DAY_OF_WEEK) >= 0 ? 7 - r2 : -r2, ChronoUnit.DAYS);
    }

    public static TemporalAdjuster previousOrSame(DayOfWeek dayOfWeek) {
        return new TemporalAdjusters$$ExternalSyntheticLambda9(dayOfWeek.getValue());
    }

    static /* synthetic */ Temporal lambda$previousOrSame$12(int i, Temporal temporal) {
        int i2 = temporal.get(ChronoField.DAY_OF_WEEK);
        if (i2 == i) {
            return temporal;
        }
        return temporal.minus(i - i2 >= 0 ? 7 - r2 : -r2, ChronoUnit.DAYS);
    }
}
