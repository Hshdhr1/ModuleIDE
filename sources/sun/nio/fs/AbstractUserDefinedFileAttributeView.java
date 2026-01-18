package sun.nio.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.attribute.UserDefinedFileAttributeView;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AbstractUserDefinedFileAttributeView implements UserDefinedFileAttributeView, DynamicFileAttributeView {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected AbstractUserDefinedFileAttributeView() {
    }

    protected void checkAccess(String str, boolean z, boolean z2) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            if (z) {
                securityManager.checkRead(str);
            }
            if (z2) {
                securityManager.checkWrite(str);
            }
            securityManager.checkPermission(new RuntimePermission("accessUserDefinedAttributes"));
        }
    }

    public final String name() {
        return "user";
    }

    public final void setAttribute(String str, Object obj) throws IOException {
        ByteBuffer byteBuffer;
        if (obj instanceof byte[]) {
            byteBuffer = ByteBuffer.wrap((byte[]) obj);
        } else {
            byteBuffer = (ByteBuffer) obj;
        }
        write(str, byteBuffer);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002a, code lost:
    
        r6 = new java.util.HashMap();
        r0 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0037, code lost:
    
        if (r0.hasNext() == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0039, code lost:
    
        r1 = (java.lang.String) r0.next();
        r2 = size(r1);
        r3 = new byte[r2];
        r4 = read(r1, java.nio.ByteBuffer.wrap(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004d, code lost:
    
        if (r4 != r2) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0050, code lost:
    
        r3 = java.util.Arrays.copyOf(r3, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0054, code lost:
    
        r6.put(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0058, code lost:
    
        return r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map readAttributes(java.lang.String[] r6) throws java.io.IOException {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            int r1 = r6.length
            r2 = 0
        L7:
            if (r2 >= r1) goto L2a
            r3 = r6[r2]
            java.lang.String r4 = "*"
            boolean r4 = r3.equals(r4)
            if (r4 == 0) goto L18
            java.util.List r0 = r5.list()
            goto L2a
        L18:
            boolean r4 = r3.isEmpty()
            if (r4 != 0) goto L24
            r0.add(r3)
            int r2 = r2 + 1
            goto L7
        L24:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            r6.<init>()
            throw r6
        L2a:
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            java.util.Iterator r0 = r0.iterator()
        L33:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L58
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            int r2 = r5.size(r1)
            byte[] r3 = new byte[r2]
            java.nio.ByteBuffer r4 = java.nio.ByteBuffer.wrap(r3)
            int r4 = r5.read(r1, r4)
            if (r4 != r2) goto L50
            goto L54
        L50:
            byte[] r3 = java.util.Arrays.copyOf(r3, r4)
        L54:
            r6.put(r1, r3)
            goto L33
        L58:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: sun.nio.fs.AbstractUserDefinedFileAttributeView.readAttributes(java.lang.String[]):java.util.Map");
    }
}
