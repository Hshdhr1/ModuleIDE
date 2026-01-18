package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: UByte.kt */
@SinceKotlin(version = "1.5")
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\n\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0087@\u0018\u0000 s2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001sB\u0011\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0000H\u0097\n¢\u0006\u0004\b\u000b\u0010\fJ\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\u000e\u0010\u000fJ\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0010H\u0087\n¢\u0006\u0004\b\u0011\u0010\u0012J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0013H\u0087\n¢\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\u0016\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b\u0017\u0010\fJ\u0018\u0010\u0016\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\u0018\u0010\u000fJ\u0018\u0010\u0016\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\n¢\u0006\u0004\b\u0019\u0010\u0012J\u0018\u0010\u0016\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\n¢\u0006\u0004\b\u001a\u0010\u001bJ\u0018\u0010\u001c\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b\u001d\u0010\fJ\u0018\u0010\u001c\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\u001e\u0010\u000fJ\u0018\u0010\u001c\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\n¢\u0006\u0004\b\u001f\u0010\u0012J\u0018\u0010\u001c\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\n¢\u0006\u0004\b \u0010\u001bJ\u0018\u0010!\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b\"\u0010\fJ\u0018\u0010!\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\n¢\u0006\u0004\b#\u0010\u000fJ\u0018\u0010!\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\n¢\u0006\u0004\b$\u0010\u0012J\u0018\u0010!\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\n¢\u0006\u0004\b%\u0010\u001bJ\u0018\u0010&\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b'\u0010\fJ\u0018\u0010&\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\n¢\u0006\u0004\b(\u0010\u000fJ\u0018\u0010&\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\n¢\u0006\u0004\b)\u0010\u0012J\u0018\u0010&\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\n¢\u0006\u0004\b*\u0010\u001bJ\u0018\u0010+\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b,\u0010\fJ\u0018\u0010+\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\n¢\u0006\u0004\b-\u0010\u000fJ\u0018\u0010+\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\n¢\u0006\u0004\b.\u0010\u0012J\u0018\u0010+\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\n¢\u0006\u0004\b/\u0010\u001bJ\u0018\u00100\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b1\u0010\fJ\u0018\u00100\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\rH\u0087\b¢\u0006\u0004\b2\u0010\u000fJ\u0018\u00100\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\b¢\u0006\u0004\b3\u0010\u0012J\u0018\u00100\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\b¢\u0006\u0004\b4\u0010\u001bJ\u0018\u00105\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b6\u00107J\u0018\u00105\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\rH\u0087\b¢\u0006\u0004\b8\u00109J\u0018\u00105\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010H\u0087\b¢\u0006\u0004\b:\u0010\u0012J\u0018\u00105\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u0013H\u0087\b¢\u0006\u0004\b;\u0010\u001bJ\u0010\u0010<\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b=\u0010\u0005J\u0010\u0010>\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b?\u0010\u0005J\u0018\u0010@\u001a\u00020A2\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bB\u0010CJ\u0018\u0010D\u001a\u00020A2\u0006\u0010\n\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bE\u0010CJ\u0018\u0010F\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bG\u00107J\u0018\u0010H\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bI\u00107J\u0018\u0010J\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bK\u00107J\u0010\u0010L\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\bM\u0010\u0005J\u0010\u0010N\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bO\u0010\u0005J\u0010\u0010P\u001a\u00020QH\u0087\b¢\u0006\u0004\bR\u0010SJ\u0010\u0010T\u001a\u00020\tH\u0087\b¢\u0006\u0004\bU\u0010VJ\u0010\u0010W\u001a\u00020XH\u0087\b¢\u0006\u0004\bY\u0010ZJ\u0010\u0010[\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b\\\u0010\u0005J\u0010\u0010]\u001a\u00020\rH\u0087\b¢\u0006\u0004\b^\u0010SJ\u0010\u0010_\u001a\u00020\u0010H\u0087\b¢\u0006\u0004\b`\u0010VJ\u0010\u0010a\u001a\u00020\u0013H\u0087\b¢\u0006\u0004\bb\u0010ZJ\u0010\u0010c\u001a\u00020dH\u0087\b¢\u0006\u0004\be\u0010fJ\u0010\u0010g\u001a\u00020hH\u0087\b¢\u0006\u0004\bi\u0010jJ\u000f\u0010k\u001a\u00020lH\u0016¢\u0006\u0004\bm\u0010nJ\u0013\u0010o\u001a\u00020p2\b\u0010\n\u001a\u0004\u0018\u00010qHÖ\u0003J\t\u0010r\u001a\u00020\tHÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003¨\u0006t"}, d2 = {"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "getData$annotations", "()V", "compareTo", "", "other", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "plus", "plus-7apg3OU", "plus-xj2QHRw", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "(BJ)J", "minus", "minus-7apg3OU", "minus-xj2QHRw", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "times", "times-7apg3OU", "times-xj2QHRw", "times-WZ4Q5Ns", "times-VKZWuLQ", "div", "div-7apg3OU", "div-xj2QHRw", "div-WZ4Q5Ns", "div-VKZWuLQ", "rem", "rem-7apg3OU", "rem-xj2QHRw", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "floorDiv", "floorDiv-7apg3OU", "floorDiv-xj2QHRw", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "mod", "mod-7apg3OU", "(BB)B", "mod-xj2QHRw", "(BS)S", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "inc", "inc-w2LRezQ", "dec", "dec-w2LRezQ", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-7apg3OU", "and", "and-7apg3OU", "or", "or-7apg3OU", "xor", "xor-7apg3OU", "inv", "inv-w2LRezQ", "toByte", "toByte-impl", "toShort", "", "toShort-impl", "(B)S", "toInt", "toInt-impl", "(B)I", "toLong", "", "toLong-impl", "(B)J", "toUByte", "toUByte-w2LRezQ", "toUShort", "toUShort-Mh2AYeg", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toFloat", "", "toFloat-impl", "(B)F", "toDouble", "", "toDouble-impl", "(B)D", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "equals", "", "", "hashCode", "Companion", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
@JvmInline
@WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class UByte implements Comparable {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final byte MAX_VALUE = -1;
    public static final byte MIN_VALUE = 0;
    public static final int SIZE_BITS = 8;
    public static final int SIZE_BYTES = 1;
    private final byte data;

    public static final /* synthetic */ UByte box-impl(byte b) {
        return new UByte(b);
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    public static byte constructor-impl(byte b) {
        return b;
    }

    public static boolean equals-impl(byte b, Object obj) {
        return (obj instanceof UByte) && b == ((UByte) obj).getData();
    }

    public static final boolean equals-impl0(byte b, byte b2) {
        return b == b2;
    }

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    public static int hashCode-impl(byte b) {
        return b;
    }

    @InlineOnly
    private static final byte toByte-impl(byte b) {
        return b;
    }

    @InlineOnly
    private static final int toInt-impl(byte b) {
        return b & 255;
    }

    @InlineOnly
    private static final long toLong-impl(byte b) {
        return b & 255;
    }

    @InlineOnly
    private static final short toShort-impl(byte b) {
        return (short) (b & 255);
    }

    @InlineOnly
    private static final byte toUByte-w2LRezQ(byte b) {
        return b;
    }

    public boolean equals(Object other) {
        return equals-impl(this.data, other);
    }

    public int hashCode() {
        return hashCode-impl(this.data);
    }

    /* renamed from: unbox-impl, reason: from getter */
    public final /* synthetic */ byte getData() {
        return this.data;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return Intrinsics.compare(getData() & 255, ((UByte) obj).getData() & 255);
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    private /* synthetic */ UByte(byte b) {
        this.data = b;
    }

    /* compiled from: UByte.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlin/UByte$Companion;", "", "<init>", "()V", "MIN_VALUE", "Lkotlin/UByte;", "B", "MAX_VALUE", "SIZE_BYTES", "", "SIZE_BITS", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @InlineOnly
    private int compareTo-7apg3OU(byte b) {
        return Intrinsics.compare(getData() & 255, b & 255);
    }

    @InlineOnly
    private static int compareTo-7apg3OU(byte b, byte b2) {
        return Intrinsics.compare(b & 255, b2 & 255);
    }

    @InlineOnly
    private static final int compareTo-xj2QHRw(byte b, short s) {
        return Intrinsics.compare(b & 255, s & 65535);
    }

    @InlineOnly
    private static final int compareTo-WZ4Q5Ns(byte b, int i) {
        return UByte$$ExternalSyntheticBackport6.m(UInt.constructor-impl(b & 255), i);
    }

    @InlineOnly
    private static final int compareTo-VKZWuLQ(byte b, long j) {
        return UByte$$ExternalSyntheticBackport5.m(ULong.constructor-impl(b & 255), j);
    }

    @InlineOnly
    private static final int plus-7apg3OU(byte b, byte b2) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) + UInt.constructor-impl(b2 & 255));
    }

    @InlineOnly
    private static final int plus-xj2QHRw(byte b, short s) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) + UInt.constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int plus-WZ4Q5Ns(byte b, int i) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) + i);
    }

    @InlineOnly
    private static final long plus-VKZWuLQ(byte b, long j) {
        return ULong.constructor-impl(ULong.constructor-impl(b & 255) + j);
    }

    @InlineOnly
    private static final int minus-7apg3OU(byte b, byte b2) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) - UInt.constructor-impl(b2 & 255));
    }

    @InlineOnly
    private static final int minus-xj2QHRw(byte b, short s) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) - UInt.constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int minus-WZ4Q5Ns(byte b, int i) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) - i);
    }

    @InlineOnly
    private static final long minus-VKZWuLQ(byte b, long j) {
        return ULong.constructor-impl(ULong.constructor-impl(b & 255) - j);
    }

    @InlineOnly
    private static final int times-7apg3OU(byte b, byte b2) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) * UInt.constructor-impl(b2 & 255));
    }

    @InlineOnly
    private static final int times-xj2QHRw(byte b, short s) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) * UInt.constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int times-WZ4Q5Ns(byte b, int i) {
        return UInt.constructor-impl(UInt.constructor-impl(b & 255) * i);
    }

    @InlineOnly
    private static final long times-VKZWuLQ(byte b, long j) {
        return ULong.constructor-impl(ULong.constructor-impl(b & 255) * j);
    }

    @InlineOnly
    private static final int div-7apg3OU(byte b, byte b2) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(b & 255), UInt.constructor-impl(b2 & 255));
    }

    @InlineOnly
    private static final int div-xj2QHRw(byte b, short s) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(b & 255), UInt.constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int div-WZ4Q5Ns(byte b, int i) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(b & 255), i);
    }

    @InlineOnly
    private static final long div-VKZWuLQ(byte b, long j) {
        return UByte$$ExternalSyntheticBackport4.m(ULong.constructor-impl(b & 255), j);
    }

    @InlineOnly
    private static final int rem-7apg3OU(byte b, byte b2) {
        return UByte$$ExternalSyntheticBackport1.m(UInt.constructor-impl(b & 255), UInt.constructor-impl(b2 & 255));
    }

    @InlineOnly
    private static final int rem-xj2QHRw(byte b, short s) {
        return UByte$$ExternalSyntheticBackport1.m(UInt.constructor-impl(b & 255), UInt.constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int rem-WZ4Q5Ns(byte b, int i) {
        return UByte$$ExternalSyntheticBackport1.m(UInt.constructor-impl(b & 255), i);
    }

    @InlineOnly
    private static final long rem-VKZWuLQ(byte b, long j) {
        return UByte$$ExternalSyntheticBackport2.m(ULong.constructor-impl(b & 255), j);
    }

    @InlineOnly
    private static final int floorDiv-7apg3OU(byte b, byte b2) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(b & 255), UInt.constructor-impl(b2 & 255));
    }

    @InlineOnly
    private static final int floorDiv-xj2QHRw(byte b, short s) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(b & 255), UInt.constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int floorDiv-WZ4Q5Ns(byte b, int i) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(b & 255), i);
    }

    @InlineOnly
    private static final long floorDiv-VKZWuLQ(byte b, long j) {
        return UByte$$ExternalSyntheticBackport4.m(ULong.constructor-impl(b & 255), j);
    }

    @InlineOnly
    private static final byte mod-7apg3OU(byte b, byte b2) {
        return constructor-impl((byte) UByte$$ExternalSyntheticBackport1.m(UInt.constructor-impl(b & 255), UInt.constructor-impl(b2 & 255)));
    }

    @InlineOnly
    private static final short mod-xj2QHRw(byte b, short s) {
        return UShort.constructor-impl((short) UByte$$ExternalSyntheticBackport1.m(UInt.constructor-impl(b & 255), UInt.constructor-impl(s & 65535)));
    }

    @InlineOnly
    private static final int mod-WZ4Q5Ns(byte b, int i) {
        return UByte$$ExternalSyntheticBackport1.m(UInt.constructor-impl(b & 255), i);
    }

    @InlineOnly
    private static final long mod-VKZWuLQ(byte b, long j) {
        return UByte$$ExternalSyntheticBackport2.m(ULong.constructor-impl(b & 255), j);
    }

    @InlineOnly
    private static final byte inc-w2LRezQ(byte b) {
        return constructor-impl((byte) (b + 1));
    }

    @InlineOnly
    private static final byte dec-w2LRezQ(byte b) {
        return constructor-impl((byte) (b - 1));
    }

    @InlineOnly
    private static final UIntRange rangeTo-7apg3OU(byte b, byte b2) {
        return new UIntRange(UInt.constructor-impl(b & 255), UInt.constructor-impl(b2 & 255), null);
    }

    @SinceKotlin(version = "1.9")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final UIntRange rangeUntil-7apg3OU(byte b, byte b2) {
        return URangesKt.until-J1ME1BU(UInt.constructor-impl(b & 255), UInt.constructor-impl(b2 & 255));
    }

    @InlineOnly
    private static final byte and-7apg3OU(byte b, byte b2) {
        return constructor-impl((byte) (b & b2));
    }

    @InlineOnly
    private static final byte or-7apg3OU(byte b, byte b2) {
        return constructor-impl((byte) (b | b2));
    }

    @InlineOnly
    private static final byte xor-7apg3OU(byte b, byte b2) {
        return constructor-impl((byte) (b ^ b2));
    }

    @InlineOnly
    private static final byte inv-w2LRezQ(byte b) {
        return constructor-impl((byte) (b ^ (-1)));
    }

    @InlineOnly
    private static final short toUShort-Mh2AYeg(byte b) {
        return UShort.constructor-impl((short) (b & 255));
    }

    @InlineOnly
    private static final int toUInt-pVg5ArA(byte b) {
        return UInt.constructor-impl(b & 255);
    }

    @InlineOnly
    private static final long toULong-s-VKNKU(byte b) {
        return ULong.constructor-impl(b & 255);
    }

    @InlineOnly
    private static final float toFloat-impl(byte b) {
        return (float) UnsignedKt.uintToDouble(b & 255);
    }

    @InlineOnly
    private static final double toDouble-impl(byte b) {
        return UnsignedKt.uintToDouble(b & 255);
    }

    @NotNull
    public static String toString-impl(byte b) {
        return String.valueOf(b & 255);
    }

    @NotNull
    public String toString() {
        return toString-impl(this.data);
    }
}
