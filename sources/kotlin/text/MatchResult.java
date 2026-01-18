package kotlin.text;

import java.util.List;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatchResult.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001\u0017J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0000H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0018"}, d2 = {"Lkotlin/text/MatchResult;", "", "range", "Lkotlin/ranges/IntRange;", "getRange", "()Lkotlin/ranges/IntRange;", "value", "", "getValue", "()Ljava/lang/String;", "groups", "Lkotlin/text/MatchGroupCollection;", "getGroups", "()Lkotlin/text/MatchGroupCollection;", "groupValues", "", "getGroupValues", "()Ljava/util/List;", "destructured", "Lkotlin/text/MatchResult$Destructured;", "getDestructured", "()Lkotlin/text/MatchResult$Destructured;", "next", "Destructured", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public interface MatchResult {
    @NotNull
    Destructured getDestructured();

    @NotNull
    List getGroupValues();

    @NotNull
    MatchGroupCollection getGroups();

    @NotNull
    IntRange getRange();

    @NotNull
    String getValue();

    @Nullable
    MatchResult next();

    /* compiled from: MatchResult.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
    public static final class DefaultImpls {
        @NotNull
        public static Destructured getDestructured(@NotNull MatchResult matchResult) {
            return new Destructured(matchResult);
        }
    }

    /* compiled from: MatchResult.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\tH\u0087\nJ\t\u0010\n\u001a\u00020\tH\u0087\nJ\t\u0010\u000b\u001a\u00020\tH\u0087\nJ\t\u0010\f\u001a\u00020\tH\u0087\nJ\t\u0010\r\u001a\u00020\tH\u0087\nJ\t\u0010\u000e\u001a\u00020\tH\u0087\nJ\t\u0010\u000f\u001a\u00020\tH\u0087\nJ\t\u0010\u0010\u001a\u00020\tH\u0087\nJ\t\u0010\u0011\u001a\u00020\tH\u0087\nJ\t\u0010\u0012\u001a\u00020\tH\u0087\nJ\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0015"}, d2 = {"Lkotlin/text/MatchResult$Destructured;", "", "match", "Lkotlin/text/MatchResult;", "<init>", "(Lkotlin/text/MatchResult;)V", "getMatch", "()Lkotlin/text/MatchResult;", "component1", "", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "toList", "", "kotlin-stdlib"}, k = 1, mv = {2, 1, 0}, xi = 48)
    public static final class Destructured {

        @NotNull
        private final MatchResult match;

        public Destructured(@NotNull MatchResult match) {
            Intrinsics.checkNotNullParameter(match, "match");
            this.match = match;
        }

        @NotNull
        public final MatchResult getMatch() {
            return this.match;
        }

        @InlineOnly
        private final String component1() {
            return (String) getMatch().getGroupValues().get(1);
        }

        @InlineOnly
        private final String component2() {
            return (String) getMatch().getGroupValues().get(2);
        }

        @InlineOnly
        private final String component3() {
            return (String) getMatch().getGroupValues().get(3);
        }

        @InlineOnly
        private final String component4() {
            return (String) getMatch().getGroupValues().get(4);
        }

        @InlineOnly
        private final String component5() {
            return (String) getMatch().getGroupValues().get(5);
        }

        @InlineOnly
        private final String component6() {
            return (String) getMatch().getGroupValues().get(6);
        }

        @InlineOnly
        private final String component7() {
            return (String) getMatch().getGroupValues().get(7);
        }

        @InlineOnly
        private final String component8() {
            return (String) getMatch().getGroupValues().get(8);
        }

        @InlineOnly
        private final String component9() {
            return (String) getMatch().getGroupValues().get(9);
        }

        @InlineOnly
        private final String component10() {
            return (String) getMatch().getGroupValues().get(10);
        }

        @NotNull
        public final List toList() {
            return this.match.getGroupValues().subList(1, this.match.getGroupValues().size());
        }
    }
}
