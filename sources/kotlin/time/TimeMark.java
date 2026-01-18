package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import org.jetbrains.annotations.NotNull;

/* compiled from: TimeSource.kt */
@SinceKotlin(version = "1.9")
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000f\u0010\u0002\u001a\u00020\u0003H&¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0003H\u0096\u0002¢\u0006\u0004\b\b\u0010\tJ\u0018\u0010\n\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0003H\u0096\u0002¢\u0006\u0004\b\u000b\u0010\tJ\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016¨\u0006\u000f"}, d2 = {"Lkotlin/time/TimeMark;", "", "elapsedNow", "Lkotlin/time/Duration;", "elapsedNow-UwyO8pc", "()J", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/TimeMark;", "minus", "minus-LRDsOJo", "hasPassedNow", "", "hasNotPassedNow", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
@WasExperimental(markerClass = {ExperimentalTime.class})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public interface TimeMark {
    long elapsedNow-UwyO8pc();

    boolean hasNotPassedNow();

    boolean hasPassedNow();

    @NotNull
    TimeMark minus-LRDsOJo(long duration);

    @NotNull
    TimeMark plus-LRDsOJo(long duration);

    /* compiled from: TimeSource.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    public static final class DefaultImpls {
        @NotNull
        public static TimeMark plus-LRDsOJo(@NotNull TimeMark timeMark, long j) {
            return new AdjustedTimeMark(timeMark, j, null);
        }

        @NotNull
        public static TimeMark minus-LRDsOJo(@NotNull TimeMark timeMark, long j) {
            return timeMark.plus-LRDsOJo(Duration.unaryMinus-UwyO8pc(j));
        }

        public static boolean hasPassedNow(@NotNull TimeMark timeMark) {
            return !Duration.isNegative-impl(timeMark.elapsedNow-UwyO8pc());
        }

        public static boolean hasNotPassedNow(@NotNull TimeMark timeMark) {
            return Duration.isNegative-impl(timeMark.elapsedNow-UwyO8pc());
        }
    }
}
