package com.github.difflib.unifieddiff;

import com.github.difflib.patch.AbstractDelta;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public class UnifiedDiffWriter {
    private static final Logger LOG = Logger.getLogger(UnifiedDiffWriter.class.getName());

    public static void write(UnifiedDiff unifiedDiff, Function function, Writer writer, int i) throws IOException {
        UnifiedDiffWriter$$ExternalSyntheticBackport0.m(function, "original lines provider needs to be specified");
        write(unifiedDiff, function, new UnifiedDiffWriter$$ExternalSyntheticLambda4(writer), i);
    }

    static /* synthetic */ void lambda$write$0(Writer writer, String str) {
        try {
            writer.append(str).append("\n");
        } catch (IOException e) {
            LOG.log(Level.SEVERE, (String) null, e);
        }
    }

    public static void write(UnifiedDiff unifiedDiff, Function function, Consumer consumer, int i) throws IOException {
        if (unifiedDiff.getHeader() != null) {
            consumer.accept(unifiedDiff.getHeader());
        }
        for (UnifiedDiffFile unifiedDiffFile : unifiedDiff.getFiles()) {
            ArrayList arrayList = new ArrayList(unifiedDiffFile.getPatch().getDeltas());
            if (!arrayList.isEmpty()) {
                writeOrNothing(consumer, unifiedDiffFile.getDiffCommand());
                if (unifiedDiffFile.getIndex() != null) {
                    consumer.accept("index " + unifiedDiffFile.getIndex());
                }
                StringBuilder sb = new StringBuilder("--- ");
                sb.append(unifiedDiffFile.getFromFile() == null ? "/dev/null" : unifiedDiffFile.getFromFile());
                consumer.accept(sb.toString());
                if (unifiedDiffFile.getToFile() != null) {
                    consumer.accept("+++ " + unifiedDiffFile.getToFile());
                }
                List list = (List) function.apply(unifiedDiffFile.getFromFile());
                ArrayList arrayList2 = new ArrayList();
                boolean z = false;
                AbstractDelta abstractDelta = (AbstractDelta) arrayList.get(0);
                arrayList2.add(abstractDelta);
                if (arrayList.size() > 1) {
                    int i2 = 1;
                    while (i2 < arrayList.size()) {
                        int position = abstractDelta.getSource().getPosition();
                        AbstractDelta abstractDelta2 = (AbstractDelta) arrayList.get(i2);
                        if (position + abstractDelta.getSource().size() + i >= abstractDelta2.getSource().getPosition() - i) {
                            arrayList2.add(abstractDelta2);
                        } else {
                            processDeltas(consumer, list, arrayList2, i, false);
                            arrayList2.clear();
                            arrayList2.add(abstractDelta2);
                        }
                        i2++;
                        abstractDelta = abstractDelta2;
                    }
                }
                if (arrayList.size() == 1 && unifiedDiffFile.getFromFile() == null) {
                    z = true;
                }
                processDeltas(consumer, list, arrayList2, i, z);
            }
        }
        if (unifiedDiff.getTail() != null) {
            consumer.accept("--");
            consumer.accept(unifiedDiff.getTail());
        }
    }

    private static void processDeltas(Consumer consumer, List list, List list2, int i, boolean z) {
        int position;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        AbstractDelta abstractDelta = (AbstractDelta) list2.get(0);
        int i3 = 1;
        if (z) {
            position = 0;
        } else {
            position = (abstractDelta.getSource().getPosition() + 1) - i;
            if (position < 1) {
                position = 1;
            }
        }
        int position2 = (abstractDelta.getTarget().getPosition() + 1) - i;
        if (position2 < 1) {
            position2 = 1;
        }
        int position3 = abstractDelta.getSource().getPosition() - i;
        if (position3 < 0) {
            position3 = 0;
        }
        int i4 = 0;
        for (int i5 = position3; i5 < abstractDelta.getSource().getPosition() && i5 < list.size(); i5++) {
            arrayList.add(" " + ((String) list.get(i5)));
            i2++;
            i4++;
        }
        getDeltaText(new UnifiedDiffWriter$$ExternalSyntheticLambda1(arrayList), abstractDelta);
        int size = i2 + abstractDelta.getSource().getLines().size();
        int size2 = i4 + abstractDelta.getTarget().getLines().size();
        while (i3 < list2.size()) {
            AbstractDelta abstractDelta2 = (AbstractDelta) list2.get(i3);
            for (int position4 = abstractDelta.getSource().getPosition() + abstractDelta.getSource().getLines().size(); position4 < abstractDelta2.getSource().getPosition() && position4 < list.size(); position4++) {
                arrayList.add(" " + ((String) list.get(position4)));
                size++;
                size2++;
            }
            getDeltaText(new UnifiedDiffWriter$$ExternalSyntheticLambda2(arrayList), abstractDelta2);
            size += abstractDelta2.getSource().getLines().size();
            size2 += abstractDelta2.getTarget().getLines().size();
            i3++;
            abstractDelta = abstractDelta2;
        }
        int position5 = abstractDelta.getSource().getPosition() + abstractDelta.getSource().getLines().size();
        for (int i6 = position5; i6 < position5 + i && i6 < list.size(); i6++) {
            arrayList.add(" " + ((String) list.get(i6)));
            size++;
            size2++;
        }
        consumer.accept("@@ -" + position + "," + size + " +" + position2 + "," + size2 + " @@");
        arrayList.forEach(new UnifiedDiffWriter$$ExternalSyntheticLambda3(consumer));
    }

    static /* synthetic */ void lambda$processDeltas$1(List list, String str) {
        list.add(str);
    }

    static /* synthetic */ void lambda$processDeltas$2(List list, String str) {
        list.add(str);
    }

    static /* synthetic */ void lambda$processDeltas$3(Consumer consumer, String str) {
        consumer.accept(str);
    }

    private static void getDeltaText(Consumer consumer, AbstractDelta abstractDelta) {
        Iterator it = abstractDelta.getSource().getLines().iterator();
        while (it.hasNext()) {
            consumer.accept("-" + ((String) it.next()));
        }
        Iterator it2 = abstractDelta.getTarget().getLines().iterator();
        while (it2.hasNext()) {
            consumer.accept("+" + ((String) it2.next()));
        }
    }

    private static void writeOrNothing(Consumer consumer, String str) throws IOException {
        if (str != null) {
            consumer.accept(str);
        }
    }
}
