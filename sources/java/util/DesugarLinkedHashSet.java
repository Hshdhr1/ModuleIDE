package java.util;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarLinkedHashSet {
    private DesugarLinkedHashSet() {
    }

    public static Spliterator spliterator(LinkedHashSet linkedHashSet) {
        return Spliterators.spliterator((Collection) linkedHashSet, 17);
    }
}
