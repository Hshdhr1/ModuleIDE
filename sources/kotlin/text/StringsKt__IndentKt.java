package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: Indent.kt */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0001H\u0007\u001a\u001e\u0010\u0003\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0001\u001a\f\u0010\u0005\u001a\u00020\u0001*\u00020\u0001H\u0007\u001a\u0014\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u0001\u001a\u0014\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\b\u001a\u00020\u0001\u001a\u0011\u0010\t\u001a\u00020\n*\u00020\u0001H\u0002¢\u0006\u0002\b\u000b\u001a!\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\r2\u0006\u0010\b\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001aJ\u0010\u000f\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\r2\u0014\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00010\rH\u0082\b¢\u0006\u0002\b\u0014¨\u0006\u0015"}, d2 = {"trimMargin", "", "marginPrefix", "replaceIndentByMargin", "newIndent", "trimIndent", "replaceIndent", "prependIndent", "indent", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "getIndentFunction", "Lkotlin/Function1;", "getIndentFunction$StringsKt__IndentKt", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "kotlin-stdlib"}, k = 5, mv = {2, 1, 0}, xi = 49, xs = "kotlin/text/StringsKt")
@SourceDebugExtension({"SMAP\nIndent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Indent.kt\nkotlin/text/StringsKt__IndentKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,129:1\n119#1,2:131\n121#1,4:146\n126#1,2:159\n119#1,2:168\n121#1,4:183\n126#1,2:190\n1#2:130\n1#2:156\n1#2:187\n1#2:211\n1583#3,11:133\n1878#3,2:144\n1880#3:157\n1594#3:158\n774#3:161\n865#3,2:162\n1563#3:164\n1634#3,3:165\n1583#3,11:170\n1878#3,2:181\n1880#3:188\n1594#3:189\n1583#3,11:198\n1878#3,2:209\n1880#3:212\n1594#3:213\n158#4,6:150\n158#4,6:192\n*S KotlinDebug\n*F\n+ 1 Indent.kt\nkotlin/text/StringsKt__IndentKt\n*L\n42#1:131,2\n42#1:146,4\n42#1:159,2\n83#1:168,2\n83#1:183,4\n83#1:190,2\n42#1:156\n83#1:187\n120#1:211\n42#1:133,11\n42#1:144,2\n42#1:157\n42#1:158\n79#1:161\n79#1:162,2\n80#1:164\n80#1:165,3\n83#1:170,11\n83#1:181,2\n83#1:188\n83#1:189\n120#1:198,11\n120#1:209,2\n120#1:212\n120#1:213\n43#1:150,6\n107#1:192,6\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
class StringsKt__IndentKt extends StringsKt__AppendableKt {
    public static /* synthetic */ String $r8$lambda$7gQTIx5gKXNh0yIHp2Ioy3cIUtg(String str) {
        return getIndentFunction$lambda$8$StringsKt__IndentKt(str);
    }

    public static /* synthetic */ String $r8$lambda$7kLj5wKXQcPzDDXZYqg7FNckCwg(String str, String str2) {
        return prependIndent$lambda$5$StringsKt__IndentKt(str, str2);
    }

    public static /* synthetic */ String $r8$lambda$nNic-LEWSJV9XuSiP33DOd1sLF4(String str, String str2) {
        return getIndentFunction$lambda$9$StringsKt__IndentKt(str, str2);
    }

    private static final String getIndentFunction$lambda$8$StringsKt__IndentKt(String line) {
        Intrinsics.checkNotNullParameter(line, "line");
        return line;
    }

    public static /* synthetic */ String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "|";
        }
        return StringsKt.trimMargin(str, str2);
    }

    @IntrinsicConstEvaluation
    @NotNull
    public static final String trimMargin(@NotNull String str, @NotNull String marginPrefix) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(marginPrefix, "marginPrefix");
        return StringsKt.replaceIndentByMargin(str, "", marginPrefix);
    }

    public static /* synthetic */ String replaceIndentByMargin$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        if ((i & 2) != 0) {
            str3 = "|";
        }
        return StringsKt.replaceIndentByMargin(str, str2, str3);
    }

    @NotNull
    public static final String replaceIndentByMargin(@NotNull String str, @NotNull String newIndent, @NotNull String marginPrefix) {
        String str2;
        String str3;
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(newIndent, "newIndent");
        Intrinsics.checkNotNullParameter(marginPrefix, "marginPrefix");
        if (StringsKt.isBlank((CharSequence) marginPrefix)) {
            throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
        }
        Iterable lines = StringsKt.lines((CharSequence) str);
        int length = str.length() + (newIndent.length() * lines.size());
        Function1 indentFunction$StringsKt__IndentKt = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex = CollectionsKt.getLastIndex(lines);
        Iterable iterable = (Collection) new ArrayList();
        int i = 0;
        for (Object obj : lines) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str4 = (String) obj;
            String str5 = null;
            if ((i == 0 || i == lastIndex) && StringsKt.isBlank((CharSequence) str4)) {
                str2 = marginPrefix;
                str4 = null;
            } else {
                CharSequence charSequence = (CharSequence) str4;
                int length2 = charSequence.length();
                int i3 = 0;
                while (true) {
                    if (i3 >= length2) {
                        i3 = -1;
                        break;
                    }
                    if (!CharsKt.isWhitespace(charSequence.charAt(i3))) {
                        break;
                    }
                    i3++;
                }
                if (i3 == -1) {
                    str2 = marginPrefix;
                } else {
                    str2 = marginPrefix;
                    if (StringsKt.startsWith$default(str4, str2, i3, false, 4, (Object) null)) {
                        int length3 = i3 + str2.length();
                        Intrinsics.checkNotNull(str4, "null cannot be cast to non-null type java.lang.String");
                        str5 = str4.substring(length3);
                        Intrinsics.checkNotNullExpressionValue(str5, "substring(...)");
                    }
                }
                if (str5 != null && (str3 = (String) indentFunction$StringsKt__IndentKt.invoke(str5)) != null) {
                    str4 = str3;
                }
            }
            if (str4 != null) {
                iterable.add(str4);
            }
            i = i2;
            marginPrefix = str2;
        }
        return CollectionsKt.joinTo$default((List) iterable, new StringBuilder(length), "\n", null, null, 0, null, null, 124, null).toString();
    }

    @IntrinsicConstEvaluation
    @NotNull
    public static final String trimIndent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return StringsKt.replaceIndent(str, "");
    }

    public static /* synthetic */ String replaceIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return StringsKt.replaceIndent(str, str2);
    }

    @NotNull
    public static final String replaceIndent(@NotNull String str, @NotNull String newIndent) {
        String str2;
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(newIndent, "newIndent");
        Iterable lines = StringsKt.lines((CharSequence) str);
        Iterable iterable = lines;
        Iterable iterable2 = (Collection) new ArrayList();
        for (Object obj : iterable) {
            if (!StringsKt.isBlank((String) obj)) {
                iterable2.add(obj);
            }
        }
        Iterable iterable3 = (List) iterable2;
        Iterable iterable4 = (Collection) new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable3, 10));
        Iterator it = iterable3.iterator();
        while (it.hasNext()) {
            iterable4.add(Integer.valueOf(indentWidth$StringsKt__IndentKt((String) it.next())));
        }
        Integer minOrNull = CollectionsKt.minOrNull(iterable4);
        int i = 0;
        int intValue = minOrNull != null ? minOrNull.intValue() : 0;
        int length = str.length() + (newIndent.length() * lines.size());
        Function1 indentFunction$StringsKt__IndentKt = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex = CollectionsKt.getLastIndex(lines);
        Iterable iterable5 = (Collection) new ArrayList();
        for (Object obj2 : iterable) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str3 = (String) obj2;
            if ((i == 0 || i == lastIndex) && StringsKt.isBlank((CharSequence) str3)) {
                str3 = null;
            } else {
                String drop = StringsKt.drop(str3, intValue);
                if (drop != null && (str2 = (String) indentFunction$StringsKt__IndentKt.invoke(drop)) != null) {
                    str3 = str2;
                }
            }
            if (str3 != null) {
                iterable5.add(str3);
            }
            i = i2;
        }
        return CollectionsKt.joinTo$default((List) iterable5, new StringBuilder(length), "\n", null, null, 0, null, null, 124, null).toString();
    }

    public static /* synthetic */ String prependIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "    ";
        }
        return StringsKt.prependIndent(str, str2);
    }

    @NotNull
    public static final String prependIndent(@NotNull String str, @NotNull String indent) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(indent, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence((CharSequence) str), new StringsKt__IndentKt$$ExternalSyntheticLambda0(indent)), "\n", null, null, 0, null, null, 62, null);
    }

    private static final String prependIndent$lambda$5$StringsKt__IndentKt(String str, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (StringsKt.isBlank((CharSequence) it)) {
            return it.length() < str.length() ? str : it;
        }
        return str + it;
    }

    private static final int indentWidth$StringsKt__IndentKt(String str) {
        CharSequence charSequence = (CharSequence) str;
        int length = charSequence.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                i = -1;
                break;
            }
            if (!CharsKt.isWhitespace(charSequence.charAt(i))) {
                break;
            }
            i++;
        }
        return i == -1 ? str.length() : i;
    }

    private static final Function1 getIndentFunction$StringsKt__IndentKt(String str) {
        return ((CharSequence) str).length() == 0 ? new StringsKt__IndentKt$$ExternalSyntheticLambda1() : new StringsKt__IndentKt$$ExternalSyntheticLambda2(str);
    }

    private static final String getIndentFunction$lambda$9$StringsKt__IndentKt(String str, String line) {
        Intrinsics.checkNotNullParameter(line, "line");
        return str + line;
    }

    private static final String reindent$StringsKt__IndentKt(List list, int i, Function1 function1, Function1 function12) {
        String str;
        int lastIndex = CollectionsKt.getLastIndex(list);
        Iterable iterable = (Collection) new ArrayList();
        int i2 = 0;
        for (Object obj : (Iterable) list) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str2 = (String) obj;
            if ((i2 == 0 || i2 == lastIndex) && StringsKt.isBlank((CharSequence) str2)) {
                str2 = null;
            } else {
                String str3 = (String) function12.invoke(str2);
                if (str3 != null && (str = (String) function1.invoke(str3)) != null) {
                    str2 = str;
                }
            }
            if (str2 != null) {
                iterable.add(str2);
            }
            i2 = i3;
        }
        return CollectionsKt.joinTo$default((List) iterable, new StringBuilder(i), "\n", null, null, 0, null, null, 124, null).toString();
    }
}
