package java.util.stream;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface Collector {

    public enum Characteristics {
        CONCURRENT,
        UNORDERED,
        IDENTITY_FINISH
    }

    BiConsumer accumulator();

    Set characteristics();

    BinaryOperator combiner();

    Function finisher();

    Supplier supplier();

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static Collector of(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Characteristics... characteristicsArr) {
            Set unmodifiableSet;
            supplier.getClass();
            biConsumer.getClass();
            binaryOperator.getClass();
            characteristicsArr.getClass();
            if (characteristicsArr.length == 0) {
                unmodifiableSet = Collectors.CH_ID;
            } else {
                unmodifiableSet = Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, characteristicsArr));
            }
            return new Collectors.CollectorImpl(supplier, biConsumer, binaryOperator, unmodifiableSet);
        }

        public static Collector of(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Function function, Characteristics... characteristicsArr) {
            supplier.getClass();
            biConsumer.getClass();
            binaryOperator.getClass();
            function.getClass();
            characteristicsArr.getClass();
            Set set = Collectors.CH_NOID;
            if (characteristicsArr.length > 0) {
                EnumSet noneOf = EnumSet.noneOf(Characteristics.class);
                Collections.addAll(noneOf, characteristicsArr);
                set = Collections.unmodifiableSet(noneOf);
            }
            return new Collectors.CollectorImpl(supplier, biConsumer, binaryOperator, function, set);
        }
    }
}
