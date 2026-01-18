package desugar.sun.nio.fs;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.regex.Pattern;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public final /* synthetic */ class DesugarLinuxFileSystem$$ExternalSyntheticLambda4 implements PathMatcher {
    public final /* synthetic */ Pattern f$0;

    public /* synthetic */ DesugarLinuxFileSystem$$ExternalSyntheticLambda4(Pattern pattern) {
        this.f$0 = pattern;
    }

    public final boolean matches(Path path) {
        return DesugarLinuxFileSystem.lambda$getPathMatcher$0(this.f$0, path);
    }
}
