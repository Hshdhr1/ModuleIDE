package java.nio.file.attribute;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.io.IOException;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface PosixFileAttributeView extends BasicFileAttributeView, FileOwnerAttributeView {
    String name();

    PosixFileAttributes readAttributes() throws IOException;

    void setGroup(GroupPrincipal groupPrincipal) throws IOException;

    void setPermissions(Set set) throws IOException;

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ BasicFileAttributes $default$readAttributes(PosixFileAttributeView _this) throws IOException {
            return _this.readAttributes();
        }
    }
}
