package com.blacksquircle.ui.editorkit.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.blacksquircle.ui.editorkit.plugin.base.EditorPlugin;
import com.blacksquircle.ui.editorkit.plugin.base.PluginContainer;
import com.blacksquircle.ui.editorkit.plugin.base.PluginSupplier;
import com.blacksquircle.ui.editorkit.widget.internal.SyntaxHighlightEditText;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextProcessor.kt */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 Z2\u00020\u00012\u00020\u0002:\u0001ZB%\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\bH\u0014J\u0012\u0010\u0014\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J*\u0010\u0017\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\bH\u0014J*\u0010\u001c\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\bH\u0014J!\u0010\u001e\u001a\u0004\u0018\u0001H\u001f\"\b\b\u0000\u0010\u001f*\u00020\u000e2\u0006\u0010 \u001a\u00020!H\u0016¢\u0006\u0002\u0010\"J\b\u0010#\u001a\u00020\u000bH\u0016J\u0010\u0010$\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020!H\u0016J\u001f\u0010%\u001a\u00020\u0011\"\b\b\u0000\u0010\u001f*\u00020\u000e2\u0006\u0010&\u001a\u0002H\u001fH\u0016¢\u0006\u0002\u0010'J\b\u0010(\u001a\u00020\u0011H\u0014J\u0010\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020+H\u0014J\u001a\u0010,\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020\b2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\u001a\u00100\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020\b2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\b\u00101\u001a\u00020\u0011H\u0014J0\u00102\u001a\u00020\u00112\u0006\u00103\u001a\u00020\u000b2\u0006\u00104\u001a\u00020\b2\u0006\u00105\u001a\u00020\b2\u0006\u00106\u001a\u00020\b2\u0006\u00107\u001a\u00020\bH\u0014J\u0018\u00108\u001a\u00020\u00112\u0006\u00109\u001a\u00020\b2\u0006\u0010:\u001a\u00020\bH\u0014J(\u0010;\u001a\u00020\u00112\u0006\u0010<\u001a\u00020\b2\u0006\u0010=\u001a\u00020\b2\u0006\u0010>\u001a\u00020\b2\u0006\u0010?\u001a\u00020\bH\u0014J\u0018\u0010@\u001a\u00020\u00112\u0006\u0010A\u001a\u00020\b2\u0006\u0010B\u001a\u00020\bH\u0014J(\u0010C\u001a\u00020\u00112\u0006\u0010D\u001a\u00020\b2\u0006\u0010E\u001a\u00020\b2\u0006\u0010F\u001a\u00020\b2\u0006\u0010G\u001a\u00020\bH\u0014J\u0010\u0010H\u001a\u00020\u000b2\u0006\u0010.\u001a\u00020IH\u0017J\u0010\u0010\f\u001a\u00020\u00112\u0006\u0010J\u001a\u00020KH\u0016J \u0010L\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010M\u001a\u00020\bH\u0014J\u0010\u0010N\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\bH\u0014J\u0010\u0010O\u001a\u00020\u00112\u0006\u0010P\u001a\u00020\u000bH\u0016J\u0010\u0010Q\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0018H\u0016J\u0010\u0010R\u001a\u00020\u00112\u0006\u0010S\u001a\u00020TH\u0016J\u0012\u0010U\u001a\u00020\u00112\b\u0010V\u001a\u0004\u0018\u00010WH\u0016J\b\u0010X\u001a\u00020\u0011H\u0016J\u0010\u0010Y\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006["}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/TextProcessor;", "Lcom/blacksquircle/ui/editorkit/widget/internal/SyntaxHighlightEditText;", "Lcom/blacksquircle/ui/editorkit/plugin/base/PluginContainer;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "internalFreezesText", "", "plugins", "Ljava/util/HashSet;", "Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "Lkotlin/collections/HashSet;", "addLine", "", "lineNumber", "lineStart", "doAfterTextChanged", "text", "Landroid/text/Editable;", "doBeforeTextChanged", "", "start", "count", "after", "doOnTextChanged", "before", "findPlugin", "T", "pluginId", "", "(Ljava/lang/String;)Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;", "getFreezesText", "hasPlugin", "installPlugin", "plugin", "(Lcom/blacksquircle/ui/editorkit/plugin/base/EditorPlugin;)V", "onColorSchemeChanged", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onKeyDown", "keyCode", "event", "Landroid/view/KeyEvent;", "onKeyUp", "onLanguageChanged", "onLayout", "changed", "left", "top", "right", "bottom", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onScrollChanged", "horiz", "vert", "oldHoriz", "oldVert", "onSelectionChanged", "selStart", "selEnd", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", "Landroid/view/MotionEvent;", "supplier", "Lcom/blacksquircle/ui/editorkit/plugin/base/PluginSupplier;", "processLine", "lineEnd", "removeLine", "setFreezesText", "freezesText", "setTextContent", "setTextSize", "size", "", "setTypeface", "tf", "Landroid/graphics/Typeface;", "showDropDown", "uninstallPlugin", "Companion", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTextProcessor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TextProcessor.kt\ncom/blacksquircle/ui/editorkit/widget/TextProcessor\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,258:1\n1#2:259\n1747#3,3:260\n*S KotlinDebug\n*F\n+ 1 TextProcessor.kt\ncom/blacksquircle/ui/editorkit/widget/TextProcessor\n*L\n256#1:260,3\n*E\n"})
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public class TextProcessor extends SyntaxHighlightEditText implements PluginContainer {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String TAG = "TextProcessor";
    private boolean internalFreezesText;

    @NotNull
    private final HashSet plugins;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TextProcessor(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TextProcessor(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ TextProcessor(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 16842859 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TextProcessor(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.plugins = new HashSet();
        this.internalFreezesText = true;
    }

    /* compiled from: TextProcessor.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/blacksquircle/ui/editorkit/widget/TextProcessor$Companion;", "", "()V", "TAG", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).onLayout(changed, left, top, right, bottom);
        }
    }

    protected void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).drawBehind(canvas);
        }
        super.onDraw(canvas);
        Iterator it2 = this.plugins.iterator();
        while (it2.hasNext()) {
            ((EditorPlugin) it2.next()).onDraw(canvas);
        }
    }

    /* renamed from: getFreezesText, reason: from getter */
    public boolean getInternalFreezesText() {
        return this.internalFreezesText;
    }

    public void setFreezesText(boolean freezesText) {
        super.setFreezesText(freezesText);
        this.internalFreezesText = freezesText;
    }

    protected void onColorSchemeChanged() {
        super.onColorSchemeChanged();
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).onColorSchemeChanged(getColorScheme());
        }
    }

    protected void onLanguageChanged() {
        super.onLanguageChanged();
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).onLanguageChanged(getLanguage());
        }
    }

    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).onScrollChanged(horiz, vert, oldHoriz, oldVert);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).onSizeChanged(w, h, oldw, oldh);
        }
    }

    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (getLayout() != null) {
            Iterator it = this.plugins.iterator();
            while (it.hasNext()) {
                ((EditorPlugin) it.next()).onSelectionChanged(selStart, selEnd);
            }
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            if (((EditorPlugin) it.next()).onTouchEvent(event)) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean onKeyUp(int keyCode, @Nullable KeyEvent event) {
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            if (((EditorPlugin) it.next()).onKeyUp(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            if (((EditorPlugin) it.next()).onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void doBeforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
        super.doBeforeTextChanged(text, start, count, after);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).beforeTextChanged(text, start, count, after);
        }
    }

    protected void doOnTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        super.doOnTextChanged(text, start, before, count);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).onTextChanged(text, start, before, count);
        }
    }

    protected void doAfterTextChanged(@Nullable Editable text) {
        super.doAfterTextChanged(text);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).afterTextChanged(text);
        }
    }

    protected void processLine(int lineNumber, int lineStart, int lineEnd) {
        super.processLine(lineNumber, lineStart, lineEnd);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).processLine(lineNumber, lineStart, lineEnd);
        }
    }

    protected void addLine(int lineNumber, int lineStart) {
        super.addLine(lineNumber, lineStart);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).addLine(lineNumber, lineStart);
        }
    }

    protected void removeLine(int lineNumber) {
        super.removeLine(lineNumber);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).removeLine(lineNumber);
        }
    }

    public void setTextContent(@NotNull CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        super.setTextContent(text);
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).setTextContent(text);
        }
    }

    public void setTextSize(float size) {
        super.setTextSize(size);
        if (getLayout() != null) {
            Iterator it = this.plugins.iterator();
            while (it.hasNext()) {
                ((EditorPlugin) it.next()).setTextSize(size);
            }
        }
    }

    public void setTypeface(@Nullable Typeface tf) {
        super.setTypeface(tf);
        if (getLayout() != null) {
            Iterator it = this.plugins.iterator();
            while (it.hasNext()) {
                ((EditorPlugin) it.next()).setTypeface(tf);
            }
        }
    }

    public void showDropDown() {
        super.showDropDown();
        Iterator it = this.plugins.iterator();
        while (it.hasNext()) {
            ((EditorPlugin) it.next()).showDropDown();
        }
    }

    public void plugins(@NotNull PluginSupplier supplier) {
        Intrinsics.checkNotNullParameter(supplier, "supplier");
        Iterable union = CollectionsKt.union(this.plugins, supplier.getPlugins());
        Iterator it = CollectionsKt.subtract(union, CollectionsKt.intersect(union, supplier.getPlugins())).iterator();
        while (it.hasNext()) {
            uninstallPlugin(((EditorPlugin) it.next()).getPluginId());
        }
        Iterator it2 = supplier.getPlugins().iterator();
        while (it2.hasNext()) {
            installPlugin((EditorPlugin) it2.next());
        }
    }

    public void installPlugin(@NotNull EditorPlugin plugin) {
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        if (!hasPlugin(plugin.getPluginId())) {
            this.plugins.add(plugin);
            plugin.onAttached(this);
        } else {
            Log.e("TextProcessor", "Plugin " + plugin + " is already attached.");
        }
    }

    public void uninstallPlugin(@NotNull String pluginId) {
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        if (hasPlugin(pluginId)) {
            EditorPlugin findPlugin = findPlugin(pluginId);
            if (findPlugin != null) {
                this.plugins.remove(findPlugin);
                findPlugin.onDetached(this);
                return;
            }
            return;
        }
        Log.e("TextProcessor", "Plugin " + pluginId + " is not attached.");
    }

    @Nullable
    public EditorPlugin findPlugin(@NotNull String pluginId) {
        Object obj;
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        Iterator it = this.plugins.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((EditorPlugin) obj).getPluginId(), pluginId)) {
                break;
            }
        }
        if (obj instanceof EditorPlugin) {
            return (EditorPlugin) obj;
        }
        return null;
    }

    public boolean hasPlugin(@NotNull String pluginId) {
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        Collection collection = (Iterable) this.plugins;
        if ((collection instanceof Collection) && collection.isEmpty()) {
            return false;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((EditorPlugin) it.next()).getPluginId(), pluginId)) {
                return true;
            }
        }
        return false;
    }
}
