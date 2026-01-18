package com.github.difflib;

import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.Patch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class UnifiedDiffUtils {
    private static final String NULL_FILE_INDICATOR = "/dev/null";
    private static final Pattern UNIFIED_DIFF_CHUNK_REGEXP = Pattern.compile("^@@\\s+-(?:(\\d+)(?:,(\\d+))?)\\s+\\+(?:(\\d+)(?:,(\\d+))?)\\s+@@$");

    public static Patch parseUnifiedDiff(List list) {
        ArrayList arrayList = new ArrayList();
        Patch patch = new Patch();
        Iterator it = list.iterator();
        int i = 0;
        int i2 = 0;
        boolean z = true;
        while (it.hasNext()) {
            String str = (String) it.next();
            if (z) {
                if (str.startsWith("+++")) {
                    z = false;
                }
            } else {
                Matcher matcher = UNIFIED_DIFF_CHUNK_REGEXP.matcher(str);
                if (matcher.find()) {
                    processLinesInPrevChunk(arrayList, patch, i, i2);
                    i = matcher.group(1) == null ? 1 : Integer.parseInt(matcher.group(1));
                    i2 = matcher.group(3) == null ? 1 : Integer.parseInt(matcher.group(3));
                    if (i == 0) {
                        i = 1;
                    }
                    if (i2 == 0) {
                        i2 = 1;
                    }
                } else if (str.length() > 0) {
                    String substring = str.substring(0, 1);
                    String substring2 = str.substring(1);
                    if (" ".equals(substring) || "+".equals(substring) || "-".equals(substring)) {
                        arrayList.add(new String[]{substring, substring2});
                    }
                } else {
                    arrayList.add(new String[]{" ", ""});
                }
            }
        }
        processLinesInPrevChunk(arrayList, patch, i, i2);
        return patch;
    }

    private static void processLinesInPrevChunk(List list, Patch patch, int i, int i2) {
        if (list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        Iterator it = list.iterator();
        int i3 = 0;
        int i4 = 0;
        while (it.hasNext()) {
            String[] strArr = (String[]) it.next();
            String str = strArr[0];
            String str2 = strArr[1];
            if (" ".equals(str) || "-".equals(str)) {
                i3++;
                arrayList.add(str2);
                if ("-".equals(str)) {
                    arrayList3.add(Integer.valueOf((i - 1) + i3));
                }
            }
            if (" ".equals(str) || "+".equals(str)) {
                i4++;
                arrayList2.add(str2);
                if ("+".equals(str)) {
                    arrayList4.add(Integer.valueOf((i2 - 1) + i4));
                }
            }
        }
        patch.addDelta(new ChangeDelta(new Chunk(i - 1, (List) arrayList, (List) arrayList3), new Chunk(i2 - 1, (List) arrayList2, (List) arrayList4)));
        list.clear();
    }

    public static List generateUnifiedDiff(String str, String str2, List list, Patch patch, int i) {
        if (!patch.getDeltas().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("--- " + ((String) Optional.ofNullable(str).orElse("/dev/null")));
            arrayList.add("+++ " + ((String) Optional.ofNullable(str2).orElse("/dev/null")));
            ArrayList arrayList2 = new ArrayList(patch.getDeltas());
            ArrayList arrayList3 = new ArrayList();
            boolean z = false;
            AbstractDelta abstractDelta = (AbstractDelta) arrayList2.get(0);
            arrayList3.add(abstractDelta);
            if (arrayList2.size() > 1) {
                int i2 = 1;
                while (i2 < arrayList2.size()) {
                    int position = abstractDelta.getSource().getPosition();
                    AbstractDelta abstractDelta2 = (AbstractDelta) arrayList2.get(i2);
                    if (position + abstractDelta.getSource().size() + i >= abstractDelta2.getSource().getPosition() - i) {
                        arrayList3.add(abstractDelta2);
                    } else {
                        arrayList.addAll(processDeltas(list, arrayList3, i, false));
                        arrayList3.clear();
                        arrayList3.add(abstractDelta2);
                    }
                    i2++;
                    abstractDelta = abstractDelta2;
                }
            }
            if (arrayList2.size() == 1 && str == null) {
                z = true;
            }
            arrayList.addAll(processDeltas(list, arrayList3, i, z));
            return arrayList;
        }
        return new ArrayList();
    }

    private static List processDeltas(List list, List list2, int i, boolean z) {
        int position;
        ArrayList arrayList = new ArrayList();
        AbstractDelta abstractDelta = (AbstractDelta) list2.get(0);
        int i2 = 1;
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
        int i3 = 0;
        int i4 = 0;
        while (position3 < abstractDelta.getSource().getPosition()) {
            arrayList.add(" " + ((String) list.get(position3)));
            i3++;
            i4++;
            position3++;
        }
        arrayList.addAll(getDeltaText(abstractDelta));
        int size = i3 + abstractDelta.getSource().getLines().size();
        int size2 = i4 + abstractDelta.getTarget().getLines().size();
        while (i2 < list2.size()) {
            AbstractDelta abstractDelta2 = (AbstractDelta) list2.get(i2);
            for (int position4 = abstractDelta.getSource().getPosition() + abstractDelta.getSource().getLines().size(); position4 < abstractDelta2.getSource().getPosition(); position4++) {
                arrayList.add(" " + ((String) list.get(position4)));
                size++;
                size2++;
            }
            arrayList.addAll(getDeltaText(abstractDelta2));
            size += abstractDelta2.getSource().getLines().size();
            size2 += abstractDelta2.getTarget().getLines().size();
            i2++;
            abstractDelta = abstractDelta2;
        }
        int position5 = abstractDelta.getSource().getPosition() + abstractDelta.getSource().getLines().size();
        for (int i5 = position5; i5 < position5 + i && i5 < list.size(); i5++) {
            arrayList.add(" " + ((String) list.get(i5)));
            size++;
            size2++;
        }
        arrayList.add(0, "@@ -" + position + "," + size + " +" + position2 + "," + size2 + " @@");
        return arrayList;
    }

    private static List getDeltaText(AbstractDelta abstractDelta) {
        ArrayList arrayList = new ArrayList();
        Iterator it = abstractDelta.getSource().getLines().iterator();
        while (it.hasNext()) {
            arrayList.add("-" + ((String) it.next()));
        }
        Iterator it2 = abstractDelta.getTarget().getLines().iterator();
        while (it2.hasNext()) {
            arrayList.add("+" + ((String) it2.next()));
        }
        return arrayList;
    }

    private UnifiedDiffUtils() {
    }

    public static List generateOriginalAndDiff(List list, List list2) {
        return generateOriginalAndDiff(list, list2, null, null);
    }

    public static List generateOriginalAndDiff(List list, List list2, String str, String str2) {
        String str3 = str == null ? "original" : str;
        if (str == null) {
            str = "revised";
        }
        List generateUnifiedDiff = generateUnifiedDiff(str3, str, list, DiffUtils.diff(list, list2), 0);
        if (generateUnifiedDiff.isEmpty()) {
            generateUnifiedDiff.add("--- " + str3);
            generateUnifiedDiff.add("+++ " + str);
            generateUnifiedDiff.add("@@ -0,0 +0,0 @@");
        } else if (generateUnifiedDiff.size() >= 3 && !((String) generateUnifiedDiff.get(2)).contains("@@ -1,")) {
            generateUnifiedDiff.set(1, generateUnifiedDiff.get(1));
            generateUnifiedDiff.add(2, "@@ -0,0 +0,0 @@");
        }
        return insertOrig((List) list.stream().map(new UnifiedDiffUtils$$ExternalSyntheticLambda0()).collect(Collectors.toList()), generateUnifiedDiff);
    }

    static /* synthetic */ String lambda$generateOriginalAndDiff$0(String str) {
        return " " + str;
    }

    private static List insertOrig(List list, List list2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int i = 0;
        while (true) {
            if (i >= list2.size()) {
                break;
            }
            String str = (String) list2.get(i);
            if (str.startsWith("@@") && !"@@ -0,0 +0,0 @@".equals(str) && !str.contains("@@ -1,")) {
                ArrayList arrayList4 = new ArrayList();
                arrayList4.addAll(arrayList3);
                arrayList2.add(arrayList4);
                arrayList3.clear();
                arrayList3.add(str);
            } else {
                if (i == list2.size() - 1) {
                    arrayList3.add(str);
                    ArrayList arrayList5 = new ArrayList();
                    arrayList5.addAll(arrayList3);
                    arrayList2.add(arrayList5);
                    arrayList3.clear();
                    break;
                }
                arrayList3.add(str);
            }
            i++;
        }
        insertOrig(arrayList2, arrayList, list);
        return arrayList;
    }

    private static void insertOrig(List list, List list2, List list3) {
        int i = 0;
        while (i < list.size()) {
            List list4 = (List) list.get(i);
            List list5 = i == list.size() + (-1) ? null : (List) list.get(i + 1);
            String str = (String) (i == 0 ? list4.get(2) : list4.get(0));
            String str2 = list5 != null ? (String) list5.get(0) : null;
            insert(list2, list4);
            Map rowMap = getRowMap(str);
            if (str2 != null) {
                insert(list2, getOrigList(list3, ((Integer) rowMap.get("orgRow")).intValue() != 0 ? (((Integer) rowMap.get("orgRow")).intValue() + ((Integer) rowMap.get("orgDel")).intValue()) - 1 : 0, ((Integer) getRowMap(str2).get("revRow")).intValue() - 2));
            }
            int intValue = (((Integer) rowMap.get("orgRow")).intValue() + ((Integer) rowMap.get("orgDel")).intValue()) - 1;
            if (intValue == -1) {
                intValue = 0;
            }
            if (!str.contains("@@ -1,") || str2 != null || ((Integer) rowMap.get("orgDel")).intValue() == list3.size()) {
                if (str2 == null && (((Integer) rowMap.get("orgRow")).intValue() + ((Integer) rowMap.get("orgDel")).intValue()) - 1 < list3.size()) {
                    insert(list2, getOrigList(list3, intValue, list3.size() - 1));
                }
            } else {
                insert(list2, getOrigList(list3, intValue, list3.size() - 1));
            }
            i++;
        }
    }

    private static void insert(List list, List list2) {
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            list.add((String) it.next());
        }
    }

    private static Map getRowMap(String str) {
        HashMap hashMap = new HashMap();
        if (str.startsWith("@@")) {
            String str2 = str.split(" ")[1];
            String[] split = str2.split(",");
            hashMap.put("orgRow", Integer.valueOf(split[0].substring(1)));
            hashMap.put("orgDel", Integer.valueOf(split[1]));
            String[] split2 = str2.split(",");
            hashMap.put("revRow", Integer.valueOf(split2[0].substring(1)));
            hashMap.put("revAdd", Integer.valueOf(split2[1]));
        }
        return hashMap;
    }

    private static List getOrigList(List list, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        if (list.size() >= 1 && i <= i2 && i2 < list.size()) {
            while (i <= i2) {
                arrayList.add(list.get(i));
                i++;
            }
        }
        return arrayList;
    }
}
