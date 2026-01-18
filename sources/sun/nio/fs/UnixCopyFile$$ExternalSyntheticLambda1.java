package sun.nio.fs;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class UnixCopyFile$$ExternalSyntheticLambda1 implements DirectoryStream.Filter {
    public final boolean accept(Object obj) {
        return UnixCopyFile.lambda$ensureEmptyDir$0((Path) obj);
    }
}
