package kotlin.time;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Duration.kt */
@SinceKotlin(version = "1.6")
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0006\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0087@\u0018\u0000 \u0089\u00012\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002\u0089\u0001B\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\f\u001a\u00020\rH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0011\u0010\u000fJ\u0010\u0010\u0016\u001a\u00020\u0000H\u0086\u0002¢\u0006\u0004\b\u0017\u0010\u0005J\u0018\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u0000H\u0086\u0002¢\u0006\u0004\b\u001a\u0010\u001bJ\u001f\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u001f\u0010 J\u0018\u0010!\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u0000H\u0086\u0002¢\u0006\u0004\b\"\u0010\u001bJ\u0018\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\tH\u0086\u0002¢\u0006\u0004\b%\u0010&J\u0018\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020'H\u0086\u0002¢\u0006\u0004\b%\u0010(J\u0018\u0010)\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\tH\u0086\u0002¢\u0006\u0004\b*\u0010&J\u0018\u0010)\u001a\u00020\u00002\u0006\u0010$\u001a\u00020'H\u0086\u0002¢\u0006\u0004\b*\u0010(J\u0018\u0010)\u001a\u00020'2\u0006\u0010\u0019\u001a\u00020\u0000H\u0086\u0002¢\u0006\u0004\b+\u0010,J\u0017\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020\u0013H\u0000¢\u0006\u0004\b/\u00100J\r\u00101\u001a\u00020\r¢\u0006\u0004\b2\u0010\u000fJ\r\u00103\u001a\u00020\r¢\u0006\u0004\b4\u0010\u000fJ\r\u00105\u001a\u00020\r¢\u0006\u0004\b6\u0010\u000fJ\r\u00107\u001a\u00020\r¢\u0006\u0004\b8\u0010\u000fJ\u0018\u0010;\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u0000H\u0096\u0002¢\u0006\u0004\b<\u0010=J\u009d\u0001\u0010>\u001a\u0002H?\"\u0004\b\u0000\u0010?2u\u0010@\u001aq\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(D\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(E\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(F\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(G\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(H\u0012\u0004\u0012\u0002H?0AH\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bI\u0010JJ\u0088\u0001\u0010>\u001a\u0002H?\"\u0004\b\u0000\u0010?2`\u0010@\u001a\\\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(E\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(F\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(G\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(H\u0012\u0004\u0012\u0002H?0KH\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bI\u0010LJs\u0010>\u001a\u0002H?\"\u0004\b\u0000\u0010?2K\u0010@\u001aG\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(F\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(G\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(H\u0012\u0004\u0012\u0002H?0MH\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bI\u0010NJ^\u0010>\u001a\u0002H?\"\u0004\b\u0000\u0010?26\u0010@\u001a2\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(G\u0012\u0013\u0012\u00110\t¢\u0006\f\bB\u0012\b\bC\u0012\u0004\b\b(H\u0012\u0004\u0012\u0002H?0OH\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bI\u0010PJ\u0015\u0010^\u001a\u00020'2\u0006\u0010.\u001a\u00020\u0013¢\u0006\u0004\b_\u0010`J\u0015\u0010a\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u0013¢\u0006\u0004\bb\u00100J\u0015\u0010c\u001a\u00020\t2\u0006\u0010.\u001a\u00020\u0013¢\u0006\u0004\bd\u0010eJ\u000f\u0010t\u001a\u00020uH\u0016¢\u0006\u0004\bv\u0010wJA\u0010x\u001a\u00020y*\u00060zj\u0002`{2\u0006\u0010|\u001a\u00020\t2\u0006\u0010}\u001a\u00020\t2\u0006\u0010~\u001a\u00020\t2\u0006\u0010.\u001a\u00020u2\u0006\u0010\u007f\u001a\u00020\rH\u0002¢\u0006\u0006\b\u0080\u0001\u0010\u0081\u0001J!\u0010t\u001a\u00020u2\u0006\u0010.\u001a\u00020\u00132\t\b\u0002\u0010\u0082\u0001\u001a\u00020\t¢\u0006\u0005\bv\u0010\u0083\u0001J\u000f\u0010\u0084\u0001\u001a\u00020u¢\u0006\u0005\b\u0085\u0001\u0010wJ\u0015\u0010\u0086\u0001\u001a\u00020\r2\t\u0010\u0019\u001a\u0005\u0018\u00010\u0087\u0001HÖ\u0003J\n\u0010\u0088\u0001\u001a\u00020\tHÖ\u0001R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0015\u0010\b\u001a\u00020\t8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0011\u00109\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b:\u0010\u0005R\u001a\u0010Q\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\bR\u0010S\u001a\u0004\bT\u0010\u000bR\u001a\u0010U\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\bV\u0010S\u001a\u0004\bW\u0010\u000bR\u001a\u0010X\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\bY\u0010S\u001a\u0004\bZ\u0010\u000bR\u001a\u0010[\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\\\u0010S\u001a\u0004\b]\u0010\u000bR\u0011\u0010f\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bg\u0010\u0005R\u0011\u0010h\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bi\u0010\u0005R\u0011\u0010j\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bk\u0010\u0005R\u0011\u0010l\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bm\u0010\u0005R\u0011\u0010n\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bo\u0010\u0005R\u0011\u0010p\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bq\u0010\u0005R\u0011\u0010r\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\bs\u0010\u0005\u0088\u0001\u0002\u0092\u0001\u00020\u0003\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u008a\u0001"}, d2 = {"Lkotlin/time/Duration;", "", "rawValue", "", "constructor-impl", "(J)J", "value", "getValue-impl", "unitDiscriminator", "", "getUnitDiscriminator-impl", "(J)I", "isInNanos", "", "isInNanos-impl", "(J)Z", "isInMillis", "isInMillis-impl", "storageUnit", "Lkotlin/time/DurationUnit;", "getStorageUnit-impl", "(J)Lkotlin/time/DurationUnit;", "unaryMinus", "unaryMinus-UwyO8pc", "plus", "other", "plus-LRDsOJo", "(JJ)J", "addValuesMixedRanges", "thisMillis", "otherNanos", "addValuesMixedRanges-UwyO8pc", "(JJJ)J", "minus", "minus-LRDsOJo", "times", "scale", "times-UwyO8pc", "(JI)J", "", "(JD)J", "div", "div-UwyO8pc", "div-LRDsOJo", "(JJ)D", "truncateTo", "unit", "truncateTo-UwyO8pc$kotlin_stdlib", "(JLkotlin/time/DurationUnit;)J", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "isInfinite", "isInfinite-impl", "isFinite", "isFinite-impl", "absoluteValue", "getAbsoluteValue-UwyO8pc", "compareTo", "compareTo-LRDsOJo", "(JJ)I", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(JLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(JLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(JLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "hoursComponent", "getHoursComponent$annotations", "()V", "getHoursComponent-impl", "minutesComponent", "getMinutesComponent$annotations", "getMinutesComponent-impl", "secondsComponent", "getSecondsComponent$annotations", "getSecondsComponent-impl", "nanosecondsComponent", "getNanosecondsComponent$annotations", "getNanosecondsComponent-impl", "toDouble", "toDouble-impl", "(JLkotlin/time/DurationUnit;)D", "toLong", "toLong-impl", "toInt", "toInt-impl", "(JLkotlin/time/DurationUnit;)I", "inWholeDays", "getInWholeDays-impl", "inWholeHours", "getInWholeHours-impl", "inWholeMinutes", "getInWholeMinutes-impl", "inWholeSeconds", "getInWholeSeconds-impl", "inWholeMilliseconds", "getInWholeMilliseconds-impl", "inWholeMicroseconds", "getInWholeMicroseconds-impl", "inWholeNanoseconds", "getInWholeNanoseconds-impl", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "appendFractional", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "whole", "fractional", "fractionalSize", "isoZeroes", "appendFractional-impl", "(JLjava/lang/StringBuilder;IIILjava/lang/String;Z)V", "decimals", "(JLkotlin/time/DurationUnit;I)Ljava/lang/String;", "toIsoString", "toIsoString-impl", "equals", "", "hashCode", "Companion", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
@JvmInline
@WasExperimental(markerClass = {ExperimentalTime.class})
@SourceDebugExtension({"SMAP\nDuration.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Duration.kt\nkotlin/time/Duration\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1068:1\n38#1:1069\n38#1:1070\n38#1:1071\n38#1:1072\n38#1:1073\n501#1:1074\n518#1:1082\n170#2,6:1075\n1#3:1081\n*S KotlinDebug\n*F\n+ 1 Duration.kt\nkotlin/time/Duration\n*L\n39#1:1069\n40#1:1070\n275#1:1071\n295#1:1072\n479#1:1073\n728#1:1074\n819#1:1082\n770#1:1075,6\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class Duration implements Comparable {
    private final long rawValue;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static final long ZERO = constructor-impl(0);
    private static final long INFINITE = DurationKt.access$durationOfMillis(4611686018427387903L);
    private static final long NEG_INFINITE = DurationKt.access$durationOfMillis(-4611686018427387903L);

    public static final /* synthetic */ Duration box-impl(long j) {
        return new Duration(j);
    }

    public static boolean equals-impl(long j, Object obj) {
        return (obj instanceof Duration) && j == ((Duration) obj).getRawValue();
    }

    public static final boolean equals-impl0(long j, long j2) {
        return j == j2;
    }

    @PublishedApi
    public static /* synthetic */ void getHoursComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getMinutesComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getNanosecondsComponent$annotations() {
    }

    @PublishedApi
    public static /* synthetic */ void getSecondsComponent$annotations() {
    }

    private static final int getUnitDiscriminator-impl(long j) {
        return ((int) j) & 1;
    }

    private static final long getValue-impl(long j) {
        return j >> 1;
    }

    public static int hashCode-impl(long j) {
        return Duration$$ExternalSyntheticBackport0.m(j);
    }

    private static final boolean isInMillis-impl(long j) {
        return (((int) j) & 1) == 1;
    }

    private static final boolean isInNanos-impl(long j) {
        return (((int) j) & 1) == 0;
    }

    public static final boolean isNegative-impl(long j) {
        return j < 0;
    }

    public static final boolean isPositive-impl(long j) {
        return j > 0;
    }

    public boolean equals(Object other) {
        return equals-impl(this.rawValue, other);
    }

    public int hashCode() {
        return hashCode-impl(this.rawValue);
    }

    /* renamed from: unbox-impl, reason: from getter */
    public final /* synthetic */ long getRawValue() {
        return this.rawValue;
    }

    public static final /* synthetic */ long access$getINFINITE$cp() {
        return INFINITE;
    }

    public static final /* synthetic */ long access$getNEG_INFINITE$cp() {
        return NEG_INFINITE;
    }

    public static final /* synthetic */ long access$getZERO$cp() {
        return ZERO;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo-LRDsOJo(((Duration) obj).getRawValue());
    }

    private /* synthetic */ Duration(long j) {
        this.rawValue = j;
    }

    private static final DurationUnit getStorageUnit-impl(long j) {
        return isInNanos-impl(j) ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
    }

    public static long constructor-impl(long j) {
        if (!DurationJvmKt.getDurationAssertionsEnabled()) {
            return j;
        }
        if (isInNanos-impl(j)) {
            long j2 = getValue-impl(j);
            if (-4611686018426999999L <= j2 && j2 < 4611686018427000000L) {
                return j;
            }
            throw new AssertionError(getValue-impl(j) + " ns is out of nanoseconds range");
        }
        long j3 = getValue-impl(j);
        if (-4611686018427387903L > j3 || j3 >= 4611686018427387904L) {
            throw new AssertionError(getValue-impl(j) + " ms is out of milliseconds range");
        }
        long j4 = getValue-impl(j);
        if (-4611686018426L > j4 || j4 >= 4611686018427L) {
            return j;
        }
        throw new AssertionError(getValue-impl(j) + " ms is denormalized");
    }

    /* compiled from: Duration.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0017\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0007J\u0015\u00100\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u000201¢\u0006\u0004\b2\u00103J\u0015\u00104\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u000201¢\u0006\u0004\b5\u00103J\u0015\u00106\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u000201¢\u0006\u0002\b7J\u0015\u00108\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000f\u001a\u000201¢\u0006\u0002\b9R\u0013\u0010\u0004\u001a\u00020\u0005¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\t\u001a\u00020\u0005¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\n\u0010\u0007R\u0016\u0010\u000b\u001a\u00020\u0005X\u0080\u0004¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\f\u0010\u0007R\u001f\u0010\u0013\u001a\u00020\u0005*\u00020\u00148Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001f\u0010\u0013\u001a\u00020\u0005*\u00020\u00198Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0015\u0010\u001a\u001a\u0004\b\u0017\u0010\u001bR\u001f\u0010\u0013\u001a\u00020\u0005*\u00020\u000e8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0015\u0010\u001c\u001a\u0004\b\u0017\u0010\u001dR\u001f\u0010\u001e\u001a\u00020\u0005*\u00020\u00148Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010\u0016\u001a\u0004\b \u0010\u0018R\u001f\u0010\u001e\u001a\u00020\u0005*\u00020\u00198Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010\u001a\u001a\u0004\b \u0010\u001bR\u001f\u0010\u001e\u001a\u00020\u0005*\u00020\u000e8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010\u001c\u001a\u0004\b \u0010\u001dR\u001f\u0010!\u001a\u00020\u0005*\u00020\u00148Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\"\u0010\u0016\u001a\u0004\b#\u0010\u0018R\u001f\u0010!\u001a\u00020\u0005*\u00020\u00198Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\"\u0010\u001a\u001a\u0004\b#\u0010\u001bR\u001f\u0010!\u001a\u00020\u0005*\u00020\u000e8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\"\u0010\u001c\u001a\u0004\b#\u0010\u001dR\u001f\u0010$\u001a\u00020\u0005*\u00020\u00148Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b%\u0010\u0016\u001a\u0004\b&\u0010\u0018R\u001f\u0010$\u001a\u00020\u0005*\u00020\u00198Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b%\u0010\u001a\u001a\u0004\b&\u0010\u001bR\u001f\u0010$\u001a\u00020\u0005*\u00020\u000e8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b%\u0010\u001c\u001a\u0004\b&\u0010\u001dR\u001f\u0010'\u001a\u00020\u0005*\u00020\u00148Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b(\u0010\u0016\u001a\u0004\b)\u0010\u0018R\u001f\u0010'\u001a\u00020\u0005*\u00020\u00198Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b(\u0010\u001a\u001a\u0004\b)\u0010\u001bR\u001f\u0010'\u001a\u00020\u0005*\u00020\u000e8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b(\u0010\u001c\u001a\u0004\b)\u0010\u001dR\u001f\u0010*\u001a\u00020\u0005*\u00020\u00148Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b+\u0010\u0016\u001a\u0004\b,\u0010\u0018R\u001f\u0010*\u001a\u00020\u0005*\u00020\u00198Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b+\u0010\u001a\u001a\u0004\b,\u0010\u001bR\u001f\u0010*\u001a\u00020\u0005*\u00020\u000e8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b+\u0010\u001c\u001a\u0004\b,\u0010\u001dR\u001f\u0010-\u001a\u00020\u0005*\u00020\u00148Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b.\u0010\u0016\u001a\u0004\b/\u0010\u0018R\u001f\u0010-\u001a\u00020\u0005*\u00020\u00198Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b.\u0010\u001a\u001a\u0004\b/\u0010\u001bR\u001f\u0010-\u001a\u00020\u0005*\u00020\u000e8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b.\u0010\u001c\u001a\u0004\b/\u0010\u001d¨\u0006:"}, d2 = {"Lkotlin/time/Duration$Companion;", "", "<init>", "()V", "ZERO", "Lkotlin/time/Duration;", "getZERO-UwyO8pc", "()J", "J", "INFINITE", "getINFINITE-UwyO8pc", "NEG_INFINITE", "getNEG_INFINITE-UwyO8pc$kotlin_stdlib", "convert", "", "value", "sourceUnit", "Lkotlin/time/DurationUnit;", "targetUnit", "nanoseconds", "", "getNanoseconds-UwyO8pc$annotations", "(I)V", "getNanoseconds-UwyO8pc", "(I)J", "", "(J)V", "(J)J", "(D)V", "(D)J", "microseconds", "getMicroseconds-UwyO8pc$annotations", "getMicroseconds-UwyO8pc", "milliseconds", "getMilliseconds-UwyO8pc$annotations", "getMilliseconds-UwyO8pc", "seconds", "getSeconds-UwyO8pc$annotations", "getSeconds-UwyO8pc", "minutes", "getMinutes-UwyO8pc$annotations", "getMinutes-UwyO8pc", "hours", "getHours-UwyO8pc$annotations", "getHours-UwyO8pc", "days", "getDays-UwyO8pc$annotations", "getDays-UwyO8pc", "parse", "", "parse-UwyO8pc", "(Ljava/lang/String;)J", "parseIsoString", "parseIsoString-UwyO8pc", "parseOrNull", "parseOrNull-FghU774", "parseIsoStringOrNull", "parseIsoStringOrNull-FghU774", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @InlineOnly
        public static /* synthetic */ void getDays-UwyO8pc$annotations(double d) {
        }

        @InlineOnly
        public static /* synthetic */ void getDays-UwyO8pc$annotations(int i) {
        }

        @InlineOnly
        public static /* synthetic */ void getDays-UwyO8pc$annotations(long j) {
        }

        @InlineOnly
        public static /* synthetic */ void getHours-UwyO8pc$annotations(double d) {
        }

        @InlineOnly
        public static /* synthetic */ void getHours-UwyO8pc$annotations(int i) {
        }

        @InlineOnly
        public static /* synthetic */ void getHours-UwyO8pc$annotations(long j) {
        }

        @InlineOnly
        public static /* synthetic */ void getMicroseconds-UwyO8pc$annotations(double d) {
        }

        @InlineOnly
        public static /* synthetic */ void getMicroseconds-UwyO8pc$annotations(int i) {
        }

        @InlineOnly
        public static /* synthetic */ void getMicroseconds-UwyO8pc$annotations(long j) {
        }

        @InlineOnly
        public static /* synthetic */ void getMilliseconds-UwyO8pc$annotations(double d) {
        }

        @InlineOnly
        public static /* synthetic */ void getMilliseconds-UwyO8pc$annotations(int i) {
        }

        @InlineOnly
        public static /* synthetic */ void getMilliseconds-UwyO8pc$annotations(long j) {
        }

        @InlineOnly
        public static /* synthetic */ void getMinutes-UwyO8pc$annotations(double d) {
        }

        @InlineOnly
        public static /* synthetic */ void getMinutes-UwyO8pc$annotations(int i) {
        }

        @InlineOnly
        public static /* synthetic */ void getMinutes-UwyO8pc$annotations(long j) {
        }

        @InlineOnly
        public static /* synthetic */ void getNanoseconds-UwyO8pc$annotations(double d) {
        }

        @InlineOnly
        public static /* synthetic */ void getNanoseconds-UwyO8pc$annotations(int i) {
        }

        @InlineOnly
        public static /* synthetic */ void getNanoseconds-UwyO8pc$annotations(long j) {
        }

        @InlineOnly
        public static /* synthetic */ void getSeconds-UwyO8pc$annotations(double d) {
        }

        @InlineOnly
        public static /* synthetic */ void getSeconds-UwyO8pc$annotations(int i) {
        }

        @InlineOnly
        public static /* synthetic */ void getSeconds-UwyO8pc$annotations(long j) {
        }

        private Companion() {
        }

        public final long getZERO-UwyO8pc() {
            return Duration.access$getZERO$cp();
        }

        public final long getINFINITE-UwyO8pc() {
            return Duration.access$getINFINITE$cp();
        }

        public final long getNEG_INFINITE-UwyO8pc$kotlin_stdlib() {
            return Duration.access$getNEG_INFINITE$cp();
        }

        @ExperimentalTime
        public final double convert(double value, @NotNull DurationUnit sourceUnit, @NotNull DurationUnit targetUnit) {
            Intrinsics.checkNotNullParameter(sourceUnit, "sourceUnit");
            Intrinsics.checkNotNullParameter(targetUnit, "targetUnit");
            return DurationUnitKt.convertDurationUnit(value, sourceUnit, targetUnit);
        }

        private final long getNanoseconds-UwyO8pc(int i) {
            return DurationKt.toDuration(i, DurationUnit.NANOSECONDS);
        }

        private final long getNanoseconds-UwyO8pc(long j) {
            return DurationKt.toDuration(j, DurationUnit.NANOSECONDS);
        }

        private final long getNanoseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.NANOSECONDS);
        }

        private final long getMicroseconds-UwyO8pc(int i) {
            return DurationKt.toDuration(i, DurationUnit.MICROSECONDS);
        }

        private final long getMicroseconds-UwyO8pc(long j) {
            return DurationKt.toDuration(j, DurationUnit.MICROSECONDS);
        }

        private final long getMicroseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MICROSECONDS);
        }

        private final long getMilliseconds-UwyO8pc(int i) {
            return DurationKt.toDuration(i, DurationUnit.MILLISECONDS);
        }

        private final long getMilliseconds-UwyO8pc(long j) {
            return DurationKt.toDuration(j, DurationUnit.MILLISECONDS);
        }

        private final long getMilliseconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MILLISECONDS);
        }

        private final long getSeconds-UwyO8pc(int i) {
            return DurationKt.toDuration(i, DurationUnit.SECONDS);
        }

        private final long getSeconds-UwyO8pc(long j) {
            return DurationKt.toDuration(j, DurationUnit.SECONDS);
        }

        private final long getSeconds-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.SECONDS);
        }

        private final long getMinutes-UwyO8pc(int i) {
            return DurationKt.toDuration(i, DurationUnit.MINUTES);
        }

        private final long getMinutes-UwyO8pc(long j) {
            return DurationKt.toDuration(j, DurationUnit.MINUTES);
        }

        private final long getMinutes-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.MINUTES);
        }

        private final long getHours-UwyO8pc(int i) {
            return DurationKt.toDuration(i, DurationUnit.HOURS);
        }

        private final long getHours-UwyO8pc(long j) {
            return DurationKt.toDuration(j, DurationUnit.HOURS);
        }

        private final long getHours-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.HOURS);
        }

        private final long getDays-UwyO8pc(int i) {
            return DurationKt.toDuration(i, DurationUnit.DAYS);
        }

        private final long getDays-UwyO8pc(long j) {
            return DurationKt.toDuration(j, DurationUnit.DAYS);
        }

        private final long getDays-UwyO8pc(double d) {
            return DurationKt.toDuration(d, DurationUnit.DAYS);
        }

        public final long parse-UwyO8pc(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                return DurationKt.access$parseDuration(value, false);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid duration string format: '" + value + "'.", e);
            }
        }

        public final long parseIsoString-UwyO8pc(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                return DurationKt.access$parseDuration(value, true);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid ISO duration string format: '" + value + "'.", e);
            }
        }

        @Nullable
        public final Duration parseOrNull-FghU774(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                return Duration.box-impl(DurationKt.access$parseDuration(value, false));
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        @Nullable
        public final Duration parseIsoStringOrNull-FghU774(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                return Duration.box-impl(DurationKt.access$parseDuration(value, true));
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
    }

    public static final long unaryMinus-UwyO8pc(long j) {
        return DurationKt.access$durationOf(-getValue-impl(j), ((int) j) & 1);
    }

    public static final long plus-LRDsOJo(long j, long j2) {
        if (isInfinite-impl(j)) {
            if (isFinite-impl(j2) || (j2 ^ j) >= 0) {
                return j;
            }
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
        }
        if (isInfinite-impl(j2)) {
            return j2;
        }
        if ((((int) j) & 1) == (((int) j2) & 1)) {
            long j3 = getValue-impl(j) + getValue-impl(j2);
            if (isInNanos-impl(j)) {
                return DurationKt.access$durationOfNanosNormalized(j3);
            }
            return DurationKt.access$durationOfMillisNormalized(j3);
        }
        if (isInMillis-impl(j)) {
            return addValuesMixedRanges-UwyO8pc(j, getValue-impl(j), getValue-impl(j2));
        }
        return addValuesMixedRanges-UwyO8pc(j, getValue-impl(j2), getValue-impl(j));
    }

    private static final long addValuesMixedRanges-UwyO8pc(long j, long j2, long j3) {
        long access$nanosToMillis = DurationKt.access$nanosToMillis(j3);
        long j4 = j2 + access$nanosToMillis;
        if (-4611686018426L <= j4 && j4 < 4611686018427L) {
            return DurationKt.access$durationOfNanos(DurationKt.access$millisToNanos(j4) + (j3 - DurationKt.access$millisToNanos(access$nanosToMillis)));
        }
        return DurationKt.access$durationOfMillis(RangesKt.coerceIn(j4, -4611686018427387903L, 4611686018427387903L));
    }

    public static final long minus-LRDsOJo(long j, long j2) {
        return plus-LRDsOJo(j, unaryMinus-UwyO8pc(j2));
    }

    public static final long times-UwyO8pc(long j, int i) {
        if (isInfinite-impl(j)) {
            if (i != 0) {
                return i > 0 ? j : unaryMinus-UwyO8pc(j);
            }
            throw new IllegalArgumentException("Multiplying infinite duration by zero yields an undefined result.");
        }
        if (i == 0) {
            return ZERO;
        }
        long j2 = getValue-impl(j);
        long j3 = i;
        long j4 = j2 * j3;
        if (!isInNanos-impl(j)) {
            if (j4 / j3 == j2) {
                return DurationKt.access$durationOfMillis(RangesKt.coerceIn(j4, new LongRange(-4611686018427387903L, 4611686018427387903L)));
            }
            return MathKt.getSign(j2) * MathKt.getSign(i) > 0 ? INFINITE : NEG_INFINITE;
        }
        if (-2147483647L <= j2 && j2 < 2147483648L) {
            return DurationKt.access$durationOfNanos(j4);
        }
        if (j4 / j3 == j2) {
            return DurationKt.access$durationOfNanosNormalized(j4);
        }
        long access$nanosToMillis = DurationKt.access$nanosToMillis(j2);
        long j5 = access$nanosToMillis * j3;
        long access$nanosToMillis2 = DurationKt.access$nanosToMillis((j2 - DurationKt.access$millisToNanos(access$nanosToMillis)) * j3) + j5;
        if (j5 / j3 != access$nanosToMillis || (access$nanosToMillis2 ^ j5) < 0) {
            return MathKt.getSign(j2) * MathKt.getSign(i) > 0 ? INFINITE : NEG_INFINITE;
        }
        return DurationKt.access$durationOfMillis(RangesKt.coerceIn(access$nanosToMillis2, new LongRange(-4611686018427387903L, 4611686018427387903L)));
    }

    public static final long times-UwyO8pc(long j, double d) {
        int roundToInt = MathKt.roundToInt(d);
        if (roundToInt == d) {
            return times-UwyO8pc(j, roundToInt);
        }
        DurationUnit durationUnit = getStorageUnit-impl(j);
        return DurationKt.toDuration(toDouble-impl(j, durationUnit) * d, durationUnit);
    }

    public static final long div-UwyO8pc(long j, int i) {
        if (i == 0) {
            if (isPositive-impl(j)) {
                return INFINITE;
            }
            if (isNegative-impl(j)) {
                return NEG_INFINITE;
            }
            throw new IllegalArgumentException("Dividing zero duration by zero yields an undefined result.");
        }
        if (isInNanos-impl(j)) {
            return DurationKt.access$durationOfNanos(getValue-impl(j) / i);
        }
        if (isInfinite-impl(j)) {
            return times-UwyO8pc(j, MathKt.getSign(i));
        }
        long j2 = i;
        long j3 = getValue-impl(j) / j2;
        if (-4611686018426L <= j3 && j3 < 4611686018427L) {
            return DurationKt.access$durationOfNanos(DurationKt.access$millisToNanos(j3) + (DurationKt.access$millisToNanos(getValue-impl(j) - (j3 * j2)) / j2));
        }
        return DurationKt.access$durationOfMillis(j3);
    }

    public static final long div-UwyO8pc(long j, double d) {
        int roundToInt = MathKt.roundToInt(d);
        if (roundToInt == d && roundToInt != 0) {
            return div-UwyO8pc(j, roundToInt);
        }
        DurationUnit durationUnit = getStorageUnit-impl(j);
        return DurationKt.toDuration(toDouble-impl(j, durationUnit) / d, durationUnit);
    }

    public static final double div-LRDsOJo(long j, long j2) {
        DurationUnit maxOf = ComparisonsKt.maxOf(getStorageUnit-impl(j), getStorageUnit-impl(j2));
        return toDouble-impl(j, maxOf) / toDouble-impl(j2, maxOf);
    }

    public static final long truncateTo-UwyO8pc$kotlin_stdlib(long j, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        DurationUnit durationUnit = getStorageUnit-impl(j);
        if (unit.compareTo(durationUnit) <= 0 || isInfinite-impl(j)) {
            return j;
        }
        return DurationKt.toDuration(getValue-impl(j) - (getValue-impl(j) % DurationUnitKt.convertDurationUnit(1L, unit, durationUnit)), durationUnit);
    }

    public static final boolean isInfinite-impl(long j) {
        return j == INFINITE || j == NEG_INFINITE;
    }

    public static final boolean isFinite-impl(long j) {
        return !isInfinite-impl(j);
    }

    public static final long getAbsoluteValue-UwyO8pc(long j) {
        return isNegative-impl(j) ? unaryMinus-UwyO8pc(j) : j;
    }

    public int compareTo-LRDsOJo(long j) {
        return compareTo-LRDsOJo(this.rawValue, j);
    }

    public static int compareTo-LRDsOJo(long j, long j2) {
        long j3 = j ^ j2;
        if (j3 < 0 || (((int) j3) & 1) == 0) {
            return Intrinsics.compare(j, j2);
        }
        int i = (((int) j) & 1) - (((int) j2) & 1);
        return isNegative-impl(j) ? -i : i;
    }

    public static final Object toComponents-impl(long j, @NotNull Function5 action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(getInWholeDays-impl(j)), Integer.valueOf(getHoursComponent-impl(j)), Integer.valueOf(getMinutesComponent-impl(j)), Integer.valueOf(getSecondsComponent-impl(j)), Integer.valueOf(getNanosecondsComponent-impl(j)));
    }

    public static final Object toComponents-impl(long j, @NotNull Function4 action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(getInWholeHours-impl(j)), Integer.valueOf(getMinutesComponent-impl(j)), Integer.valueOf(getSecondsComponent-impl(j)), Integer.valueOf(getNanosecondsComponent-impl(j)));
    }

    public static final Object toComponents-impl(long j, @NotNull Function3 action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(getInWholeMinutes-impl(j)), Integer.valueOf(getSecondsComponent-impl(j)), Integer.valueOf(getNanosecondsComponent-impl(j)));
    }

    public static final Object toComponents-impl(long j, @NotNull Function2 action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(getInWholeSeconds-impl(j)), Integer.valueOf(getNanosecondsComponent-impl(j)));
    }

    public static final int getHoursComponent-impl(long j) {
        if (isInfinite-impl(j)) {
            return 0;
        }
        return (int) (getInWholeHours-impl(j) % 24);
    }

    public static final int getMinutesComponent-impl(long j) {
        if (isInfinite-impl(j)) {
            return 0;
        }
        return (int) (getInWholeMinutes-impl(j) % 60);
    }

    public static final int getSecondsComponent-impl(long j) {
        if (isInfinite-impl(j)) {
            return 0;
        }
        return (int) (getInWholeSeconds-impl(j) % 60);
    }

    public static final int getNanosecondsComponent-impl(long j) {
        if (isInfinite-impl(j)) {
            return 0;
        }
        return (int) (isInMillis-impl(j) ? DurationKt.access$millisToNanos(getValue-impl(j) % 1000) : getValue-impl(j) % 1000000000);
    }

    public static final double toDouble-impl(long j, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (j == INFINITE) {
            return Double.POSITIVE_INFINITY;
        }
        if (j == NEG_INFINITE) {
            return Double.NEGATIVE_INFINITY;
        }
        return DurationUnitKt.convertDurationUnit(getValue-impl(j), getStorageUnit-impl(j), unit);
    }

    public static final long toLong-impl(long j, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (j == INFINITE) {
            return Long.MAX_VALUE;
        }
        if (j == NEG_INFINITE) {
            return Long.MIN_VALUE;
        }
        return DurationUnitKt.convertDurationUnit(getValue-impl(j), getStorageUnit-impl(j), unit);
    }

    public static final int toInt-impl(long j, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        return (int) RangesKt.coerceIn(toLong-impl(j, unit), -2147483648L, 2147483647L);
    }

    public static final long getInWholeDays-impl(long j) {
        return toLong-impl(j, DurationUnit.DAYS);
    }

    public static final long getInWholeHours-impl(long j) {
        return toLong-impl(j, DurationUnit.HOURS);
    }

    public static final long getInWholeMinutes-impl(long j) {
        return toLong-impl(j, DurationUnit.MINUTES);
    }

    public static final long getInWholeSeconds-impl(long j) {
        return toLong-impl(j, DurationUnit.SECONDS);
    }

    public static final long getInWholeMilliseconds-impl(long j) {
        return (isInMillis-impl(j) && isFinite-impl(j)) ? getValue-impl(j) : toLong-impl(j, DurationUnit.MILLISECONDS);
    }

    public static final long getInWholeMicroseconds-impl(long j) {
        return toLong-impl(j, DurationUnit.MICROSECONDS);
    }

    public static final long getInWholeNanoseconds-impl(long j) {
        long j2 = getValue-impl(j);
        if (isInNanos-impl(j)) {
            return j2;
        }
        if (j2 > 9223372036854L) {
            return Long.MAX_VALUE;
        }
        if (j2 < -9223372036854L) {
            return Long.MIN_VALUE;
        }
        return DurationKt.access$millisToNanos(j2);
    }

    @NotNull
    public String toString() {
        return toString-impl(this.rawValue);
    }

    @NotNull
    public static String toString-impl(long j) {
        if (j == 0) {
            return "0s";
        }
        if (j == INFINITE) {
            return "Infinity";
        }
        if (j == NEG_INFINITE) {
            return "-Infinity";
        }
        boolean z = isNegative-impl(j);
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append('-');
        }
        long j2 = getAbsoluteValue-UwyO8pc(j);
        long j3 = getInWholeDays-impl(j2);
        int i = getHoursComponent-impl(j2);
        int i2 = getMinutesComponent-impl(j2);
        int i3 = getSecondsComponent-impl(j2);
        int i4 = getNanosecondsComponent-impl(j2);
        int i5 = 0;
        boolean z2 = j3 != 0;
        boolean z3 = i != 0;
        boolean z4 = i2 != 0;
        boolean z5 = (i3 == 0 && i4 == 0) ? false : true;
        if (z2) {
            sb.append(j3);
            sb.append('d');
            i5 = 1;
        }
        if (z3 || (z2 && (z4 || z5))) {
            int i6 = i5 + 1;
            if (i5 > 0) {
                sb.append(' ');
            }
            sb.append(i);
            sb.append('h');
            i5 = i6;
        }
        if (z4 || (z5 && (z3 || z2))) {
            int i7 = i5 + 1;
            if (i5 > 0) {
                sb.append(' ');
            }
            sb.append(i2);
            sb.append('m');
            i5 = i7;
        }
        if (z5) {
            int i8 = i5 + 1;
            if (i5 > 0) {
                sb.append(' ');
            }
            if (i3 != 0 || z2 || z3 || z4) {
                appendFractional-impl(j, sb, i3, i4, 9, "s", false);
            } else if (i4 >= 1000000) {
                appendFractional-impl(j, sb, i4 / 1000000, i4 % 1000000, 6, "ms", false);
            } else if (i4 >= 1000) {
                appendFractional-impl(j, sb, i4 / 1000, i4 % 1000, 3, "us", false);
            } else {
                sb.append(i4);
                sb.append("ns");
            }
            i5 = i8;
        }
        if (z && i5 > 1) {
            sb.insert(1, '(').append(')');
        }
        return sb.toString();
    }

    private static final void appendFractional-impl(long j, StringBuilder sb, int i, int i2, int i3, String str, boolean z) {
        sb.append(i);
        if (i2 != 0) {
            sb.append('.');
            CharSequence padStart = StringsKt.padStart(String.valueOf(i2), i3, '0');
            int i4 = -1;
            int length = padStart.length() - 1;
            if (length >= 0) {
                while (true) {
                    int i5 = length - 1;
                    if (padStart.charAt(length) != '0') {
                        i4 = length;
                        break;
                    } else if (i5 < 0) {
                        break;
                    } else {
                        length = i5;
                    }
                }
            }
            int i6 = i4 + 1;
            if (!z && i6 < 3) {
                sb.append(padStart, 0, i6);
                Intrinsics.checkNotNullExpressionValue(sb, "append(...)");
            } else {
                sb.append(padStart, 0, ((i4 + 3) / 3) * 3);
                Intrinsics.checkNotNullExpressionValue(sb, "append(...)");
            }
        }
        sb.append(str);
    }

    public static /* synthetic */ String toString-impl$default(long j, DurationUnit durationUnit, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return toString-impl(j, durationUnit, i);
    }

    @NotNull
    public static final String toString-impl(long j, @NotNull DurationUnit unit, int i) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (i < 0) {
            throw new IllegalArgumentException(("decimals must be not negative, but was " + i).toString());
        }
        double d = toDouble-impl(j, unit);
        if (Double.isInfinite(d)) {
            return String.valueOf(d);
        }
        return DurationJvmKt.formatToExactDecimals(d, RangesKt.coerceAtMost(i, 12)) + DurationUnitKt.shortName(unit);
    }

    @NotNull
    public static final String toIsoString-impl(long j) {
        StringBuilder sb = new StringBuilder();
        if (isNegative-impl(j)) {
            sb.append('-');
        }
        sb.append("PT");
        long j2 = getAbsoluteValue-UwyO8pc(j);
        long j3 = getInWholeHours-impl(j2);
        int i = getMinutesComponent-impl(j2);
        int i2 = getSecondsComponent-impl(j2);
        int i3 = getNanosecondsComponent-impl(j2);
        long j4 = isInfinite-impl(j) ? 9999999999999L : j3;
        boolean z = true;
        boolean z2 = j4 != 0;
        boolean z3 = (i2 == 0 && i3 == 0) ? false : true;
        if (i == 0 && (!z3 || !z2)) {
            z = false;
        }
        if (z2) {
            sb.append(j4);
            sb.append('H');
        }
        if (z) {
            sb.append(i);
            sb.append('M');
        }
        if (z3 || (!z2 && !z)) {
            appendFractional-impl(j, sb, i2, i3, 9, "S", true);
        }
        return sb.toString();
    }
}
