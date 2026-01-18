package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;

/* compiled from: AbstractMap.kt */
@Metadata(d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010(\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0005J\u000f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u0096\u0002R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"kotlin/collections/AbstractMap$keys$1", "Lkotlin/collections/AbstractSet;", "contains", "", "element", "(Ljava/lang/Object;)Z", "iterator", "", "size", "", "getSize", "()I", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class AbstractMap$keys$1 extends AbstractSet {
    final /* synthetic */ AbstractMap this$0;

    AbstractMap$keys$1(AbstractMap abstractMap) {
        this.this$0 = abstractMap;
    }

    public boolean contains(Object element) {
        return this.this$0.containsKey(element);
    }

    public Iterator iterator() {
        return new AbstractMap$keys$1$iterator$1(this.this$0.entrySet().iterator());
    }

    public int getSize() {
        return this.this$0.size();
    }
}
