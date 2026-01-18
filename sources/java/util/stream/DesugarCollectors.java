package java.util.stream;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final class DesugarCollectors {
    static final Set CH_NOID = Collections.EMPTY_SET;
    static final Set CH_UNORDERED_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));

    static /* bridge */ /* synthetic */ Function -$$Nest$smcastingIdentity() {
        return castingIdentity();
    }

    static /* synthetic */ Object lambda$castingIdentity$9(Object obj) {
        return obj;
    }

    private DesugarCollectors() {
    }

    public static Collector filtering(Predicate predicate, Collector collector) {
        return new CollectorImpl(collector.supplier(), new DesugarCollectors$$ExternalSyntheticLambda18(predicate, collector.accumulator()), collector.combiner(), collector.finisher(), collector.characteristics());
    }

    static /* synthetic */ void lambda$filtering$0(Predicate predicate, BiConsumer biConsumer, Object obj, Object obj2) {
        if (predicate.test(obj2)) {
            biConsumer.accept(obj, obj2);
        }
    }

    public static Collector flatMapping(Function function, Collector collector) {
        return new CollectorImpl(collector.supplier(), new DesugarCollectors$$ExternalSyntheticLambda14(function, collector.accumulator()), collector.combiner(), collector.finisher(), collector.characteristics());
    }

    static /* synthetic */ void lambda$flatMapping$2(Function function, BiConsumer biConsumer, Object obj, Object obj2) {
        Stream stream = (Stream) function.apply(obj2);
        if (stream != null) {
            try {
                ((Stream) stream.sequential()).forEach(new DesugarCollectors$$ExternalSyntheticLambda13(biConsumer, obj));
            } catch (Throwable th) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Throwable th2) {
                        DesugarCollectors$$ExternalSyntheticBackport0.m(th, th2);
                    }
                }
                throw th;
            }
        }
        if (stream != null) {
            stream.close();
        }
    }

    static /* synthetic */ void lambda$flatMapping$1(BiConsumer biConsumer, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
    }

    public static Collector toUnmodifiableList() {
        return new CollectorImpl(new DesugarCollectors$$ExternalSyntheticLambda5(), new DesugarCollectors$$ExternalSyntheticLambda6(), new DesugarCollectors$$ExternalSyntheticLambda7(), new DesugarCollectors$$ExternalSyntheticLambda8(), CH_NOID);
    }

    static /* synthetic */ List lambda$toUnmodifiableList$3(List list, List list2) {
        list.addAll(list2);
        return list;
    }

    static /* synthetic */ List lambda$toUnmodifiableList$4(List list) {
        return DesugarCollectors$$ExternalSyntheticBackport4.m(list.toArray());
    }

    public static Collector toUnmodifiableMap(Function function, Function function2) {
        DesugarCollectors$$ExternalSyntheticBackport1.m(function, "keyMapper");
        DesugarCollectors$$ExternalSyntheticBackport1.m(function2, "valueMapper");
        return Collectors.collectingAndThen(Collectors.toMap(function, function2), new DesugarCollectors$$ExternalSyntheticLambda15());
    }

    static /* synthetic */ Map lambda$toUnmodifiableMap$5(Map map) {
        return DesugarCollectors$$ExternalSyntheticBackport2.m((Map.Entry[]) map.entrySet().toArray(new Map.Entry[0]));
    }

    public static Collector toUnmodifiableMap(Function function, Function function2, BinaryOperator binaryOperator) {
        DesugarCollectors$$ExternalSyntheticBackport1.m(function, "keyMapper");
        DesugarCollectors$$ExternalSyntheticBackport1.m(function2, "valueMapper");
        DesugarCollectors$$ExternalSyntheticBackport1.m(binaryOperator, "mergeFunction");
        return Collectors.collectingAndThen(Collectors.toMap(function, function2, binaryOperator, new DesugarCollectors$$ExternalSyntheticLambda16()), new DesugarCollectors$$ExternalSyntheticLambda17());
    }

    static /* synthetic */ Map lambda$toUnmodifiableMap$6(HashMap hashMap) {
        return DesugarCollectors$$ExternalSyntheticBackport2.m((Map.Entry[]) hashMap.entrySet().toArray(new Map.Entry[0]));
    }

    public static Collector toUnmodifiableSet() {
        return new CollectorImpl(new DesugarCollectors$$ExternalSyntheticLambda9(), new DesugarCollectors$$ExternalSyntheticLambda10(), new DesugarCollectors$$ExternalSyntheticLambda11(), new DesugarCollectors$$ExternalSyntheticLambda12(), CH_UNORDERED_NOID);
    }

    static /* synthetic */ Set lambda$toUnmodifiableSet$7(Set set, Set set2) {
        if (set.size() < set2.size()) {
            set2.addAll(set);
            return set2;
        }
        set.addAll(set2);
        return set;
    }

    static /* synthetic */ Set lambda$toUnmodifiableSet$8(Set set) {
        return DesugarCollectors$$ExternalSyntheticBackport3.m(set.toArray());
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
            this(supplier, biConsumer, binaryOperator, DesugarCollectors.-$$Nest$smcastingIdentity(), set);
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

    private static Function castingIdentity() {
        return new DesugarCollectors$$ExternalSyntheticLambda19();
    }
}
