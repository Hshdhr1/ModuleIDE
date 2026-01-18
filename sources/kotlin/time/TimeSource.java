package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.ComparableTimeMark;
import org.jetbrains.annotations.NotNull;

/* compiled from: TimeSource.kt */
@SinceKotlin(version = "1.9")
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u0000 \u00062\u00020\u0001:\u0003\u0004\u0005\u0006J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0007"}, d2 = {"Lkotlin/time/TimeSource;", "", "markNow", "Lkotlin/time/TimeMark;", "WithComparableMarks", "Monotonic", "Companion", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
@WasExperimental(markerClass = {ExperimentalTime.class})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public interface TimeSource {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* compiled from: TimeSource.kt */
    @SinceKotlin(version = "1.9")
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lkotlin/time/TimeSource$WithComparableMarks;", "Lkotlin/time/TimeSource;", "markNow", "Lkotlin/time/ComparableTimeMark;", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    @WasExperimental(markerClass = {ExperimentalTime.class})
    public interface WithComparableMarks extends TimeSource {
        @NotNull
        ComparableTimeMark markNow();
    }

    @NotNull
    TimeMark markNow();

    /* compiled from: TimeSource.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\nB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0004\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\u000b"}, d2 = {"Lkotlin/time/TimeSource$Monotonic;", "Lkotlin/time/TimeSource$WithComparableMarks;", "<init>", "()V", "markNow", "Lkotlin/time/TimeSource$Monotonic$ValueTimeMark;", "markNow-z9LOYto", "()J", "toString", "", "ValueTimeMark", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Monotonic implements WithComparableMarks {

        @NotNull
        public static final Monotonic INSTANCE = new Monotonic();

        private Monotonic() {
        }

        public /* bridge */ /* synthetic */ ComparableTimeMark markNow() {
            return ValueTimeMark.box-impl(markNow-z9LOYto());
        }

        public /* bridge */ /* synthetic */ TimeMark markNow() {
            return ValueTimeMark.box-impl(markNow-z9LOYto());
        }

        public long markNow-z9LOYto() {
            return MonotonicTimeSource.INSTANCE.markNow-z9LOYto();
        }

        @NotNull
        public String toString() {
            return MonotonicTimeSource.INSTANCE.toString();
        }

        /* compiled from: TimeSource.kt */
        @SinceKotlin(version = "1.9")
        @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0087@\u0018\u00002\u00020\u0001B\u0015\b\u0000\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0004\b\n\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\tH\u0096\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u0018\u0010\u000f\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\tH\u0096\u0002¢\u0006\u0004\b\u0010\u0010\u000eJ\u000f\u0010\u0011\u001a\u00020\u0012H\u0016¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\u0012H\u0016¢\u0006\u0004\b\u0016\u0010\u0014J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0001H\u0096\u0002¢\u0006\u0004\b\u0018\u0010\u0019J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0000H\u0086\u0002¢\u0006\u0004\b\u001a\u0010\u000eJ\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0017\u001a\u00020\u0000H\u0086\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u0013\u0010\u001f\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010 HÖ\u0003J\t\u0010!\u001a\u00020\u001cHÖ\u0001J\t\u0010\"\u001a\u00020#HÖ\u0001R\u0014\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X\u0080\u0004¢\u0006\u0004\n\u0002\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00060\u0003j\u0002`\u0004¨\u0006$"}, d2 = {"Lkotlin/time/TimeSource$Monotonic$ValueTimeMark;", "Lkotlin/time/ComparableTimeMark;", "reading", "", "Lkotlin/time/ValueTimeMarkReading;", "constructor-impl", "(J)J", "J", "elapsedNow", "Lkotlin/time/Duration;", "elapsedNow-UwyO8pc", "plus", "duration", "plus-LRDsOJo", "(JJ)J", "minus", "minus-LRDsOJo", "hasPassedNow", "", "hasPassedNow-impl", "(J)Z", "hasNotPassedNow", "hasNotPassedNow-impl", "other", "minus-UwyO8pc", "(JLkotlin/time/ComparableTimeMark;)J", "minus-6eNON_k", "compareTo", "", "compareTo-6eNON_k", "(JJ)I", "equals", "", "hashCode", "toString", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
        @JvmInline
        @WasExperimental(markerClass = {ExperimentalTime.class})
        public static final class ValueTimeMark implements ComparableTimeMark {
            private final long reading;

            public static final /* synthetic */ ValueTimeMark box-impl(long j) {
                return new ValueTimeMark(j);
            }

            public static long constructor-impl(long j) {
                return j;
            }

            public static boolean equals-impl(long j, Object obj) {
                return (obj instanceof ValueTimeMark) && j == ((ValueTimeMark) obj).getReading();
            }

            public static final boolean equals-impl0(long j, long j2) {
                return j == j2;
            }

            public static int hashCode-impl(long j) {
                return TimeSource$Monotonic$ValueTimeMark$$ExternalSyntheticBackport0.m(j);
            }

            public static String toString-impl(long j) {
                return "ValueTimeMark(reading=" + j + ')';
            }

            public boolean equals(Object other) {
                return equals-impl(this.reading, other);
            }

            public int hashCode() {
                return hashCode-impl(this.reading);
            }

            public String toString() {
                return toString-impl(this.reading);
            }

            /* renamed from: unbox-impl, reason: from getter */
            public final /* synthetic */ long getReading() {
                return this.reading;
            }

            public static int compareTo-impl(long j, @NotNull ComparableTimeMark other) {
                Intrinsics.checkNotNullParameter(other, "other");
                return box-impl(j).compareTo(other);
            }

            public int compareTo(@NotNull ComparableTimeMark comparableTimeMark) {
                return ComparableTimeMark.DefaultImpls.compareTo(this, comparableTimeMark);
            }

            public /* bridge */ /* synthetic */ ComparableTimeMark minus-LRDsOJo(long j) {
                return box-impl(minus-LRDsOJo(j));
            }

            public /* bridge */ /* synthetic */ TimeMark minus-LRDsOJo(long j) {
                return box-impl(minus-LRDsOJo(j));
            }

            public /* bridge */ /* synthetic */ ComparableTimeMark plus-LRDsOJo(long j) {
                return box-impl(plus-LRDsOJo(j));
            }

            public /* bridge */ /* synthetic */ TimeMark plus-LRDsOJo(long j) {
                return box-impl(plus-LRDsOJo(j));
            }

            private /* synthetic */ ValueTimeMark(long j) {
                this.reading = j;
            }

            public static long elapsedNow-UwyO8pc(long j) {
                return MonotonicTimeSource.INSTANCE.elapsedFrom-6eNON_k(j);
            }

            public long elapsedNow-UwyO8pc() {
                return elapsedNow-UwyO8pc(this.reading);
            }

            public static long plus-LRDsOJo(long j, long j2) {
                return MonotonicTimeSource.INSTANCE.adjustReading-6QKq23U(j, j2);
            }

            public long plus-LRDsOJo(long j) {
                return plus-LRDsOJo(this.reading, j);
            }

            public static long minus-LRDsOJo(long j, long j2) {
                return MonotonicTimeSource.INSTANCE.adjustReading-6QKq23U(j, Duration.unaryMinus-UwyO8pc(j2));
            }

            public long minus-LRDsOJo(long j) {
                return minus-LRDsOJo(this.reading, j);
            }

            public static boolean hasPassedNow-impl(long j) {
                return !Duration.isNegative-impl(elapsedNow-UwyO8pc(j));
            }

            public boolean hasPassedNow() {
                return hasPassedNow-impl(this.reading);
            }

            public static boolean hasNotPassedNow-impl(long j) {
                return Duration.isNegative-impl(elapsedNow-UwyO8pc(j));
            }

            public boolean hasNotPassedNow() {
                return hasNotPassedNow-impl(this.reading);
            }

            public long minus-UwyO8pc(@NotNull ComparableTimeMark other) {
                Intrinsics.checkNotNullParameter(other, "other");
                return minus-UwyO8pc(this.reading, other);
            }

            public static long minus-UwyO8pc(long j, @NotNull ComparableTimeMark other) {
                Intrinsics.checkNotNullParameter(other, "other");
                if (!(other instanceof ValueTimeMark)) {
                    throw new IllegalArgumentException("Subtracting or comparing time marks from different time sources is not possible: " + toString-impl(j) + " and " + other);
                }
                return minus-6eNON_k(j, ((ValueTimeMark) other).getReading());
            }

            public static final long minus-6eNON_k(long j, long j2) {
                return MonotonicTimeSource.INSTANCE.differenceBetween-fRLX17w(j, j2);
            }

            public static final int compareTo-6eNON_k(long j, long j2) {
                return Duration.compareTo-LRDsOJo(minus-6eNON_k(j, j2), Duration.INSTANCE.getZERO-UwyO8pc());
            }
        }
    }

    /* compiled from: TimeSource.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lkotlin/time/TimeSource$Companion;", "", "<init>", "()V", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }
}
