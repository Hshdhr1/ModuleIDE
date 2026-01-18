package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: UInt.kt */
@SinceKotlin(version = "1.5")
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0087@\u0018\u0000 x2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001xB\u0011\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u0087\n¢\u0006\u0004\b\u000b\u0010\fJ\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\u000e\u0010\u000fJ\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0000H\u0097\n¢\u0006\u0004\b\u0010\u0010\u0011J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0012H\u0087\n¢\u0006\u0004\b\u0013\u0010\u0014J\u0018\u0010\u0015\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nH\u0087\n¢\u0006\u0004\b\u0016\u0010\fJ\u0018\u0010\u0015\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\u0017\u0010\u000fJ\u0018\u0010\u0015\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b\u0018\u0010\u0011J\u0018\u0010\u0015\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u0012H\u0087\n¢\u0006\u0004\b\u0019\u0010\u001aJ\u0018\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nH\u0087\n¢\u0006\u0004\b\u001c\u0010\fJ\u0018\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\u001d\u0010\u000fJ\u0018\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b\u001e\u0010\u0011J\u0018\u0010\u001b\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u0012H\u0087\n¢\u0006\u0004\b\u001f\u0010\u001aJ\u0018\u0010 \u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nH\u0087\n¢\u0006\u0004\b!\u0010\fJ\u0018\u0010 \u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\"\u0010\u000fJ\u0018\u0010 \u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b#\u0010\u0011J\u0018\u0010 \u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u0012H\u0087\n¢\u0006\u0004\b$\u0010\u001aJ\u0018\u0010%\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nH\u0087\n¢\u0006\u0004\b&\u0010\fJ\u0018\u0010%\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b'\u0010\u000fJ\u0018\u0010%\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b(\u0010\u0011J\u0018\u0010%\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u0012H\u0087\n¢\u0006\u0004\b)\u0010\u001aJ\u0018\u0010*\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nH\u0087\n¢\u0006\u0004\b+\u0010\fJ\u0018\u0010*\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b,\u0010\u000fJ\u0018\u0010*\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b-\u0010\u0011J\u0018\u0010*\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u0012H\u0087\n¢\u0006\u0004\b.\u0010\u001aJ\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nH\u0087\b¢\u0006\u0004\b0\u0010\fJ\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\b¢\u0006\u0004\b1\u0010\u000fJ\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b2\u0010\u0011J\u0018\u0010/\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u0012H\u0087\b¢\u0006\u0004\b3\u0010\u001aJ\u0018\u00104\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\nH\u0087\b¢\u0006\u0004\b5\u00106J\u0018\u00104\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\rH\u0087\b¢\u0006\u0004\b7\u00108J\u0018\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b9\u0010\u0011J\u0018\u00104\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u0012H\u0087\b¢\u0006\u0004\b:\u0010\u001aJ\u0010\u0010;\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b<\u0010\u0005J\u0010\u0010=\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b>\u0010\u0005J\u0018\u0010?\u001a\u00020@2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bA\u0010BJ\u0018\u0010C\u001a\u00020@2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bD\u0010BJ\u0018\u0010E\u001a\u00020\u00002\u0006\u0010F\u001a\u00020\u0003H\u0087\f¢\u0006\u0004\bG\u0010\u0011J\u0018\u0010H\u001a\u00020\u00002\u0006\u0010F\u001a\u00020\u0003H\u0087\f¢\u0006\u0004\bI\u0010\u0011J\u0018\u0010J\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bK\u0010\u0011J\u0018\u0010L\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bM\u0010\u0011J\u0018\u0010N\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bO\u0010\u0011J\u0010\u0010P\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\bQ\u0010\u0005J\u0010\u0010R\u001a\u00020SH\u0087\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020WH\u0087\b¢\u0006\u0004\bX\u0010YJ\u0010\u0010Z\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\b[\u0010\u0005J\u0010\u0010\\\u001a\u00020]H\u0087\b¢\u0006\u0004\b^\u0010_J\u0010\u0010`\u001a\u00020\nH\u0087\b¢\u0006\u0004\ba\u0010UJ\u0010\u0010b\u001a\u00020\rH\u0087\b¢\u0006\u0004\bc\u0010YJ\u0010\u0010d\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\be\u0010\u0005J\u0010\u0010f\u001a\u00020\u0012H\u0087\b¢\u0006\u0004\bg\u0010_J\u0010\u0010h\u001a\u00020iH\u0087\b¢\u0006\u0004\bj\u0010kJ\u0010\u0010l\u001a\u00020mH\u0087\b¢\u0006\u0004\bn\u0010oJ\u000f\u0010p\u001a\u00020qH\u0016¢\u0006\u0004\br\u0010sJ\u0013\u0010t\u001a\u00020u2\b\u0010\t\u001a\u0004\u0018\u00010vHÖ\u0003J\t\u0010w\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003¨\u0006y"}, d2 = {"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "getData$annotations", "()V", "compareTo", "other", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "compareTo-WZ4Q5Ns", "(II)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "plus", "plus-7apg3OU", "plus-xj2QHRw", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "(IJ)J", "minus", "minus-7apg3OU", "minus-xj2QHRw", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "times", "times-7apg3OU", "times-xj2QHRw", "times-WZ4Q5Ns", "times-VKZWuLQ", "div", "div-7apg3OU", "div-xj2QHRw", "div-WZ4Q5Ns", "div-VKZWuLQ", "rem", "rem-7apg3OU", "rem-xj2QHRw", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "floorDiv", "floorDiv-7apg3OU", "floorDiv-xj2QHRw", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "mod", "mod-7apg3OU", "(IB)B", "mod-xj2QHRw", "(IS)S", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "inc", "inc-pVg5ArA", "dec", "dec-pVg5ArA", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-WZ4Q5Ns", "shl", "bitCount", "shl-pVg5ArA", "shr", "shr-pVg5ArA", "and", "and-WZ4Q5Ns", "or", "or-WZ4Q5Ns", "xor", "xor-WZ4Q5Ns", "inv", "inv-pVg5ArA", "toByte", "", "toByte-impl", "(I)B", "toShort", "", "toShort-impl", "(I)S", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toUByte", "toUByte-w2LRezQ", "toUShort", "toUShort-Mh2AYeg", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toFloat", "", "toFloat-impl", "(I)F", "toDouble", "", "toDouble-impl", "(I)D", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "equals", "", "", "hashCode", "Companion", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
@JvmInline
@WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class UInt implements Comparable {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final int MAX_VALUE = -1;
    public static final int MIN_VALUE = 0;
    public static final int SIZE_BITS = 32;
    public static final int SIZE_BYTES = 4;
    private final int data;

    public static final /* synthetic */ UInt box-impl(int i) {
        return new UInt(i);
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    public static int constructor-impl(int i) {
        return i;
    }

    public static boolean equals-impl(int i, Object obj) {
        return (obj instanceof UInt) && i == ((UInt) obj).getData();
    }

    public static final boolean equals-impl0(int i, int i2) {
        return i == i2;
    }

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    public static int hashCode-impl(int i) {
        return i;
    }

    @InlineOnly
    private static final byte toByte-impl(int i) {
        return (byte) i;
    }

    @InlineOnly
    private static final int toInt-impl(int i) {
        return i;
    }

    @InlineOnly
    private static final long toLong-impl(int i) {
        return i & 4294967295L;
    }

    @InlineOnly
    private static final short toShort-impl(int i) {
        return (short) i;
    }

    @InlineOnly
    private static final int toUInt-pVg5ArA(int i) {
        return i;
    }

    public boolean equals(Object other) {
        return equals-impl(this.data, other);
    }

    public int hashCode() {
        return hashCode-impl(this.data);
    }

    /* renamed from: unbox-impl, reason: from getter */
    public final /* synthetic */ int getData() {
        return this.data;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return UnsignedKt.uintCompare(getData(), ((UInt) obj).getData());
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    private /* synthetic */ UInt(int i) {
        this.data = i;
    }

    /* compiled from: UInt.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlin/UInt$Companion;", "", "<init>", "()V", "MIN_VALUE", "Lkotlin/UInt;", "I", "MAX_VALUE", "SIZE_BYTES", "", "SIZE_BITS", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @InlineOnly
    private static final int compareTo-7apg3OU(int i, byte b) {
        return UInt$$ExternalSyntheticBackport3.m(i, constructor-impl(b & 255));
    }

    @InlineOnly
    private static final int compareTo-xj2QHRw(int i, short s) {
        return UInt$$ExternalSyntheticBackport6.m(i, constructor-impl(s & 65535));
    }

    @InlineOnly
    private int compareTo-WZ4Q5Ns(int i) {
        return UnsignedKt.uintCompare(getData(), i);
    }

    @InlineOnly
    private static int compareTo-WZ4Q5Ns(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2);
    }

    @InlineOnly
    private static final int compareTo-VKZWuLQ(int i, long j) {
        return UInt$$ExternalSyntheticBackport2.m(ULong.constructor-impl(i & 4294967295L), j);
    }

    @InlineOnly
    private static final int plus-7apg3OU(int i, byte b) {
        return constructor-impl(i + constructor-impl(b & 255));
    }

    @InlineOnly
    private static final int plus-xj2QHRw(int i, short s) {
        return constructor-impl(i + constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int plus-WZ4Q5Ns(int i, int i2) {
        return constructor-impl(i + i2);
    }

    @InlineOnly
    private static final long plus-VKZWuLQ(int i, long j) {
        return ULong.constructor-impl(ULong.constructor-impl(i & 4294967295L) + j);
    }

    @InlineOnly
    private static final int minus-7apg3OU(int i, byte b) {
        return constructor-impl(i - constructor-impl(b & 255));
    }

    @InlineOnly
    private static final int minus-xj2QHRw(int i, short s) {
        return constructor-impl(i - constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int minus-WZ4Q5Ns(int i, int i2) {
        return constructor-impl(i - i2);
    }

    @InlineOnly
    private static final long minus-VKZWuLQ(int i, long j) {
        return ULong.constructor-impl(ULong.constructor-impl(i & 4294967295L) - j);
    }

    @InlineOnly
    private static final int times-7apg3OU(int i, byte b) {
        return constructor-impl(i * constructor-impl(b & 255));
    }

    @InlineOnly
    private static final int times-xj2QHRw(int i, short s) {
        return constructor-impl(i * constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int times-WZ4Q5Ns(int i, int i2) {
        return constructor-impl(i * i2);
    }

    @InlineOnly
    private static final long times-VKZWuLQ(int i, long j) {
        return ULong.constructor-impl(ULong.constructor-impl(i & 4294967295L) * j);
    }

    @InlineOnly
    private static final int div-7apg3OU(int i, byte b) {
        return UInt$$ExternalSyntheticBackport1.m(i, constructor-impl(b & 255));
    }

    @InlineOnly
    private static final int div-xj2QHRw(int i, short s) {
        return UInt$$ExternalSyntheticBackport1.m(i, constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int div-WZ4Q5Ns(int i, int i2) {
        return UnsignedKt.uintDivide-J1ME1BU(i, i2);
    }

    @InlineOnly
    private static final long div-VKZWuLQ(int i, long j) {
        return UInt$$ExternalSyntheticBackport0.m(ULong.constructor-impl(i & 4294967295L), j);
    }

    @InlineOnly
    private static final int rem-7apg3OU(int i, byte b) {
        return UInt$$ExternalSyntheticBackport5.m(i, constructor-impl(b & 255));
    }

    @InlineOnly
    private static final int rem-xj2QHRw(int i, short s) {
        return UInt$$ExternalSyntheticBackport5.m(i, constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int rem-WZ4Q5Ns(int i, int i2) {
        return UnsignedKt.uintRemainder-J1ME1BU(i, i2);
    }

    @InlineOnly
    private static final long rem-VKZWuLQ(int i, long j) {
        return UInt$$ExternalSyntheticBackport7.m(ULong.constructor-impl(i & 4294967295L), j);
    }

    @InlineOnly
    private static final int floorDiv-7apg3OU(int i, byte b) {
        return UInt$$ExternalSyntheticBackport1.m(i, constructor-impl(b & 255));
    }

    @InlineOnly
    private static final int floorDiv-xj2QHRw(int i, short s) {
        return UInt$$ExternalSyntheticBackport1.m(i, constructor-impl(s & 65535));
    }

    @InlineOnly
    private static final int floorDiv-WZ4Q5Ns(int i, int i2) {
        return UInt$$ExternalSyntheticBackport1.m(i, i2);
    }

    @InlineOnly
    private static final long floorDiv-VKZWuLQ(int i, long j) {
        return UInt$$ExternalSyntheticBackport0.m(ULong.constructor-impl(i & 4294967295L), j);
    }

    @InlineOnly
    private static final byte mod-7apg3OU(int i, byte b) {
        return UByte.constructor-impl((byte) UInt$$ExternalSyntheticBackport5.m(i, constructor-impl(b & 255)));
    }

    @InlineOnly
    private static final short mod-xj2QHRw(int i, short s) {
        return UShort.constructor-impl((short) UInt$$ExternalSyntheticBackport5.m(i, constructor-impl(s & 65535)));
    }

    @InlineOnly
    private static final int mod-WZ4Q5Ns(int i, int i2) {
        return UInt$$ExternalSyntheticBackport5.m(i, i2);
    }

    @InlineOnly
    private static final long mod-VKZWuLQ(int i, long j) {
        return UInt$$ExternalSyntheticBackport7.m(ULong.constructor-impl(i & 4294967295L), j);
    }

    @InlineOnly
    private static final int inc-pVg5ArA(int i) {
        return constructor-impl(i + 1);
    }

    @InlineOnly
    private static final int dec-pVg5ArA(int i) {
        return constructor-impl(i - 1);
    }

    @InlineOnly
    private static final UIntRange rangeTo-WZ4Q5Ns(int i, int i2) {
        return new UIntRange(i, i2, null);
    }

    @SinceKotlin(version = "1.9")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final UIntRange rangeUntil-WZ4Q5Ns(int i, int i2) {
        return URangesKt.until-J1ME1BU(i, i2);
    }

    @InlineOnly
    private static final int shl-pVg5ArA(int i, int i2) {
        return constructor-impl(i << i2);
    }

    @InlineOnly
    private static final int shr-pVg5ArA(int i, int i2) {
        return constructor-impl(i >>> i2);
    }

    @InlineOnly
    private static final int and-WZ4Q5Ns(int i, int i2) {
        return constructor-impl(i & i2);
    }

    @InlineOnly
    private static final int or-WZ4Q5Ns(int i, int i2) {
        return constructor-impl(i | i2);
    }

    @InlineOnly
    private static final int xor-WZ4Q5Ns(int i, int i2) {
        return constructor-impl(i ^ i2);
    }

    @InlineOnly
    private static final int inv-pVg5ArA(int i) {
        return constructor-impl(i ^ (-1));
    }

    @InlineOnly
    private static final byte toUByte-w2LRezQ(int i) {
        return UByte.constructor-impl((byte) i);
    }

    @InlineOnly
    private static final short toUShort-Mh2AYeg(int i) {
        return UShort.constructor-impl((short) i);
    }

    @InlineOnly
    private static final long toULong-s-VKNKU(int i) {
        return ULong.constructor-impl(i & 4294967295L);
    }

    @InlineOnly
    private static final float toFloat-impl(int i) {
        return (float) UnsignedKt.uintToDouble(i);
    }

    @InlineOnly
    private static final double toDouble-impl(int i) {
        return UnsignedKt.uintToDouble(i);
    }

    @NotNull
    public static String toString-impl(int i) {
        return String.valueOf(i & 4294967295L);
    }

    @NotNull
    public String toString() {
        return toString-impl(this.data);
    }
}
