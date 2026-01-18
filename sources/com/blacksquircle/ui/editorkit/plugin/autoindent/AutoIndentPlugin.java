package com.blacksquircle.ui.editorkit.plugin.autoindent;

import android.util.Log;
import com.blacksquircle.ui.editorkit.ExtensionsKt;
import com.blacksquircle.ui.editorkit.model.TextChange;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AutoIndentPlugin.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0003\u0018\u0000 %2\u00020\u0001:\u0001%B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\u001d\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u00182\u0006\u0010\u0014\u001a\u00020\u0015H\u0002¢\u0006\u0002\u0010\u0019J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0015H\u0002J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0015H\u0002J\u0010\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020 H\u0016J*\u0010!\u001a\u00020\u00132\b\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/autoindent/AutoIndentPlugin;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "()V", "autoCloseBrackets", "", "getAutoCloseBrackets", "()Z", "setAutoCloseBrackets", "(Z)V", "autoCloseQuotes", "getAutoCloseQuotes", "setAutoCloseQuotes", "autoIndentLines", "getAutoIndentLines", "setAutoIndentLines", "isAutoIndenting", "newText", "", "completeIndentation", "", "start", "", "count", "executeIndentation", "", "(I)[Ljava/lang/String;", "getIndentationForLine", "line", "getIndentationForOffset", "offset", "onAttached", "editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "onTextChanged", "text", "", "before", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class AutoIndentPlugin extends EditorPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String PLUGIN_ID = "autoindent-7401";
    private boolean autoCloseBrackets;
    private boolean autoCloseQuotes;
    private boolean autoIndentLines;
    private boolean isAutoIndenting;

    @NotNull
    private String newText;

    public static /* synthetic */ void $r8$lambda$mI2w4mBZvo6qJ9BR6jYWCFKUBOs(AutoIndentPlugin autoIndentPlugin, int i, int i2, String str, int i3) {
        completeIndentation$lambda$0(autoIndentPlugin, i, i2, str, i3);
    }

    public AutoIndentPlugin() {
        super("autoindent-7401");
        this.autoIndentLines = true;
        this.autoCloseBrackets = true;
        this.autoCloseQuotes = true;
        this.newText = "";
    }

    public final boolean getAutoIndentLines() {
        return this.autoIndentLines;
    }

    public final void setAutoIndentLines(boolean z) {
        this.autoIndentLines = z;
    }

    public final boolean getAutoCloseBrackets() {
        return this.autoCloseBrackets;
    }

    public final void setAutoCloseBrackets(boolean z) {
        this.autoCloseBrackets = z;
    }

    public final boolean getAutoCloseQuotes() {
        return this.autoCloseQuotes;
    }

    public final void setAutoCloseQuotes(boolean z) {
        this.autoCloseQuotes = z;
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onAttached(editText);
        Log.d("autoindent-7401", "AutoIndent plugin loaded successfully!");
    }

    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        super.onTextChanged(text, start, before, count);
        this.newText = String.valueOf(text != null ? text.subSequence(start, start + count) : null);
        completeIndentation(start, count);
        this.newText = "";
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void completeIndentation(int r11, int r12) {
        /*
            r10 = this;
            boolean r0 = r10.isAutoIndenting
            if (r0 != 0) goto L6a
            java.lang.String[] r0 = r10.executeIndentation(r11)
            r1 = 0
            r1 = r0[r1]
            r2 = 1
            java.lang.String r3 = ""
            if (r1 != 0) goto L1f
            r4 = r0[r2]
            if (r4 == 0) goto L15
            goto L1f
        L15:
            r1 = 2
            r1 = r0[r1]
            if (r1 == 0) goto L6a
            if (r1 != 0) goto L1d
            goto L47
        L1d:
            r8 = r1
            goto L48
        L1f:
            if (r1 != 0) goto L22
            r1 = r3
        L22:
            r2 = r0[r2]
            if (r2 != 0) goto L27
            r2 = r3
        L27:
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r3)
            if (r4 == 0) goto L33
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r3 != 0) goto L6a
        L33:
            java.lang.String r3 = r10.newText
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            r4.append(r3)
            r4.append(r2)
            java.lang.String r3 = r4.toString()
        L47:
            r8 = r3
        L48:
            r1 = 3
            r0 = r0[r1]
            if (r0 == 0) goto L55
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            int r0 = java.lang.Integer.parseInt(r0)
            goto L5a
        L55:
            int r0 = r8.length()
            int r0 = r0 + r11
        L5a:
            r9 = r0
            com.blacksquircle.ui.editorkit.widget.TextProcessor r0 = r10.getEditText()
            com.blacksquircle.ui.editorkit.plugin.autoindent.AutoIndentPlugin$$ExternalSyntheticLambda0 r4 = new com.blacksquircle.ui.editorkit.plugin.autoindent.AutoIndentPlugin$$ExternalSyntheticLambda0
            r5 = r10
            r6 = r11
            r7 = r12
            r4.<init>(r5, r6, r7, r8, r9)
            r0.post(r4)
        L6a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blacksquircle.ui.editorkit.plugin.autoindent.AutoIndentPlugin.completeIndentation(int, int):void");
    }

    private static final void completeIndentation$lambda$0(AutoIndentPlugin autoIndentPlugin, int i, int i2, String str, int i3) {
        Intrinsics.checkNotNullParameter(autoIndentPlugin, "this$0");
        Intrinsics.checkNotNullParameter(str, "$replacementValue");
        autoIndentPlugin.isAutoIndenting = true;
        autoIndentPlugin.getEditText().getText().replace(i, i2 + i, (CharSequence) str);
        autoIndentPlugin.getUndoStack().pop();
        TextChange pop = autoIndentPlugin.getUndoStack().pop();
        if (!Intrinsics.areEqual(str, "")) {
            pop.setNewText(str);
            autoIndentPlugin.getUndoStack().push(pop);
        }
        ExtensionsKt.setSelectionIndex(autoIndentPlugin.getEditText(), i3);
        autoIndentPlugin.isAutoIndenting = false;
    }

    private final String[] executeIndentation(int start) {
        int i;
        if (Intrinsics.areEqual(this.newText, "\n") && this.autoIndentLines) {
            String indentationForOffset = getIndentationForOffset(start);
            StringBuilder sb = new StringBuilder(indentationForOffset);
            int length = sb.length() + start + 1;
            if (start > 0 && getEditText().getText().charAt(start - 1) == '{') {
                sb.append(getEditText().tab());
                length = sb.length() + start + 1;
            }
            int i2 = start + 1;
            if (i2 < getEditText().getText().length() && getEditText().getText().charAt(i2) == '}') {
                sb.append("\n");
                sb.append(indentationForOffset);
            }
            String[] strArr = new String[4];
            strArr[1] = sb.toString();
            strArr[3] = String.valueOf(length);
            return strArr;
        }
        if (Intrinsics.areEqual(this.newText, "\"") && this.autoCloseQuotes) {
            int i3 = start + 1;
            if (i3 >= getEditText().getText().length()) {
                String[] strArr2 = new String[4];
                strArr2[1] = "\"";
                strArr2[3] = String.valueOf(i3);
                return strArr2;
            }
            if (getEditText().getText().charAt(i3) == '\"' && getEditText().getText().charAt(start - 1) != '\\') {
                String[] strArr3 = new String[4];
                strArr3[2] = "";
                strArr3[3] = String.valueOf(i3);
                return strArr3;
            }
            if (getEditText().getText().charAt(i3) != '\"' || getEditText().getText().charAt(start - 1) != '\\') {
                String[] strArr4 = new String[4];
                strArr4[1] = "\"";
                strArr4[3] = String.valueOf(i3);
                return strArr4;
            }
        } else if (Intrinsics.areEqual(this.newText, "'") && this.autoCloseQuotes) {
            int i4 = start + 1;
            if (i4 >= getEditText().getText().length()) {
                String[] strArr5 = new String[4];
                strArr5[1] = "'";
                strArr5[3] = String.valueOf(i4);
                return strArr5;
            }
            if (i4 >= getEditText().getText().length()) {
                String[] strArr6 = new String[4];
                strArr6[1] = "'";
                strArr6[3] = String.valueOf(i4);
                return strArr6;
            }
            if (getEditText().getText().charAt(i4) == '\'' && start > 0 && getEditText().getText().charAt(start - 1) != '\\') {
                String[] strArr7 = new String[4];
                strArr7[2] = "";
                strArr7[3] = String.valueOf(i4);
                return strArr7;
            }
            if (getEditText().getText().charAt(i4) != '\'' || start <= 0 || getEditText().getText().charAt(start - 1) != '\\') {
                String[] strArr8 = new String[4];
                strArr8[1] = "'";
                strArr8[3] = String.valueOf(i4);
                return strArr8;
            }
        } else {
            if (Intrinsics.areEqual(this.newText, "{") && this.autoCloseBrackets) {
                String[] strArr9 = new String[4];
                strArr9[1] = "}";
                strArr9[3] = String.valueOf(start + 1);
                return strArr9;
            }
            if (Intrinsics.areEqual(this.newText, "}") && this.autoCloseBrackets) {
                int i5 = start + 1;
                if (i5 < getEditText().getText().length() && getEditText().getText().charAt(i5) == '}') {
                    String[] strArr10 = new String[4];
                    strArr10[2] = "";
                    strArr10[3] = String.valueOf(i5);
                    return strArr10;
                }
            } else {
                if (Intrinsics.areEqual(this.newText, "(") && this.autoCloseBrackets) {
                    String[] strArr11 = new String[4];
                    strArr11[1] = ")";
                    strArr11[3] = String.valueOf(start + 1);
                    return strArr11;
                }
                if (Intrinsics.areEqual(this.newText, ")") && this.autoCloseBrackets) {
                    int i6 = start + 1;
                    if (i6 < getEditText().getText().length() && getEditText().getText().charAt(i6) == ')') {
                        String[] strArr12 = new String[4];
                        strArr12[2] = "";
                        strArr12[3] = String.valueOf(i6);
                        return strArr12;
                    }
                } else {
                    if (Intrinsics.areEqual(this.newText, "[") && this.autoCloseBrackets) {
                        String[] strArr13 = new String[4];
                        strArr13[1] = "]";
                        strArr13[3] = String.valueOf(start + 1);
                        return strArr13;
                    }
                    if (Intrinsics.areEqual(this.newText, "]") && this.autoCloseBrackets && (i = start + 1) < getEditText().getText().length() && getEditText().getText().charAt(i) == ']') {
                        String[] strArr14 = new String[4];
                        strArr14[2] = "";
                        strArr14[3] = String.valueOf(i);
                        return strArr14;
                    }
                }
            }
        }
        return new String[4];
    }

    private final String getIndentationForOffset(int offset) {
        return getIndentationForLine(getStructure().getLineForIndex(offset));
    }

    private final String getIndentationForLine(int line) {
        int start = getStructure().getLine(line).getStart();
        int i = start;
        while (i < getEditText().getText().length()) {
            char charAt = getEditText().getText().charAt(i);
            if (!CharsKt.isWhitespace(charAt) || charAt == '\n') {
                break;
            }
            i++;
        }
        return getEditText().getText().subSequence(start, i).toString();
    }

    /* compiled from: AutoIndentPlugin.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/autoindent/AutoIndentPlugin$Companion;", "", "()V", "PLUGIN_ID", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
