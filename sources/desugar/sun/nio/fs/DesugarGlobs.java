package desugar.sun.nio.fs;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class DesugarGlobs {
    private static char EOL = 0;
    private static final String globMetaChars = "\\*?[{";
    private static final String regexMetaChars = ".^$+{[]|()";

    private DesugarGlobs() {
    }

    private static boolean isRegexMeta(char c) {
        return ".^$+{[]|()".indexOf(c) != -1;
    }

    private static boolean isGlobMeta(char c) {
        return "\\*?[{".indexOf(c) != -1;
    }

    private static char next(String glob, int i) {
        if (i < glob.length()) {
            return glob.charAt(i);
        }
        return EOL;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x010c, code lost:
    
        throw new java.util.regex.PatternSyntaxException("Explicit 'name separator' in class", r16, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00f8, code lost:
    
        r3 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x010d, code lost:
    
        if (r7 != ']') goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x010f, code lost:
    
        r1.append("]]");
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x011e, code lost:
    
        throw new java.util.regex.PatternSyntaxException("Missing ']", r16, r3 - 1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String toRegexPattern(java.lang.String r16, boolean r17) {
        /*
            Method dump skipped, instructions count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: desugar.sun.nio.fs.DesugarGlobs.toRegexPattern(java.lang.String, boolean):java.lang.String");
    }

    static String toUnixRegexPattern(String globPattern) {
        return toRegexPattern(globPattern, false);
    }

    static String toWindowsRegexPattern(String globPattern) {
        return toRegexPattern(globPattern, true);
    }
}
