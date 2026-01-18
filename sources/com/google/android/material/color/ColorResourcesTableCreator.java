package com.google.android.material.color;

import android.content.Context;
import android.util.Pair;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes37.dex */
final class ColorResourcesTableCreator {
    private static final byte ANDROID_PACKAGE_ID = 1;
    private static final byte APPLICATION_PACKAGE_ID = Byte.MAX_VALUE;
    private static final short HEADER_TYPE_PACKAGE = 512;
    private static final short HEADER_TYPE_RES_TABLE = 2;
    private static final short HEADER_TYPE_STRING_POOL = 1;
    private static final short HEADER_TYPE_TYPE = 513;
    private static final short HEADER_TYPE_TYPE_SPEC = 514;
    private static final String RESOURCE_TYPE_NAME_COLOR = "color";
    private static byte typeIdColor;
    private static final PackageInfo ANDROID_PACKAGE_INFO = new PackageInfo(1, "android");
    private static final Comparator COLOR_RESOURCE_COMPARATOR = new 1();

    static /* synthetic */ byte[] access$1200(char x0) {
        return charToByteArray(x0);
    }

    static /* synthetic */ byte access$1300() {
        return typeIdColor;
    }

    static /* synthetic */ Comparator access$400() {
        return COLOR_RESOURCE_COMPARATOR;
    }

    static /* synthetic */ byte[] access$500(int x0) {
        return intToByteArray(x0);
    }

    static /* synthetic */ byte[] access$600(short x0) {
        return shortToByteArray(x0);
    }

    static /* synthetic */ byte[] access$800(String x0) {
        return stringToByteArrayUtf8(x0);
    }

    static /* synthetic */ byte[] access$900(String x0) {
        return stringToByteArray(x0);
    }

    private ColorResourcesTableCreator() {
    }

    class 1 implements Comparator {
        1() {
        }

        public int compare(ColorResource res1, ColorResource res2) {
            return ColorResource.access$000(res1) - ColorResource.access$000(res2);
        }
    }

    static byte[] create(Context context, Map map) throws IOException {
        PackageInfo packageInfo;
        if (map.entrySet().isEmpty()) {
            throw new IllegalArgumentException("No color resources provided for harmonization.");
        }
        PackageInfo applicationPackageInfo = new PackageInfo(127, context.getPackageName());
        HashMap hashMap = new HashMap();
        ColorResource colorResource = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            colorResource = new ColorResource(((Integer) entry.getKey()).intValue(), context.getResources().getResourceName(((Integer) entry.getKey()).intValue()), ((Integer) entry.getValue()).intValue());
            if (!context.getResources().getResourceTypeName(((Integer) entry.getKey()).intValue()).equals("color")) {
                throw new IllegalArgumentException("Non color resource found: name=" + ColorResource.access$100(colorResource) + ", typeId=" + Integer.toHexString(ColorResource.access$200(colorResource) & 255));
            }
            if (ColorResource.access$300(colorResource) == 1) {
                packageInfo = ANDROID_PACKAGE_INFO;
            } else if (ColorResource.access$300(colorResource) == Byte.MAX_VALUE) {
                packageInfo = applicationPackageInfo;
            } else {
                throw new IllegalArgumentException("Not supported with unknown package id: " + ColorResource.access$300(colorResource));
            }
            if (!hashMap.containsKey(packageInfo)) {
                hashMap.put(packageInfo, new ArrayList());
            }
            ((List) hashMap.get(packageInfo)).add(colorResource);
        }
        byte access$200 = ColorResource.access$200(colorResource);
        typeIdColor = access$200;
        if (access$200 == 0) {
            throw new IllegalArgumentException("No color resources found for harmonization.");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new ResTable(hashMap).writeTo(outputStream);
        return outputStream.toByteArray();
    }

    private static class ResTable {
        private static final short HEADER_SIZE = 12;
        private final ResChunkHeader header;
        private final int packageCount;
        private final List packageChunks = new ArrayList();
        private final StringPoolChunk stringPool = new StringPoolChunk(new String[0]);

        ResTable(Map map) {
            this.packageCount = map.size();
            for (Map.Entry<PackageInfo, List<ColorResource>> entry : map.entrySet()) {
                List<ColorResource> colorResources = (List) entry.getValue();
                Collections.sort(colorResources, ColorResourcesTableCreator.access$400());
                this.packageChunks.add(new PackageChunk((PackageInfo) entry.getKey(), colorResources));
            }
            this.header = new ResChunkHeader((short) 2, (short) 12, getOverallSize());
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(ColorResourcesTableCreator.access$500(this.packageCount));
            this.stringPool.writeTo(outputStream);
            for (PackageChunk packageChunk : this.packageChunks) {
                packageChunk.writeTo(outputStream);
            }
        }

        private int getOverallSize() {
            int packageChunkSize = 0;
            for (PackageChunk packageChunk : this.packageChunks) {
                packageChunkSize += packageChunk.getChunkSize();
            }
            return this.stringPool.getChunkSize() + 12 + packageChunkSize;
        }
    }

    private static class ResChunkHeader {
        private final int chunkSize;
        private final short headerSize;
        private final short type;

        ResChunkHeader(short type, short headerSize, int chunkSize) {
            this.type = type;
            this.headerSize = headerSize;
            this.chunkSize = chunkSize;
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            outputStream.write(ColorResourcesTableCreator.access$600(this.type));
            outputStream.write(ColorResourcesTableCreator.access$600(this.headerSize));
            outputStream.write(ColorResourcesTableCreator.access$500(this.chunkSize));
        }
    }

    private static class StringPoolChunk {
        private static final int FLAG_UTF8 = 256;
        private static final short HEADER_SIZE = 28;
        private static final int STYLED_SPAN_LIST_END = -1;
        private final int chunkSize;
        private final ResChunkHeader header;
        private final int stringCount;
        private final List stringIndex;
        private final List strings;
        private final int stringsPaddingSize;
        private final int stringsStart;
        private final int styledSpanCount;
        private final List styledSpanIndex;
        private final List styledSpans;
        private final int styledSpansStart;
        private final boolean utf8Encode;

        StringPoolChunk(String... rawStrings) {
            this(false, rawStrings);
        }

        StringPoolChunk(boolean utf8, String... rawStrings) {
            this.stringIndex = new ArrayList();
            this.styledSpanIndex = new ArrayList();
            this.strings = new ArrayList();
            this.styledSpans = new ArrayList();
            this.utf8Encode = utf8;
            int stringOffset = 0;
            for (String string : rawStrings) {
                Pair<byte[], List<StringStyledSpan>> processedString = processString(string);
                this.stringIndex.add(Integer.valueOf(stringOffset));
                stringOffset += ((byte[]) processedString.first).length;
                this.strings.add(processedString.first);
                this.styledSpans.add(processedString.second);
            }
            int styledSpanOffset = 0;
            for (List<StringStyledSpan> styledSpanList : this.styledSpans) {
                for (StringStyledSpan styledSpan : styledSpanList) {
                    this.stringIndex.add(Integer.valueOf(stringOffset));
                    stringOffset += StringStyledSpan.access$700(styledSpan).length;
                    this.strings.add(StringStyledSpan.access$700(styledSpan));
                }
                this.styledSpanIndex.add(Integer.valueOf(styledSpanOffset));
                styledSpanOffset += (styledSpanList.size() * 12) + 4;
            }
            int stringOffsetResidue = stringOffset % 4;
            int i = stringOffsetResidue == 0 ? 0 : 4 - stringOffsetResidue;
            this.stringsPaddingSize = i;
            int size = this.strings.size();
            this.stringCount = size;
            this.styledSpanCount = this.strings.size() - rawStrings.length;
            boolean hasStyledSpans = this.strings.size() - rawStrings.length > 0;
            if (!hasStyledSpans) {
                this.styledSpanIndex.clear();
                this.styledSpans.clear();
            }
            int size2 = (size * 4) + 28 + (this.styledSpanIndex.size() * 4);
            this.stringsStart = size2;
            int stringsSize = i + stringOffset;
            this.styledSpansStart = hasStyledSpans ? size2 + stringsSize : 0;
            int i2 = size2 + stringsSize + (hasStyledSpans ? styledSpanOffset : 0);
            this.chunkSize = i2;
            this.header = new ResChunkHeader((short) 1, (short) 28, i2);
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(ColorResourcesTableCreator.access$500(this.stringCount));
            outputStream.write(ColorResourcesTableCreator.access$500(this.styledSpanCount));
            outputStream.write(ColorResourcesTableCreator.access$500(this.utf8Encode ? 256 : 0));
            outputStream.write(ColorResourcesTableCreator.access$500(this.stringsStart));
            outputStream.write(ColorResourcesTableCreator.access$500(this.styledSpansStart));
            for (Integer index : this.stringIndex) {
                outputStream.write(ColorResourcesTableCreator.access$500(index.intValue()));
            }
            for (Integer index2 : this.styledSpanIndex) {
                outputStream.write(ColorResourcesTableCreator.access$500(index2.intValue()));
            }
            for (byte[] string : this.strings) {
                outputStream.write(string);
            }
            int i = this.stringsPaddingSize;
            if (i > 0) {
                outputStream.write(new byte[i]);
            }
            for (List<StringStyledSpan> styledSpanList : this.styledSpans) {
                for (StringStyledSpan styledSpan : styledSpanList) {
                    styledSpan.writeTo(outputStream);
                }
                outputStream.write(ColorResourcesTableCreator.access$500(-1));
            }
        }

        int getChunkSize() {
            return this.chunkSize;
        }

        private Pair processString(String rawString) {
            return new Pair(this.utf8Encode ? ColorResourcesTableCreator.access$800(rawString) : ColorResourcesTableCreator.access$900(rawString), Collections.emptyList());
        }
    }

    private static class StringStyledSpan {
        private int firstCharacterIndex;
        private int lastCharacterIndex;
        private int nameReference;
        private byte[] styleString;

        private StringStyledSpan() {
        }

        static /* synthetic */ byte[] access$700(StringStyledSpan x0) {
            return x0.styleString;
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            outputStream.write(ColorResourcesTableCreator.access$500(this.nameReference));
            outputStream.write(ColorResourcesTableCreator.access$500(this.firstCharacterIndex));
            outputStream.write(ColorResourcesTableCreator.access$500(this.lastCharacterIndex));
        }
    }

    private static class PackageChunk {
        private static final short HEADER_SIZE = 288;
        private static final int PACKAGE_NAME_MAX_LENGTH = 128;
        private final ResChunkHeader header;
        private final StringPoolChunk keyStrings;
        private final PackageInfo packageInfo;
        private final TypeSpecChunk typeSpecChunk;
        private final StringPoolChunk typeStrings = new StringPoolChunk(false, "?1", "?2", "?3", "?4", "?5", "color");

        PackageChunk(PackageInfo packageInfo, List list) {
            this.packageInfo = packageInfo;
            String[] keys = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                keys[i] = ColorResource.access$100((ColorResource) list.get(i));
            }
            this.keyStrings = new StringPoolChunk(true, keys);
            this.typeSpecChunk = new TypeSpecChunk(list);
            this.header = new ResChunkHeader((short) 512, (short) 288, getChunkSize());
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(ColorResourcesTableCreator.access$500(PackageInfo.access$1000(this.packageInfo)));
            char[] packageName = PackageInfo.access$1100(this.packageInfo).toCharArray();
            for (int i = 0; i < 128; i++) {
                if (i < packageName.length) {
                    outputStream.write(ColorResourcesTableCreator.access$1200(packageName[i]));
                } else {
                    outputStream.write(ColorResourcesTableCreator.access$1200((char) 0));
                }
            }
            outputStream.write(ColorResourcesTableCreator.access$500(288));
            outputStream.write(ColorResourcesTableCreator.access$500(0));
            outputStream.write(ColorResourcesTableCreator.access$500(this.typeStrings.getChunkSize() + 288));
            outputStream.write(ColorResourcesTableCreator.access$500(0));
            outputStream.write(ColorResourcesTableCreator.access$500(0));
            this.typeStrings.writeTo(outputStream);
            this.keyStrings.writeTo(outputStream);
            this.typeSpecChunk.writeTo(outputStream);
        }

        int getChunkSize() {
            return this.typeStrings.getChunkSize() + 288 + this.keyStrings.getChunkSize() + this.typeSpecChunk.getChunkSizeWithTypeChunk();
        }
    }

    private static class TypeSpecChunk {
        private static final short HEADER_SIZE = 16;
        private static final int SPEC_PUBLIC = 1073741824;
        private final int entryCount;
        private final int[] entryFlags;
        private final ResChunkHeader header;
        private final TypeChunk typeChunk;

        TypeSpecChunk(List list) {
            this.entryCount = ColorResource.access$000((ColorResource) list.get(list.size() - 1)) + 1;
            HashSet hashSet = new HashSet();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ColorResource colorResource = (ColorResource) it.next();
                hashSet.add(Short.valueOf(ColorResource.access$000(colorResource)));
            }
            this.entryFlags = new int[this.entryCount];
            for (short entryId = 0; entryId < this.entryCount; entryId = (short) (entryId + 1)) {
                if (hashSet.contains(Short.valueOf(entryId))) {
                    this.entryFlags[entryId] = 1073741824;
                }
            }
            this.header = new ResChunkHeader((short) 514, (short) 16, getChunkSize());
            this.typeChunk = new TypeChunk(list, hashSet, this.entryCount);
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(new byte[]{ColorResourcesTableCreator.access$1300(), 0, 0, 0});
            outputStream.write(ColorResourcesTableCreator.access$500(this.entryCount));
            for (int entryFlag : this.entryFlags) {
                outputStream.write(ColorResourcesTableCreator.access$500(entryFlag));
            }
            this.typeChunk.writeTo(outputStream);
        }

        int getChunkSizeWithTypeChunk() {
            return getChunkSize() + this.typeChunk.getChunkSize();
        }

        private int getChunkSize() {
            return (this.entryCount * 4) + 16;
        }
    }

    private static class TypeChunk {
        private static final byte CONFIG_SIZE = 64;
        private static final short HEADER_SIZE = 84;
        private static final int OFFSET_NO_ENTRY = -1;
        private final byte[] config;
        private final int entryCount;
        private final ResChunkHeader header;
        private final int[] offsetTable;
        private final ResEntry[] resEntries;

        TypeChunk(List list, Set set, int entryCount) {
            byte[] bArr = new byte[64];
            this.config = bArr;
            this.entryCount = entryCount;
            bArr[0] = 64;
            this.resEntries = new ResEntry[list.size()];
            for (int index = 0; index < list.size(); index++) {
                ColorResource colorResource = (ColorResource) list.get(index);
                this.resEntries[index] = new ResEntry(index, ColorResource.access$1400(colorResource));
            }
            this.offsetTable = new int[entryCount];
            int currentOffset = 0;
            for (short entryId = 0; entryId < entryCount; entryId = (short) (entryId + 1)) {
                if (set.contains(Short.valueOf(entryId))) {
                    this.offsetTable[entryId] = currentOffset;
                    currentOffset += 16;
                } else {
                    this.offsetTable[entryId] = -1;
                }
            }
            this.header = new ResChunkHeader((short) 513, (short) 84, getChunkSize());
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            this.header.writeTo(outputStream);
            outputStream.write(new byte[]{ColorResourcesTableCreator.access$1300(), 0, 0, 0});
            outputStream.write(ColorResourcesTableCreator.access$500(this.entryCount));
            outputStream.write(ColorResourcesTableCreator.access$500(getEntryStart()));
            outputStream.write(this.config);
            for (int offset : this.offsetTable) {
                outputStream.write(ColorResourcesTableCreator.access$500(offset));
            }
            for (ResEntry entry : this.resEntries) {
                entry.writeTo(outputStream);
            }
        }

        int getChunkSize() {
            return getEntryStart() + (this.resEntries.length * 16);
        }

        private int getEntryStart() {
            return getOffsetTableSize() + 84;
        }

        private int getOffsetTableSize() {
            return this.offsetTable.length * 4;
        }
    }

    private static class ResEntry {
        private static final byte DATA_TYPE_AARRGGBB = 28;
        private static final short ENTRY_SIZE = 8;
        private static final short FLAG_PUBLIC = 2;
        private static final int SIZE = 16;
        private static final short VALUE_SIZE = 8;
        private final int data;
        private final int keyStringIndex;

        ResEntry(int keyStringIndex, int data) {
            this.keyStringIndex = keyStringIndex;
            this.data = data;
        }

        void writeTo(ByteArrayOutputStream outputStream) throws IOException {
            outputStream.write(ColorResourcesTableCreator.access$600((short) 8));
            outputStream.write(ColorResourcesTableCreator.access$600((short) 2));
            outputStream.write(ColorResourcesTableCreator.access$500(this.keyStringIndex));
            outputStream.write(ColorResourcesTableCreator.access$600((short) 8));
            outputStream.write(new byte[]{0, 28});
            outputStream.write(ColorResourcesTableCreator.access$500(this.data));
        }
    }

    static class PackageInfo {
        private final int id;
        private final String name;

        static /* synthetic */ int access$1000(PackageInfo x0) {
            return x0.id;
        }

        static /* synthetic */ String access$1100(PackageInfo x0) {
            return x0.name;
        }

        PackageInfo(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class ColorResource {
        private final short entryId;
        private final String name;
        private final byte packageId;
        private final byte typeId;
        private final int value;

        static /* synthetic */ short access$000(ColorResource x0) {
            return x0.entryId;
        }

        static /* synthetic */ String access$100(ColorResource x0) {
            return x0.name;
        }

        static /* synthetic */ int access$1400(ColorResource x0) {
            return x0.value;
        }

        static /* synthetic */ byte access$200(ColorResource x0) {
            return x0.typeId;
        }

        static /* synthetic */ byte access$300(ColorResource x0) {
            return x0.packageId;
        }

        ColorResource(int id, String name, int value) {
            this.name = name;
            this.value = value;
            this.entryId = (short) (65535 & id);
            this.typeId = (byte) ((id >> 16) & 255);
            this.packageId = (byte) ((id >> 24) & 255);
        }
    }

    private static byte[] shortToByteArray(short value) {
        return new byte[]{(byte) (value & 255), (byte) ((value >> 8) & 255)};
    }

    private static byte[] charToByteArray(char value) {
        return new byte[]{(byte) (value & 255), (byte) ((value >> '\b') & 255)};
    }

    private static byte[] intToByteArray(int value) {
        return new byte[]{(byte) (value & 255), (byte) ((value >> 8) & 255), (byte) ((value >> 16) & 255), (byte) ((value >> 24) & 255)};
    }

    private static byte[] stringToByteArray(String value) {
        char[] chars = value.toCharArray();
        byte[] bytes = new byte[(chars.length * 2) + 4];
        byte[] lengthBytes = shortToByteArray((short) chars.length);
        bytes[0] = lengthBytes[0];
        bytes[1] = lengthBytes[1];
        for (int i = 0; i < chars.length; i++) {
            byte[] charBytes = charToByteArray(chars[i]);
            bytes[(i * 2) + 2] = charBytes[0];
            bytes[(i * 2) + 3] = charBytes[1];
        }
        int i2 = bytes.length;
        bytes[i2 - 2] = 0;
        bytes[bytes.length - 1] = 0;
        return bytes;
    }

    private static byte[] stringToByteArrayUtf8(String value) {
        byte[] rawBytes = value.getBytes(Charset.forName("UTF-8"));
        byte stringLength = (byte) rawBytes.length;
        byte[] bytes = new byte[rawBytes.length + 3];
        System.arraycopy(rawBytes, 0, bytes, 2, stringLength);
        bytes[1] = stringLength;
        bytes[0] = stringLength;
        bytes[bytes.length - 1] = 0;
        return bytes;
    }
}
