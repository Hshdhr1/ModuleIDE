package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class VocabularyImpl implements Vocabulary {
    private static final String[] EMPTY_NAMES = new String[0];
    public static final VocabularyImpl EMPTY_VOCABULARY = new VocabularyImpl(EMPTY_NAMES, EMPTY_NAMES, EMPTY_NAMES);
    private final String[] displayNames;
    private final String[] literalNames;
    private final int maxTokenType;
    private final String[] symbolicNames;

    public VocabularyImpl(String[] literalNames, String[] symbolicNames) {
        this(literalNames, symbolicNames, null);
    }

    public VocabularyImpl(String[] literalNames, String[] symbolicNames, String[] displayNames) {
        this.literalNames = literalNames == null ? EMPTY_NAMES : literalNames;
        this.symbolicNames = symbolicNames == null ? EMPTY_NAMES : symbolicNames;
        this.displayNames = displayNames == null ? EMPTY_NAMES : displayNames;
        this.maxTokenType = Math.max(this.displayNames.length, Math.max(this.literalNames.length, this.symbolicNames.length)) - 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.antlr.v4.runtime.Vocabulary fromTokenNames(java.lang.String[] r7) {
        /*
            r6 = 0
            if (r7 == 0) goto L6
            int r5 = r7.length
            if (r5 != 0) goto L9
        L6:
            org.antlr.v4.runtime.VocabularyImpl r5 = org.antlr.v4.runtime.VocabularyImpl.EMPTY_VOCABULARY
        L8:
            return r5
        L9:
            int r5 = r7.length
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r7, r5)
            java.lang.String[] r2 = (java.lang.String[]) r2
            int r5 = r7.length
            java.lang.Object[] r3 = java.util.Arrays.copyOf(r7, r5)
            java.lang.String[] r3 = (java.lang.String[]) r3
            r1 = 0
        L18:
            int r5 = r7.length
            if (r1 >= r5) goto L42
            r4 = r7[r1]
            if (r4 != 0) goto L22
        L1f:
            int r1 = r1 + 1
            goto L18
        L22:
            boolean r5 = r4.isEmpty()
            if (r5 != 0) goto L3d
            r5 = 0
            char r0 = r4.charAt(r5)
            r5 = 39
            if (r0 != r5) goto L34
            r3[r1] = r6
            goto L1f
        L34:
            boolean r5 = java.lang.Character.isUpperCase(r0)
            if (r5 == 0) goto L3d
            r2[r1] = r6
            goto L1f
        L3d:
            r2[r1] = r6
            r3[r1] = r6
            goto L1f
        L42:
            org.antlr.v4.runtime.VocabularyImpl r5 = new org.antlr.v4.runtime.VocabularyImpl
            r5.<init>(r2, r3, r7)
            goto L8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.antlr.v4.runtime.VocabularyImpl.fromTokenNames(java.lang.String[]):org.antlr.v4.runtime.Vocabulary");
    }

    public int getMaxTokenType() {
        return this.maxTokenType;
    }

    public String getLiteralName(int tokenType) {
        if (tokenType < 0 || tokenType >= this.literalNames.length) {
            return null;
        }
        return this.literalNames[tokenType];
    }

    public String getSymbolicName(int tokenType) {
        if (tokenType >= 0 && tokenType < this.symbolicNames.length) {
            return this.symbolicNames[tokenType];
        }
        if (tokenType == -1) {
            return "EOF";
        }
        return null;
    }

    public String getDisplayName(int tokenType) {
        String displayName;
        if (tokenType < 0 || tokenType >= this.displayNames.length || (displayName = this.displayNames[tokenType]) == null) {
            String literalName = getLiteralName(tokenType);
            if (literalName != null) {
                return literalName;
            }
            String symbolicName = getSymbolicName(tokenType);
            return symbolicName != null ? symbolicName : Integer.toString(tokenType);
        }
        return displayName;
    }
}
