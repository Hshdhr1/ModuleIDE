package com.blacksquircle.ui.editorkit.model;

import androidx.annotation.ColorInt;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ColorScheme.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\bT\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0093\u0002\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0001\u0010\b\u001a\u00020\u0003\u0012\b\b\u0001\u0010\t\u001a\u00020\u0003\u0012\b\b\u0001\u0010\n\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0001\u0010\f\u001a\u00020\u0003\u0012\b\b\u0001\u0010\r\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0012\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0014\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0015\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0016\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0017\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0018\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0019\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u001a\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u001b\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u001c\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u001d\u001a\u00020\u0003¢\u0006\u0002\u0010\u001eJ\t\u0010;\u001a\u00020\u0003HÆ\u0003J\t\u0010<\u001a\u00020\u0003HÆ\u0003J\t\u0010=\u001a\u00020\u0003HÆ\u0003J\t\u0010>\u001a\u00020\u0003HÆ\u0003J\t\u0010?\u001a\u00020\u0003HÆ\u0003J\t\u0010@\u001a\u00020\u0003HÆ\u0003J\t\u0010A\u001a\u00020\u0003HÆ\u0003J\t\u0010B\u001a\u00020\u0003HÆ\u0003J\t\u0010C\u001a\u00020\u0003HÆ\u0003J\t\u0010D\u001a\u00020\u0003HÆ\u0003J\t\u0010E\u001a\u00020\u0003HÆ\u0003J\t\u0010F\u001a\u00020\u0003HÆ\u0003J\t\u0010G\u001a\u00020\u0003HÆ\u0003J\t\u0010H\u001a\u00020\u0003HÆ\u0003J\t\u0010I\u001a\u00020\u0003HÆ\u0003J\t\u0010J\u001a\u00020\u0003HÆ\u0003J\t\u0010K\u001a\u00020\u0003HÆ\u0003J\t\u0010L\u001a\u00020\u0003HÆ\u0003J\t\u0010M\u001a\u00020\u0003HÆ\u0003J\t\u0010N\u001a\u00020\u0003HÆ\u0003J\t\u0010O\u001a\u00020\u0003HÆ\u0003J\t\u0010P\u001a\u00020\u0003HÆ\u0003J\t\u0010Q\u001a\u00020\u0003HÆ\u0003J\t\u0010R\u001a\u00020\u0003HÆ\u0003J\t\u0010S\u001a\u00020\u0003HÆ\u0003J\t\u0010T\u001a\u00020\u0003HÆ\u0003J\t\u0010U\u001a\u00020\u0003HÆ\u0003J\u0097\u0002\u0010V\u001a\u00020\u00002\b\b\u0003\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0004\u001a\u00020\u00032\b\b\u0003\u0010\u0005\u001a\u00020\u00032\b\b\u0003\u0010\u0006\u001a\u00020\u00032\b\b\u0003\u0010\u0007\u001a\u00020\u00032\b\b\u0003\u0010\b\u001a\u00020\u00032\b\b\u0003\u0010\t\u001a\u00020\u00032\b\b\u0003\u0010\n\u001a\u00020\u00032\b\b\u0003\u0010\u000b\u001a\u00020\u00032\b\b\u0003\u0010\f\u001a\u00020\u00032\b\b\u0003\u0010\r\u001a\u00020\u00032\b\b\u0003\u0010\u000e\u001a\u00020\u00032\b\b\u0003\u0010\u000f\u001a\u00020\u00032\b\b\u0003\u0010\u0010\u001a\u00020\u00032\b\b\u0003\u0010\u0011\u001a\u00020\u00032\b\b\u0003\u0010\u0012\u001a\u00020\u00032\b\b\u0003\u0010\u0013\u001a\u00020\u00032\b\b\u0003\u0010\u0014\u001a\u00020\u00032\b\b\u0003\u0010\u0015\u001a\u00020\u00032\b\b\u0003\u0010\u0016\u001a\u00020\u00032\b\b\u0003\u0010\u0017\u001a\u00020\u00032\b\b\u0003\u0010\u0018\u001a\u00020\u00032\b\b\u0003\u0010\u0019\u001a\u00020\u00032\b\b\u0003\u0010\u001a\u001a\u00020\u00032\b\b\u0003\u0010\u001b\u001a\u00020\u00032\b\b\u0003\u0010\u001c\u001a\u00020\u00032\b\b\u0003\u0010\u001d\u001a\u00020\u0003HÆ\u0001J\u0013\u0010W\u001a\u00020X2\b\u0010Y\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010Z\u001a\u00020\u0003HÖ\u0001J\t\u0010[\u001a\u00020\\HÖ\u0001R\u0011\u0010\u001b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u001c\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010 R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010 R\u0011\u0010\u0018\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010 R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010 R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010 R\u0011\u0010\u001d\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010 R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u0010 R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b(\u0010 R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b)\u0010 R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b*\u0010 R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b+\u0010 R\u0011\u0010\u0011\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b,\u0010 R\u0011\u0010\u0013\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b-\u0010 R\u0011\u0010\u0016\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b.\u0010 R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b/\u0010 R\u0011\u0010\u0010\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b0\u0010 R\u0011\u0010\u0014\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b1\u0010 R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b2\u0010 R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b3\u0010 R\u0011\u0010\u0017\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b4\u0010 R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b5\u0010 R\u0011\u0010\u0019\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b6\u0010 R\u0011\u0010\u001a\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b7\u0010 R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b8\u0010 R\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b9\u0010 R\u0011\u0010\u0015\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b:\u0010 ¨\u0006]"}, d2 = {"Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "", "textColor", "", "cursorColor", "backgroundColor", "gutterColor", "gutterDividerColor", "gutterCurrentLineNumberColor", "gutterTextColor", "selectedLineColor", "selectionColor", "suggestionQueryColor", "findResultBackgroundColor", "delimiterBackgroundColor", "numberColor", "operatorColor", "keywordColor", "typeColor", "langConstColor", "preprocessorColor", "variableColor", "methodColor", "stringColor", "commentColor", "tagColor", "tagNameColor", "attrNameColor", "attrValueColor", "entityRefColor", "(IIIIIIIIIIIIIIIIIIIIIIIIIII)V", "getAttrNameColor", "()I", "getAttrValueColor", "getBackgroundColor", "getCommentColor", "getCursorColor", "getDelimiterBackgroundColor", "getEntityRefColor", "getFindResultBackgroundColor", "getGutterColor", "getGutterCurrentLineNumberColor", "getGutterDividerColor", "getGutterTextColor", "getKeywordColor", "getLangConstColor", "getMethodColor", "getNumberColor", "getOperatorColor", "getPreprocessorColor", "getSelectedLineColor", "getSelectionColor", "getStringColor", "getSuggestionQueryColor", "getTagColor", "getTagNameColor", "getTextColor", "getTypeColor", "getVariableColor", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final /* data */ class ColorScheme {
    private final int attrNameColor;
    private final int attrValueColor;
    private final int backgroundColor;
    private final int commentColor;
    private final int cursorColor;
    private final int delimiterBackgroundColor;
    private final int entityRefColor;
    private final int findResultBackgroundColor;
    private final int gutterColor;
    private final int gutterCurrentLineNumberColor;
    private final int gutterDividerColor;
    private final int gutterTextColor;
    private final int keywordColor;
    private final int langConstColor;
    private final int methodColor;
    private final int numberColor;
    private final int operatorColor;
    private final int preprocessorColor;
    private final int selectedLineColor;
    private final int selectionColor;
    private final int stringColor;
    private final int suggestionQueryColor;
    private final int tagColor;
    private final int tagNameColor;
    private final int textColor;
    private final int typeColor;
    private final int variableColor;

    public static /* synthetic */ ColorScheme copy$default(ColorScheme colorScheme, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20, int i21, int i22, int i23, int i24, int i25, int i26, int i27, int i28, Object obj) {
        int i29;
        int i30;
        int i31 = (i28 & 1) != 0 ? colorScheme.textColor : i;
        int i32 = (i28 & 2) != 0 ? colorScheme.cursorColor : i2;
        int i33 = (i28 & 4) != 0 ? colorScheme.backgroundColor : i3;
        int i34 = (i28 & 8) != 0 ? colorScheme.gutterColor : i4;
        int i35 = (i28 & 16) != 0 ? colorScheme.gutterDividerColor : i5;
        int i36 = (i28 & 32) != 0 ? colorScheme.gutterCurrentLineNumberColor : i6;
        int i37 = (i28 & 64) != 0 ? colorScheme.gutterTextColor : i7;
        int i38 = (i28 & 128) != 0 ? colorScheme.selectedLineColor : i8;
        int i39 = (i28 & 256) != 0 ? colorScheme.selectionColor : i9;
        int i40 = (i28 & 512) != 0 ? colorScheme.suggestionQueryColor : i10;
        int i41 = (i28 & 1024) != 0 ? colorScheme.findResultBackgroundColor : i11;
        int i42 = (i28 & 2048) != 0 ? colorScheme.delimiterBackgroundColor : i12;
        int i43 = (i28 & 4096) != 0 ? colorScheme.numberColor : i13;
        int i44 = (i28 & 8192) != 0 ? colorScheme.operatorColor : i14;
        int i45 = i31;
        int i46 = (i28 & 16384) != 0 ? colorScheme.keywordColor : i15;
        int i47 = (i28 & 32768) != 0 ? colorScheme.typeColor : i16;
        int i48 = (i28 & 65536) != 0 ? colorScheme.langConstColor : i17;
        int i49 = (i28 & 131072) != 0 ? colorScheme.preprocessorColor : i18;
        int i50 = (i28 & 262144) != 0 ? colorScheme.variableColor : i19;
        int i51 = (i28 & 524288) != 0 ? colorScheme.methodColor : i20;
        int i52 = (i28 & 1048576) != 0 ? colorScheme.stringColor : i21;
        int i53 = (i28 & 2097152) != 0 ? colorScheme.commentColor : i22;
        int i54 = (i28 & 4194304) != 0 ? colorScheme.tagColor : i23;
        int i55 = (i28 & 8388608) != 0 ? colorScheme.tagNameColor : i24;
        int i56 = (i28 & 16777216) != 0 ? colorScheme.attrNameColor : i25;
        int i57 = (i28 & 33554432) != 0 ? colorScheme.attrValueColor : i26;
        if ((i28 & 67108864) != 0) {
            i30 = i57;
            i29 = colorScheme.entityRefColor;
        } else {
            i29 = i27;
            i30 = i57;
        }
        return colorScheme.copy(i45, i32, i33, i34, i35, i36, i37, i38, i39, i40, i41, i42, i43, i44, i46, i47, i48, i49, i50, i51, i52, i53, i54, i55, i56, i30, i29);
    }

    /* renamed from: component1, reason: from getter */
    public final int getTextColor() {
        return this.textColor;
    }

    /* renamed from: component10, reason: from getter */
    public final int getSuggestionQueryColor() {
        return this.suggestionQueryColor;
    }

    /* renamed from: component11, reason: from getter */
    public final int getFindResultBackgroundColor() {
        return this.findResultBackgroundColor;
    }

    /* renamed from: component12, reason: from getter */
    public final int getDelimiterBackgroundColor() {
        return this.delimiterBackgroundColor;
    }

    /* renamed from: component13, reason: from getter */
    public final int getNumberColor() {
        return this.numberColor;
    }

    /* renamed from: component14, reason: from getter */
    public final int getOperatorColor() {
        return this.operatorColor;
    }

    /* renamed from: component15, reason: from getter */
    public final int getKeywordColor() {
        return this.keywordColor;
    }

    /* renamed from: component16, reason: from getter */
    public final int getTypeColor() {
        return this.typeColor;
    }

    /* renamed from: component17, reason: from getter */
    public final int getLangConstColor() {
        return this.langConstColor;
    }

    /* renamed from: component18, reason: from getter */
    public final int getPreprocessorColor() {
        return this.preprocessorColor;
    }

    /* renamed from: component19, reason: from getter */
    public final int getVariableColor() {
        return this.variableColor;
    }

    /* renamed from: component2, reason: from getter */
    public final int getCursorColor() {
        return this.cursorColor;
    }

    /* renamed from: component20, reason: from getter */
    public final int getMethodColor() {
        return this.methodColor;
    }

    /* renamed from: component21, reason: from getter */
    public final int getStringColor() {
        return this.stringColor;
    }

    /* renamed from: component22, reason: from getter */
    public final int getCommentColor() {
        return this.commentColor;
    }

    /* renamed from: component23, reason: from getter */
    public final int getTagColor() {
        return this.tagColor;
    }

    /* renamed from: component24, reason: from getter */
    public final int getTagNameColor() {
        return this.tagNameColor;
    }

    /* renamed from: component25, reason: from getter */
    public final int getAttrNameColor() {
        return this.attrNameColor;
    }

    /* renamed from: component26, reason: from getter */
    public final int getAttrValueColor() {
        return this.attrValueColor;
    }

    /* renamed from: component27, reason: from getter */
    public final int getEntityRefColor() {
        return this.entityRefColor;
    }

    /* renamed from: component3, reason: from getter */
    public final int getBackgroundColor() {
        return this.backgroundColor;
    }

    /* renamed from: component4, reason: from getter */
    public final int getGutterColor() {
        return this.gutterColor;
    }

    /* renamed from: component5, reason: from getter */
    public final int getGutterDividerColor() {
        return this.gutterDividerColor;
    }

    /* renamed from: component6, reason: from getter */
    public final int getGutterCurrentLineNumberColor() {
        return this.gutterCurrentLineNumberColor;
    }

    /* renamed from: component7, reason: from getter */
    public final int getGutterTextColor() {
        return this.gutterTextColor;
    }

    /* renamed from: component8, reason: from getter */
    public final int getSelectedLineColor() {
        return this.selectedLineColor;
    }

    /* renamed from: component9, reason: from getter */
    public final int getSelectionColor() {
        return this.selectionColor;
    }

    @NotNull
    public final ColorScheme copy(@ColorInt int textColor, @ColorInt int cursorColor, @ColorInt int backgroundColor, @ColorInt int gutterColor, @ColorInt int gutterDividerColor, @ColorInt int gutterCurrentLineNumberColor, @ColorInt int gutterTextColor, @ColorInt int selectedLineColor, @ColorInt int selectionColor, @ColorInt int suggestionQueryColor, @ColorInt int findResultBackgroundColor, @ColorInt int delimiterBackgroundColor, @ColorInt int numberColor, @ColorInt int operatorColor, @ColorInt int keywordColor, @ColorInt int typeColor, @ColorInt int langConstColor, @ColorInt int preprocessorColor, @ColorInt int variableColor, @ColorInt int methodColor, @ColorInt int stringColor, @ColorInt int commentColor, @ColorInt int tagColor, @ColorInt int tagNameColor, @ColorInt int attrNameColor, @ColorInt int attrValueColor, @ColorInt int entityRefColor) {
        return new ColorScheme(textColor, cursorColor, backgroundColor, gutterColor, gutterDividerColor, gutterCurrentLineNumberColor, gutterTextColor, selectedLineColor, selectionColor, suggestionQueryColor, findResultBackgroundColor, delimiterBackgroundColor, numberColor, operatorColor, keywordColor, typeColor, langConstColor, preprocessorColor, variableColor, methodColor, stringColor, commentColor, tagColor, tagNameColor, attrNameColor, attrValueColor, entityRefColor);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ColorScheme)) {
            return false;
        }
        ColorScheme colorScheme = (ColorScheme) other;
        return this.textColor == colorScheme.textColor && this.cursorColor == colorScheme.cursorColor && this.backgroundColor == colorScheme.backgroundColor && this.gutterColor == colorScheme.gutterColor && this.gutterDividerColor == colorScheme.gutterDividerColor && this.gutterCurrentLineNumberColor == colorScheme.gutterCurrentLineNumberColor && this.gutterTextColor == colorScheme.gutterTextColor && this.selectedLineColor == colorScheme.selectedLineColor && this.selectionColor == colorScheme.selectionColor && this.suggestionQueryColor == colorScheme.suggestionQueryColor && this.findResultBackgroundColor == colorScheme.findResultBackgroundColor && this.delimiterBackgroundColor == colorScheme.delimiterBackgroundColor && this.numberColor == colorScheme.numberColor && this.operatorColor == colorScheme.operatorColor && this.keywordColor == colorScheme.keywordColor && this.typeColor == colorScheme.typeColor && this.langConstColor == colorScheme.langConstColor && this.preprocessorColor == colorScheme.preprocessorColor && this.variableColor == colorScheme.variableColor && this.methodColor == colorScheme.methodColor && this.stringColor == colorScheme.stringColor && this.commentColor == colorScheme.commentColor && this.tagColor == colorScheme.tagColor && this.tagNameColor == colorScheme.tagNameColor && this.attrNameColor == colorScheme.attrNameColor && this.attrValueColor == colorScheme.attrValueColor && this.entityRefColor == colorScheme.entityRefColor;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((((((((((((((((((this.textColor * 31) + this.cursorColor) * 31) + this.backgroundColor) * 31) + this.gutterColor) * 31) + this.gutterDividerColor) * 31) + this.gutterCurrentLineNumberColor) * 31) + this.gutterTextColor) * 31) + this.selectedLineColor) * 31) + this.selectionColor) * 31) + this.suggestionQueryColor) * 31) + this.findResultBackgroundColor) * 31) + this.delimiterBackgroundColor) * 31) + this.numberColor) * 31) + this.operatorColor) * 31) + this.keywordColor) * 31) + this.typeColor) * 31) + this.langConstColor) * 31) + this.preprocessorColor) * 31) + this.variableColor) * 31) + this.methodColor) * 31) + this.stringColor) * 31) + this.commentColor) * 31) + this.tagColor) * 31) + this.tagNameColor) * 31) + this.attrNameColor) * 31) + this.attrValueColor) * 31) + this.entityRefColor;
    }

    @NotNull
    public String toString() {
        return "ColorScheme(textColor=" + this.textColor + ", cursorColor=" + this.cursorColor + ", backgroundColor=" + this.backgroundColor + ", gutterColor=" + this.gutterColor + ", gutterDividerColor=" + this.gutterDividerColor + ", gutterCurrentLineNumberColor=" + this.gutterCurrentLineNumberColor + ", gutterTextColor=" + this.gutterTextColor + ", selectedLineColor=" + this.selectedLineColor + ", selectionColor=" + this.selectionColor + ", suggestionQueryColor=" + this.suggestionQueryColor + ", findResultBackgroundColor=" + this.findResultBackgroundColor + ", delimiterBackgroundColor=" + this.delimiterBackgroundColor + ", numberColor=" + this.numberColor + ", operatorColor=" + this.operatorColor + ", keywordColor=" + this.keywordColor + ", typeColor=" + this.typeColor + ", langConstColor=" + this.langConstColor + ", preprocessorColor=" + this.preprocessorColor + ", variableColor=" + this.variableColor + ", methodColor=" + this.methodColor + ", stringColor=" + this.stringColor + ", commentColor=" + this.commentColor + ", tagColor=" + this.tagColor + ", tagNameColor=" + this.tagNameColor + ", attrNameColor=" + this.attrNameColor + ", attrValueColor=" + this.attrValueColor + ", entityRefColor=" + this.entityRefColor + ")";
    }

    public ColorScheme(@ColorInt int i, @ColorInt int i2, @ColorInt int i3, @ColorInt int i4, @ColorInt int i5, @ColorInt int i6, @ColorInt int i7, @ColorInt int i8, @ColorInt int i9, @ColorInt int i10, @ColorInt int i11, @ColorInt int i12, @ColorInt int i13, @ColorInt int i14, @ColorInt int i15, @ColorInt int i16, @ColorInt int i17, @ColorInt int i18, @ColorInt int i19, @ColorInt int i20, @ColorInt int i21, @ColorInt int i22, @ColorInt int i23, @ColorInt int i24, @ColorInt int i25, @ColorInt int i26, @ColorInt int i27) {
        this.textColor = i;
        this.cursorColor = i2;
        this.backgroundColor = i3;
        this.gutterColor = i4;
        this.gutterDividerColor = i5;
        this.gutterCurrentLineNumberColor = i6;
        this.gutterTextColor = i7;
        this.selectedLineColor = i8;
        this.selectionColor = i9;
        this.suggestionQueryColor = i10;
        this.findResultBackgroundColor = i11;
        this.delimiterBackgroundColor = i12;
        this.numberColor = i13;
        this.operatorColor = i14;
        this.keywordColor = i15;
        this.typeColor = i16;
        this.langConstColor = i17;
        this.preprocessorColor = i18;
        this.variableColor = i19;
        this.methodColor = i20;
        this.stringColor = i21;
        this.commentColor = i22;
        this.tagColor = i23;
        this.tagNameColor = i24;
        this.attrNameColor = i25;
        this.attrValueColor = i26;
        this.entityRefColor = i27;
    }

    public final int getTextColor() {
        return this.textColor;
    }

    public final int getCursorColor() {
        return this.cursorColor;
    }

    public final int getBackgroundColor() {
        return this.backgroundColor;
    }

    public final int getGutterColor() {
        return this.gutterColor;
    }

    public final int getGutterDividerColor() {
        return this.gutterDividerColor;
    }

    public final int getGutterCurrentLineNumberColor() {
        return this.gutterCurrentLineNumberColor;
    }

    public final int getGutterTextColor() {
        return this.gutterTextColor;
    }

    public final int getSelectedLineColor() {
        return this.selectedLineColor;
    }

    public final int getSelectionColor() {
        return this.selectionColor;
    }

    public final int getSuggestionQueryColor() {
        return this.suggestionQueryColor;
    }

    public final int getFindResultBackgroundColor() {
        return this.findResultBackgroundColor;
    }

    public final int getDelimiterBackgroundColor() {
        return this.delimiterBackgroundColor;
    }

    public final int getNumberColor() {
        return this.numberColor;
    }

    public final int getOperatorColor() {
        return this.operatorColor;
    }

    public final int getKeywordColor() {
        return this.keywordColor;
    }

    public final int getTypeColor() {
        return this.typeColor;
    }

    public final int getLangConstColor() {
        return this.langConstColor;
    }

    public final int getPreprocessorColor() {
        return this.preprocessorColor;
    }

    public final int getVariableColor() {
        return this.variableColor;
    }

    public final int getMethodColor() {
        return this.methodColor;
    }

    public final int getStringColor() {
        return this.stringColor;
    }

    public final int getCommentColor() {
        return this.commentColor;
    }

    public final int getTagColor() {
        return this.tagColor;
    }

    public final int getTagNameColor() {
        return this.tagNameColor;
    }

    public final int getAttrNameColor() {
        return this.attrNameColor;
    }

    public final int getAttrValueColor() {
        return this.attrValueColor;
    }

    public final int getEntityRefColor() {
        return this.entityRefColor;
    }
}
