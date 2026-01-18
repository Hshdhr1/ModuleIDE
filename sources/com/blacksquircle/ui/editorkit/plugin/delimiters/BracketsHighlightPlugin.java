package com.blacksquircle.ui.editorkit.plugin.delimiters;

import android.text.style.BackgroundColorSpan;
import android.util.Log;
import com.blacksquircle.ui.editorkit.model.ColorScheme;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: BracketsHighlightPlugin.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0016J\u0018\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/delimiters/BracketsHighlightPlugin;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "()V", "closedDelimiterSpan", "Landroid/text/style/BackgroundColorSpan;", "delimiters", "", "openDelimiterSpan", "checkMatchingBracket", "", "pos", "", "onAttached", "editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "onColorSchemeChanged", "colorScheme", "Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "onSelectionChanged", "selStart", "selEnd", "showBracket", "i", "j", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class BracketsHighlightPlugin extends EditorPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String PLUGIN_ID = "brackets-highlight-1180";

    @NotNull
    private BackgroundColorSpan closedDelimiterSpan;

    @NotNull
    private final char[] delimiters;

    @NotNull
    private BackgroundColorSpan openDelimiterSpan;

    public BracketsHighlightPlugin() {
        super("brackets-highlight-1180");
        this.delimiters = new char[]{'{', '[', '(', '<', '}', ']', ')', '>'};
        this.openDelimiterSpan = new BackgroundColorSpan(-7829368);
        this.closedDelimiterSpan = new BackgroundColorSpan(-7829368);
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onAttached(editText);
        Log.d("brackets-highlight-1180", "BracketsHighlight plugin loaded successfully!");
    }

    public void onColorSchemeChanged(@NotNull ColorScheme colorScheme) {
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
        super.onColorSchemeChanged(colorScheme);
        this.openDelimiterSpan = new BackgroundColorSpan(colorScheme.getDelimiterBackgroundColor());
        this.closedDelimiterSpan = new BackgroundColorSpan(colorScheme.getDelimiterBackgroundColor());
    }

    public void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart == selEnd) {
            checkMatchingBracket(selStart);
        }
    }

    private final void checkMatchingBracket(int pos) {
        if (getEditText().getLayout() == null) {
            return;
        }
        getEditText().getText().removeSpan(this.openDelimiterSpan);
        getEditText().getText().removeSpan(this.closedDelimiterSpan);
        if (pos <= 0 || pos > getEditText().getText().length()) {
            return;
        }
        int i = pos - 1;
        char charAt = getEditText().getText().charAt(i);
        int length = this.delimiters.length;
        int i2 = 0;
        while (i2 < length) {
            char[] cArr = this.delimiters;
            if (cArr[i2] == charAt) {
                int length2 = cArr.length / 2;
                int i3 = 1;
                boolean z = i2 <= length2 + (-1);
                char c = cArr[(length2 + i2) % cArr.length];
                if (z) {
                    int i4 = pos;
                    while (true) {
                        if (i4 >= getEditText().getText().length()) {
                            break;
                        }
                        if (getEditText().getText().charAt(i4) == c) {
                            i3--;
                        }
                        if (getEditText().getText().charAt(i4) == charAt) {
                            i3++;
                        }
                        if (i3 == 0) {
                            showBracket(i, i4);
                            break;
                        }
                        i4++;
                    }
                } else {
                    int i5 = pos - 2;
                    while (true) {
                        if (i5 < 0) {
                            break;
                        }
                        if (getEditText().getText().charAt(i5) == c) {
                            i3--;
                        }
                        if (getEditText().getText().charAt(i5) == charAt) {
                            i3++;
                        }
                        if (i3 == 0) {
                            showBracket(i5, i);
                            break;
                        }
                        i5--;
                    }
                }
            }
            i2++;
        }
    }

    private final void showBracket(int i, int j) {
        getEditText().getText().setSpan(this.openDelimiterSpan, i, i + 1, 33);
        getEditText().getText().setSpan(this.closedDelimiterSpan, j, j + 1, 33);
    }

    /* compiled from: BracketsHighlightPlugin.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/delimiters/BracketsHighlightPlugin$Companion;", "", "()V", "PLUGIN_ID", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
