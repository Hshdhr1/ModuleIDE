package java.util.stream;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class Collectors {
    static final Set CH_CONCURRENT_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
    static final Set CH_CONCURRENT_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED));
    static final Set CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    static final Set CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
    static final Set CH_NOID = Collections.EMPTY_SET;
    static final Set CH_UNORDERED_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));

    static /* bridge */ /* synthetic */ Function -$$Nest$smcastingIdentity() {
        return castingIdentity();
    }

    static /* synthetic */ Object lambda$castingIdentity$2(Object obj) {
        return obj;
    }

    static /* synthetic */ long lambda$counting$17(Object obj) {
        return 1L;
    }

    private Collectors() {
    }

    private static IllegalStateException duplicateKeyException(Object obj, Object obj2, Object obj3) {
        return new IllegalStateException(String.format("Duplicate key %s (attempted merging values %s and %s)", obj, obj2, obj3));
    }

    private static BinaryOperator uniqKeysMapMerger() {
        return new Collectors$$ExternalSyntheticLambda5();
    }

    static /* synthetic */ Map lambda$uniqKeysMapMerger$0(Map map, Map map2) {
        for (Map.Entry entry : map2.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            value.getClass();
            Object putIfAbsent = map.putIfAbsent(key, value);
            if (putIfAbsent != null) {
                throw duplicateKeyException(key, putIfAbsent, value);
            }
        }
        return map;
    }

    private static BiConsumer uniqKeysMapAccumulator(Function function, Function function2) {
        return new Collectors$$ExternalSyntheticLambda6(function, function2);
    }

    static /* synthetic */ void lambda$uniqKeysMapAccumulator$1(Function function, Function function2, Map map, Object obj) {
        Object apply = function.apply(obj);
        Object apply2 = function2.apply(obj);
        apply2.getClass();
        Object putIfAbsent = map.putIfAbsent(apply, apply2);
        if (putIfAbsent != null) {
            throw duplicateKeyException(apply, putIfAbsent, apply2);
        }
    }

    private static Function castingIdentity() {
        return new Collectors$$ExternalSyntheticLambda70();
    }

    static class CollectorImpl implements Collector {
        private final BiConsumer accumulator;
        private final Set characteristics;
        private final BinaryOperator combiner;
        private final Function finisher;
        private final Supplier supplier;

        CollectorImpl(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Function function, Set set) {
            this.supplier = supplier;
            this.accumulator = biConsumer;
            this.combiner = binaryOperator;
            this.finisher = function;
            this.characteristics = set;
        }

        CollectorImpl(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Set set) {
            this(supplier, biConsumer, binaryOperator, Collectors.-$$Nest$smcastingIdentity(), set);
        }

        public BiConsumer accumulator() {
            return this.accumulator;
        }

        public Supplier supplier() {
            return this.supplier;
        }

        public BinaryOperator combiner() {
            return this.combiner;
        }

        public Function finisher() {
            return this.finisher;
        }

        public Set characteristics() {
            return this.characteristics;
        }
    }

    public static Collector toCollection(Supplier supplier) {
        return new CollectorImpl(supplier, new Collectors$$ExternalSyntheticLambda24(), new Collectors$$ExternalSyntheticLambda25(), CH_ID);
    }

    static /* synthetic */ Collection lambda$toCollection$3(Collection collection, Collection collection2) {
        collection.addAll(collection2);
        return collection;
    }

    public static Collector toList() {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda26(), new Collectors$$ExternalSyntheticLambda27(), new Collectors$$ExternalSyntheticLambda69(), CH_ID);
    }

    static /* synthetic */ List lambda$toList$4(List list, List list2) {
        list.addAll(list2);
        return list;
    }

    public static Collector toUnmodifiableList() {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda26(), new Collectors$$ExternalSyntheticLambda27(), new Collectors$$ExternalSyntheticLambda28(), new Collectors$$ExternalSyntheticLambda29(), CH_NOID);
    }

    static /* synthetic */ List lambda$toUnmodifiableList$5(List list, List list2) {
        list.addAll(list2);
        return list;
    }

    static /* synthetic */ List lambda$toUnmodifiableList$6(List list) {
        return Collectors$$ExternalSyntheticBackport3.m(list.toArray());
    }

    public static Collector toSet() {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda48(), new Collectors$$ExternalSyntheticLambda49(), new Collectors$$ExternalSyntheticLambda77(), CH_UNORDERED_ID);
    }

    static /* synthetic */ Set lambda$toSet$7(Set set, Set set2) {
        if (set.size() < set2.size()) {
            set2.addAll(set);
            return set2;
        }
        set.addAll(set2);
        return set;
    }

    public static Collector toUnmodifiableSet() {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda48(), new Collectors$$ExternalSyntheticLambda49(), new Collectors$$ExternalSyntheticLambda50(), new Collectors$$ExternalSyntheticLambda51(), CH_UNORDERED_NOID);
    }

    static /* synthetic */ Set lambda$toUnmodifiableSet$8(Set set, Set set2) {
        if (set.size() < set2.size()) {
            set2.addAll(set);
            return set2;
        }
        set.addAll(set2);
        return set;
    }

    static /* synthetic */ Set lambda$toUnmodifiableSet$9(Set set) {
        return Collectors$$ExternalSyntheticBackport1.m(set.toArray());
    }

    public static Collector joining() {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda96(), new Collectors$$ExternalSyntheticLambda97(), new Collectors$$ExternalSyntheticLambda98(), new Collectors$$ExternalSyntheticLambda99(), CH_NOID);
    }

    static /* synthetic */ StringBuilder lambda$joining$10(StringBuilder sb, StringBuilder sb2) {
        sb.append(sb2);
        return sb;
    }

    public static Collector joining(CharSequence charSequence) {
        return joining(charSequence, "", "");
    }

    public static Collector joining(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda73(charSequence, charSequence2, charSequence3), new Collectors$$ExternalSyntheticLambda74(), new Collectors$$ExternalSyntheticLambda75(), new Collectors$$ExternalSyntheticLambda76(), CH_NOID);
    }

    static /* synthetic */ StringJoiner lambda$joining$11(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return new StringJoiner(charSequence, charSequence2, charSequence3);
    }

    private static BinaryOperator mapMerger(BinaryOperator binaryOperator) {
        return new Collectors$$ExternalSyntheticLambda95(binaryOperator);
    }

    static /* synthetic */ Map lambda$mapMerger$12(BinaryOperator binaryOperator, Map map, Map map2) {
        for (Map.Entry entry : map2.entrySet()) {
            map.merge(entry.getKey(), entry.getValue(), binaryOperator);
        }
        return map;
    }

    public static Collector mapping(Function function, Collector collector) {
        return new CollectorImpl(collector.supplier(), new Collectors$$ExternalSyntheticLambda46(collector.accumulator(), function), collector.combiner(), collector.finisher(), collector.characteristics());
    }

    static /* synthetic */ void lambda$mapping$13(BiConsumer biConsumer, Function function, Object obj, Object obj2) {
        biConsumer.accept(obj, function.apply(obj2));
    }

    public static Collector flatMapping(Function function, Collector collector) {
        return new CollectorImpl(collector.supplier(), new Collectors$$ExternalSyntheticLambda82(function, collector.accumulator()), collector.combiner(), collector.finisher(), collector.characteristics());
    }

    static /* synthetic */ void lambda$flatMapping$15(Function function, BiConsumer biConsumer, Object obj, Object obj2) {
        Stream stream = (Stream) function.apply(obj2);
        if (stream != null) {
            try {
                ((Stream) stream.sequential()).forEach(new Collectors$$ExternalSyntheticLambda19(biConsumer, obj));
            } catch (Throwable th) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Throwable th2) {
                        Collectors$$ExternalSyntheticBackport2.m(th, th2);
                    }
                }
                throw th;
            }
        }
        if (stream != null) {
            stream.close();
        }
    }

    static /* synthetic */ void lambda$flatMapping$14(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
    }

    public static Collector filtering(Predicate predicate, Collector collector) {
        return new CollectorImpl(collector.supplier(), new Collectors$$ExternalSyntheticLambda72(predicate, collector.accumulator()), collector.combiner(), collector.finisher(), collector.characteristics());
    }

    static /* synthetic */ void lambda$filtering$16(Predicate predicate, BiConsumer biConsumer, Object obj, Object obj2) {
        if (predicate.test(obj2)) {
            biConsumer.accept(obj, obj2);
        }
    }

    public static Collector collectingAndThen(Collector collector, Function function) {
        Set characteristics = collector.characteristics();
        if (characteristics.contains(Collector.Characteristics.IDENTITY_FINISH)) {
            if (characteristics.size() == 1) {
                characteristics = CH_NOID;
            } else {
                EnumSet copyOf = EnumSet.copyOf(characteristics);
                copyOf.remove(Collector.Characteristics.IDENTITY_FINISH);
                characteristics = Collections.unmodifiableSet(copyOf);
            }
        }
        return new CollectorImpl(collector.supplier(), collector.accumulator(), collector.combiner(), collector.finisher().andThen(function), characteristics);
    }

    public static Collector counting() {
        return summingLong(new Collectors$$ExternalSyntheticLambda80());
    }

    public static Collector minBy(Comparator comparator) {
        return reducing(BinaryOperator.-CC.minBy(comparator));
    }

    public static Collector maxBy(Comparator comparator) {
        return reducing(BinaryOperator.-CC.maxBy(comparator));
    }

    public static Collector summingInt(ToIntFunction toIntFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda15(), new Collectors$$ExternalSyntheticLambda16(toIntFunction), new Collectors$$ExternalSyntheticLambda17(), new Collectors$$ExternalSyntheticLambda18(), CH_NOID);
    }

    static /* synthetic */ int[] lambda$summingInt$18() {
        return new int[1];
    }

    static /* synthetic */ void lambda$summingInt$19(ToIntFunction toIntFunction, int[] iArr, Object obj) {
        iArr[0] = iArr[0] + toIntFunction.applyAsInt(obj);
    }

    static /* synthetic */ int[] lambda$summingInt$20(int[] iArr, int[] iArr2) {
        iArr[0] = iArr[0] + iArr2[0];
        return iArr;
    }

    static /* synthetic */ Integer lambda$summingInt$21(int[] iArr) {
        return Integer.valueOf(iArr[0]);
    }

    public static Collector summingLong(ToLongFunction toLongFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda83(), new Collectors$$ExternalSyntheticLambda84(toLongFunction), new Collectors$$ExternalSyntheticLambda85(), new Collectors$$ExternalSyntheticLambda86(), CH_NOID);
    }

    static /* synthetic */ long[] lambda$summingLong$22() {
        return new long[1];
    }

    static /* synthetic */ void lambda$summingLong$23(ToLongFunction toLongFunction, long[] jArr, Object obj) {
        jArr[0] = jArr[0] + toLongFunction.applyAsLong(obj);
    }

    static /* synthetic */ long[] lambda$summingLong$24(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        return jArr;
    }

    static /* synthetic */ Long lambda$summingLong$25(long[] jArr) {
        return Long.valueOf(jArr[0]);
    }

    public static Collector summingDouble(ToDoubleFunction toDoubleFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda60(), new Collectors$$ExternalSyntheticLambda61(toDoubleFunction), new Collectors$$ExternalSyntheticLambda62(), new Collectors$$ExternalSyntheticLambda63(), CH_NOID);
    }

    static /* synthetic */ double[] lambda$summingDouble$26() {
        return new double[3];
    }

    static /* synthetic */ void lambda$summingDouble$27(ToDoubleFunction toDoubleFunction, double[] dArr, Object obj) {
        double applyAsDouble = toDoubleFunction.applyAsDouble(obj);
        sumWithCompensation(dArr, applyAsDouble);
        dArr[2] = dArr[2] + applyAsDouble;
    }

    static /* synthetic */ double[] lambda$summingDouble$28(double[] dArr, double[] dArr2) {
        sumWithCompensation(dArr, dArr2[0]);
        dArr[2] = dArr[2] + dArr2[2];
        return sumWithCompensation(dArr, dArr2[1]);
    }

    static /* synthetic */ Double lambda$summingDouble$29(double[] dArr) {
        return Double.valueOf(computeFinalSum(dArr));
    }

    static double[] sumWithCompensation(double[] dArr, double d) {
        double d2 = d - dArr[1];
        double d3 = dArr[0];
        double d4 = d3 + d2;
        dArr[1] = (d4 - d3) - d2;
        dArr[0] = d4;
        return dArr;
    }

    static double computeFinalSum(double[] dArr) {
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        return (Double.isNaN(d) && Double.isInfinite(d2)) ? d2 : d;
    }

    public static Collector averagingInt(ToIntFunction toIntFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda41(), new Collectors$$ExternalSyntheticLambda42(toIntFunction), new Collectors$$ExternalSyntheticLambda43(), new Collectors$$ExternalSyntheticLambda44(), CH_NOID);
    }

    static /* synthetic */ long[] lambda$averagingInt$30() {
        return new long[2];
    }

    static /* synthetic */ void lambda$averagingInt$31(ToIntFunction toIntFunction, long[] jArr, Object obj) {
        jArr[0] = jArr[0] + toIntFunction.applyAsInt(obj);
        jArr[1] = jArr[1] + 1;
    }

    static /* synthetic */ long[] lambda$averagingInt$32(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
        return jArr;
    }

    static /* synthetic */ Double lambda$averagingInt$33(long[] jArr) {
        double d;
        long j = jArr[1];
        if (j == 0) {
            d = 0.0d;
        } else {
            double d2 = jArr[0];
            double d3 = j;
            Double.isNaN(d2);
            Double.isNaN(d3);
            d = d2 / d3;
        }
        return Double.valueOf(d);
    }

    public static Collector averagingLong(ToLongFunction toLongFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda91(), new Collectors$$ExternalSyntheticLambda92(toLongFunction), new Collectors$$ExternalSyntheticLambda93(), new Collectors$$ExternalSyntheticLambda94(), CH_NOID);
    }

    static /* synthetic */ long[] lambda$averagingLong$34() {
        return new long[2];
    }

    static /* synthetic */ void lambda$averagingLong$35(ToLongFunction toLongFunction, long[] jArr, Object obj) {
        jArr[0] = jArr[0] + toLongFunction.applyAsLong(obj);
        jArr[1] = jArr[1] + 1;
    }

    static /* synthetic */ long[] lambda$averagingLong$36(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] + jArr2[0];
        jArr[1] = jArr[1] + jArr2[1];
        return jArr;
    }

    static /* synthetic */ Double lambda$averagingLong$37(long[] jArr) {
        double d;
        long j = jArr[1];
        if (j == 0) {
            d = 0.0d;
        } else {
            double d2 = jArr[0];
            double d3 = j;
            Double.isNaN(d2);
            Double.isNaN(d3);
            d = d2 / d3;
        }
        return Double.valueOf(d);
    }

    public static Collector averagingDouble(ToDoubleFunction toDoubleFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda37(), new Collectors$$ExternalSyntheticLambda38(toDoubleFunction), new Collectors$$ExternalSyntheticLambda39(), new Collectors$$ExternalSyntheticLambda40(), CH_NOID);
    }

    static /* synthetic */ double[] lambda$averagingDouble$38() {
        return new double[4];
    }

    static /* synthetic */ void lambda$averagingDouble$39(ToDoubleFunction toDoubleFunction, double[] dArr, Object obj) {
        double applyAsDouble = toDoubleFunction.applyAsDouble(obj);
        sumWithCompensation(dArr, applyAsDouble);
        dArr[2] = dArr[2] + 1.0d;
        dArr[3] = dArr[3] + applyAsDouble;
    }

    static /* synthetic */ double[] lambda$averagingDouble$40(double[] dArr, double[] dArr2) {
        sumWithCompensation(dArr, dArr2[0]);
        sumWithCompensation(dArr, dArr2[1]);
        dArr[2] = dArr[2] + dArr2[2];
        dArr[3] = dArr[3] + dArr2[3];
        return dArr;
    }

    static /* synthetic */ Double lambda$averagingDouble$41(double[] dArr) {
        return Double.valueOf(dArr[2] != 0.0d ? computeFinalSum(dArr) / dArr[2] : 0.0d);
    }

    public static Collector reducing(Object obj, BinaryOperator binaryOperator) {
        return new CollectorImpl(boxSupplier(obj), new Collectors$$ExternalSyntheticLambda57(binaryOperator), new Collectors$$ExternalSyntheticLambda58(binaryOperator), new Collectors$$ExternalSyntheticLambda59(), CH_NOID);
    }

    static /* synthetic */ void lambda$reducing$42(BinaryOperator binaryOperator, Object[] objArr, Object obj) {
        objArr[0] = binaryOperator.apply(objArr[0], obj);
    }

    static /* synthetic */ Object[] lambda$reducing$43(BinaryOperator binaryOperator, Object[] objArr, Object[] objArr2) {
        objArr[0] = binaryOperator.apply(objArr[0], objArr2[0]);
        return objArr;
    }

    static /* synthetic */ Object lambda$reducing$44(Object[] objArr) {
        return objArr[0];
    }

    private static Supplier boxSupplier(Object obj) {
        return new Collectors$$ExternalSyntheticLambda23(obj);
    }

    static /* synthetic */ Object[] lambda$boxSupplier$45(Object obj) {
        return new Object[]{obj};
    }

    class 1OptionalBox implements Consumer {
        final /* synthetic */ BinaryOperator val$op;
        Object value = null;
        boolean present = false;

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer.-CC.$default$andThen(this, consumer);
        }

        1OptionalBox(BinaryOperator binaryOperator) {
            this.val$op = binaryOperator;
        }

        public void accept(Object obj) {
            if (this.present) {
                this.value = this.val$op.apply(this.value, obj);
            } else {
                this.value = obj;
                this.present = true;
            }
        }
    }

    public static Collector reducing(BinaryOperator binaryOperator) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda87(binaryOperator), new Collectors$$ExternalSyntheticLambda88(), new Collectors$$ExternalSyntheticLambda89(), new Collectors$$ExternalSyntheticLambda90(), CH_NOID);
    }

    static /* synthetic */ 1OptionalBox lambda$reducing$46(BinaryOperator binaryOperator) {
        return new 1OptionalBox(binaryOperator);
    }

    static /* synthetic */ 1OptionalBox lambda$reducing$47(1OptionalBox r1, 1OptionalBox r2) {
        if (r2.present) {
            r1.accept(r2.value);
        }
        return r1;
    }

    static /* synthetic */ Optional lambda$reducing$48(1OptionalBox r0) {
        return Optional.ofNullable(r0.value);
    }

    public static Collector reducing(Object obj, Function function, BinaryOperator binaryOperator) {
        return new CollectorImpl(boxSupplier(obj), new Collectors$$ExternalSyntheticLambda9(binaryOperator, function), new Collectors$$ExternalSyntheticLambda10(binaryOperator), new Collectors$$ExternalSyntheticLambda11(), CH_NOID);
    }

    static /* synthetic */ void lambda$reducing$49(BinaryOperator binaryOperator, Function function, Object[] objArr, Object obj) {
        objArr[0] = binaryOperator.apply(objArr[0], function.apply(obj));
    }

    static /* synthetic */ Object[] lambda$reducing$50(BinaryOperator binaryOperator, Object[] objArr, Object[] objArr2) {
        objArr[0] = binaryOperator.apply(objArr[0], objArr2[0]);
        return objArr;
    }

    static /* synthetic */ Object lambda$reducing$51(Object[] objArr) {
        return objArr[0];
    }

    public static Collector groupingBy(Function function) {
        return groupingBy(function, toList());
    }

    public static Collector groupingBy(Function function, Collector collector) {
        return groupingBy(function, new Collectors$$ExternalSyntheticLambda81(), collector);
    }

    public static Collector groupingBy(Function function, Supplier supplier, Collector collector) {
        Collectors$$ExternalSyntheticLambda64 collectors$$ExternalSyntheticLambda64 = new Collectors$$ExternalSyntheticLambda64(function, collector.supplier(), collector.accumulator());
        BinaryOperator mapMerger = mapMerger(collector.combiner());
        if (collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new CollectorImpl(supplier, collectors$$ExternalSyntheticLambda64, mapMerger, CH_ID);
        }
        return new CollectorImpl(supplier, collectors$$ExternalSyntheticLambda64, mapMerger, new Collectors$$ExternalSyntheticLambda65(collector.finisher()), CH_NOID);
    }

    static /* synthetic */ void lambda$groupingBy$53(Function function, Supplier supplier, BiConsumer biConsumer, Map map, Object obj) {
        biConsumer.accept(map.computeIfAbsent(Collectors$$ExternalSyntheticBackport0.m(function.apply(obj), "element cannot be mapped to a null key"), new Collectors$$ExternalSyntheticLambda71(supplier)), obj);
    }

    static /* synthetic */ Object lambda$groupingBy$52(Supplier supplier, Object obj) {
        return supplier.get();
    }

    static /* synthetic */ Object lambda$groupingBy$54(Function function, Object obj, Object obj2) {
        return function.apply(obj2);
    }

    static /* synthetic */ Map lambda$groupingBy$55(Function function, Map map) {
        map.replaceAll(new Collectors$$ExternalSyntheticLambda47(function));
        return map;
    }

    public static Collector groupingByConcurrent(Function function) {
        return groupingByConcurrent(function, new Collectors$$ExternalSyntheticLambda7(), toList());
    }

    public static Collector groupingByConcurrent(Function function, Collector collector) {
        return groupingByConcurrent(function, new Collectors$$ExternalSyntheticLambda7(), collector);
    }

    public static Collector groupingByConcurrent(Function function, Supplier supplier, Collector collector) {
        BiConsumer collectors$$ExternalSyntheticLambda13;
        Supplier supplier2 = collector.supplier();
        BiConsumer accumulator = collector.accumulator();
        BinaryOperator mapMerger = mapMerger(collector.combiner());
        if (collector.characteristics().contains(Collector.Characteristics.CONCURRENT)) {
            collectors$$ExternalSyntheticLambda13 = new Collectors$$ExternalSyntheticLambda12(function, supplier2, accumulator);
        } else {
            collectors$$ExternalSyntheticLambda13 = new Collectors$$ExternalSyntheticLambda13(function, supplier2, accumulator);
        }
        BiConsumer biConsumer = collectors$$ExternalSyntheticLambda13;
        if (collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new CollectorImpl(supplier, biConsumer, mapMerger, CH_CONCURRENT_ID);
        }
        return new CollectorImpl(supplier, biConsumer, mapMerger, new Collectors$$ExternalSyntheticLambda14(collector.finisher()), CH_CONCURRENT_NOID);
    }

    static /* synthetic */ void lambda$groupingByConcurrent$57(Function function, Supplier supplier, BiConsumer biConsumer, ConcurrentMap concurrentMap, Object obj) {
        biConsumer.accept(concurrentMap.computeIfAbsent(Collectors$$ExternalSyntheticBackport0.m(function.apply(obj), "element cannot be mapped to a null key"), new Collectors$$ExternalSyntheticLambda8(supplier)), obj);
    }

    static /* synthetic */ Object lambda$groupingByConcurrent$56(Supplier supplier, Object obj) {
        return supplier.get();
    }

    static /* synthetic */ void lambda$groupingByConcurrent$59(Function function, Supplier supplier, BiConsumer biConsumer, ConcurrentMap concurrentMap, Object obj) {
        Object computeIfAbsent = concurrentMap.computeIfAbsent(Collectors$$ExternalSyntheticBackport0.m(function.apply(obj), "element cannot be mapped to a null key"), new Collectors$$ExternalSyntheticLambda35(supplier));
        synchronized (computeIfAbsent) {
            biConsumer.accept(computeIfAbsent, obj);
        }
    }

    static /* synthetic */ Object lambda$groupingByConcurrent$58(Supplier supplier, Object obj) {
        return supplier.get();
    }

    static /* synthetic */ Object lambda$groupingByConcurrent$60(Function function, Object obj, Object obj2) {
        return function.apply(obj2);
    }

    static /* synthetic */ ConcurrentMap lambda$groupingByConcurrent$61(Function function, ConcurrentMap concurrentMap) {
        concurrentMap.replaceAll(new Collectors$$ExternalSyntheticLambda52(function));
        return concurrentMap;
    }

    public static Collector partitioningBy(Predicate predicate) {
        return partitioningBy(predicate, toList());
    }

    public static Collector partitioningBy(Predicate predicate, Collector collector) {
        Collectors$$ExternalSyntheticLambda53 collectors$$ExternalSyntheticLambda53 = new Collectors$$ExternalSyntheticLambda53(collector.accumulator(), predicate);
        Collectors$$ExternalSyntheticLambda54 collectors$$ExternalSyntheticLambda54 = new Collectors$$ExternalSyntheticLambda54(collector.combiner());
        Collectors$$ExternalSyntheticLambda55 collectors$$ExternalSyntheticLambda55 = new Collectors$$ExternalSyntheticLambda55(collector);
        if (collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new CollectorImpl(collectors$$ExternalSyntheticLambda55, collectors$$ExternalSyntheticLambda53, collectors$$ExternalSyntheticLambda54, CH_ID);
        }
        return new CollectorImpl(collectors$$ExternalSyntheticLambda55, collectors$$ExternalSyntheticLambda53, collectors$$ExternalSyntheticLambda54, new Collectors$$ExternalSyntheticLambda56(collector), CH_NOID);
    }

    static /* synthetic */ void lambda$partitioningBy$62(BiConsumer biConsumer, Predicate predicate, Partition partition, Object obj) {
        biConsumer.accept(predicate.test(obj) ? partition.forTrue : partition.forFalse, obj);
    }

    static /* synthetic */ Partition lambda$partitioningBy$63(BinaryOperator binaryOperator, Partition partition, Partition partition2) {
        return new Partition(binaryOperator.apply(partition.forTrue, partition2.forTrue), binaryOperator.apply(partition.forFalse, partition2.forFalse));
    }

    static /* synthetic */ Partition lambda$partitioningBy$64(Collector collector) {
        return new Partition(collector.supplier().get(), collector.supplier().get());
    }

    static /* synthetic */ Map lambda$partitioningBy$65(Collector collector, Partition partition) {
        return new Partition(collector.finisher().apply(partition.forTrue), collector.finisher().apply(partition.forFalse));
    }

    public static Collector toMap(Function function, Function function2) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda81(), uniqKeysMapAccumulator(function, function2), uniqKeysMapMerger(), CH_ID);
    }

    public static Collector toUnmodifiableMap(Function function, Function function2) {
        Collectors$$ExternalSyntheticBackport0.m(function, "keyMapper");
        Collectors$$ExternalSyntheticBackport0.m(function2, "valueMapper");
        return collectingAndThen(toMap(function, function2), new Collectors$$ExternalSyntheticLambda36());
    }

    static /* synthetic */ Map lambda$toUnmodifiableMap$66(Map map) {
        return Collectors$$ExternalSyntheticBackport4.m((Map.Entry[]) map.entrySet().toArray(new Map.Entry[0]));
    }

    public static Collector toMap(Function function, Function function2, BinaryOperator binaryOperator) {
        return toMap(function, function2, binaryOperator, new Collectors$$ExternalSyntheticLambda81());
    }

    public static Collector toUnmodifiableMap(Function function, Function function2, BinaryOperator binaryOperator) {
        Collectors$$ExternalSyntheticBackport0.m(function, "keyMapper");
        Collectors$$ExternalSyntheticBackport0.m(function2, "valueMapper");
        Collectors$$ExternalSyntheticBackport0.m(binaryOperator, "mergeFunction");
        return collectingAndThen(toMap(function, function2, binaryOperator, new Collectors$$ExternalSyntheticLambda78()), new Collectors$$ExternalSyntheticLambda79());
    }

    static /* synthetic */ Map lambda$toUnmodifiableMap$67(HashMap hashMap) {
        return Collectors$$ExternalSyntheticBackport4.m((Map.Entry[]) hashMap.entrySet().toArray(new Map.Entry[0]));
    }

    public static Collector toMap(Function function, Function function2, BinaryOperator binaryOperator, Supplier supplier) {
        return new CollectorImpl(supplier, new Collectors$$ExternalSyntheticLambda31(function, function2, binaryOperator), mapMerger(binaryOperator), CH_ID);
    }

    static /* synthetic */ void lambda$toMap$68(Function function, Function function2, BinaryOperator binaryOperator, Map map, Object obj) {
        map.merge(function.apply(obj), function2.apply(obj), binaryOperator);
    }

    public static Collector toConcurrentMap(Function function, Function function2) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda45(), uniqKeysMapAccumulator(function, function2), uniqKeysMapMerger(), CH_CONCURRENT_ID);
    }

    public static Collector toConcurrentMap(Function function, Function function2, BinaryOperator binaryOperator) {
        return toConcurrentMap(function, function2, binaryOperator, new Collectors$$ExternalSyntheticLambda7());
    }

    public static Collector toConcurrentMap(Function function, Function function2, BinaryOperator binaryOperator, Supplier supplier) {
        return new CollectorImpl(supplier, new Collectors$$ExternalSyntheticLambda30(function, function2, binaryOperator), mapMerger(binaryOperator), CH_CONCURRENT_ID);
    }

    static /* synthetic */ void lambda$toConcurrentMap$69(Function function, Function function2, BinaryOperator binaryOperator, ConcurrentMap concurrentMap, Object obj) {
        concurrentMap.merge(function.apply(obj), function2.apply(obj), binaryOperator);
    }

    public static Collector summarizingInt(ToIntFunction toIntFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda32(), new Collectors$$ExternalSyntheticLambda33(toIntFunction), new Collectors$$ExternalSyntheticLambda34(), CH_ID);
    }

    static /* synthetic */ void lambda$summarizingInt$70(ToIntFunction toIntFunction, IntSummaryStatistics intSummaryStatistics, Object obj) {
        intSummaryStatistics.accept(toIntFunction.applyAsInt(obj));
    }

    static /* synthetic */ IntSummaryStatistics lambda$summarizingInt$71(IntSummaryStatistics intSummaryStatistics, IntSummaryStatistics intSummaryStatistics2) {
        intSummaryStatistics.combine(intSummaryStatistics2);
        return intSummaryStatistics;
    }

    public static Collector summarizingLong(ToLongFunction toLongFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda66(), new Collectors$$ExternalSyntheticLambda67(toLongFunction), new Collectors$$ExternalSyntheticLambda68(), CH_ID);
    }

    static /* synthetic */ void lambda$summarizingLong$72(ToLongFunction toLongFunction, LongSummaryStatistics longSummaryStatistics, Object obj) {
        longSummaryStatistics.accept(toLongFunction.applyAsLong(obj));
    }

    static /* synthetic */ LongSummaryStatistics lambda$summarizingLong$73(LongSummaryStatistics longSummaryStatistics, LongSummaryStatistics longSummaryStatistics2) {
        longSummaryStatistics.combine(longSummaryStatistics2);
        return longSummaryStatistics;
    }

    public static Collector summarizingDouble(ToDoubleFunction toDoubleFunction) {
        return new CollectorImpl(new Collectors$$ExternalSyntheticLambda20(), new Collectors$$ExternalSyntheticLambda21(toDoubleFunction), new Collectors$$ExternalSyntheticLambda22(), CH_ID);
    }

    static /* synthetic */ void lambda$summarizingDouble$74(ToDoubleFunction toDoubleFunction, DoubleSummaryStatistics doubleSummaryStatistics, Object obj) {
        doubleSummaryStatistics.accept(toDoubleFunction.applyAsDouble(obj));
    }

    static /* synthetic */ DoubleSummaryStatistics lambda$summarizingDouble$75(DoubleSummaryStatistics doubleSummaryStatistics, DoubleSummaryStatistics doubleSummaryStatistics2) {
        doubleSummaryStatistics.combine(doubleSummaryStatistics2);
        return doubleSummaryStatistics;
    }

    private static final class Partition extends AbstractMap implements Map {
        final Object forFalse;
        final Object forTrue;

        public /* synthetic */ Object compute(Object obj, BiFunction biFunction) {
            return Map.-CC.$default$compute(this, obj, biFunction);
        }

        public /* synthetic */ Object computeIfAbsent(Object obj, Function function) {
            return Map.-CC.$default$computeIfAbsent(this, obj, function);
        }

        public /* synthetic */ Object computeIfPresent(Object obj, BiFunction biFunction) {
            return Map.-CC.$default$computeIfPresent(this, obj, biFunction);
        }

        public /* synthetic */ void forEach(BiConsumer biConsumer) {
            Map.-CC.$default$forEach(this, biConsumer);
        }

        public /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
            return Map.-CC.$default$getOrDefault(this, obj, obj2);
        }

        public /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
            return Map.-CC.$default$merge(this, obj, obj2, biFunction);
        }

        public /* synthetic */ Object putIfAbsent(Object obj, Object obj2) {
            return Map.-CC.$default$putIfAbsent(this, obj, obj2);
        }

        public /* synthetic */ boolean remove(Object obj, Object obj2) {
            return Map.-CC.$default$remove(this, obj, obj2);
        }

        public /* synthetic */ Object replace(Object obj, Object obj2) {
            return Map.-CC.$default$replace(this, obj, obj2);
        }

        public /* synthetic */ boolean replace(Object obj, Object obj2, Object obj3) {
            return Map.-CC.$default$replace(this, obj, obj2, obj3);
        }

        public /* synthetic */ void replaceAll(BiFunction biFunction) {
            Map.-CC.$default$replaceAll(this, biFunction);
        }

        Partition(Object obj, Object obj2) {
            this.forTrue = obj;
            this.forFalse = obj2;
        }

        class 1 extends AbstractSet {
            public int size() {
                return 2;
            }

            1() {
            }

            public Iterator iterator() {
                return Collectors$Partition$1$$ExternalSyntheticBackport0.m(new AbstractMap.SimpleImmutableEntry(false, Partition.this.forFalse), new AbstractMap.SimpleImmutableEntry(true, Partition.this.forTrue)).iterator();
            }
        }

        public Set entrySet() {
            return new 1();
        }
    }
}
