package com.blacksquircle.ui.editorkit.plugin.autocomplete;

import android.graphics.Rect;
import android.text.Layout;
import android.util.Log;
import android.widget.ListAdapter;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.widget.TextProcessor;
import com.blacksquircle.ui.language.base.Language;
import com.blacksquircle.ui.language.base.provider.SuggestionProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AutoCompletePlugin.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\r\n\u0002\b\r\u0018\u0000 +2\u00020\u0001:\u0001+B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000bH\u0002J\u0012\u0010\u0014\u001a\u00020\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\rH\u0002J(\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000bH\u0016J*\u0010\u001d\u001a\u00020\r2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u000bH\u0016J \u0010#\u001a\u00020\r2\u0006\u0010$\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000bH\u0016J\u0010\u0010'\u001a\u00020\r2\u0006\u0010$\u001a\u00020\u000bH\u0016J\u0010\u0010(\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010)\u001a\u00020\rH\u0016J\b\u0010*\u001a\u00020\rH\u0002R(\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006,"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/AutoCompletePlugin;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "()V", "value", "Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/SuggestionAdapter;", "suggestionAdapter", "getSuggestionAdapter", "()Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/SuggestionAdapter;", "setSuggestionAdapter", "(Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/SuggestionAdapter;)V", "getVisibleHeight", "", "onAttached", "", "editText", "Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "onDetached", "onDropDownSizeChange", "width", "height", "onLanguageChanged", "language", "Lcom/blacksquircle/ui/language/base/Language;", "onPopupChangePosition", "onSizeChanged", "w", "h", "oldw", "oldh", "onTextChanged", "text", "", "start", "before", "count", "processLine", "lineNumber", "lineStart", "lineEnd", "removeLine", "setTextContent", "showDropDown", "updateAdapter", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class AutoCompletePlugin extends EditorPlugin {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    public static final String PLUGIN_ID = "autocomplete-6743";

    @Nullable
    private SuggestionAdapter suggestionAdapter;

    public AutoCompletePlugin() {
        super("autocomplete-6743");
    }

    @Nullable
    public final SuggestionAdapter getSuggestionAdapter() {
        return this.suggestionAdapter;
    }

    public final void setSuggestionAdapter(@Nullable SuggestionAdapter suggestionAdapter) {
        this.suggestionAdapter = suggestionAdapter;
        updateAdapter();
    }

    public void onAttached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        super.onAttached(editText);
        editText.setTokenizer(new SymbolsTokenizer());
        editText.setAdapter((ListAdapter) this.suggestionAdapter);
        Log.d("autocomplete-6743", "AutoComplete plugin loaded successfully!");
    }

    public void onDetached(@NotNull TextProcessor editText) {
        Intrinsics.checkNotNullParameter(editText, "editText");
        editText.setTokenizer(null);
        editText.setAdapter(null);
        super.onDetached(editText);
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        onDropDownSizeChange(w, h);
    }

    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        super.onTextChanged(text, start, before, count);
        onPopupChangePosition();
    }

    public void setTextContent(@NotNull CharSequence text) {
        SuggestionProvider provider;
        SuggestionProvider provider2;
        Intrinsics.checkNotNullParameter(text, "text");
        super.setTextContent(text);
        Language language = getLanguage();
        if (language != null && (provider2 = language.getProvider()) != null) {
            provider2.clearLines();
        }
        Language language2 = getLanguage();
        if (language2 == null || (provider = language2.getProvider()) == null) {
            return;
        }
        provider.processAllLines(getStructure());
    }

    public void processLine(int lineNumber, int lineStart, int lineEnd) {
        SuggestionProvider provider;
        super.processLine(lineNumber, lineStart, lineEnd);
        Language language = getLanguage();
        if (language == null || (provider = language.getProvider()) == null) {
            return;
        }
        provider.processLine(lineNumber, getEditText().getText().subSequence(lineStart, lineEnd));
    }

    public void removeLine(int lineNumber) {
        SuggestionProvider provider;
        super.removeLine(lineNumber);
        Language language = getLanguage();
        if (language == null || (provider = language.getProvider()) == null) {
            return;
        }
        provider.deleteLine(lineNumber);
    }

    public void onLanguageChanged(@Nullable Language language) {
        SuggestionProvider provider;
        SuggestionAdapter suggestionAdapter;
        super.onLanguageChanged(language);
        if (language == null || (provider = language.getProvider()) == null || (suggestionAdapter = this.suggestionAdapter) == null) {
            return;
        }
        suggestionAdapter.setSuggestionProvider(provider);
    }

    public void showDropDown() {
        if (getEditText().isPopupShowing() || !getEditText().hasFocus()) {
            return;
        }
        super.showDropDown();
    }

    private final void updateAdapter() {
        SuggestionAdapter suggestionAdapter;
        SuggestionProvider provider;
        if (!isAttached() || (suggestionAdapter = this.suggestionAdapter) == null) {
            return;
        }
        Language language = getLanguage();
        if (language != null && (provider = language.getProvider()) != null) {
            suggestionAdapter.setSuggestionProvider(provider);
        }
        getEditText().setAdapter((ListAdapter) suggestionAdapter);
    }

    private final void onDropDownSizeChange(int width, int height) {
        getEditText().setDropDownWidth(width / 2);
        getEditText().setDropDownHeight(height / 2);
        onPopupChangePosition();
    }

    private final void onPopupChangePosition() {
        Layout layout = getEditText().getLayout();
        if (layout == null) {
            return;
        }
        int lineForOffset = layout.getLineForOffset(getEditText().getSelectionStart());
        float primaryHorizontal = layout.getPrimaryHorizontal(getEditText().getSelectionStart());
        int lineBaseline = layout.getLineBaseline(lineForOffset);
        getEditText().setDropDownHorizontalOffset((int) (primaryHorizontal + getEditText().getPaddingStart()));
        int scrollY = lineBaseline - getEditText().getScrollY();
        int dropDownHeight = getEditText().getDropDownHeight() + scrollY;
        TextProcessor editText = getEditText();
        if (dropDownHeight > getVisibleHeight()) {
            scrollY -= getEditText().getDropDownHeight();
        }
        editText.setDropDownVerticalOffset(scrollY);
    }

    private final int getVisibleHeight() {
        Rect rect = new Rect();
        getEditText().getWindowVisibleDisplayFrame(rect);
        return rect.bottom - rect.top;
    }

    /* compiled from: AutoCompletePlugin.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/AutoCompletePlugin$Companion;", "", "()V", "PLUGIN_ID", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
