package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.BuilderInference;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Collections.kt */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u001a%\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0000¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002\u001a+\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u00022\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003\"\u0002H\u0002¢\u0006\u0002\u0010\t\u001a\u0015\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002H\u0087\b\u001a\u0015\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000b\"\u0004\b\u0000\u0010\u0002H\u0087\b\u001a$\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\rj\b\u0012\u0004\u0012\u0002H\u0002`\u000e\"\u0004\b\u0000\u0010\u0002H\u0087\b¢\u0006\u0002\u0010\u000f\u001a+\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000b\"\u0004\b\u0000\u0010\u00022\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003\"\u0002H\u0002¢\u0006\u0002\u0010\t\u001a5\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\rj\b\u0012\u0004\u0012\u0002H\u0002`\u000e\"\u0004\b\u0000\u0010\u00022\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003\"\u0002H\u0002¢\u0006\u0002\u0010\u0010\u001a%\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\b\b\u0000\u0010\u0002*\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0014\u001a3\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\b\b\u0000\u0010\u0002*\u00020\u00122\u0016\u0010\b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00020\u0003\"\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\t\u001aC\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0016\u001a\u00020\u00172!\u0010\u0018\u001a\u001d\u0012\u0013\u0012\u00110\u0017¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u0002H\u00020\u0019H\u0087\bø\u0001\u0000\u001aC\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000b\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0016\u001a\u00020\u00172!\u0010\u0018\u001a\u001d\u0012\u0013\u0012\u00110\u0017¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u0002H\u00020\u0019H\u0087\bø\u0001\u0000\u001aF\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001f0\u0006\"\u0004\b\u0000\u0010\u001f2\u001f\b\u0001\u0010 \u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001f0\u000b\u0012\u0004\u0012\u00020!0\u0019¢\u0006\u0002\b\"H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001aN\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u001f0\u0006\"\u0004\b\u0000\u0010\u001f2\u0006\u0010#\u001a\u00020\u00172\u001f\b\u0001\u0010 \u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001f0\u000b\u0012\u0004\u0012\u00020!0\u0019¢\u0006\u0002\b\"H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a\u0019\u0010+\u001a\u00020,\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0087\b\u001a,\u0010-\u001a\u00020,\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a!\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u001a!\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0006H\u0087\b\u001aH\u0010/\u001a\u0002H0\"\u0010\b\u0000\u00101*\u0006\u0012\u0002\b\u00030\u0001*\u0002H0\"\u0004\b\u0001\u00100*\u0002H12\f\u00102\u001a\b\u0012\u0004\u0012\u0002H003H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u00104\u001a,\u00105\u001a\u00020,\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b6*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0087\b\u001a&\u00107\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u0002082\u0006\u00109\u001a\u00020:H\u0007\u001a\u001e\u0010;\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0000\u001aE\u0010<\u001a\u00020\u0017\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020=*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00062\b\u0010\u0013\u001a\u0004\u0018\u0001H\u00022\b\b\u0002\u0010>\u001a\u00020\u00172\b\b\u0002\u0010?\u001a\u00020\u0017¢\u0006\u0002\u0010@\u001aS\u0010<\u001a\u00020\u0017\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\u0006\u0010\u0013\u001a\u0002H\u00022\u001a\u0010A\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020Bj\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`C2\b\b\u0002\u0010>\u001a\u00020\u00172\b\b\u0002\u0010?\u001a\u00020\u0017¢\u0006\u0002\u0010D\u001ag\u0010E\u001a\u00020\u0017\"\u0004\b\u0000\u0010\u0002\"\u000e\b\u0001\u0010F*\b\u0012\u0004\u0012\u0002HF0=*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\u0010G\u001a\u0004\u0018\u0001HF2\b\b\u0002\u0010>\u001a\u00020\u00172\b\b\u0002\u0010?\u001a\u00020\u00172\u0016\b\u0004\u0010H\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001HF0\u0019H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010I\u001a>\u0010<\u001a\u00020\u0017\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00062\b\b\u0002\u0010>\u001a\u00020\u00172\b\b\u0002\u0010?\u001a\u00020\u00172\u0012\u0010J\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00170\u0019\u001a%\u0010K\u001a\u00020!2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010>\u001a\u00020\u00172\u0006\u0010?\u001a\u00020\u0017H\u0002¢\u0006\u0002\bL\u001a\b\u0010M\u001a\u00020!H\u0001\u001a\b\u0010N\u001a\u00020!H\u0001\u001a!\u0010O\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00032\n\u0010P\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0000¢\u0006\u0002\u0010Q\u001a3\u0010O\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u00022\n\u0010P\u001a\u0006\u0012\u0002\b\u00030\u00012\f\u0010R\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0000¢\u0006\u0002\u0010S\"\u0019\u0010$\u001a\u00020%*\u0006\u0012\u0002\b\u00030\u00018F¢\u0006\u0006\u001a\u0004\b&\u0010'\"!\u0010(\u001a\u00020\u0017\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00068F¢\u0006\u0006\u001a\u0004\b)\u0010*\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006T"}, d2 = {"asCollection", "", "T", "", "([Ljava/lang/Object;)Ljava/util/Collection;", "emptyList", "", "listOf", "elements", "([Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "()Ljava/util/ArrayList;", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "List", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "buildList", "E", "builderAction", "", "Lkotlin/ExtensionFunctionType;", "capacity", "indices", "Lkotlin/ranges/IntRange;", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "getLastIndex", "(Ljava/util/List;)I", "isNotEmpty", "", "isNullOrEmpty", "orEmpty", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "containsAll", "Lkotlin/internal/OnlyInputTypes;", "shuffled", "", "random", "Lkotlin/random/Random;", "optimizeReadOnlyList", "binarySearch", "", "fromIndex", "toIndex", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "comparison", "rangeCheck", "rangeCheck$CollectionsKt__CollectionsKt", "throwIndexOverflow", "throwCountOverflow", "collectionToArrayCommonImpl", "collection", "(Ljava/util/Collection;)[Ljava/lang/Object;", "array", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "kotlin-stdlib"}, k = 5, mv = {2, 1, 0}, xi = 49, xs = "kotlin/collections/CollectionsKt")
@SourceDebugExtension({"SMAP\nCollections.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Collections.kt\nkotlin/collections/CollectionsKt__CollectionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,526:1\n1#2:527\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
class CollectionsKt__CollectionsKt extends CollectionsKt__CollectionsJVMKt {
    @NotNull
    public static final Collection asCollection(@NotNull Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        return new ArrayAsCollection(objArr, false);
    }

    @NotNull
    public static final List emptyList() {
        return EmptyList.INSTANCE;
    }

    @NotNull
    public static final List listOf(@NotNull Object... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return elements.length > 0 ? ArraysKt.asList(elements) : CollectionsKt.emptyList();
    }

    @InlineOnly
    private static final List listOf() {
        return CollectionsKt.emptyList();
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final List mutableListOf() {
        return new ArrayList();
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final ArrayList arrayListOf() {
        return new ArrayList();
    }

    @NotNull
    public static final List mutableListOf(@NotNull Object... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return elements.length == 0 ? new ArrayList() : new ArrayList(new ArrayAsCollection(elements, true));
    }

    @NotNull
    public static final ArrayList arrayListOf(@NotNull Object... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return elements.length == 0 ? new ArrayList() : new ArrayList(new ArrayAsCollection(elements, true));
    }

    @NotNull
    public static final List listOfNotNull(@Nullable Object obj) {
        return obj != null ? CollectionsKt.listOf(obj) : CollectionsKt.emptyList();
    }

    @NotNull
    public static final List listOfNotNull(@NotNull Object... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return ArraysKt.filterNotNull(elements);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final List List(int i, Function1 init) {
        Intrinsics.checkNotNullParameter(init, "init");
        List arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(init.invoke(Integer.valueOf(i2)));
        }
        return arrayList;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final List MutableList(int i, Function1 init) {
        Intrinsics.checkNotNullParameter(init, "init");
        List arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(init.invoke(Integer.valueOf(i2)));
        }
        return arrayList;
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final List buildList(@BuilderInference Function1 builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        List createListBuilder = CollectionsKt.createListBuilder();
        builderAction.invoke(createListBuilder);
        return CollectionsKt.build(createListBuilder);
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalStdlibApi.class})
    @InlineOnly
    private static final List buildList(int i, @BuilderInference Function1 builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        List createListBuilder = CollectionsKt.createListBuilder(i);
        builderAction.invoke(createListBuilder);
        return CollectionsKt.build(createListBuilder);
    }

    @NotNull
    public static final IntRange getIndices(@NotNull Collection collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        return new IntRange(0, collection.size() - 1);
    }

    public static final int getLastIndex(@NotNull List list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return list.size() - 1;
    }

    @InlineOnly
    private static final boolean isNotEmpty(Collection collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        return !collection.isEmpty();
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    @InlineOnly
    private static final Collection orEmpty(Collection collection) {
        return collection == null ? CollectionsKt.emptyList() : collection;
    }

    @InlineOnly
    private static final List orEmpty(List list) {
        return list == null ? CollectionsKt.emptyList() : list;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object ifEmpty(Collection collection, Function0 defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        return collection.isEmpty() ? defaultValue.invoke() : collection;
    }

    @InlineOnly
    private static final boolean containsAll(Collection collection, Collection elements) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        return collection.containsAll(elements);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final List shuffled(@NotNull Iterable iterable, @NotNull Random random) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        List mutableList = CollectionsKt.toMutableList(iterable);
        CollectionsKt.shuffle(mutableList, random);
        return mutableList;
    }

    @NotNull
    public static final List optimizeReadOnlyList(@NotNull List list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        int size = list.size();
        if (size != 0) {
            return size != 1 ? list : CollectionsKt.listOf(list.get(0));
        }
        return CollectionsKt.emptyList();
    }

    public static /* synthetic */ int binarySearch$default(List list, Comparable comparable, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = list.size();
        }
        return CollectionsKt.binarySearch(list, comparable, i, i2);
    }

    public static final int binarySearch(@NotNull List list, @Nullable Comparable comparable, int i, int i2) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        rangeCheck$CollectionsKt__CollectionsKt(list.size(), i, i2);
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            int compareValues = ComparisonsKt.compareValues((Comparable) list.get(i4), comparable);
            if (compareValues < 0) {
                i = i4 + 1;
            } else {
                if (compareValues <= 0) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public static /* synthetic */ int binarySearch$default(List list, Object obj, Comparator comparator, int i, int i2, int i3, Object obj2) {
        if ((i3 & 4) != 0) {
            i = 0;
        }
        if ((i3 & 8) != 0) {
            i2 = list.size();
        }
        return CollectionsKt.binarySearch(list, obj, comparator, i, i2);
    }

    public static final int binarySearch(@NotNull List list, Object obj, @NotNull Comparator comparator, int i, int i2) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        rangeCheck$CollectionsKt__CollectionsKt(list.size(), i, i2);
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            int compare = comparator.compare(list.get(i4), obj);
            if (compare < 0) {
                i = i4 + 1;
            } else {
                if (compare <= 0) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public static /* synthetic */ int binarySearchBy$default(List list, Comparable comparable, int i, int i2, Function1 selector, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = list.size();
        }
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return CollectionsKt.binarySearch(list, i, i2, new 1(selector, comparable));
    }

    /* compiled from: Collections.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 176)
    public static final class 1 implements Function1 {
        final /* synthetic */ Comparable $key;
        final /* synthetic */ Function1 $selector;

        public 1(Function1 function1, Comparable comparable) {
            this.$selector = function1;
            this.$key = comparable;
        }

        public final Integer invoke(Object obj) {
            return Integer.valueOf(ComparisonsKt.compareValues((Comparable) this.$selector.invoke(obj), this.$key));
        }
    }

    public static final int binarySearchBy(@NotNull List list, @Nullable Comparable comparable, int i, int i2, @NotNull Function1 selector) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(selector, "selector");
        return CollectionsKt.binarySearch(list, i, i2, new 1(selector, comparable));
    }

    public static /* synthetic */ int binarySearch$default(List list, int i, int i2, Function1 function1, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = list.size();
        }
        return CollectionsKt.binarySearch(list, i, i2, function1);
    }

    public static final int binarySearch(@NotNull List list, int i, int i2, @NotNull Function1 comparison) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(comparison, "comparison");
        rangeCheck$CollectionsKt__CollectionsKt(list.size(), i, i2);
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            int intValue = ((Number) comparison.invoke(list.get(i4))).intValue();
            if (intValue < 0) {
                i = i4 + 1;
            } else {
                if (intValue <= 0) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    private static final void rangeCheck$CollectionsKt__CollectionsKt(int i, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException("fromIndex (" + i2 + ") is greater than toIndex (" + i3 + ").");
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("fromIndex (" + i2 + ") is less than zero.");
        }
        if (i3 <= i) {
            return;
        }
        throw new IndexOutOfBoundsException("toIndex (" + i3 + ") is greater than size (" + i + ").");
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    public static final void throwIndexOverflow() {
        throw new ArithmeticException("Index overflow has happened.");
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    public static final void throwCountOverflow() {
        throw new ArithmeticException("Count overflow has happened.");
    }

    @NotNull
    public static final Object[] collectionToArrayCommonImpl(@NotNull Collection collection) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        int i = 0;
        if (collection.isEmpty()) {
            return new Object[0];
        }
        Object[] objArr = new Object[collection.size()];
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
        return objArr;
    }

    @NotNull
    public static final Object[] collectionToArrayCommonImpl(@NotNull Collection collection, @NotNull Object[] array) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        Intrinsics.checkNotNullParameter(array, "array");
        int i = 0;
        if (collection.isEmpty()) {
            return CollectionsKt.terminateCollectionToArray(0, array);
        }
        if (array.length < collection.size()) {
            array = ArraysKt.arrayOfNulls(array, collection.size());
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            array[i] = it.next();
            i++;
        }
        return CollectionsKt.terminateCollectionToArray(collection.size(), array);
    }
}
