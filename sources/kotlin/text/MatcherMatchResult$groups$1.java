package kotlin.text;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.AbstractCollection;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt;

/* compiled from: Regex.kt */
@Metadata(d1 = {"\u0000/\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\u0011\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000bH\u0096\u0002J\u0013\u0010\f\u001a\u0004\u0018\u00010\u00032\u0006\u0010\r\u001a\u00020\u0005H\u0096\u0002J\u0013\u0010\f\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0096\u0002R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"kotlin/text/MatcherMatchResult$groups$1", "Lkotlin/text/MatchNamedGroupCollection;", "Lkotlin/collections/AbstractCollection;", "Lkotlin/text/MatchGroup;", "size", "", "getSize", "()I", "isEmpty", "", "iterator", "", "get", "index", "name", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class MatcherMatchResult$groups$1 extends AbstractCollection implements MatchNamedGroupCollection {
    final /* synthetic */ MatcherMatchResult this$0;

    public static /* synthetic */ MatchGroup $r8$lambda$6qTiT2TOre74mkZ4SBVT0fE9ajA(MatcherMatchResult$groups$1 matcherMatchResult$groups$1, int i) {
        return iterator$lambda$0(matcherMatchResult$groups$1, i);
    }

    public boolean isEmpty() {
        return false;
    }

    MatcherMatchResult$groups$1(MatcherMatchResult matcherMatchResult) {
        this.this$0 = matcherMatchResult;
    }

    public final /* bridge */ boolean contains(Object obj) {
        if (obj == null ? true : obj instanceof MatchGroup) {
            return contains((MatchGroup) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(MatchGroup matchGroup) {
        return super.contains((Object) matchGroup);
    }

    public int getSize() {
        return MatcherMatchResult.access$getMatchResult(this.this$0).groupCount() + 1;
    }

    private static final MatchGroup iterator$lambda$0(MatcherMatchResult$groups$1 matcherMatchResult$groups$1, int i) {
        return matcherMatchResult$groups$1.get(i);
    }

    public Iterator iterator() {
        return SequencesKt.map(CollectionsKt.asSequence(CollectionsKt.getIndices(this)), new MatcherMatchResult$groups$1$$ExternalSyntheticLambda0(this)).iterator();
    }

    public MatchGroup get(int index) {
        IntRange access$range = RegexKt.access$range(MatcherMatchResult.access$getMatchResult(this.this$0), index);
        if (access$range.getStart().intValue() < 0) {
            return null;
        }
        String group = MatcherMatchResult.access$getMatchResult(this.this$0).group(index);
        Intrinsics.checkNotNullExpressionValue(group, "group(...)");
        return new MatchGroup(group, access$range);
    }

    public MatchGroup get(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(MatcherMatchResult.access$getMatchResult(this.this$0), name);
    }
}
