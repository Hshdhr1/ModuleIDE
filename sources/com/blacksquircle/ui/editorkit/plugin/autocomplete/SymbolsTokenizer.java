package com.blacksquircle.ui.editorkit.plugin.autocomplete;

import android.widget.MultiAutoCompleteTextView;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* compiled from: SymbolsTokenizer.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\r\n\u0002\b\u0005\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0016J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/SymbolsTokenizer;", "Landroid/widget/MultiAutoCompleteTextView$Tokenizer;", "()V", "findTokenEnd", "", "text", "", "cursor", "findTokenStart", "terminateToken", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class SymbolsTokenizer implements MultiAutoCompleteTextView.Tokenizer {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String TOKEN = "!@#$%^&*()_+-={}|[]:;'<>/<.? \r\n\t";

    @NotNull
    public CharSequence terminateToken(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        return text;
    }

    /* compiled from: SymbolsTokenizer.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/SymbolsTokenizer$Companion;", "", "()V", "TOKEN", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public int findTokenStart(@NotNull CharSequence text, int cursor) {
        Intrinsics.checkNotNullParameter(text, "text");
        int i = cursor;
        while (i > 0 && !StringsKt.contains$default("!@#$%^&*()_+-={}|[]:;'<>/<.? \r\n\t", text.charAt(i - 1), false, 2, (Object) null)) {
            i--;
        }
        while (i < cursor && text.charAt(i) == ' ') {
            i++;
        }
        return i;
    }

    public int findTokenEnd(@NotNull CharSequence text, int cursor) {
        Intrinsics.checkNotNullParameter(text, "text");
        while (cursor < text.length()) {
            if (StringsKt.contains$default("!@#$%^&*()_+-={}|[]:;'<>/<.? \r\n\t", text.charAt(cursor - 1), false, 2, (Object) null)) {
                return cursor;
            }
            cursor++;
        }
        return text.length();
    }
}
