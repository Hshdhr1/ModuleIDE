package kotlin.io.path;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.nio.file.Path;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final /* synthetic */ class PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ ArrayList f$0;
    public final /* synthetic */ Function3 f$1;
    public final /* synthetic */ Path f$2;
    public final /* synthetic */ Path f$3;
    public final /* synthetic */ Path f$4;
    public final /* synthetic */ Function3 f$5;

    public /* synthetic */ PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticLambda2(ArrayList arrayList, Function3 function3, Path path, Path path2, Path path3, Function3 function32) {
        this.f$0 = arrayList;
        this.f$1 = function3;
        this.f$2 = path;
        this.f$3 = path2;
        this.f$4 = path3;
        this.f$5 = function32;
    }

    public final Object invoke(Object obj) {
        return PathsKt__PathRecursiveFunctionsKt.$r8$lambda$QF8Uhuq7fTQtpHX-cuQgT1yM3No(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, (FileVisitorBuilder) obj);
    }
}
