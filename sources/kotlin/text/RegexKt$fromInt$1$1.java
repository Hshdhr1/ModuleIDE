package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

/* compiled from: Regex.kt */
@Metadata(k = 3, mv = {2, 1, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes62.dex */
public final class RegexKt$fromInt$1$1 implements Function1 {
    final /* synthetic */ int $value;

    public RegexKt$fromInt$1$1(int i) {
        this.$value = i;
    }

    public final Boolean invoke(Enum r3) {
        FlagEnum flagEnum = (FlagEnum) r3;
        return Boolean.valueOf((this.$value & flagEnum.getMask()) == flagEnum.getValue());
    }
}
