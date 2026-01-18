package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: Duration.kt */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0015\u001a\u0019\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007¢\u0006\u0002\u0010\u0005\u001a\u0019\u0010\u0000\u001a\u00020\u0001*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0004H\u0007¢\u0006\u0002\u0010\u0007\u001a\u0019\u0010\u0000\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007¢\u0006\u0002\u0010\t\u001a\u001c\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\n¢\u0006\u0004\b\f\u0010\r\u001a\u001c\u0010\n\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\n¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u001d\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002¢\u0006\u0002\u0010\u0015\u001a\u0010\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a)\u0010\u0017\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00022\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00140\u001aH\u0082\b\u001a)\u0010\u001c\u001a\u00020\u0002*\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00022\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00140\u001aH\u0082\b\u001a\u0010\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006H\u0002\u001a\u0010\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0006H\u0002\u001a\u0015\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010'\u001a\u0015\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010'\u001a\u001d\u0010*\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u0002H\u0002¢\u0006\u0002\u0010-\u001a\u0015\u0010.\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010'\u001a\u0015\u0010/\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010'\"\u000e\u0010\u001d\u001a\u00020\u0002X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001f\u001a\u00020\u0006X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010 \u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"toDuration", "Lkotlin/time/Duration;", "", "unit", "Lkotlin/time/DurationUnit;", "(ILkotlin/time/DurationUnit;)J", "", "(JLkotlin/time/DurationUnit;)J", "", "(DLkotlin/time/DurationUnit;)J", "times", "duration", "times-mvk6XK0", "(IJ)J", "times-kIfJnKk", "(DJ)J", "parseDuration", "value", "", "strictIso", "", "(Ljava/lang/String;Z)J", "parseOverLongIsoComponent", "substringWhile", "startIndex", "predicate", "Lkotlin/Function1;", "", "skipWhile", "NANOS_IN_MILLIS", "MAX_NANOS", "MAX_MILLIS", "MAX_NANOS_IN_MILLIS", "nanosToMillis", "nanos", "millisToNanos", "millis", "durationOfNanos", "normalNanos", "(J)J", "durationOfMillis", "normalMillis", "durationOf", "normalValue", "unitDiscriminator", "(JI)J", "durationOfNanosNormalized", "durationOfMillisNormalized", "kotlin-stdlib"}, k = 2, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDuration.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Duration.kt\nkotlin/time/DurationKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1068:1\n1021#1,6:1070\n1024#1,3:1076\n1021#1,6:1079\n1021#1,6:1085\n1024#1,3:1091\n1#2:1069\n*S KotlinDebug\n*F\n+ 1 Duration.kt\nkotlin/time/DurationKt\n*L\n936#1:1070,6\n970#1:1076,3\n973#1:1079,6\n976#1:1085,6\n1021#1:1091,3\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class DurationKt {
    public static final long MAX_MILLIS = 4611686018427387903L;
    public static final long MAX_NANOS = 4611686018426999999L;
    private static final long MAX_NANOS_IN_MILLIS = 4611686018426L;
    public static final int NANOS_IN_MILLIS = 1000000;

    public static final /* synthetic */ long access$durationOf(long j, int i) {
        return durationOf(j, i);
    }

    public static final /* synthetic */ long access$durationOfMillis(long j) {
        return durationOfMillis(j);
    }

    public static final /* synthetic */ long access$durationOfMillisNormalized(long j) {
        return durationOfMillisNormalized(j);
    }

    public static final /* synthetic */ long access$durationOfNanos(long j) {
        return durationOfNanos(j);
    }

    public static final /* synthetic */ long access$durationOfNanosNormalized(long j) {
        return durationOfNanosNormalized(j);
    }

    public static final /* synthetic */ long access$millisToNanos(long j) {
        return millisToNanos(j);
    }

    public static final /* synthetic */ long access$nanosToMillis(long j) {
        return nanosToMillis(j);
    }

    public static final /* synthetic */ long access$parseDuration(String str, boolean z) {
        return parseDuration(str, z);
    }

    private static final long millisToNanos(long j) {
        return j * 1000000;
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    public static final long toDuration(int i, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (unit.compareTo(DurationUnit.SECONDS) <= 0) {
            return durationOfNanos(DurationUnitKt.convertDurationUnitOverflow(i, unit, DurationUnit.NANOSECONDS));
        }
        return toDuration(i, unit);
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    public static final long toDuration(long j, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        long convertDurationUnitOverflow = DurationUnitKt.convertDurationUnitOverflow(4611686018426999999L, DurationUnit.NANOSECONDS, unit);
        if ((-convertDurationUnitOverflow) <= j && j <= convertDurationUnitOverflow) {
            return durationOfNanos(DurationUnitKt.convertDurationUnitOverflow(j, unit, DurationUnit.NANOSECONDS));
        }
        return durationOfMillis(RangesKt.coerceIn(DurationUnitKt.convertDurationUnit(j, unit, DurationUnit.MILLISECONDS), -4611686018427387903L, 4611686018427387903L));
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    public static final long toDuration(double d, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        double convertDurationUnit = DurationUnitKt.convertDurationUnit(d, unit, DurationUnit.NANOSECONDS);
        if (Double.isNaN(convertDurationUnit)) {
            throw new IllegalArgumentException("Duration value cannot be NaN.".toString());
        }
        long roundToLong = MathKt.roundToLong(convertDurationUnit);
        if (-4611686018426999999L <= roundToLong && roundToLong < 4611686018427000000L) {
            return durationOfNanos(roundToLong);
        }
        return durationOfMillisNormalized(MathKt.roundToLong(DurationUnitKt.convertDurationUnit(d, unit, DurationUnit.MILLISECONDS)));
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    @InlineOnly
    private static final long times-mvk6XK0(int i, long j) {
        return Duration.times-UwyO8pc(j, i);
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    @InlineOnly
    private static final long times-kIfJnKk(double d, long j) {
        return Duration.times-UwyO8pc(j, d);
    }

    private static final long parseDuration(String str, boolean z) {
        int i;
        char charAt;
        char charAt2;
        int i2;
        int length = str.length();
        if (length == 0) {
            throw new IllegalArgumentException("The string is empty");
        }
        long j = Duration.INSTANCE.getZERO-UwyO8pc();
        char charAt3 = str.charAt(0);
        int i3 = (charAt3 == '+' || charAt3 == '-') ? 1 : 0;
        boolean z2 = i3 > 0;
        DurationUnit durationUnit = null;
        boolean z3 = z2 && StringsKt.startsWith$default((CharSequence) str, '-', false, 2, (Object) null);
        if (length <= i3) {
            throw new IllegalArgumentException("No components");
        }
        char c = ':';
        char c2 = '0';
        if (str.charAt(i3) == 'P') {
            int i4 = i3 + 1;
            if (i4 == length) {
                throw new IllegalArgumentException();
            }
            DurationUnit durationUnit2 = null;
            boolean z4 = false;
            while (i4 < length) {
                if (str.charAt(i4) != 'T') {
                    int i5 = i4;
                    while (true) {
                        if (i5 >= str.length()) {
                            i2 = length;
                            break;
                        }
                        char charAt4 = str.charAt(i5);
                        if (c2 > charAt4 || charAt4 >= c) {
                            i2 = length;
                            if (!StringsKt.contains$default("+-.", charAt4, false, 2, (Object) null)) {
                                break;
                            }
                        } else {
                            i2 = length;
                        }
                        i5++;
                        length = i2;
                        c = ':';
                        c2 = '0';
                    }
                    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
                    CharSequence substring = str.substring(i4, i5);
                    Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                    CharSequence charSequence = substring;
                    if (charSequence.length() == 0) {
                        throw new IllegalArgumentException();
                    }
                    int length2 = i4 + substring.length();
                    CharSequence charSequence2 = (CharSequence) str;
                    if (length2 < 0 || length2 >= charSequence2.length()) {
                        throw new IllegalArgumentException("Missing unit for value " + substring);
                    }
                    char charAt5 = charSequence2.charAt(length2);
                    i4 = length2 + 1;
                    DurationUnit durationUnitByIsoChar = DurationUnitKt.durationUnitByIsoChar(charAt5, z4);
                    if (durationUnit2 != null && durationUnit2.compareTo(durationUnitByIsoChar) <= 0) {
                        throw new IllegalArgumentException("Unexpected order of duration components");
                    }
                    int indexOf$default = StringsKt.indexOf$default(charSequence, '.', 0, false, 6, (Object) null);
                    if (durationUnitByIsoChar == DurationUnit.SECONDS && indexOf$default > 0) {
                        Intrinsics.checkNotNull(substring, "null cannot be cast to non-null type java.lang.String");
                        String substring2 = substring.substring(0, indexOf$default);
                        Intrinsics.checkNotNullExpressionValue(substring2, "substring(...)");
                        long j2 = Duration.plus-LRDsOJo(j, toDuration(parseOverLongIsoComponent(substring2), durationUnitByIsoChar));
                        Intrinsics.checkNotNull(substring, "null cannot be cast to non-null type java.lang.String");
                        String substring3 = substring.substring(indexOf$default);
                        Intrinsics.checkNotNullExpressionValue(substring3, "substring(...)");
                        j = Duration.plus-LRDsOJo(j2, toDuration(Double.parseDouble(substring3), durationUnitByIsoChar));
                    } else {
                        j = Duration.plus-LRDsOJo(j, toDuration(parseOverLongIsoComponent(substring), durationUnitByIsoChar));
                    }
                    durationUnit2 = durationUnitByIsoChar;
                    length = i2;
                    c = ':';
                    c2 = '0';
                } else {
                    if (z4 || (i4 = i4 + 1) == length) {
                        throw new IllegalArgumentException();
                    }
                    z4 = true;
                }
            }
        } else {
            if (z) {
                throw new IllegalArgumentException();
            }
            int max = Math.max(length - i3, 8);
            char c3 = ':';
            if (StringsKt.regionMatches(str, i3, "Infinity", 0, max, true)) {
                j = Duration.INSTANCE.getINFINITE-UwyO8pc();
            } else {
                boolean z5 = !z2;
                if (z2 && str.charAt(i3) == '(' && StringsKt.last((CharSequence) str) == ')') {
                    i3++;
                    int i6 = length - 1;
                    if (i3 == i6) {
                        throw new IllegalArgumentException("No components");
                    }
                    i = i6;
                    z5 = true;
                } else {
                    i = length;
                }
                boolean z6 = false;
                while (i3 < i) {
                    if (z6 && z5) {
                        while (i3 < str.length() && str.charAt(i3) == ' ') {
                            i3++;
                        }
                    }
                    int i7 = i3;
                    while (i7 < str.length() && (('0' <= (charAt2 = str.charAt(i7)) && charAt2 < c3) || charAt2 == '.')) {
                        i7++;
                    }
                    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
                    CharSequence substring4 = str.substring(i3, i7);
                    Intrinsics.checkNotNullExpressionValue(substring4, "substring(...)");
                    CharSequence charSequence3 = substring4;
                    if (charSequence3.length() == 0) {
                        throw new IllegalArgumentException();
                    }
                    int length3 = i3 + substring4.length();
                    int i8 = length3;
                    while (i8 < str.length() && 'a' <= (charAt = str.charAt(i8)) && charAt < '{') {
                        i8++;
                    }
                    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
                    String substring5 = str.substring(length3, i8);
                    Intrinsics.checkNotNullExpressionValue(substring5, "substring(...)");
                    i3 = length3 + substring5.length();
                    DurationUnit durationUnitByShortName = DurationUnitKt.durationUnitByShortName(substring5);
                    if (durationUnit != null && durationUnit.compareTo(durationUnitByShortName) <= 0) {
                        throw new IllegalArgumentException("Unexpected order of duration components");
                    }
                    int indexOf$default2 = StringsKt.indexOf$default(charSequence3, '.', 0, false, 6, (Object) null);
                    if (indexOf$default2 > 0) {
                        Intrinsics.checkNotNull(substring4, "null cannot be cast to non-null type java.lang.String");
                        String substring6 = substring4.substring(0, indexOf$default2);
                        Intrinsics.checkNotNullExpressionValue(substring6, "substring(...)");
                        long j3 = Duration.plus-LRDsOJo(j, toDuration(Long.parseLong(substring6), durationUnitByShortName));
                        Intrinsics.checkNotNull(substring4, "null cannot be cast to non-null type java.lang.String");
                        String substring7 = substring4.substring(indexOf$default2);
                        Intrinsics.checkNotNullExpressionValue(substring7, "substring(...)");
                        j = Duration.plus-LRDsOJo(j3, toDuration(Double.parseDouble(substring7), durationUnitByShortName));
                        if (i3 < i) {
                            throw new IllegalArgumentException("Fractional component must be last");
                        }
                    } else {
                        j = Duration.plus-LRDsOJo(j, toDuration(Long.parseLong(substring4), durationUnitByShortName));
                    }
                    durationUnit = durationUnitByShortName;
                    z6 = true;
                    c3 = ':';
                }
            }
        }
        return z3 ? Duration.unaryMinus-UwyO8pc(j) : j;
    }

    private static final long parseOverLongIsoComponent(String str) {
        char charAt;
        int length = str.length();
        int i = (length <= 0 || !StringsKt.contains$default("+-", str.charAt(0), false, 2, (Object) null)) ? 0 : 1;
        if (length - i > 16) {
            int i2 = i;
            while (true) {
                if (i < length) {
                    char charAt2 = str.charAt(i);
                    if (charAt2 != '0') {
                        if ('1' > charAt2 || charAt2 >= ':') {
                            break;
                        }
                    } else if (i2 == i) {
                        i2++;
                    }
                    i++;
                } else if (length - i2 > 16) {
                    return str.charAt(0) == '-' ? Long.MIN_VALUE : Long.MAX_VALUE;
                }
            }
        }
        return (!StringsKt.startsWith$default(str, "+", false, 2, (Object) null) || length <= 1 || '0' > (charAt = str.charAt(1)) || charAt >= ':') ? Long.parseLong(str) : Long.parseLong(StringsKt.drop(str, 1));
    }

    private static final int skipWhile(String str, int i, Function1 function1) {
        while (i < str.length() && ((Boolean) function1.invoke(Character.valueOf(str.charAt(i)))).booleanValue()) {
            i++;
        }
        return i;
    }

    private static final long nanosToMillis(long j) {
        return j / 1000000;
    }

    private static final long durationOfNanos(long j) {
        return Duration.constructor-impl(j << 1);
    }

    private static final long durationOfMillis(long j) {
        return Duration.constructor-impl((j << 1) + 1);
    }

    private static final long durationOf(long j, int i) {
        return Duration.constructor-impl((j << 1) + i);
    }

    private static final long durationOfNanosNormalized(long j) {
        if (-4611686018426999999L <= j && j < 4611686018427000000L) {
            return durationOfNanos(j);
        }
        return durationOfMillis(nanosToMillis(j));
    }

    private static final long durationOfMillisNormalized(long j) {
        if (-4611686018426L <= j && j < 4611686018427L) {
            return durationOfNanos(millisToNanos(j));
        }
        return durationOfMillis(RangesKt.coerceIn(j, -4611686018427387903L, 4611686018427387903L));
    }

    private static final String substringWhile(String str, int i, Function1 function1) {
        int i2 = i;
        while (i2 < str.length() && ((Boolean) function1.invoke(Character.valueOf(str.charAt(i2)))).booleanValue()) {
            i2++;
        }
        Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
        String substring = str.substring(i, i2);
        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
        return substring;
    }
}
