package com.github.difflib.unifieddiff;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.io.Writer;
import java.util.function.Consumer;

/* compiled from: D8$$SyntheticClass */
@SynthesizedClassV2(apiLevel = -2, kind = 18, versionHash = "58e1befcef90c64cc6d403d58c95352586f3f4919e4e23215f3f69a97b1a49c4")
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final /* synthetic */ class UnifiedDiffWriter$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ Writer f$0;

    public /* synthetic */ UnifiedDiffWriter$$ExternalSyntheticLambda4(Writer writer) {
        this.f$0 = writer;
    }

    public final void accept(Object obj) {
        UnifiedDiffWriter.lambda$write$0(this.f$0, (String) obj);
    }
}
