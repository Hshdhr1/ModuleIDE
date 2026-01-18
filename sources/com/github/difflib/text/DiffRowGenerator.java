package com.github.difflib.text;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.InsertDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.text.DiffRow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class DiffRowGenerator {
    private final int columnWidth;
    private final boolean decompressDeltas;
    private final BiPredicate equalizer;
    private final boolean ignoreWhiteSpaces;
    private final Function inlineDiffSplitter;
    private final Function lineNormalizer;
    private final boolean mergeOriginalRevised;
    private final BiFunction newTag;
    private final BiFunction oldTag;
    private final Function processDiffs;
    private final boolean replaceOriginalLinefeedInChangesWithSpaces;
    private final boolean reportLinesUnchanged;
    private final boolean showInlineDiffs;
    public static final BiPredicate DEFAULT_EQUALIZER = new DiffRowGenerator$$ExternalSyntheticLambda1();
    public static final BiPredicate IGNORE_WHITESPACE_EQUALIZER = new DiffRowGenerator$$ExternalSyntheticLambda2();
    public static final Function LINE_NORMALIZER_FOR_HTML = new DiffRowGenerator$$ExternalSyntheticLambda3();
    public static final Function SPLITTER_BY_CHARACTER = new DiffRowGenerator$$ExternalSyntheticLambda4();
    public static final Pattern SPLIT_BY_WORD_PATTERN = Pattern.compile("\\s+|[,.\\[\\](){}/\\\\*+\\-#<>;:&\\']+");
    public static final Function SPLITTER_BY_WORD = new DiffRowGenerator$$ExternalSyntheticLambda5();
    public static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    /* synthetic */ DiffRowGenerator(Builder builder, 1 r2) {
        this(builder);
    }

    static /* synthetic */ boolean lambda$static$0(String str, String str2) {
        return adjustWhitespace(str).equals(adjustWhitespace(str2));
    }

    static /* synthetic */ List lambda$static$1(String str) {
        ArrayList arrayList = new ArrayList(str.length());
        for (char c : str.toCharArray()) {
            arrayList.add(Character.valueOf(c).toString());
        }
        return arrayList;
    }

    static /* synthetic */ List lambda$static$2(String str) {
        return splitStringPreserveDelimiter(str, SPLIT_BY_WORD_PATTERN);
    }

    public static Builder create() {
        return new Builder(null);
    }

    private static String adjustWhitespace(String str) {
        return WHITESPACE_PATTERN.matcher(str.trim()).replaceAll(" ");
    }

    protected static final List splitStringPreserveDelimiter(String str, Pattern pattern) {
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            Matcher matcher = pattern.matcher(str);
            int i = 0;
            while (matcher.find()) {
                if (i < matcher.start()) {
                    arrayList.add(str.substring(i, matcher.start()));
                }
                arrayList.add(matcher.group());
                i = matcher.end();
            }
            if (i < str.length()) {
                arrayList.add(str.substring(i));
            }
        }
        return arrayList;
    }

    static void wrapInTag(List list, int i, int i2, DiffRow.Tag tag, BiFunction biFunction, Function function, boolean z) {
        while (i2 >= i) {
            while (true) {
                if (i2 <= i) {
                    break;
                }
                int i3 = i2 - 1;
                if (!"\n".equals(list.get(i3))) {
                    break;
                }
                if (z) {
                    list.set(i3, " ");
                    break;
                }
                i2--;
            }
            if (i2 == i) {
                return;
            }
            list.add(i2, biFunction.apply(tag, false));
            if (function != null) {
                int i4 = i2 - 1;
                list.set(i4, function.apply(list.get(i4)));
            }
            while (true) {
                i2--;
                if (i2 > i) {
                    int i5 = i2 - 1;
                    if ("\n".equals(list.get(i5))) {
                        if (z) {
                            list.set(i5, " ");
                        }
                    }
                    if (function != null) {
                        list.set(i5, function.apply(list.get(i5)));
                    }
                }
            }
            list.add(i2, biFunction.apply(tag, true));
            i2--;
        }
    }

    private DiffRowGenerator(Builder builder) {
        this.showInlineDiffs = Builder.access$100(builder);
        boolean access$200 = Builder.access$200(builder);
        this.ignoreWhiteSpaces = access$200;
        this.oldTag = Builder.access$300(builder);
        this.newTag = Builder.access$400(builder);
        this.columnWidth = Builder.access$500(builder);
        this.mergeOriginalRevised = Builder.access$600(builder);
        Function access$700 = Builder.access$700(builder);
        this.inlineDiffSplitter = access$700;
        this.decompressDeltas = Builder.access$800(builder);
        if (Builder.access$900(builder) != null) {
            this.equalizer = Builder.access$900(builder);
        } else {
            this.equalizer = access$200 ? IGNORE_WHITESPACE_EQUALIZER : DEFAULT_EQUALIZER;
        }
        this.reportLinesUnchanged = Builder.access$1000(builder);
        Function access$1100 = Builder.access$1100(builder);
        this.lineNormalizer = access$1100;
        this.processDiffs = Builder.access$1200(builder);
        this.replaceOriginalLinefeedInChangesWithSpaces = Builder.access$1300(builder);
        access$700.getClass();
        access$1100.getClass();
    }

    public List generateDiffRows(List list, List list2) {
        return generateDiffRows(list, DiffUtils.diff(list, list2, this.equalizer));
    }

    public List generateDiffRows(List list, Patch patch) {
        ArrayList arrayList = new ArrayList();
        List deltas = patch.getDeltas();
        int i = 0;
        if (this.decompressDeltas) {
            Iterator it = deltas.iterator();
            while (it.hasNext()) {
                Iterator it2 = decompressDeltas((AbstractDelta) it.next()).iterator();
                while (it2.hasNext()) {
                    i = transformDeltaIntoDiffRow(list, i, arrayList, (AbstractDelta) it2.next());
                }
            }
        } else {
            Iterator it3 = deltas.iterator();
            while (it3.hasNext()) {
                i = transformDeltaIntoDiffRow(list, i, arrayList, (AbstractDelta) it3.next());
            }
        }
        for (String str : list.subList(i, list.size())) {
            arrayList.add(buildDiffRow(DiffRow.Tag.EQUAL, str, str));
        }
        return arrayList;
    }

    private int transformDeltaIntoDiffRow(List list, int i, List list2, AbstractDelta abstractDelta) {
        Chunk source = abstractDelta.getSource();
        Chunk target = abstractDelta.getTarget();
        for (String str : list.subList(i, source.getPosition())) {
            list2.add(buildDiffRow(DiffRow.Tag.EQUAL, str, str));
        }
        int i2 = 1.$SwitchMap$com$github$difflib$patch$DeltaType[abstractDelta.getType().ordinal()];
        if (i2 == 1) {
            Iterator it = target.getLines().iterator();
            while (it.hasNext()) {
                list2.add(buildDiffRow(DiffRow.Tag.INSERT, "", (String) it.next()));
            }
        } else if (i2 == 2) {
            Iterator it2 = source.getLines().iterator();
            while (it2.hasNext()) {
                list2.add(buildDiffRow(DiffRow.Tag.DELETE, (String) it2.next(), ""));
            }
        } else if (this.showInlineDiffs) {
            list2.addAll(generateInlineDiffs(abstractDelta));
        } else {
            int i3 = 0;
            while (i3 < Math.max(source.size(), target.size())) {
                list2.add(buildDiffRow(DiffRow.Tag.CHANGE, source.getLines().size() > i3 ? (String) source.getLines().get(i3) : "", target.getLines().size() > i3 ? (String) target.getLines().get(i3) : ""));
                i3++;
            }
        }
        return source.last() + 1;
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$difflib$patch$DeltaType;

        static {
            int[] iArr = new int[DeltaType.values().length];
            $SwitchMap$com$github$difflib$patch$DeltaType = iArr;
            try {
                iArr[DeltaType.INSERT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$difflib$patch$DeltaType[DeltaType.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private List decompressDeltas(AbstractDelta abstractDelta) {
        if (abstractDelta.getType() == DeltaType.CHANGE && abstractDelta.getSource().size() != abstractDelta.getTarget().size()) {
            ArrayList arrayList = new ArrayList();
            int min = Math.min(abstractDelta.getSource().size(), abstractDelta.getTarget().size());
            Chunk source = abstractDelta.getSource();
            Chunk target = abstractDelta.getTarget();
            arrayList.add(new ChangeDelta(new Chunk(source.getPosition(), source.getLines().subList(0, min)), new Chunk(target.getPosition(), target.getLines().subList(0, min))));
            if (source.getLines().size() < target.getLines().size()) {
                arrayList.add(new InsertDelta(new Chunk(source.getPosition() + min, Collections.EMPTY_LIST), new Chunk(target.getPosition() + min, target.getLines().subList(min, target.getLines().size()))));
                return arrayList;
            }
            arrayList.add(new DeleteDelta(new Chunk(source.getPosition() + min, source.getLines().subList(min, source.getLines().size())), new Chunk(target.getPosition() + min, Collections.EMPTY_LIST)));
            return arrayList;
        }
        return Collections.singletonList(abstractDelta);
    }

    private DiffRow buildDiffRow(DiffRow.Tag tag, String str, String str2) {
        if (this.reportLinesUnchanged) {
            return new DiffRow(tag, str, str2);
        }
        String preprocessLine = preprocessLine(str);
        if (DiffRow.Tag.DELETE == tag && (this.mergeOriginalRevised || this.showInlineDiffs)) {
            preprocessLine = ((String) this.oldTag.apply(tag, true)) + preprocessLine + ((String) this.oldTag.apply(tag, false));
        }
        String preprocessLine2 = preprocessLine(str2);
        if (DiffRow.Tag.INSERT == tag) {
            if (this.mergeOriginalRevised) {
                preprocessLine = ((String) this.newTag.apply(tag, true)) + preprocessLine2 + ((String) this.newTag.apply(tag, false));
            } else if (this.showInlineDiffs) {
                preprocessLine2 = ((String) this.newTag.apply(tag, true)) + preprocessLine2 + ((String) this.newTag.apply(tag, false));
            }
        }
        return new DiffRow(tag, preprocessLine, preprocessLine2);
    }

    private DiffRow buildDiffRowWithoutNormalizing(DiffRow.Tag tag, String str, String str2) {
        return new DiffRow(tag, StringUtils.wrapText(str, this.columnWidth), StringUtils.wrapText(str2, this.columnWidth));
    }

    List normalizeLines(List list) {
        if (this.reportLinesUnchanged) {
            return list;
        }
        Stream stream = list.stream();
        Function function = this.lineNormalizer;
        function.getClass();
        return (List) stream.map(new DiffRowGenerator$$ExternalSyntheticLambda6(function)).collect(Collectors.toList());
    }

    private List generateInlineDiffs(AbstractDelta abstractDelta) {
        int i;
        List normalizeLines = normalizeLines(abstractDelta.getSource().getLines());
        List normalizeLines2 = normalizeLines(abstractDelta.getTarget().getLines());
        String m = DiffRowGenerator$$ExternalSyntheticBackport0.m("\n", normalizeLines);
        String m2 = DiffRowGenerator$$ExternalSyntheticBackport0.m("\n", normalizeLines2);
        List list = (List) this.inlineDiffSplitter.apply(m);
        List list2 = (List) this.inlineDiffSplitter.apply(m2);
        List deltas = DiffUtils.diff(list, list2, this.equalizer).getDeltas();
        Collections.reverse(deltas);
        Iterator it = deltas.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            AbstractDelta abstractDelta2 = (AbstractDelta) it.next();
            Chunk source = abstractDelta2.getSource();
            Chunk target = abstractDelta2.getTarget();
            if (abstractDelta2.getType() == DeltaType.DELETE) {
                wrapInTag(list, source.getPosition(), source.getPosition() + source.size(), DiffRow.Tag.DELETE, this.oldTag, this.processDiffs, this.replaceOriginalLinefeedInChangesWithSpaces && this.mergeOriginalRevised);
            } else if (abstractDelta2.getType() == DeltaType.INSERT) {
                if (this.mergeOriginalRevised) {
                    list.addAll(source.getPosition(), list2.subList(target.getPosition(), target.getPosition() + target.size()));
                    wrapInTag(list, source.getPosition(), target.size() + source.getPosition(), DiffRow.Tag.INSERT, this.newTag, this.processDiffs, false);
                } else {
                    wrapInTag(list2, target.getPosition(), target.size() + target.getPosition(), DiffRow.Tag.INSERT, this.newTag, this.processDiffs, false);
                }
            } else if (abstractDelta2.getType() == DeltaType.CHANGE) {
                if (this.mergeOriginalRevised) {
                    list.addAll(source.getPosition() + source.size(), list2.subList(target.getPosition(), target.getPosition() + target.size()));
                    wrapInTag(list, source.getPosition() + source.size(), source.getPosition() + source.size() + target.size(), DiffRow.Tag.CHANGE, this.newTag, this.processDiffs, false);
                } else {
                    wrapInTag(list2, target.getPosition(), target.size() + target.getPosition(), DiffRow.Tag.CHANGE, this.newTag, this.processDiffs, false);
                }
                wrapInTag(list, source.getPosition(), source.getPosition() + source.size(), DiffRow.Tag.CHANGE, this.oldTag, this.processDiffs, this.replaceOriginalLinefeedInChangesWithSpaces && this.mergeOriginalRevised);
            }
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            sb.append((String) it2.next());
        }
        Iterator it3 = list2.iterator();
        while (it3.hasNext()) {
            sb2.append((String) it3.next());
        }
        List asList = Arrays.asList(sb.toString().split("\n"));
        List asList2 = Arrays.asList(sb2.toString().split("\n"));
        ArrayList arrayList = new ArrayList();
        while (i < Math.max(asList.size(), asList2.size())) {
            DiffRow.Tag tag = DiffRow.Tag.CHANGE;
            String str = "";
            String str2 = asList.size() > i ? (String) asList.get(i) : "";
            if (asList2.size() > i) {
                str = (String) asList2.get(i);
            }
            arrayList.add(buildDiffRowWithoutNormalizing(tag, str2, str));
            i++;
        }
        return arrayList;
    }

    private String preprocessLine(String str) {
        if (this.columnWidth == 0) {
            return (String) this.lineNormalizer.apply(str);
        }
        return StringUtils.wrapText((String) this.lineNormalizer.apply(str), this.columnWidth);
    }

    public static class Builder {
        private int columnWidth;
        private boolean decompressDeltas;
        private BiPredicate equalizer;
        private boolean ignoreWhiteSpaces;
        private Function inlineDiffSplitter;
        private Function lineNormalizer;
        private boolean mergeOriginalRevised;
        private BiFunction newTag;
        private BiFunction oldTag;
        private Function processDiffs;
        private boolean replaceOriginalLinefeedInChangesWithSpaces;
        private boolean reportLinesUnchanged;
        private boolean showInlineDiffs;

        /* synthetic */ Builder(1 r1) {
            this();
        }

        static /* synthetic */ boolean access$100(Builder builder) {
            return builder.showInlineDiffs;
        }

        static /* synthetic */ boolean access$1000(Builder builder) {
            return builder.reportLinesUnchanged;
        }

        static /* synthetic */ Function access$1100(Builder builder) {
            return builder.lineNormalizer;
        }

        static /* synthetic */ Function access$1200(Builder builder) {
            return builder.processDiffs;
        }

        static /* synthetic */ boolean access$1300(Builder builder) {
            return builder.replaceOriginalLinefeedInChangesWithSpaces;
        }

        static /* synthetic */ boolean access$200(Builder builder) {
            return builder.ignoreWhiteSpaces;
        }

        static /* synthetic */ BiFunction access$300(Builder builder) {
            return builder.oldTag;
        }

        static /* synthetic */ BiFunction access$400(Builder builder) {
            return builder.newTag;
        }

        static /* synthetic */ int access$500(Builder builder) {
            return builder.columnWidth;
        }

        static /* synthetic */ boolean access$600(Builder builder) {
            return builder.mergeOriginalRevised;
        }

        static /* synthetic */ Function access$700(Builder builder) {
            return builder.inlineDiffSplitter;
        }

        static /* synthetic */ boolean access$800(Builder builder) {
            return builder.decompressDeltas;
        }

        static /* synthetic */ BiPredicate access$900(Builder builder) {
            return builder.equalizer;
        }

        static /* synthetic */ String lambda$new$0(DiffRow.Tag tag, Boolean bool) {
            return bool.booleanValue() ? "<span class=\"editOldInline\">" : "</span>";
        }

        static /* synthetic */ String lambda$new$1(DiffRow.Tag tag, Boolean bool) {
            return bool.booleanValue() ? "<span class=\"editNewInline\">" : "</span>";
        }

        private Builder() {
            this.showInlineDiffs = false;
            this.ignoreWhiteSpaces = false;
            this.decompressDeltas = true;
            this.oldTag = new DiffRowGenerator$Builder$$ExternalSyntheticLambda0();
            this.newTag = new DiffRowGenerator$Builder$$ExternalSyntheticLambda1();
            this.columnWidth = 0;
            this.mergeOriginalRevised = false;
            this.reportLinesUnchanged = false;
            this.inlineDiffSplitter = DiffRowGenerator.SPLITTER_BY_CHARACTER;
            this.lineNormalizer = DiffRowGenerator.LINE_NORMALIZER_FOR_HTML;
            this.processDiffs = null;
            this.equalizer = null;
            this.replaceOriginalLinefeedInChangesWithSpaces = false;
        }

        public Builder showInlineDiffs(boolean z) {
            this.showInlineDiffs = z;
            return this;
        }

        public Builder ignoreWhiteSpaces(boolean z) {
            this.ignoreWhiteSpaces = z;
            return this;
        }

        public Builder reportLinesUnchanged(boolean z) {
            this.reportLinesUnchanged = z;
            return this;
        }

        public Builder oldTag(BiFunction biFunction) {
            this.oldTag = biFunction;
            return this;
        }

        static /* synthetic */ String lambda$oldTag$2(Function function, DiffRow.Tag tag, Boolean bool) {
            return (String) function.apply(bool);
        }

        public Builder oldTag(Function function) {
            this.oldTag = new DiffRowGenerator$Builder$$ExternalSyntheticLambda2(function);
            return this;
        }

        public Builder newTag(BiFunction biFunction) {
            this.newTag = biFunction;
            return this;
        }

        static /* synthetic */ String lambda$newTag$3(Function function, DiffRow.Tag tag, Boolean bool) {
            return (String) function.apply(bool);
        }

        public Builder newTag(Function function) {
            this.newTag = new DiffRowGenerator$Builder$$ExternalSyntheticLambda3(function);
            return this;
        }

        public Builder processDiffs(Function function) {
            this.processDiffs = function;
            return this;
        }

        public Builder columnWidth(int i) {
            if (i >= 0) {
                this.columnWidth = i;
            }
            return this;
        }

        public DiffRowGenerator build() {
            return new DiffRowGenerator(this, null);
        }

        public Builder mergeOriginalRevised(boolean z) {
            this.mergeOriginalRevised = z;
            return this;
        }

        public Builder decompressDeltas(boolean z) {
            this.decompressDeltas = z;
            return this;
        }

        public Builder inlineDiffByWord(boolean z) {
            this.inlineDiffSplitter = z ? DiffRowGenerator.SPLITTER_BY_WORD : DiffRowGenerator.SPLITTER_BY_CHARACTER;
            return this;
        }

        public Builder inlineDiffBySplitter(Function function) {
            this.inlineDiffSplitter = function;
            return this;
        }

        public Builder lineNormalizer(Function function) {
            this.lineNormalizer = function;
            return this;
        }

        public Builder equalizer(BiPredicate biPredicate) {
            this.equalizer = biPredicate;
            return this;
        }

        public Builder replaceOriginalLinefeedInChangesWithSpaces(boolean z) {
            this.replaceOriginalLinefeedInChangesWithSpaces = z;
            return this;
        }
    }
}
