package java.util.concurrent;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarTimeUnit {
    private static final long DAY_SCALE = 86400000000000L;
    private static final long HOUR_SCALE = 3600000000000L;
    private static final long MICRO_SCALE = 1000;
    private static final long MILLI_SCALE = 1000000;
    private static final long MINUTE_SCALE = 60000000000L;
    private static final long NANO_SCALE = 1;
    private static final long SECOND_SCALE = 1000000000;

    private DesugarTimeUnit() {
    }

    public static ChronoUnit toChronoUnit(TimeUnit timeUnit) {
        switch (1.$SwitchMap$java$util$concurrent$TimeUnit[timeUnit.ordinal()]) {
            case 1:
                return ChronoUnit.NANOS;
            case 2:
                return ChronoUnit.MICROS;
            case 3:
                return ChronoUnit.MILLIS;
            case 4:
                return ChronoUnit.SECONDS;
            case 5:
                return ChronoUnit.MINUTES;
            case 6:
                return ChronoUnit.HOURS;
            case 7:
                return ChronoUnit.DAYS;
            default:
                throw new AssertionError();
        }
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoUnit;
        static final /* synthetic */ int[] $SwitchMap$java$util$concurrent$TimeUnit;

        static {
            int[] iArr = new int[ChronoUnit.values().length];
            $SwitchMap$java$time$temporal$ChronoUnit = iArr;
            try {
                iArr[ChronoUnit.NANOS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MICROS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MILLIS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[TimeUnit.values().length];
            $SwitchMap$java$util$concurrent$TimeUnit = iArr2;
            try {
                iArr2[TimeUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public static TimeUnit of(ChronoUnit chronoUnit) {
        switch (1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit) DesugarTimeUnit$$ExternalSyntheticBackport0.m(chronoUnit, "chronoUnit")).ordinal()]) {
            case 1:
                return TimeUnit.NANOSECONDS;
            case 2:
                return TimeUnit.MICROSECONDS;
            case 3:
                return TimeUnit.MILLISECONDS;
            case 4:
                return TimeUnit.SECONDS;
            case 5:
                return TimeUnit.MINUTES;
            case 6:
                return TimeUnit.HOURS;
            case 7:
                return TimeUnit.DAYS;
            default:
                throw new IllegalArgumentException("No TimeUnit equivalent for " + chronoUnit);
        }
    }

    public static long convert(TimeUnit timeUnit, Duration duration) {
        return timeUnit.convert(duration.toNanos(), TimeUnit.NANOSECONDS);
    }
}
