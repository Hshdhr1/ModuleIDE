package org.antlr.v4.runtime;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class RuntimeMetaData {
    public static final String VERSION = "4.9.2";

    public static String getRuntimeVersion() {
        return "4.9.2";
    }

    public static void checkVersion(String generatingToolVersion, String compileTimeVersion) {
        boolean runtimeConflictsWithGeneratingTool = false;
        if (generatingToolVersion != null) {
            runtimeConflictsWithGeneratingTool = ("4.9.2".equals(generatingToolVersion) || getMajorMinorVersion("4.9.2").equals(getMajorMinorVersion(generatingToolVersion))) ? false : true;
        }
        boolean runtimeConflictsWithCompileTimeTool = ("4.9.2".equals(compileTimeVersion) || getMajorMinorVersion("4.9.2").equals(getMajorMinorVersion(compileTimeVersion))) ? false : true;
        if (runtimeConflictsWithGeneratingTool) {
            System.err.printf("ANTLR Tool version %s used for code generation does not match the current runtime version %s%n", new Object[]{generatingToolVersion, "4.9.2"});
        }
        if (runtimeConflictsWithCompileTimeTool) {
            System.err.printf("ANTLR Runtime version %s used for parser compilation does not match the current runtime version %s%n", new Object[]{compileTimeVersion, "4.9.2"});
        }
    }

    public static String getMajorMinorVersion(String version) {
        int firstDot = version.indexOf(46);
        int secondDot = firstDot >= 0 ? version.indexOf(46, firstDot + 1) : -1;
        int firstDash = version.indexOf(45);
        int referenceLength = version.length();
        if (secondDot >= 0) {
            referenceLength = Math.min(referenceLength, secondDot);
        }
        if (firstDash >= 0) {
            referenceLength = Math.min(referenceLength, firstDash);
        }
        return version.substring(0, referenceLength);
    }
}
