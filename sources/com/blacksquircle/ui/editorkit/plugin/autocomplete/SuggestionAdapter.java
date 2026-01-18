package com.blacksquircle.ui.editorkit.plugin.autocomplete;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import com.blacksquircle.ui.language.base.model.Suggestion;
import com.blacksquircle.ui.language.base.provider.SuggestionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SuggestionAdapter.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\"\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\n\u001a\u00020\u000bR\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/SuggestionAdapter;", "Landroid/widget/ArrayAdapter;", "Lcom/blacksquircle/ui/language/base/model/Suggestion;", "context", "Landroid/content/Context;", "resourceId", "", "(Landroid/content/Context;I)V", "queryText", "", "suggestionProvider", "Lcom/blacksquircle/ui/language/base/provider/SuggestionProvider;", "createViewHolder", "Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/SuggestionAdapter$SuggestionViewHolder;", "parent", "Landroid/view/ViewGroup;", "getFilter", "Landroid/widget/Filter;", "getView", "Landroid/view/View;", "position", "convertView", "setSuggestionProvider", "", "SuggestionViewHolder", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public abstract class SuggestionAdapter extends ArrayAdapter {

    @Nullable
    private String queryText;

    @Nullable
    private SuggestionProvider suggestionProvider;

    @NotNull
    public abstract SuggestionViewHolder createViewHolder(@NotNull ViewGroup parent);

    public static final /* synthetic */ SuggestionProvider access$getSuggestionProvider$p(SuggestionAdapter suggestionAdapter) {
        return suggestionAdapter.suggestionProvider;
    }

    public static final /* synthetic */ void access$setQueryText$p(SuggestionAdapter suggestionAdapter, String str) {
        suggestionAdapter.queryText = str;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuggestionAdapter(@NotNull Context context, int i) {
        super(context, i);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @NotNull
    public View getView(int position, @Nullable View convertView, @NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        SuggestionViewHolder createViewHolder = createViewHolder(parent);
        Suggestion suggestion = (Suggestion) getItem(position);
        String str = this.queryText;
        if (str == null) {
            str = "";
        }
        createViewHolder.bind(suggestion, str);
        return createViewHolder.getItemView();
    }

    /* compiled from: SuggestionAdapter.kt */
    @Metadata(d1 = {"\u0000)\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014J\u001a\u0010\t\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\u0006H\u0014R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"com/blacksquircle/ui/editorkit/plugin/autocomplete/SuggestionAdapter$getFilter$1", "Landroid/widget/Filter;", "suggestions", "", "Lcom/blacksquircle/ui/language/base/model/Suggestion;", "performFiltering", "Landroid/widget/Filter$FilterResults;", "constraint", "", "publishResults", "", "results", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class 1 extends Filter {

        @NotNull
        private final List suggestions = new ArrayList();

        1() {
        }

        @NotNull
        protected Filter.FilterResults performFiltering(@Nullable CharSequence constraint) {
            this.suggestions.clear();
            SuggestionProvider access$getSuggestionProvider$p = SuggestionAdapter.access$getSuggestionProvider$p(SuggestionAdapter.this);
            if (access$getSuggestionProvider$p != null) {
                SuggestionAdapter suggestionAdapter = SuggestionAdapter.this;
                String valueOf = String.valueOf(constraint);
                for (Suggestion suggestion : access$getSuggestionProvider$p.getAll()) {
                    String suggestion2 = suggestion.toString();
                    if (StringsKt.startsWith(suggestion2, valueOf, true) && !StringsKt.equals(suggestion2, valueOf, true)) {
                        SuggestionAdapter.access$setQueryText$p(suggestionAdapter, valueOf);
                        this.suggestions.add(suggestion);
                    }
                }
            }
            Filter.FilterResults filterResults = new Filter.FilterResults();
            filterResults.values = this.suggestions;
            filterResults.count = this.suggestions.size();
            return filterResults;
        }

        protected void publishResults(@Nullable CharSequence constraint, @NotNull Filter.FilterResults results) {
            Intrinsics.checkNotNullParameter(results, "results");
            SuggestionAdapter.this.clear();
            SuggestionAdapter.this.addAll((Collection) this.suggestions);
            SuggestionAdapter.this.notifyDataSetChanged();
        }
    }

    @NotNull
    public Filter getFilter() {
        return new 1();
    }

    public final void setSuggestionProvider(@NotNull SuggestionProvider suggestionProvider) {
        Intrinsics.checkNotNullParameter(suggestionProvider, "suggestionProvider");
        this.suggestionProvider = suggestionProvider;
    }

    /* compiled from: SuggestionAdapter.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lcom/blacksquircle/ui/editorkit/plugin/autocomplete/SuggestionAdapter$SuggestionViewHolder;", "", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "getItemView", "()Landroid/view/View;", "bind", "", "suggestion", "Lcom/blacksquircle/ui/language/base/model/Suggestion;", "query", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class SuggestionViewHolder {

        @NotNull
        private final View itemView;

        public abstract void bind(@Nullable Suggestion suggestion, @NotNull String query);

        public SuggestionViewHolder(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "itemView");
            this.itemView = view;
        }

        @NotNull
        public final View getItemView() {
            return this.itemView;
        }
    }
}
