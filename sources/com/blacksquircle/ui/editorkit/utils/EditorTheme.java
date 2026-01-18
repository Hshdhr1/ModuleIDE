package com.blacksquircle.ui.editorkit.utils;

import android.graphics.Color;
import com.blacksquircle.ui.editorkit.model.ColorScheme;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: EditorTheme.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006¨\u0006\u0017"}, d2 = {"Lcom/blacksquircle/ui/editorkit/utils/EditorTheme;", "", "()V", "DARCULA", "Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "getDARCULA", "()Lcom/blacksquircle/ui/editorkit/model/ColorScheme;", "ECLIPSE", "getECLIPSE", "INTELLIJ_LIGHT", "getINTELLIJ_LIGHT", "LADIES_NIGHT", "getLADIES_NIGHT", "MONOKAI", "getMONOKAI", "OBSIDIAN", "getOBSIDIAN", "SOLARIZED_LIGHT", "getSOLARIZED_LIGHT", "TOMORROW_NIGHT", "getTOMORROW_NIGHT", "VISUAL_STUDIO", "getVISUAL_STUDIO", "editorkit_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes58.dex */
public final class EditorTheme {

    @NotNull
    public static final EditorTheme INSTANCE = new EditorTheme();

    @NotNull
    private static final ColorScheme DARCULA = new ColorScheme(Color.parseColor("#ABB7C5"), Color.parseColor("#BBBBBB"), Color.parseColor("#303030"), Color.parseColor("#313335"), Color.parseColor("#555555"), Color.parseColor("#A4A3A3"), Color.parseColor("#616366"), Color.parseColor("#3A3A3A"), Color.parseColor("#28427F"), Color.parseColor("#987DAC"), Color.parseColor("#33654B"), Color.parseColor("#33654B"), Color.parseColor("#6897BB"), Color.parseColor("#E8E2B7"), Color.parseColor("#EC7600"), Color.parseColor("#EC7600"), Color.parseColor("#EC7600"), Color.parseColor("#C9C54E"), Color.parseColor("#9378A7"), Color.parseColor("#FEC76C"), Color.parseColor("#6E875A"), Color.parseColor("#66747B"), Color.parseColor("#E2C077"), Color.parseColor("#E2C077"), Color.parseColor("#BABABA"), Color.parseColor("#ABC16D"), Color.parseColor("#6897BB"));

    @NotNull
    private static final ColorScheme MONOKAI = new ColorScheme(Color.parseColor("#F8F8F8"), Color.parseColor("#BBBBBB"), Color.parseColor("#272823"), Color.parseColor("#272823"), Color.parseColor("#5B5A4F"), Color.parseColor("#C8BBAC"), Color.parseColor("#5B5A4F"), Color.parseColor("#34352D"), Color.parseColor("#666666"), Color.parseColor("#7CE0F3"), Color.parseColor("#5F5E5A"), Color.parseColor("#5F5E5A"), Color.parseColor("#BB8FF8"), Color.parseColor("#F8F8F2"), Color.parseColor("#EB347E"), Color.parseColor("#7FD0E4"), Color.parseColor("#EB347E"), Color.parseColor("#EB347E"), Color.parseColor("#7FD0E4"), Color.parseColor("#B6E951"), Color.parseColor("#EBE48C"), Color.parseColor("#89826D"), Color.parseColor("#F8F8F8"), Color.parseColor("#EB347E"), Color.parseColor("#B6E951"), Color.parseColor("#EBE48C"), Color.parseColor("#BB8FF8"));

    @NotNull
    private static final ColorScheme OBSIDIAN = new ColorScheme(Color.parseColor("#E0E2E4"), Color.parseColor("#BBBBBB"), Color.parseColor("#2A3134"), Color.parseColor("#2A3134"), Color.parseColor("#67777B"), Color.parseColor("#E0E0E0"), Color.parseColor("#859599"), Color.parseColor("#31393C"), Color.parseColor("#616161"), Color.parseColor("#9EC56F"), Color.parseColor("#838177"), Color.parseColor("#616161"), Color.parseColor("#F8CE4E"), Color.parseColor("#E7E2BC"), Color.parseColor("#9EC56F"), Color.parseColor("#9EC56F"), Color.parseColor("#9EC56F"), Color.parseColor("#9B84B9"), Color.parseColor("#6E8BAE"), Color.parseColor("#E7E2BC"), Color.parseColor("#DE7C2E"), Color.parseColor("#808C92"), Color.parseColor("#E7E2BC"), Color.parseColor("#9EC56F"), Color.parseColor("#E0E2E4"), Color.parseColor("#DE7C2E"), Color.parseColor("#F8CE4E"));

    @NotNull
    private static final ColorScheme LADIES_NIGHT = new ColorScheme(Color.parseColor("#E0E2E4"), Color.parseColor("#BBBBBB"), Color.parseColor("#22282C"), Color.parseColor("#2A3134"), Color.parseColor("#4F575A"), Color.parseColor("#E0E2E4"), Color.parseColor("#859599"), Color.parseColor("#373340"), Color.parseColor("#5B2B41"), Color.parseColor("#6E8BAE"), Color.parseColor("#8A4364"), Color.parseColor("#616161"), Color.parseColor("#7EFBFD"), Color.parseColor("#E7E2BC"), Color.parseColor("#DA89A2"), Color.parseColor("#DA89A2"), Color.parseColor("#DA89A2"), Color.parseColor("#9B84B9"), Color.parseColor("#6EA4C7"), Color.parseColor("#8FB4C5"), Color.parseColor("#75D367"), Color.parseColor("#808C92"), Color.parseColor("#E7E2BC"), Color.parseColor("#DA89A2"), Color.parseColor("#E0E2E4"), Color.parseColor("#75D367"), Color.parseColor("#7EFBFD"));

    @NotNull
    private static final ColorScheme TOMORROW_NIGHT = new ColorScheme(Color.parseColor("#C6C8C6"), Color.parseColor("#BBBBBB"), Color.parseColor("#222426"), Color.parseColor("#222426"), Color.parseColor("#4B4D51"), Color.parseColor("#FFFFFF"), Color.parseColor("#C6C8C6"), Color.parseColor("#2D2F33"), Color.parseColor("#383B40"), Color.parseColor("#EAC780"), Color.parseColor("#4B4E54"), Color.parseColor("#616161"), Color.parseColor("#D49668"), Color.parseColor("#CFD1CF"), Color.parseColor("#AD95B8"), Color.parseColor("#AD95B8"), Color.parseColor("#AD95B8"), Color.parseColor("#CFD1CF"), Color.parseColor("#EAC780"), Color.parseColor("#87A1BB"), Color.parseColor("#B7BC73"), Color.parseColor("#969896"), Color.parseColor("#CFD1CF"), Color.parseColor("#AD95B8"), Color.parseColor("#C6C8C6"), Color.parseColor("#B7BC73"), Color.parseColor("#D49668"));

    @NotNull
    private static final ColorScheme VISUAL_STUDIO = new ColorScheme(Color.parseColor("#C8C8C8"), Color.parseColor("#BBBBBB"), Color.parseColor("#232323"), Color.parseColor("#2C2C2C"), Color.parseColor("#555555"), Color.parseColor("#FFFFFF"), Color.parseColor("#C6C8C6"), Color.parseColor("#141414"), Color.parseColor("#454464"), Color.parseColor("#4F98F7"), Color.parseColor("#1C3D6B"), Color.parseColor("#616161"), Color.parseColor("#BACDAB"), Color.parseColor("#DCDCDC"), Color.parseColor("#669BD1"), Color.parseColor("#669BD1"), Color.parseColor("#669BD1"), Color.parseColor("#C49594"), Color.parseColor("#9DDDFF"), Color.parseColor("#71C6B1"), Color.parseColor("#CE9F89"), Color.parseColor("#6BA455"), Color.parseColor("#DCDCDC"), Color.parseColor("#669BD1"), Color.parseColor("#C8C8C8"), Color.parseColor("#CE9F89"), Color.parseColor("#BACDAB"));

    @NotNull
    private static final ColorScheme INTELLIJ_LIGHT = new ColorScheme(Color.parseColor("#000000"), Color.parseColor("#000000"), Color.parseColor("#FFFFFF"), Color.parseColor("#F2F2F2"), Color.parseColor("#D4D4D4"), Color.parseColor("#828282"), Color.parseColor("#ADADAD"), Color.parseColor("#FCFAEE"), Color.parseColor("#AFD1FB"), Color.parseColor("#3A6EAE"), Color.parseColor("#E2FEDE"), Color.parseColor("#A2D7D8"), Color.parseColor("#284FE2"), Color.parseColor("#000000"), Color.parseColor("#1232AC"), Color.parseColor("#1232AC"), Color.parseColor("#1232AC"), Color.parseColor("#9A892E"), Color.parseColor("#7C1E8F"), Color.parseColor("#286077"), Color.parseColor("#377B2A"), Color.parseColor("#8C8C8C"), Color.parseColor("#000000"), Color.parseColor("#1232AC"), Color.parseColor("#2649CC"), Color.parseColor("#377B2A"), Color.parseColor("#264ADD"));

    @NotNull
    private static final ColorScheme SOLARIZED_LIGHT = new ColorScheme(Color.parseColor("#697A82"), Color.parseColor("#5C6D74"), Color.parseColor("#FCF6E5"), Color.parseColor("#EDE8D7"), Color.parseColor("#B6BAB4"), Color.parseColor("#77878B"), Color.parseColor("#A5ADAB"), Color.parseColor("#F2EDDE"), Color.parseColor("#AFD1FB"), Color.parseColor("#5274B5"), Color.parseColor("#E8F0D0"), Color.parseColor("#C1DBCD"), Color.parseColor("#BC5429"), Color.parseColor("#697A82"), Color.parseColor("#89982E"), Color.parseColor("#89982E"), Color.parseColor("#89982E"), Color.parseColor("#AE8B2D"), Color.parseColor("#6D71BE"), Color.parseColor("#C24480"), Color.parseColor("#519F98"), Color.parseColor("#96A0A1"), Color.parseColor("#697A82"), Color.parseColor("#4689CC"), Color.parseColor("#697A82"), Color.parseColor("#519F98"), Color.parseColor("#BC5429"));

    @NotNull
    private static final ColorScheme ECLIPSE = new ColorScheme(Color.parseColor("#000000"), Color.parseColor("#000000"), Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"), Color.parseColor("#D4D4D4"), Color.parseColor("#828282"), Color.parseColor("#ADADAD"), Color.parseColor("#E8F2FE"), Color.parseColor("#AFD1FB"), Color.parseColor("#3A6FAD"), Color.parseColor("#E2FEDE"), Color.parseColor("#7BBCFE"), Color.parseColor("#0000F5"), Color.parseColor("#000000"), Color.parseColor("#800055"), Color.parseColor("#800055"), Color.parseColor("#800055"), Color.parseColor("#9A892E"), Color.parseColor("#5D1776"), Color.parseColor("#000000"), Color.parseColor("#2602F5"), Color.parseColor("#4F7E61"), Color.parseColor("#437D7E"), Color.parseColor("#437D7E"), Color.parseColor("#800055"), Color.parseColor("#2602F5"), Color.parseColor("#800055"));

    private EditorTheme() {
    }

    @NotNull
    public final ColorScheme getDARCULA() {
        return DARCULA;
    }

    @NotNull
    public final ColorScheme getMONOKAI() {
        return MONOKAI;
    }

    @NotNull
    public final ColorScheme getOBSIDIAN() {
        return OBSIDIAN;
    }

    @NotNull
    public final ColorScheme getLADIES_NIGHT() {
        return LADIES_NIGHT;
    }

    @NotNull
    public final ColorScheme getTOMORROW_NIGHT() {
        return TOMORROW_NIGHT;
    }

    @NotNull
    public final ColorScheme getVISUAL_STUDIO() {
        return VISUAL_STUDIO;
    }

    @NotNull
    public final ColorScheme getINTELLIJ_LIGHT() {
        return INTELLIJ_LIGHT;
    }

    @NotNull
    public final ColorScheme getSOLARIZED_LIGHT() {
        return SOLARIZED_LIGHT;
    }

    @NotNull
    public final ColorScheme getECLIPSE() {
        return ECLIPSE;
    }
}
