package com.blacksquircle.ui.editorkit.widget.internal;

import android.text.Editable;
import android.text.TextWatcher;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

/* compiled from: LineNumbersEditText.kt */
@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J*\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0016J*\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016¨\u0006\u000e"}, d2 = {"com/blacksquircle/ui/editorkit/widget/internal/LineNumbersEditText$textWatcher$1", "Landroid/text/TextWatcher;", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "", "count", "after", "onTextChanged", "before", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class LineNumbersEditText$textWatcher$1 implements TextWatcher {
    final /* synthetic */ LineNumbersEditText this$0;

    LineNumbersEditText$textWatcher$1(LineNumbersEditText lineNumbersEditText) {
        this.this$0 = lineNumbersEditText;
    }

    public void beforeTextChanged(@Nullable CharSequence s, int start, int count, int after) {
        this.this$0.doBeforeTextChanged(s, start, count, after);
    }

    public void onTextChanged(@Nullable CharSequence s, int start, int before, int count) {
        this.this$0.doOnTextChanged(s, start, before, count);
    }

    public void afterTextChanged(@Nullable Editable s) {
        this.this$0.doAfterTextChanged(s);
    }
}
