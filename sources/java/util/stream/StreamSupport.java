package java.util.stream;

import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.DoublePipeline;
import java.util.stream.IntPipeline;
import java.util.stream.LongPipeline;
import java.util.stream.ReferencePipeline;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class StreamSupport {
    private StreamSupport() {
    }

    public static Stream stream(Spliterator spliterator, boolean z) {
        spliterator.getClass();
        return new ReferencePipeline.Head(spliterator, StreamOpFlag.fromCharacteristics(spliterator), z);
    }

    public static Stream stream(Supplier supplier, int i, boolean z) {
        supplier.getClass();
        return new ReferencePipeline.Head(supplier, StreamOpFlag.fromCharacteristics(i), z);
    }

    public static IntStream intStream(Spliterator.OfInt ofInt, boolean z) {
        return new IntPipeline.Head(ofInt, StreamOpFlag.fromCharacteristics(ofInt), z);
    }

    public static IntStream intStream(Supplier supplier, int i, boolean z) {
        return new IntPipeline.Head(supplier, StreamOpFlag.fromCharacteristics(i), z);
    }

    public static LongStream longStream(Spliterator.OfLong ofLong, boolean z) {
        return new LongPipeline.Head(ofLong, StreamOpFlag.fromCharacteristics(ofLong), z);
    }

    public static LongStream longStream(Supplier supplier, int i, boolean z) {
        return new LongPipeline.Head(supplier, StreamOpFlag.fromCharacteristics(i), z);
    }

    public static DoubleStream doubleStream(Spliterator.OfDouble ofDouble, boolean z) {
        return new DoublePipeline.Head(ofDouble, StreamOpFlag.fromCharacteristics(ofDouble), z);
    }

    public static DoubleStream doubleStream(Supplier supplier, int i, boolean z) {
        return new DoublePipeline.Head(supplier, StreamOpFlag.fromCharacteristics(i), z);
    }
}
