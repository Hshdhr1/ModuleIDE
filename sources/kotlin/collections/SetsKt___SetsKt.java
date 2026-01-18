package kotlin.collections;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Sets.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004\u001a,\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004¨\u0006\r"}, d2 = {"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"}, k = 5, mv = {2, 1, 0}, xi = 49, xs = "kotlin/collections/SetsKt")
@SourceDebugExtension({"SMAP\n_Sets.kt\nKotlin\n*S Kotlin\n*F\n+ 1 _Sets.kt\nkotlin/collections/SetsKt___SetsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,140:1\n865#2,2:141\n855#2,2:143\n1#3:145\n*S KotlinDebug\n*F\n+ 1 _Sets.kt\nkotlin/collections/SetsKt___SetsKt\n*L\n29#1:141,2\n53#1:143,2\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
class SetsKt___SetsKt extends SetsKt__SetsKt {
    @NotNull
    public static final Set minus(@NotNull Set set, Object obj) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Collection linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(set.size()));
        boolean z = false;
        for (Object obj2 : (Iterable) set) {
            boolean z2 = true;
            if (!z && Intrinsics.areEqual(obj2, obj)) {
                z = true;
                z2 = false;
            }
            if (z2) {
                linkedHashSet.add(obj2);
            }
        }
        return (Set) linkedHashSet;
    }

    @NotNull
    public static final Set minus(@NotNull Set set, @NotNull Object[] elements) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Set linkedHashSet = new LinkedHashSet((Collection) set);
        CollectionsKt.removeAll((Collection) linkedHashSet, elements);
        return linkedHashSet;
    }

    @NotNull
    public static final Set minus(@NotNull Set set, @NotNull Iterable elements) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Set convertToListIfNotCollection = CollectionsKt.convertToListIfNotCollection(elements);
        if (convertToListIfNotCollection.isEmpty()) {
            return CollectionsKt.toSet((Iterable) set);
        }
        if (!(convertToListIfNotCollection instanceof Set)) {
            Set linkedHashSet = new LinkedHashSet((Collection) set);
            linkedHashSet.removeAll(convertToListIfNotCollection);
            return linkedHashSet;
        }
        Set set2 = (Collection) new LinkedHashSet();
        for (Object obj : (Iterable) set) {
            if (!convertToListIfNotCollection.contains(obj)) {
                set2.add(obj);
            }
        }
        return set2;
    }

    @NotNull
    public static final Set minus(@NotNull Set set, @NotNull Sequence elements) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Set linkedHashSet = new LinkedHashSet((Collection) set);
        CollectionsKt.removeAll((Collection) linkedHashSet, elements);
        return linkedHashSet;
    }

    @InlineOnly
    private static final Set minusElement(Set set, Object obj) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        return SetsKt.minus(set, obj);
    }

    @NotNull
    public static final Set plus(@NotNull Set set, Object obj) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Set linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(set.size() + 1));
        linkedHashSet.addAll((Collection) set);
        linkedHashSet.add(obj);
        return linkedHashSet;
    }

    @NotNull
    public static final Set plus(@NotNull Set set, @NotNull Object[] elements) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Set linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(set.size() + elements.length));
        linkedHashSet.addAll((Collection) set);
        CollectionsKt.addAll((Collection) linkedHashSet, elements);
        return linkedHashSet;
    }

    @NotNull
    public static final Set plus(@NotNull Set set, @NotNull Iterable elements) {
        int size;
        Intrinsics.checkNotNullParameter(set, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Number collectionSizeOrNull = CollectionsKt.collectionSizeOrNull(elements);
        if (collectionSizeOrNull != null) {
            size = set.size() + collectionSizeOrNull.intValue();
        } else {
            size = set.size() * 2;
        }
        Set linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(size));
        linkedHashSet.addAll((Collection) set);
        CollectionsKt.addAll((Collection) linkedHashSet, elements);
        return linkedHashSet;
    }

    @NotNull
    public static final Set plus(@NotNull Set set, @NotNull Sequence elements) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        Intrinsics.checkNotNullParameter(elements, "elements");
        Set linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(set.size() * 2));
        linkedHashSet.addAll((Collection) set);
        CollectionsKt.addAll((Collection) linkedHashSet, elements);
        return linkedHashSet;
    }

    @InlineOnly
    private static final Set plusElement(Set set, Object obj) {
        Intrinsics.checkNotNullParameter(set, "<this>");
        return SetsKt.plus(set, obj);
    }
}
