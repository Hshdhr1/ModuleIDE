package java.time.temporal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.Chronology;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class TemporalQueries {
    static final TemporalQuery ZONE_ID = new 1();
    static final TemporalQuery CHRONO = new 2();
    static final TemporalQuery PRECISION = new 3();
    static final TemporalQuery OFFSET = new 4();
    static final TemporalQuery ZONE = new 5();
    static final TemporalQuery LOCAL_DATE = new 6();
    static final TemporalQuery LOCAL_TIME = new 7();

    private TemporalQueries() {
    }

    public static TemporalQuery zoneId() {
        return ZONE_ID;
    }

    public static TemporalQuery chronology() {
        return CHRONO;
    }

    public static TemporalQuery precision() {
        return PRECISION;
    }

    public static TemporalQuery zone() {
        return ZONE;
    }

    public static TemporalQuery offset() {
        return OFFSET;
    }

    public static TemporalQuery localDate() {
        return LOCAL_DATE;
    }

    public static TemporalQuery localTime() {
        return LOCAL_TIME;
    }

    class 1 implements TemporalQuery {
        1() {
        }

        public ZoneId queryFrom(TemporalAccessor temporalAccessor) {
            return (ZoneId) temporalAccessor.query(TemporalQueries.ZONE_ID);
        }

        public String toString() {
            return "ZoneId";
        }
    }

    class 2 implements TemporalQuery {
        2() {
        }

        public Chronology queryFrom(TemporalAccessor temporalAccessor) {
            return (Chronology) temporalAccessor.query(TemporalQueries.CHRONO);
        }

        public String toString() {
            return "Chronology";
        }
    }

    class 3 implements TemporalQuery {
        3() {
        }

        public TemporalUnit queryFrom(TemporalAccessor temporalAccessor) {
            return (TemporalUnit) temporalAccessor.query(TemporalQueries.PRECISION);
        }

        public String toString() {
            return "Precision";
        }
    }

    class 4 implements TemporalQuery {
        4() {
        }

        public ZoneOffset queryFrom(TemporalAccessor temporalAccessor) {
            if (temporalAccessor.isSupported(ChronoField.OFFSET_SECONDS)) {
                return ZoneOffset.ofTotalSeconds(temporalAccessor.get(ChronoField.OFFSET_SECONDS));
            }
            return null;
        }

        public String toString() {
            return "ZoneOffset";
        }
    }

    class 5 implements TemporalQuery {
        5() {
        }

        public ZoneId queryFrom(TemporalAccessor temporalAccessor) {
            ZoneId zoneId = (ZoneId) temporalAccessor.query(TemporalQueries.ZONE_ID);
            return zoneId != null ? zoneId : (ZoneId) temporalAccessor.query(TemporalQueries.OFFSET);
        }

        public String toString() {
            return "Zone";
        }
    }

    class 6 implements TemporalQuery {
        6() {
        }

        public LocalDate queryFrom(TemporalAccessor temporalAccessor) {
            if (temporalAccessor.isSupported(ChronoField.EPOCH_DAY)) {
                return LocalDate.ofEpochDay(temporalAccessor.getLong(ChronoField.EPOCH_DAY));
            }
            return null;
        }

        public String toString() {
            return "LocalDate";
        }
    }

    class 7 implements TemporalQuery {
        7() {
        }

        public LocalTime queryFrom(TemporalAccessor temporalAccessor) {
            if (temporalAccessor.isSupported(ChronoField.NANO_OF_DAY)) {
                return LocalTime.ofNanoOfDay(temporalAccessor.getLong(ChronoField.NANO_OF_DAY));
            }
            return null;
        }

        public String toString() {
            return "LocalTime";
        }
    }
}
