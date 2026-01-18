package org.antlr.v4.runtime.misc;

import java.lang.reflect.Method;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes61.dex */
public class TestRig {
    public static void main(String[] args) {
        try {
            Class<?> testRigClass = Class.forName("org.antlr.v4.gui.TestRig");
            System.err.println("Warning: TestRig moved to org.antlr.v4.gui.TestRig; calling automatically");
            try {
                Method mainMethod = testRigClass.getMethod("main", new Class[]{String[].class});
                mainMethod.invoke((Object) null, new Object[]{args});
            } catch (Exception e) {
                System.err.println("Problems calling org.antlr.v4.gui.TestRig.main(args)");
            }
        } catch (ClassNotFoundException e2) {
            System.err.println("Use of TestRig now requires the use of the tool jar, antlr-4.X-complete.jar");
            System.err.println("Maven users need group ID org.antlr and artifact ID antlr4");
        }
    }
}
