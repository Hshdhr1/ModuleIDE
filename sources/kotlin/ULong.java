package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.ULongRange;
import kotlin.ranges.URangesKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: ULong.kt */
@SinceKotlin(version = "1.5")
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b2\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0010\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0087@\u0018\u0000 {2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001{B\u0011\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0087\n¢\u0006\u0004\b\f\u0010\rJ\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b\u000f\u0010\u0010J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\u0012\u0010\u0013J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0000H\u0097\n¢\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\u0016\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0087\n¢\u0006\u0004\b\u0017\u0010\u0018J\u0018\u0010\u0016\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b\u0019\u0010\u001aJ\u0018\u0010\u0016\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\u001b\u0010\u001cJ\u0018\u0010\u0016\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b\u001d\u0010\u001eJ\u0018\u0010\u001f\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0087\n¢\u0006\u0004\b \u0010\u0018J\u0018\u0010\u001f\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b!\u0010\u001aJ\u0018\u0010\u001f\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\"\u0010\u001cJ\u0018\u0010\u001f\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b#\u0010\u001eJ\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0087\n¢\u0006\u0004\b%\u0010\u0018J\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b&\u0010\u001aJ\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b'\u0010\u001cJ\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b(\u0010\u001eJ\u0018\u0010)\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0087\n¢\u0006\u0004\b*\u0010\u0018J\u0018\u0010)\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b+\u0010\u001aJ\u0018\u0010)\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b,\u0010\u001cJ\u0018\u0010)\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b-\u0010\u001eJ\u0018\u0010.\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0087\n¢\u0006\u0004\b/\u0010\u0018J\u0018\u0010.\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b0\u0010\u001aJ\u0018\u0010.\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b1\u0010\u001cJ\u0018\u0010.\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b2\u0010\u001eJ\u0018\u00103\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0087\b¢\u0006\u0004\b4\u0010\u0018J\u0018\u00103\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000eH\u0087\b¢\u0006\u0004\b5\u0010\u001aJ\u0018\u00103\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\b6\u0010\u001cJ\u0018\u00103\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b7\u0010\u001eJ\u0018\u00108\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000bH\u0087\b¢\u0006\u0004\b9\u0010:J\u0018\u00108\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000eH\u0087\b¢\u0006\u0004\b;\u0010<J\u0018\u00108\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\b=\u0010\u0013J\u0018\u00108\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b>\u0010\u001eJ\u0010\u0010?\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b@\u0010\u0005J\u0010\u0010A\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bB\u0010\u0005J\u0018\u0010C\u001a\u00020D2\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bE\u0010FJ\u0018\u0010G\u001a\u00020D2\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bH\u0010FJ\u0018\u0010I\u001a\u00020\u00002\u0006\u0010J\u001a\u00020\tH\u0087\f¢\u0006\u0004\bK\u0010\u001cJ\u0018\u0010L\u001a\u00020\u00002\u0006\u0010J\u001a\u00020\tH\u0087\f¢\u0006\u0004\bM\u0010\u001cJ\u0018\u0010N\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bO\u0010\u001eJ\u0018\u0010P\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bQ\u0010\u001eJ\u0018\u0010R\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bS\u0010\u001eJ\u0010\u0010T\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\bU\u0010\u0005J\u0010\u0010V\u001a\u00020WH\u0087\b¢\u0006\u0004\bX\u0010YJ\u0010\u0010Z\u001a\u00020[H\u0087\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020\tH\u0087\b¢\u0006\u0004\b_\u0010`J\u0010\u0010a\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bb\u0010\u0005J\u0010\u0010c\u001a\u00020\u000bH\u0087\b¢\u0006\u0004\bd\u0010YJ\u0010\u0010e\u001a\u00020\u000eH\u0087\b¢\u0006\u0004\bf\u0010]J\u0010\u0010g\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\bh\u0010`J\u0010\u0010i\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\bj\u0010\u0005J\u0010\u0010k\u001a\u00020lH\u0087\b¢\u0006\u0004\bm\u0010nJ\u0010\u0010o\u001a\u00020pH\u0087\b¢\u0006\u0004\bq\u0010rJ\u000f\u0010s\u001a\u00020tH\u0016¢\u0006\u0004\bu\u0010vJ\u0013\u0010w\u001a\u00020x2\b\u0010\n\u001a\u0004\u0018\u00010yHÖ\u0003J\t\u0010z\u001a\u00020\tHÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003¨\u0006|"}, d2 = {"Lkotlin/ULong;", "", "data", "", "constructor-impl", "(J)J", "getData$annotations", "()V", "compareTo", "", "other", "Lkotlin/UByte;", "compareTo-7apg3OU", "(JB)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(JS)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(JI)I", "compareTo-VKZWuLQ", "(JJ)I", "plus", "plus-7apg3OU", "(JB)J", "plus-xj2QHRw", "(JS)J", "plus-WZ4Q5Ns", "(JI)J", "plus-VKZWuLQ", "(JJ)J", "minus", "minus-7apg3OU", "minus-xj2QHRw", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "times", "times-7apg3OU", "times-xj2QHRw", "times-WZ4Q5Ns", "times-VKZWuLQ", "div", "div-7apg3OU", "div-xj2QHRw", "div-WZ4Q5Ns", "div-VKZWuLQ", "rem", "rem-7apg3OU", "rem-xj2QHRw", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "floorDiv", "floorDiv-7apg3OU", "floorDiv-xj2QHRw", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "mod", "mod-7apg3OU", "(JB)B", "mod-xj2QHRw", "(JS)S", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "inc", "inc-s-VKNKU", "dec", "dec-s-VKNKU", "rangeTo", "Lkotlin/ranges/ULongRange;", "rangeTo-VKZWuLQ", "(JJ)Lkotlin/ranges/ULongRange;", "rangeUntil", "rangeUntil-VKZWuLQ", "shl", "bitCount", "shl-s-VKNKU", "shr", "shr-s-VKNKU", "and", "and-VKZWuLQ", "or", "or-VKZWuLQ", "xor", "xor-VKZWuLQ", "inv", "inv-s-VKNKU", "toByte", "", "toByte-impl", "(J)B", "toShort", "", "toShort-impl", "(J)S", "toInt", "toInt-impl", "(J)I", "toLong", "toLong-impl", "toUByte", "toUByte-w2LRezQ", "toUShort", "toUShort-Mh2AYeg", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toFloat", "", "toFloat-impl", "(J)F", "toDouble", "", "toDouble-impl", "(J)D", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "equals", "", "", "hashCode", "Companion", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
@JvmInline
@WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class ULong implements Comparable {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final long MAX_VALUE = -1;
    public static final long MIN_VALUE = 0;
    public static final int SIZE_BITS = 64;
    public static final int SIZE_BYTES = 8;
    private final long data;

    public static final /* synthetic */ ULong box-impl(long j) {
        return new ULong(j);
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    public static long constructor-impl(long j) {
        return j;
    }

    public static boolean equals-impl(long j, Object obj) {
        return (obj instanceof ULong) && j == ((ULong) obj).getData();
    }

    public static final boolean equals-impl0(long j, long j2) {
        return j == j2;
    }

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    public static int hashCode-impl(long j) {
        return ULong$$ExternalSyntheticBackport3.m(j);
    }

    @InlineOnly
    private static final byte toByte-impl(long j) {
        return (byte) j;
    }

    @InlineOnly
    private static final int toInt-impl(long j) {
        return (int) j;
    }

    @InlineOnly
    private static final long toLong-impl(long j) {
        return j;
    }

    @InlineOnly
    private static final short toShort-impl(long j) {
        return (short) j;
    }

    @InlineOnly
    private static final long toULong-s-VKNKU(long j) {
        return j;
    }

    public boolean equals(Object other) {
        return equals-impl(this.data, other);
    }

    public int hashCode() {
        return hashCode-impl(this.data);
    }

    /* renamed from: unbox-impl, reason: from getter */
    public final /* synthetic */ long getData() {
        return this.data;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return UnsignedKt.ulongCompare(getData(), ((ULong) obj).getData());
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    private /* synthetic */ ULong(long j) {
        this.data = j;
    }

    /* compiled from: ULong.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlin/ULong$Companion;", "", "<init>", "()V", "MIN_VALUE", "Lkotlin/ULong;", "J", "MAX_VALUE", "SIZE_BYTES", "", "SIZE_BITS", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @InlineOnly
    private static final int compareTo-7apg3OU(long j, byte b) {
        return ULong$$ExternalSyntheticBackport1.m(j, constructor-impl(b & 255));
    }

    @InlineOnly
    private static final int compareTo-xj2QHRw(long j, short s) {
        return ULong$$ExternalSyntheticBackport1.m(j, constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int compareTo-WZ4Q5Ns(long j, int i) {
        return ULong$$ExternalSyntheticBackport1.m(j, constructor-impl(i & 4294967295L));
    }

    @InlineOnly
    private int compareTo-VKZWuLQ(long j) {
        return UnsignedKt.ulongCompare(getData(), j);
    }

    @InlineOnly
    private static int compareTo-VKZWuLQ(long j, long j2) {
        return UnsignedKt.ulongCompare(j, j2);
    }

    @InlineOnly
    private static final long plus-7apg3OU(long j, byte b) {
        return constructor-impl(j + constructor-impl(b & 255));
    }

    @InlineOnly
    private static final long plus-xj2QHRw(long j, short s) {
        return constructor-impl(j + constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final long plus-WZ4Q5Ns(long j, int i) {
        return constructor-impl(j + constructor-impl(i & 4294967295L));
    }

    @InlineOnly
    private static final long plus-VKZWuLQ(long j, long j2) {
        return constructor-impl(j + j2);
    }

    @InlineOnly
    private static final long minus-7apg3OU(long j, byte b) {
        return constructor-impl(j - constructor-impl(b & 255));
    }

    @InlineOnly
    private static final long minus-xj2QHRw(long j, short s) {
        return constructor-impl(j - constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final long minus-WZ4Q5Ns(long j, int i) {
        return constructor-impl(j - constructor-impl(i & 4294967295L));
    }

    @InlineOnly
    private static final long minus-VKZWuLQ(long j, long j2) {
        return constructor-impl(j - j2);
    }

    @InlineOnly
    private static final long times-7apg3OU(long j, byte b) {
        return constructor-impl(j * constructor-impl(b & 255));
    }

    @InlineOnly
    private static final long times-xj2QHRw(long j, short s) {
        return constructor-impl(j * constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final long times-WZ4Q5Ns(long j, int i) {
        return constructor-impl(j * constructor-impl(i & 4294967295L));
    }

    @InlineOnly
    private static final long times-VKZWuLQ(long j, long j2) {
        return constructor-impl(j * j2);
    }

    @InlineOnly
    private static final long div-7apg3OU(long j, byte b) {
        return ULong$$ExternalSyntheticBackport2.m(j, constructor-impl(b & 255));
    }

    @InlineOnly
    private static final long div-xj2QHRw(long j, short s) {
        return ULong$$ExternalSyntheticBackport2.m(j, constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final long div-WZ4Q5Ns(long j, int i) {
        return ULong$$ExternalSyntheticBackport2.m(j, constructor-impl(i & 4294967295L));
    }

    @InlineOnly
    private static final long div-VKZWuLQ(long j, long j2) {
        return UnsignedKt.ulongDivide-eb3DHEI(j, j2);
    }

    @InlineOnly
    private static final long rem-7apg3OU(long j, byte b) {
        return ULong$$ExternalSyntheticBackport0.m(j, constructor-impl(b & 255));
    }

    @InlineOnly
    private static final long rem-xj2QHRw(long j, short s) {
        return ULong$$ExternalSyntheticBackport0.m(j, constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final long rem-WZ4Q5Ns(long j, int i) {
        return ULong$$ExternalSyntheticBackport0.m(j, constructor-impl(i & 4294967295L));
    }

    @InlineOnly
    private static final long rem-VKZWuLQ(long j, long j2) {
        return UnsignedKt.ulongRemainder-eb3DHEI(j, j2);
    }

    @InlineOnly
    private static final long floorDiv-7apg3OU(long j, byte b) {
        return ULong$$ExternalSyntheticBackport2.m(j, constructor-impl(b & 255));
    }

    @InlineOnly
    private static final long floorDiv-xj2QHRw(long j, short s) {
        return ULong$$ExternalSyntheticBackport2.m(j, constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final long floorDiv-WZ4Q5Ns(long j, int i) {
        return ULong$$ExternalSyntheticBackport2.m(j, constructor-impl(i & 4294967295L));
    }

    @InlineOnly
    private static final long floorDiv-VKZWuLQ(long j, long j2) {
        return ULong$$ExternalSyntheticBackport2.m(j, j2);
    }

    @InlineOnly
    private static final byte mod-7apg3OU(long j, byte b) {
        return UByte.constructor-impl((byte) ULong$$ExternalSyntheticBackport0.m(j, constructor-impl(b & 255)));
    }

    @InlineOnly
    private static final short mod-xj2QHRw(long j, short s) {
        return UShort.constructor-impl((short) ULong$$ExternalSyntheticBackport0.m(j, constructor-impl(s & 65535)));
    }

    @InlineOnly
    private static final int mod-WZ4Q5Ns(long j, int i) {
        return UInt.constructor-impl((int) ULong$$ExternalSyntheticBackport0.m(j, constructor-impl(i & 4294967295L)));
    }

    @InlineOnly
    private static final long mod-VKZWuLQ(long j, long j2) {
        return ULong$$ExternalSyntheticBackport0.m(j, j2);
    }

    @InlineOnly
    private static final long inc-s-VKNKU(long j) {
        return constructor-impl(j + 1);
    }

    @InlineOnly
    private static final long dec-s-VKNKU(long j) {
        return constructor-impl(j - 1);
    }

    @InlineOnly
    private static final ULongRange rangeTo-VKZWuLQ(long j, long j2) {
        return new ULongRange(j, j2, null);
    }

    @SinceKotlin(version = "1.9")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final ULongRange rangeUntil-VKZWuLQ(long j, long j2) {
        return URangesKt.until-eb3DHEI(j, j2);
    }

    @InlineOnly
    private static final long shl-s-VKNKU(long j, int i) {
        return constructor-impl(j << i);
    }

    @InlineOnly
    private static final long shr-s-VKNKU(long j, int i) {
        return constructor-impl(j >>> i);
    }

    @InlineOnly
    private static final long and-VKZWuLQ(long j, long j2) {
        return constructor-impl(j & j2);
    }

    @InlineOnly
    private static final long or-VKZWuLQ(long j, long j2) {
        return constructor-impl(j | j2);
    }

    @InlineOnly
    private static final long xor-VKZWuLQ(long j, long j2) {
        return constructor-impl(j ^ j2);
    }

    @InlineOnly
    private static final long inv-s-VKNKU(long j) {
        return constructor-impl(j ^ (-1));
    }

    @InlineOnly
    private static final byte toUByte-w2LRezQ(long j) {
        return UByte.constructor-impl((byte) j);
    }

    @InlineOnly
    private static final short toUShort-Mh2AYeg(long j) {
        return UShort.constructor-impl((short) j);
    }

    @InlineOnly
    private static final int toUInt-pVg5ArA(long j) {
        return UInt.constructor-impl((int) j);
    }

    @InlineOnly
    private static final float toFloat-impl(long j) {
        return (float) UnsignedKt.ulongToDouble(j);
    }

    @InlineOnly
    private static final double toDouble-impl(long j) {
        return UnsignedKt.ulongToDouble(j);
    }

    @NotNull
    public static String toString-impl(long j) {
        return UnsignedKt.ulongToString(j, 10);
    }

    @NotNull
    public String toString() {
        return toString-impl(this.data);
    }
}
