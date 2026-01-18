package java.nio.file.attribute;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.io.IOException;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public interface DosFileAttributeView extends BasicFileAttributeView {
    String name();

    DosFileAttributes readAttributes() throws IOException;

    void setArchive(boolean z) throws IOException;

    void setHidden(boolean z) throws IOException;

    void setReadOnly(boolean z) throws IOException;

    void setSystem(boolean z) throws IOException;

    @SynthesizedClassV2(apiLevel = -2, kind = 10, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
    public final /* synthetic */ class -CC {
        public static /* bridge */ /* synthetic */ BasicFileAttributes $default$readAttributes(DosFileAttributeView _this) throws IOException {
            return _this.readAttributes();
        }
    }
}
