package kotlin.time;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

/* compiled from: Instant.kt */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\f\n\u0002\u0010\u0015\n\u0002\b\u0006\u001a\u0010\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000fH\u0003\u001a\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0003\u001a'\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\t2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0082\b\u001a'\u0010\u001c\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\t2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0082\b\u001a\u0010\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u0014H\u0000\u001a\u0014\u0010&\u001a\u00020\u0014*\u00020\u00142\u0006\u0010$\u001a\u00020\u0001H\u0002\u001a\u0014\u0010,\u001a\u00020\u0011*\u00020\u000f2\u0006\u0010-\u001a\u00020\u0014H\u0002\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0000\u0010\u0005\"\u001f\u0010\u0006\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\u0004\u001a\u0004\b\u0006\u0010\u0005\"\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001d\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001f\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010 \u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010!\u001a\u00020\u0014X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\"\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010#\u001a\u00020\u0014X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010'\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010)\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010*\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010+\u001a\u00020(X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"isDistantPast", "", "Lkotlin/time/Instant;", "isDistantPast$annotations", "(Lkotlin/time/Instant;)V", "(Lkotlin/time/Instant;)Z", "isDistantFuture", "isDistantFuture$annotations", "DISTANT_PAST_SECONDS", "", "DISTANT_FUTURE_SECONDS", "MIN_SECOND", "MAX_SECOND", "parseIso", "isoString", "", "formatIso", "", "instant", "DAYS_PER_CYCLE", "", "DAYS_0000_TO_1970", "safeAddOrElse", "a", "b", "action", "Lkotlin/Function0;", "", "safeMultiplyOrElse", "SECONDS_PER_HOUR", "SECONDS_PER_MINUTE", "HOURS_PER_DAY", "SECONDS_PER_DAY", "NANOS_PER_SECOND", "NANOS_PER_MILLI", "MILLIS_PER_SECOND", "isLeapYear", "year", "monthLength", "POWERS_OF_TEN", "", "asciiDigitPositionsInIsoStringAfterYear", "colonsInIsoOffsetString", "asciiDigitsInIsoOffsetString", "truncateForErrorMessage", "maxLength", "kotlin-stdlib"}, k = 2, mv = {2, 1, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nInstant.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Instant.kt\nkotlin/time/InstantKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,799:1\n1#2:800\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class InstantKt {
    private static final int DAYS_0000_TO_1970 = 719528;
    private static final int DAYS_PER_CYCLE = 146097;
    private static final long DISTANT_FUTURE_SECONDS = 3093527980800L;
    private static final long DISTANT_PAST_SECONDS = -3217862419201L;
    private static final int HOURS_PER_DAY = 24;
    private static final long MAX_SECOND = 31556889864403199L;
    private static final int MILLIS_PER_SECOND = 1000;
    private static final long MIN_SECOND = -31557014167219200L;
    private static final int NANOS_PER_MILLI = 1000000;
    public static final int NANOS_PER_SECOND = 1000000000;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;

    @NotNull
    private static final int[] POWERS_OF_TEN = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};

    @NotNull
    private static final int[] asciiDigitPositionsInIsoStringAfterYear = {1, 2, 4, 5, 7, 8, 10, 11, 13, 14};

    @NotNull
    private static final int[] colonsInIsoOffsetString = {3, 6};

    @NotNull
    private static final int[] asciiDigitsInIsoOffsetString = {1, 2, 4, 5, 7, 8};

    public static /* synthetic */ boolean $r8$lambda$I4Ho3pgFaMHPUu94-CPnZwqLJ1M(char c) {
        return parseIso$lambda$3(c);
    }

    public static /* synthetic */ boolean $r8$lambda$IkUn2NROTCX2lBvTn4A-V5RmZMI(char c) {
        return parseIso$lambda$6(c);
    }

    public static /* synthetic */ boolean $r8$lambda$UGD8nIP5JmIHBHSiFgQEHXQXYXA(char c) {
        return parseIso$lambda$5(c);
    }

    public static /* synthetic */ boolean $r8$lambda$VQuIyPyOaYjo_HSMWZ5md5B5WS4(char c) {
        return parseIso$lambda$4(c);
    }

    public static /* synthetic */ boolean $r8$lambda$_13rfjrKJh1j1Bat0IdAkLKuo8Q(char c) {
        return parseIso$lambda$2(c);
    }

    public static /* synthetic */ boolean $r8$lambda$leSQdQHWqQTxUchB19nQd5l-GeM(char c) {
        return parseIso$lambda$1(c);
    }

    public static final /* synthetic */ String access$formatIso(Instant instant) {
        return formatIso(instant);
    }

    public static final /* synthetic */ Instant access$parseIso(CharSequence charSequence) {
        return parseIso(charSequence);
    }

    @SinceKotlin(version = "2.1")
    @InlineOnly
    @ExperimentalTime
    public static /* synthetic */ void isDistantFuture$annotations(Instant instant) {
    }

    @SinceKotlin(version = "2.1")
    @InlineOnly
    @ExperimentalTime
    public static /* synthetic */ void isDistantPast$annotations(Instant instant) {
    }

    private static final int monthLength(int i, boolean z) {
        return i != 2 ? (i == 4 || i == 6 || i == 9 || i == 11) ? 30 : 31 : z ? 29 : 28;
    }

    private static final boolean parseIso$lambda$1(char c) {
        return c == '-';
    }

    private static final boolean parseIso$lambda$2(char c) {
        return c == '-';
    }

    private static final boolean parseIso$lambda$3(char c) {
        return c == 'T' || c == 't';
    }

    private static final boolean parseIso$lambda$4(char c) {
        return c == ':';
    }

    private static final boolean parseIso$lambda$5(char c) {
        return c == ':';
    }

    private static final boolean parseIso$lambda$6(char c) {
        return '0' <= c && c < ':';
    }

    private static final boolean isDistantPast(Instant instant) {
        Intrinsics.checkNotNullParameter(instant, "<this>");
        return instant.compareTo(Instant.INSTANCE.getDISTANT_PAST()) <= 0;
    }

    private static final boolean isDistantFuture(Instant instant) {
        Intrinsics.checkNotNullParameter(instant, "<this>");
        return instant.compareTo(Instant.INSTANCE.getDISTANT_FUTURE()) >= 0;
    }

    private static final Void parseIso$parseFailure(CharSequence charSequence, String str) {
        throw new InstantFormatException(str + " when parsing an Instant from \"" + truncateForErrorMessage(charSequence, 64) + '\"');
    }

    private static final void parseIso$expect(CharSequence charSequence, String str, int i, Function1 function1) {
        char charAt = charSequence.charAt(i);
        if (((Boolean) function1.invoke(Character.valueOf(charAt))).booleanValue()) {
            return;
        }
        parseIso$parseFailure(charSequence, "Expected " + str + ", but got '" + charAt + "' at position " + i);
        throw new KotlinNothingValueException();
    }

    @ExperimentalTime
    private static final Instant parseIso(CharSequence charSequence) {
        int i;
        int i2;
        int i3;
        char charAt;
        char charAt2;
        if (charSequence.length() <= 0) {
            throw new IllegalArgumentException("An empty string is not a valid Instant".toString());
        }
        char charAt3 = charSequence.charAt(0);
        if (charAt3 == '+' || charAt3 == '-') {
            i = 1;
        } else {
            charAt3 = ' ';
            i = 0;
        }
        int i4 = i;
        int i5 = 0;
        while (i4 < charSequence.length() && '0' <= (charAt2 = charSequence.charAt(i4)) && charAt2 < ':') {
            i5 = (i5 * 10) + (charSequence.charAt(i4) - '0');
            i4++;
        }
        int i6 = i4 - i;
        if (i6 > 10) {
            parseIso$parseFailure(charSequence, "Expected at most 10 digits for the year number, got " + i6 + " digits");
            throw new KotlinNothingValueException();
        }
        if (i6 == 10 && Intrinsics.compare((int) charSequence.charAt(i), 50) >= 0) {
            parseIso$parseFailure(charSequence, "Expected at most 9 digits for the year number or year 1000000000, got " + i6 + " digits");
            throw new KotlinNothingValueException();
        }
        if (i6 < 4) {
            parseIso$parseFailure(charSequence, "The year number must be padded to 4 digits, got " + i6 + " digits");
            throw new KotlinNothingValueException();
        }
        if (charAt3 == '+' && i6 == 4) {
            parseIso$parseFailure(charSequence, "The '+' sign at the start is only valid for year numbers longer than 4 digits");
            throw new KotlinNothingValueException();
        }
        if (charAt3 == ' ' && i6 != 4) {
            parseIso$parseFailure(charSequence, "A '+' or '-' sign is required for year numbers longer than 4 digits");
            throw new KotlinNothingValueException();
        }
        if (charAt3 == '-') {
            i5 = -i5;
        }
        int i7 = i5;
        int i8 = i4 + 16;
        if (charSequence.length() < i8) {
            parseIso$parseFailure(charSequence, "The input string is too short");
            throw new KotlinNothingValueException();
        }
        parseIso$expect(charSequence, "'-'", i4, new InstantKt$$ExternalSyntheticLambda0());
        parseIso$expect(charSequence, "'-'", i4 + 3, new InstantKt$$ExternalSyntheticLambda1());
        parseIso$expect(charSequence, "'T' or 't'", i4 + 6, new InstantKt$$ExternalSyntheticLambda2());
        parseIso$expect(charSequence, "':'", i4 + 9, new InstantKt$$ExternalSyntheticLambda3());
        parseIso$expect(charSequence, "':'", i4 + 12, new InstantKt$$ExternalSyntheticLambda4());
        for (int i9 : asciiDigitPositionsInIsoStringAfterYear) {
            parseIso$expect(charSequence, "an ASCII digit", i9 + i4, new InstantKt$$ExternalSyntheticLambda5());
        }
        int parseIso$twoDigitNumber = parseIso$twoDigitNumber(charSequence, i4 + 1);
        int parseIso$twoDigitNumber2 = parseIso$twoDigitNumber(charSequence, i4 + 4);
        int parseIso$twoDigitNumber3 = parseIso$twoDigitNumber(charSequence, i4 + 7);
        int parseIso$twoDigitNumber4 = parseIso$twoDigitNumber(charSequence, i4 + 10);
        int parseIso$twoDigitNumber5 = parseIso$twoDigitNumber(charSequence, i4 + 13);
        int i10 = i4 + 15;
        if (charSequence.charAt(i10) == '.') {
            i10 = i8;
            int i11 = 0;
            while (i10 < charSequence.length() && '0' <= (charAt = charSequence.charAt(i10)) && charAt < ':') {
                i11 = (i11 * 10) + (charSequence.charAt(i10) - '0');
                i10++;
            }
            int i12 = i10 - i8;
            if (1 > i12 || i12 >= 10) {
                parseIso$parseFailure(charSequence, "1..9 digits are supported for the fraction of the second, got " + i12 + " digits");
                throw new KotlinNothingValueException();
            }
            i2 = i11 * POWERS_OF_TEN[9 - i12];
        } else {
            i2 = 0;
        }
        if (i10 >= charSequence.length()) {
            parseIso$parseFailure(charSequence, "The UTC offset at the end of the string is missing");
            throw new KotlinNothingValueException();
        }
        char charAt4 = charSequence.charAt(i10);
        if (charAt4 == '+' || charAt4 == '-') {
            int length = charSequence.length() - i10;
            if (length > 9) {
                parseIso$parseFailure(charSequence, "The UTC offset string \"" + truncateForErrorMessage(charSequence.subSequence(i10, charSequence.length()).toString(), 16) + "\" is too long");
                throw new KotlinNothingValueException();
            }
            if (length % 3 != 0) {
                parseIso$parseFailure(charSequence, "Invalid UTC offset string \"" + charSequence.subSequence(i10, charSequence.length()).toString() + '\"');
                throw new KotlinNothingValueException();
            }
            for (int i13 : colonsInIsoOffsetString) {
                int i14 = i10 + i13;
                if (i14 >= charSequence.length()) {
                    break;
                }
                if (charSequence.charAt(i14) != ':') {
                    parseIso$parseFailure(charSequence, "Expected ':' at index " + i14 + ", got '" + charSequence.charAt(i14) + '\'');
                    throw new KotlinNothingValueException();
                }
            }
            int[] iArr = asciiDigitsInIsoOffsetString;
            int length2 = iArr.length;
            int i15 = 0;
            while (i15 < length2) {
                int i16 = iArr[i15] + i10;
                if (i16 >= charSequence.length()) {
                    break;
                }
                char charAt5 = charSequence.charAt(i16);
                int[] iArr2 = iArr;
                if ('0' > charAt5 || charAt5 >= ':') {
                    parseIso$parseFailure(charSequence, "Expected an ASCII digit at index " + i16 + ", got '" + charSequence.charAt(i16) + '\'');
                    throw new KotlinNothingValueException();
                }
                i15++;
                iArr = iArr2;
            }
            int parseIso$twoDigitNumber6 = parseIso$twoDigitNumber(charSequence, i10 + 1);
            int parseIso$twoDigitNumber7 = length > 3 ? parseIso$twoDigitNumber(charSequence, i10 + 4) : 0;
            int parseIso$twoDigitNumber8 = length > 6 ? parseIso$twoDigitNumber(charSequence, i10 + 7) : 0;
            if (parseIso$twoDigitNumber7 > 59) {
                parseIso$parseFailure(charSequence, "Expected offset-minute-of-hour in 0..59, got " + parseIso$twoDigitNumber7);
                throw new KotlinNothingValueException();
            }
            if (parseIso$twoDigitNumber8 > 59) {
                parseIso$parseFailure(charSequence, "Expected offset-second-of-minute in 0..59, got " + parseIso$twoDigitNumber8);
                throw new KotlinNothingValueException();
            }
            if (parseIso$twoDigitNumber6 > 17 && (parseIso$twoDigitNumber6 != 18 || parseIso$twoDigitNumber7 != 0 || parseIso$twoDigitNumber8 != 0)) {
                parseIso$parseFailure(charSequence, "Expected an offset in -18:00..+18:00, got " + charSequence.subSequence(i10, charSequence.length()).toString());
                throw new KotlinNothingValueException();
            }
            i3 = (charAt4 == '-' ? -1 : 1) * ((parseIso$twoDigitNumber6 * 3600) + (parseIso$twoDigitNumber7 * 60) + parseIso$twoDigitNumber8);
        } else {
            if (charAt4 != 'Z' && charAt4 != 'z') {
                parseIso$parseFailure(charSequence, "Expected the UTC offset at position " + i10 + ", got '" + charAt4 + '\'');
                throw new KotlinNothingValueException();
            }
            int i17 = i10 + 1;
            if (charSequence.length() != i17) {
                parseIso$parseFailure(charSequence, "Extra text after the instant at position " + i17);
                throw new KotlinNothingValueException();
            }
            i3 = 0;
        }
        if (1 > parseIso$twoDigitNumber || parseIso$twoDigitNumber >= 13) {
            parseIso$parseFailure(charSequence, "Expected a month number in 1..12, got " + parseIso$twoDigitNumber);
            throw new KotlinNothingValueException();
        }
        if (1 > parseIso$twoDigitNumber2 || parseIso$twoDigitNumber2 > monthLength(parseIso$twoDigitNumber, isLeapYear(i7))) {
            parseIso$parseFailure(charSequence, "Expected a valid day-of-month for month " + parseIso$twoDigitNumber + " of year " + i7 + ", got " + parseIso$twoDigitNumber2);
            throw new KotlinNothingValueException();
        }
        if (parseIso$twoDigitNumber3 > 23) {
            parseIso$parseFailure(charSequence, "Expected hour in 0..23, got " + parseIso$twoDigitNumber3);
            throw new KotlinNothingValueException();
        }
        if (parseIso$twoDigitNumber4 > 59) {
            parseIso$parseFailure(charSequence, "Expected minute-of-hour in 0..59, got " + parseIso$twoDigitNumber4);
            throw new KotlinNothingValueException();
        }
        if (parseIso$twoDigitNumber5 > 59) {
            parseIso$parseFailure(charSequence, "Expected second-of-minute in 0..59, got " + parseIso$twoDigitNumber5);
            throw new KotlinNothingValueException();
        }
        return new UnboundLocalDateTime(i7, parseIso$twoDigitNumber, parseIso$twoDigitNumber2, parseIso$twoDigitNumber3, parseIso$twoDigitNumber4, parseIso$twoDigitNumber5, i2).toInstant(i3);
    }

    private static final int parseIso$twoDigitNumber(CharSequence charSequence, int i) {
        return ((charSequence.charAt(i) - '0') * 10) + (charSequence.charAt(i + 1) - '0');
    }

    @ExperimentalTime
    private static final String formatIso(Instant instant) {
        int[] iArr;
        Appendable sb = new StringBuilder();
        UnboundLocalDateTime fromInstant = UnboundLocalDateTime.Companion.fromInstant(instant);
        int year = fromInstant.getYear();
        int i = 0;
        if (Math.abs(year) < 1000) {
            CharSequence sb2 = new StringBuilder();
            if (year >= 0) {
                sb2.append(year + 10000);
                Intrinsics.checkNotNullExpressionValue(sb2.deleteCharAt(0), "deleteCharAt(...)");
            } else {
                sb2.append(year - 10000);
                Intrinsics.checkNotNullExpressionValue(sb2.deleteCharAt(1), "deleteCharAt(...)");
            }
            sb.append(sb2);
        } else {
            if (year >= 10000) {
                sb.append('+');
            }
            sb.append(year);
        }
        sb.append('-');
        Appendable appendable = sb;
        formatIso$lambda$8$appendTwoDigits(appendable, sb, fromInstant.getMonth());
        sb.append('-');
        formatIso$lambda$8$appendTwoDigits(appendable, sb, fromInstant.getDay());
        sb.append('T');
        formatIso$lambda$8$appendTwoDigits(appendable, sb, fromInstant.getHour());
        sb.append(':');
        formatIso$lambda$8$appendTwoDigits(appendable, sb, fromInstant.getMinute());
        sb.append(':');
        formatIso$lambda$8$appendTwoDigits(appendable, sb, fromInstant.getSecond());
        if (fromInstant.getNanosecond() != 0) {
            sb.append('.');
            while (true) {
                int nanosecond = fromInstant.getNanosecond();
                iArr = POWERS_OF_TEN;
                int i2 = i + 1;
                if (nanosecond % iArr[i2] != 0) {
                    break;
                }
                i = i2;
            }
            int i3 = i - (i % 3);
            String valueOf = String.valueOf((fromInstant.getNanosecond() / iArr[i3]) + iArr[9 - i3]);
            Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
            String substring = valueOf.substring(1);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            sb.append(substring);
        }
        sb.append('Z');
        return sb.toString();
    }

    private static final void formatIso$lambda$8$appendTwoDigits(Appendable appendable, StringBuilder sb, int i) {
        if (i < 10) {
            appendable.append('0');
        }
        sb.append(i);
    }

    private static final long safeAddOrElse(long j, long j2, Function0 function0) {
        long j3 = j + j2;
        if ((j ^ j3) >= 0 || (j ^ j2) < 0) {
            return j3;
        }
        function0.invoke();
        throw new KotlinNothingValueException();
    }

    private static final long safeMultiplyOrElse(long j, long j2, Function0 function0) {
        if (j2 == 1) {
            return j;
        }
        if (j == 1) {
            return j2;
        }
        if (j == 0 || j2 == 0) {
            return 0L;
        }
        long j3 = j * j2;
        if (j3 / j2 == j && ((j != Long.MIN_VALUE || j2 != -1) && (j2 != Long.MIN_VALUE || j != -1))) {
            return j3;
        }
        function0.invoke();
        throw new KotlinNothingValueException();
    }

    public static final boolean isLeapYear(int i) {
        if ((i & 3) == 0) {
            return i % 100 != 0 || i % 400 == 0;
        }
        return false;
    }

    private static final String truncateForErrorMessage(CharSequence charSequence, int i) {
        if (charSequence.length() <= i) {
            return charSequence.toString();
        }
        return charSequence.subSequence(0, i).toString() + "...";
    }
}
