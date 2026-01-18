package kotlin.streams.jdk8;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

/* compiled from: Streams.kt */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001*\u00020\u0005H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0007H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\b0\u0001*\u00020\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f*\u00020\u0005H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f*\u00020\u0007H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f*\u00020\tH\u0007¨\u0006\r"}, d2 = {"asSequence", "Lkotlin/sequences/Sequence;", "T", "Ljava/util/stream/Stream;", "", "Ljava/util/stream/IntStream;", "", "Ljava/util/stream/LongStream;", "", "Ljava/util/stream/DoubleStream;", "asStream", "toList", "", "kotlin-stdlib-jdk8"}, k = 2, mv = {2, 1, 0}, pn = "kotlin.streams", xi = 48)
@JvmName(name = "StreamsKt")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class StreamsKt {
    public static /* synthetic */ Spliterator $r8$lambda$jBRaOqrA5A8aZRswyILQO-7uCm8(Sequence sequence) {
        return asStream$lambda$4(sequence);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Sequence asSequence(@NotNull Stream stream) {
        Intrinsics.checkNotNullParameter(stream, "<this>");
        return new StreamsKt$asSequence$$inlined$Sequence$1(stream);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Sequence asSequence(@NotNull IntStream intStream) {
        Intrinsics.checkNotNullParameter(intStream, "<this>");
        return new StreamsKt$asSequence$$inlined$Sequence$2(intStream);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Sequence asSequence(@NotNull LongStream longStream) {
        Intrinsics.checkNotNullParameter(longStream, "<this>");
        return new StreamsKt$asSequence$$inlined$Sequence$3(longStream);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Sequence asSequence(@NotNull DoubleStream doubleStream) {
        Intrinsics.checkNotNullParameter(doubleStream, "<this>");
        return new StreamsKt$asSequence$$inlined$Sequence$4(doubleStream);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Stream asStream(@NotNull Sequence sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Stream stream = StreamSupport.stream(new StreamsKt$$ExternalSyntheticLambda0(sequence), 16, false);
        Intrinsics.checkNotNullExpressionValue(stream, "stream(...)");
        return stream;
    }

    private static final Spliterator asStream$lambda$4(Sequence sequence) {
        return Spliterators.spliteratorUnknownSize(sequence.iterator(), 16);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List toList(@NotNull Stream stream) {
        Intrinsics.checkNotNullParameter(stream, "<this>");
        Object collect = stream.collect(Collectors.toList());
        Intrinsics.checkNotNullExpressionValue(collect, "collect(...)");
        return (List) collect;
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List toList(@NotNull IntStream intStream) {
        Intrinsics.checkNotNullParameter(intStream, "<this>");
        int[] array = intStream.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        return ArraysKt.asList(array);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List toList(@NotNull LongStream longStream) {
        Intrinsics.checkNotNullParameter(longStream, "<this>");
        long[] array = longStream.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        return ArraysKt.asList(array);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List toList(@NotNull DoubleStream doubleStream) {
        Intrinsics.checkNotNullParameter(doubleStream, "<this>");
        double[] array = doubleStream.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        return ArraysKt.asList(array);
    }
}
