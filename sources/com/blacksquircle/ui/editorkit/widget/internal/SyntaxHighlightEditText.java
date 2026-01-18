package com.blacksquircle.ui.editorkit.widget.internal;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;
import com.blacksquircle.ui.editorkit.ExtensionsKt;
import com.blacksquircle.ui.editorkit.model.ColorScheme;
import com.blacksquircle.ui.editorkit.model.ErrorSpan;
import com.blacksquircle.ui.editorkit.model.FindParams;
import com.blacksquircle.ui.editorkit.model.FindResult;
import com.blacksquircle.ui.editorkit.model.FindResultSpan;
import com.blacksquircle.ui.editorkit.model.StyleSpan;
import com.blacksquircle.ui.editorkit.model.SyntaxHighlightSpan;
import com.blacksquircle.ui.editorkit.model.TabWidthSpan;
import com.blacksquircle.ui.editorkit.utils.EditorTheme;
import com.blacksquircle.ui.editorkit.utils.ReflectionKt;
import com.blacksquircle.ui.editorkit.utils.StylingTask;
import com.blacksquircle.ui.language.base.Language;
import com.blacksquircle.ui.language.base.model.SyntaxHighlightResult;
import com.blacksquircle.ui.language.base.model.TokenType;
import com.blacksquircle.ui.language.base.styler.LanguageStyler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SyntaxHighlightEditText.kt */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0002\b\r\b&\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010.\u001a\u00020/H\u0002J\u0006\u00100\u001a\u00020/J\u0012\u00101\u001a\u00020/2\b\u00102\u001a\u0004\u0018\u000103H\u0014J*\u00104\u001a\u00020/2\b\u00102\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u0007H\u0014J*\u00109\u001a\u00020/2\b\u00102\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u0007H\u0014J\u000e\u0010;\u001a\u00020/2\u0006\u0010<\u001a\u00020=J\u0014\u0010;\u001a\u00020/2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150>J\u0006\u0010?\u001a\u00020/J\u0006\u0010@\u001a\u00020/J\b\u0010A\u001a\u00020/H\u0014J\b\u0010B\u001a\u00020/H\u0014J(\u0010C\u001a\u00020/2\u0006\u0010D\u001a\u00020\u00072\u0006\u0010E\u001a\u00020\u00072\u0006\u0010F\u001a\u00020\u00072\u0006\u0010G\u001a\u00020\u0007H\u0014J(\u0010H\u001a\u00020/2\u0006\u0010I\u001a\u00020\u00072\u0006\u0010J\u001a\u00020\u00072\u0006\u0010K\u001a\u00020\u00072\u0006\u0010L\u001a\u00020\u0007H\u0014J\u000e\u0010M\u001a\u00020/2\u0006\u0010N\u001a\u00020OJ\u000e\u0010P\u001a\u00020/2\u0006\u0010N\u001a\u00020OJ\b\u0010Q\u001a\u00020/H\u0002J\b\u0010R\u001a\u00020/H\u0002J\u000e\u0010S\u001a\u00020/2\u0006\u0010T\u001a\u00020\u0007J\u0010\u0010U\u001a\u00020/2\u0006\u00102\u001a\u000205H\u0016J\u0018\u0010V\u001a\u00020/2\u0006\u0010W\u001a\u00020\u00072\u0006\u0010X\u001a\u00020\u0007H\u0002J\b\u0010Y\u001a\u00020/H\u0002J\u0006\u0010Z\u001a\u00020OJ\b\u0010[\u001a\u00020/H\u0002R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\u001a\u001a\u0004\u0018\u00010\u00192\b\u0010\n\u001a\u0004\u0018\u00010\u0019@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-¨\u0006\\"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/internal/SyntaxHighlightEditText;", "Lcom/blacksquircle/ui/editorkit/widget/internal/UndoRedoEditText;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "addedTextCount", "value", "Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "colorScheme", "getColorScheme", "()Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "setColorScheme", "(Lcom/blacksquircle/ui/editorkit/model/ColorScheme;)V", "findResultStyleSpan", "Lcom/blacksquircle/ui/editorkit/model/StyleSpan;", "findResults", "", "Lcom/blacksquircle/ui/editorkit/model/FindResult;", "isErrorSpansVisible", "", "isSyntaxHighlighting", "Lcom/blacksquircle/ui/language/base/Language;", "language", "getLanguage", "()Lcom/blacksquircle/ui/language/base/Language;", "setLanguage", "(Lcom/blacksquircle/ui/language/base/Language;)V", "selectedFindResult", "syntaxHighlightResults", "Lcom/blacksquircle/ui/language/base/model/SyntaxHighlightResult;", "tabWidth", "getTabWidth", "()I", "setTabWidth", "(I)V", "task", "Lcom/blacksquircle/ui/editorkit/utils/StylingTask;", "useSpacesInsteadOfTabs", "getUseSpacesInsteadOfTabs", "()Z", "setUseSpacesInsteadOfTabs", "(Z)V", "cancelSyntaxHighlighting", "", "clearFindResultSpans", "doAfterTextChanged", "text", "Landroid/text/Editable;", "doBeforeTextChanged", "", "start", "count", "after", "doOnTextChanged", "before", "find", "params", "Lcom/blacksquircle/ui/editorkit/model/FindParams;", "", "findNext", "findPrevious", "onColorSchemeChanged", "onLanguageChanged", "onScrollChanged", "horiz", "vert", "oldHoriz", "oldVert", "onSizeChanged", "w", "h", "oldw", "oldh", "replaceAllFindResults", "replaceText", "", "replaceFindResult", "scrollToFindResult", "selectResult", "setErrorLine", "lineNumber", "setTextContent", "shiftSpans", "from", "byHowMuch", "syntaxHighlight", "tab", "updateSyntaxHighlighting", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSyntaxHighlightEditText.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SyntaxHighlightEditText.kt\ncom/blacksquircle/ui/editorkit/widget/internal/SyntaxHighlightEditText\n+ 2 SpannedString.kt\nandroidx/core/text/SpannedStringKt\n*L\n1#1,415:1\n34#2:416\n34#2:417\n34#2:418\n34#2:419\n34#2:420\n*S KotlinDebug\n*F\n+ 1 SyntaxHighlightEditText.kt\ncom/blacksquircle/ui/editorkit/widget/internal/SyntaxHighlightEditText\n*L\n238#1:416\n297#1:417\n311#1:418\n351#1:419\n374#1:420\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public abstract class SyntaxHighlightEditText extends UndoRedoEditText {
    private int addedTextCount;

    @NotNull
    private ColorScheme colorScheme;

    @Nullable
    private StyleSpan findResultStyleSpan;

    @NotNull
    private final List findResults;
    private boolean isErrorSpansVisible;
    private boolean isSyntaxHighlighting;

    @Nullable
    private Language language;
    private int selectedFindResult;

    @NotNull
    private final List syntaxHighlightResults;
    private int tabWidth;

    @Nullable
    private StylingTask task;
    private boolean useSpacesInsteadOfTabs;

    /* compiled from: SyntaxHighlightEditText.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TokenType.values().length];
            try {
                iArr[TokenType.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TokenType.OPERATOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TokenType.KEYWORD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TokenType.TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TokenType.LANG_CONST.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[TokenType.PREPROCESSOR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[TokenType.VARIABLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[TokenType.METHOD.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[TokenType.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[TokenType.COMMENT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[TokenType.TAG.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[TokenType.TAG_NAME.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[TokenType.ATTR_NAME.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[TokenType.ATTR_VALUE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[TokenType.ENTITY_REF.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ CharSequence $r8$lambda$I3ALOzI0SQ31ygHw2RndtES2a3g(SyntaxHighlightEditText syntaxHighlightEditText, CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return _init_$lambda$0(syntaxHighlightEditText, charSequence, i, i2, spanned, i3, i4);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SyntaxHighlightEditText(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SyntaxHighlightEditText(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ SyntaxHighlightEditText(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 16842859 : i);
    }

    public static final /* synthetic */ List access$getSyntaxHighlightResults$p(SyntaxHighlightEditText syntaxHighlightEditText) {
        return syntaxHighlightEditText.syntaxHighlightResults;
    }

    public static final /* synthetic */ void access$updateSyntaxHighlighting(SyntaxHighlightEditText syntaxHighlightEditText) {
        syntaxHighlightEditText.updateSyntaxHighlighting();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SyntaxHighlightEditText(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.colorScheme = EditorTheme.INSTANCE.getDARCULA();
        this.useSpacesInsteadOfTabs = true;
        this.tabWidth = 4;
        this.syntaxHighlightResults = new ArrayList();
        this.findResults = new ArrayList();
        setFilters(new InputFilter[]{new SyntaxHighlightEditText$$ExternalSyntheticLambda0(this)});
    }

    @Nullable
    public final Language getLanguage() {
        return this.language;
    }

    public final void setLanguage(@Nullable Language language) {
        this.language = language;
        onLanguageChanged();
    }

    @NotNull
    public final ColorScheme getColorScheme() {
        return this.colorScheme;
    }

    public final void setColorScheme(@NotNull ColorScheme colorScheme) {
        Intrinsics.checkNotNullParameter(colorScheme, "value");
        this.colorScheme = colorScheme;
        onColorSchemeChanged();
    }

    public final boolean getUseSpacesInsteadOfTabs() {
        return this.useSpacesInsteadOfTabs;
    }

    public final void setUseSpacesInsteadOfTabs(boolean z) {
        this.useSpacesInsteadOfTabs = z;
    }

    public final int getTabWidth() {
        return this.tabWidth;
    }

    public final void setTabWidth(int i) {
        this.tabWidth = i;
    }

    private static final CharSequence _init_$lambda$0(SyntaxHighlightEditText syntaxHighlightEditText, CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        Intrinsics.checkNotNullParameter(syntaxHighlightEditText, "this$0");
        return (i2 - i != 1 || i >= charSequence.length() || i3 >= spanned.length() || charSequence.charAt(i) != '\t') ? charSequence : syntaxHighlightEditText.tab();
    }

    public void setTextContent(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.syntaxHighlightResults.clear();
        this.findResults.clear();
        super.setTextContent(text);
        syntaxHighlight();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateSyntaxHighlighting();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        updateSyntaxHighlighting();
    }

    protected void doBeforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
        this.addedTextCount -= count;
        cancelSyntaxHighlighting();
        if (!this.isSyntaxHighlighting) {
            super.doBeforeTextChanged(text, start, count, after);
        }
        abortFling();
    }

    protected void doOnTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        this.addedTextCount += count;
        if (this.isSyntaxHighlighting) {
            return;
        }
        super.doOnTextChanged(text, start, before, count);
    }

    protected void doAfterTextChanged(@Nullable Editable text) {
        if (!this.isSyntaxHighlighting) {
            shiftSpans(getSelectionStart(), this.addedTextCount);
        }
        this.addedTextCount = 0;
        syntaxHighlight();
    }

    protected void onLanguageChanged() {
        syntaxHighlight();
    }

    protected void onColorSchemeChanged() {
        this.findResultStyleSpan = new StyleSpan(this.colorScheme.getFindResultBackgroundColor(), false, false, false, false, 30, null);
        setTextColor(this.colorScheme.getTextColor());
        ReflectionKt.setCursorDrawableColor((TextView) this, this.colorScheme.getCursorColor());
        setBackgroundColor(this.colorScheme.getBackgroundColor());
        setHighlightColor(this.colorScheme.getSelectionColor());
    }

    @NotNull
    public final String tab() {
        if (this.useSpacesInsteadOfTabs) {
            return StringsKt.repeat(" ", this.tabWidth);
        }
        return "\t";
    }

    public final void setErrorLine(int lineNumber) {
        if (lineNumber > 0) {
            int i = lineNumber - 1;
            int indexForStartOfLine = getStructure().getIndexForStartOfLine(i);
            int indexForEndOfLine = getStructure().getIndexForEndOfLine(i);
            if (indexForStartOfLine >= getText().length() || indexForEndOfLine >= getText().length() || indexForStartOfLine <= -1 || indexForEndOfLine <= -1) {
                return;
            }
            this.isErrorSpansVisible = true;
            getText().setSpan(new ErrorSpan(0.0f, 0.0f, 0, 7, null), indexForStartOfLine, indexForEndOfLine, 33);
        }
    }

    public final void find(@NotNull FindParams params) {
        Pattern compile;
        Intrinsics.checkNotNullParameter(params, "params");
        clearFindResultSpans();
        if (params.getQuery().length() > 0) {
            try {
                if (params.getRegex() && params.getMatchCase()) {
                    compile = Pattern.compile(params.getQuery());
                } else if (params.getRegex() && !params.getMatchCase()) {
                    compile = Pattern.compile(params.getQuery(), 66);
                } else if (params.getWordsOnly() && params.getMatchCase()) {
                    compile = Pattern.compile("\\s" + params.getQuery() + "\\s");
                } else if (params.getWordsOnly() && !params.getMatchCase()) {
                    compile = Pattern.compile("\\s" + Pattern.quote(params.getQuery()) + "\\s", 66);
                } else {
                    compile = params.getMatchCase() ? Pattern.compile(Pattern.quote(params.getQuery())) : Pattern.compile(Pattern.quote(params.getQuery()), 66);
                }
                Matcher matcher = compile.matcher(getText());
                while (matcher.find()) {
                    this.findResults.add(new FindResult(matcher.start(), matcher.end()));
                }
                if (!this.findResults.isEmpty()) {
                    selectResult();
                }
            } catch (PatternSyntaxException unused) {
            }
        }
        updateSyntaxHighlighting();
    }

    public final void find(@NotNull List findResults) {
        Intrinsics.checkNotNullParameter(findResults, "findResults");
        clearFindResultSpans();
        Collection collection = (Collection) findResults;
        if (!collection.isEmpty()) {
            this.findResults.addAll(collection);
            selectResult();
        }
        updateSyntaxHighlighting();
    }

    public final void findNext() {
        if (this.selectedFindResult < this.findResults.size() - 1) {
            this.selectedFindResult++;
            selectResult();
        }
    }

    public final void findPrevious() {
        int i = this.selectedFindResult;
        if (i <= 0 || i >= this.findResults.size()) {
            return;
        }
        this.selectedFindResult--;
        selectResult();
    }

    public final void replaceFindResult(@NotNull String replaceText) {
        Intrinsics.checkNotNullParameter(replaceText, "replaceText");
        if (this.findResults.isEmpty()) {
            return;
        }
        FindResult findResult = (FindResult) this.findResults.get(this.selectedFindResult);
        getText().replace(findResult.getStart(), findResult.getEnd(), (CharSequence) replaceText);
        this.findResults.remove(findResult);
        if (this.selectedFindResult >= this.findResults.size()) {
            this.selectedFindResult--;
        }
    }

    public final void replaceAllFindResults(@NotNull String replaceText) {
        Intrinsics.checkNotNullParameter(replaceText, "replaceText");
        if (this.findResults.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder(getText());
        int size = this.findResults.size();
        while (true) {
            size--;
            if (-1 < size) {
                FindResult findResult = (FindResult) this.findResults.get(size);
                sb.replace(findResult.getStart(), findResult.getEnd(), replaceText);
                this.findResults.remove(size);
            } else {
                setText((CharSequence) sb.toString());
                return;
            }
        }
    }

    public final void clearFindResultSpans() {
        this.selectedFindResult = 0;
        this.findResults.clear();
        Spanned text = getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        for (FindResultSpan findResultSpan : (FindResultSpan[]) text.getSpans(0, getText().length(), FindResultSpan.class)) {
            getText().removeSpan(findResultSpan);
        }
    }

    private final void selectResult() {
        FindResult findResult = (FindResult) this.findResults.get(this.selectedFindResult);
        ExtensionsKt.setSelectionRange((EditText) this, findResult.getStart(), findResult.getEnd());
        scrollToFindResult();
    }

    private final void scrollToFindResult() {
        int scrollX;
        if (this.selectedFindResult < this.findResults.size()) {
            FindResult findResult = (FindResult) this.findResults.get(this.selectedFindResult);
            TextView textView = (TextView) this;
            if (findResult.getStart() < getLayout().getLineStart(com.blacksquircle.ui.editorkit.utils.ExtensionsKt.getTopVisibleLine(textView)) || findResult.getEnd() > getLayout().getLineEnd(com.blacksquircle.ui.editorkit.utils.ExtensionsKt.getBottomVisibleLine(textView))) {
                int height = (getLayout().getHeight() - getHeight()) + getPaddingBottom() + getPaddingTop();
                int lineTop = getLayout().getLineTop(getLayout().getLineForOffset(findResult.getStart()));
                if (lineTop <= height) {
                    height = lineTop;
                }
                if (getHorizontallyScrollable()) {
                    scrollX = (int) getLayout().getPrimaryHorizontal(findResult.getStart());
                } else {
                    scrollX = getScrollX();
                }
                scrollTo(scrollX, height);
            }
        }
    }

    private final void shiftSpans(int from, int byHowMuch) {
        for (SyntaxHighlightResult syntaxHighlightResult : this.syntaxHighlightResults) {
            if (syntaxHighlightResult.getStart() >= from) {
                syntaxHighlightResult.setStart(syntaxHighlightResult.getStart() + byHowMuch);
            }
            if (syntaxHighlightResult.getEnd() >= from) {
                syntaxHighlightResult.setEnd(syntaxHighlightResult.getEnd() + byHowMuch);
            }
        }
        for (FindResult findResult : this.findResults) {
            if (findResult.getStart() > from) {
                findResult.setStart(findResult.getStart() + byHowMuch);
            }
            if (findResult.getEnd() >= from) {
                findResult.setEnd(findResult.getEnd() + byHowMuch);
            }
        }
        if (this.isErrorSpansVisible) {
            Spanned text = getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            for (ErrorSpan errorSpan : (ErrorSpan[]) text.getSpans(0, getText().length(), ErrorSpan.class)) {
                getText().removeSpan(errorSpan);
            }
            this.isErrorSpansVisible = false;
        }
    }

    private final void updateSyntaxHighlighting() {
        int numberColor;
        if (getLayout() != null) {
            TextView textView = (TextView) this;
            int lineStart = getLayout().getLineStart(com.blacksquircle.ui.editorkit.utils.ExtensionsKt.getTopVisibleLine(textView));
            int lineEnd = getLayout().getLineEnd(com.blacksquircle.ui.editorkit.utils.ExtensionsKt.getBottomVisibleLine(textView));
            this.isSyntaxHighlighting = true;
            Spanned text = getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            for (SyntaxHighlightSpan syntaxHighlightSpan : (SyntaxHighlightSpan[]) text.getSpans(0, getText().length(), SyntaxHighlightSpan.class)) {
                getText().removeSpan(syntaxHighlightSpan);
            }
            for (SyntaxHighlightResult syntaxHighlightResult : this.syntaxHighlightResults) {
                boolean z = syntaxHighlightResult.getStart() >= 0 && syntaxHighlightResult.getEnd() <= getText().length();
                boolean z2 = syntaxHighlightResult.getStart() <= syntaxHighlightResult.getEnd();
                int start = syntaxHighlightResult.getStart();
                boolean z3 = (lineStart <= start && start <= lineEnd) || (syntaxHighlightResult.getStart() <= lineEnd && syntaxHighlightResult.getEnd() >= lineStart);
                if (z && z2 && z3) {
                    Editable text2 = getText();
                    switch (WhenMappings.$EnumSwitchMapping$0[syntaxHighlightResult.getTokenType().ordinal()]) {
                        case 1:
                            numberColor = this.colorScheme.getNumberColor();
                            break;
                        case 2:
                            numberColor = this.colorScheme.getOperatorColor();
                            break;
                        case 3:
                            numberColor = this.colorScheme.getKeywordColor();
                            break;
                        case 4:
                            numberColor = this.colorScheme.getTypeColor();
                            break;
                        case 5:
                            numberColor = this.colorScheme.getLangConstColor();
                            break;
                        case 6:
                            numberColor = this.colorScheme.getPreprocessorColor();
                            break;
                        case 7:
                            numberColor = this.colorScheme.getVariableColor();
                            break;
                        case 8:
                            numberColor = this.colorScheme.getMethodColor();
                            break;
                        case 9:
                            numberColor = this.colorScheme.getStringColor();
                            break;
                        case 10:
                            numberColor = this.colorScheme.getCommentColor();
                            break;
                        case 11:
                            numberColor = this.colorScheme.getTagColor();
                            break;
                        case 12:
                            numberColor = this.colorScheme.getTagNameColor();
                            break;
                        case 13:
                            numberColor = this.colorScheme.getAttrNameColor();
                            break;
                        case 14:
                            numberColor = this.colorScheme.getAttrValueColor();
                            break;
                        case 15:
                            numberColor = this.colorScheme.getEntityRefColor();
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    text2.setSpan(new SyntaxHighlightSpan(new StyleSpan(numberColor, false, false, false, false, 30, null)), syntaxHighlightResult.getStart() < lineStart ? lineStart : syntaxHighlightResult.getStart(), syntaxHighlightResult.getEnd() > lineEnd ? lineEnd : syntaxHighlightResult.getEnd(), 33);
                }
            }
            this.isSyntaxHighlighting = false;
            Spanned text3 = getText();
            Intrinsics.checkNotNullExpressionValue(text3, "getText(...)");
            for (FindResultSpan findResultSpan : (FindResultSpan[]) text3.getSpans(0, getText().length(), FindResultSpan.class)) {
                getText().removeSpan(findResultSpan);
            }
            StyleSpan styleSpan = this.findResultStyleSpan;
            if (styleSpan != null) {
                for (FindResult findResult : this.findResults) {
                    boolean z4 = findResult.getStart() >= 0 && findResult.getEnd() <= getText().length();
                    boolean z5 = findResult.getStart() <= findResult.getEnd();
                    int start2 = findResult.getStart();
                    boolean z6 = (lineStart <= start2 && start2 <= lineEnd) || (findResult.getStart() <= lineEnd && findResult.getEnd() >= lineStart);
                    if (z4 && z5 && z6) {
                        getText().setSpan(new FindResultSpan(styleSpan), findResult.getStart() < lineStart ? lineStart : findResult.getStart(), findResult.getEnd() > lineEnd ? lineEnd : findResult.getEnd(), 33);
                    }
                }
            }
            if (!this.useSpacesInsteadOfTabs) {
                Spanned text4 = getText();
                Intrinsics.checkNotNullExpressionValue(text4, "getText(...)");
                for (TabWidthSpan tabWidthSpan : (TabWidthSpan[]) text4.getSpans(0, getText().length(), TabWidthSpan.class)) {
                    getText().removeSpan(tabWidthSpan);
                }
                Matcher matcher = Pattern.compile("\t").matcher(getText().subSequence(lineStart, lineEnd));
                while (matcher.find()) {
                    int start3 = matcher.start() + lineStart;
                    int end = matcher.end() + lineStart;
                    if (start3 >= 0 && end <= getText().length()) {
                        getText().setSpan(new TabWidthSpan(this.tabWidth), start3, end, 18);
                    }
                }
            }
            postInvalidate();
        }
    }

    /* compiled from: SyntaxHighlightEditText.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "spans", "", "Lcom/blacksquircle/ui/language/base/model/SyntaxHighlightResult;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class 2 extends Lambda implements Function1 {
        2() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((List) obj);
            return Unit.INSTANCE;
        }

        public final void invoke(@NotNull List list) {
            Intrinsics.checkNotNullParameter(list, "spans");
            SyntaxHighlightEditText.access$getSyntaxHighlightResults$p(SyntaxHighlightEditText.this).clear();
            SyntaxHighlightEditText.access$getSyntaxHighlightResults$p(SyntaxHighlightEditText.this).addAll((Collection) list);
            SyntaxHighlightEditText.access$updateSyntaxHighlighting(SyntaxHighlightEditText.this);
        }
    }

    private final void syntaxHighlight() {
        cancelSyntaxHighlighting();
        StylingTask stylingTask = new StylingTask(new 1(), new 2());
        this.task = stylingTask;
        stylingTask.execute();
    }

    /* compiled from: SyntaxHighlightEditText.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/blacksquircle/ui/language/base/model/SyntaxHighlightResult;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class 1 extends Lambda implements Function0 {
        1() {
            super(0);
        }

        @NotNull
        public final List invoke() {
            LanguageStyler styler;
            List execute;
            Language language = SyntaxHighlightEditText.this.getLanguage();
            return (language == null || (styler = language.getStyler()) == null || (execute = styler.execute(SyntaxHighlightEditText.this.getStructure())) == null) ? CollectionsKt.emptyList() : execute;
        }
    }

    private final void cancelSyntaxHighlighting() {
        StylingTask stylingTask = this.task;
        if (stylingTask != null) {
            stylingTask.cancel();
        }
        this.task = null;
    }
}
