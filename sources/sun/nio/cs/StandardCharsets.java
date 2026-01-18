package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import sun.security.action.GetPropertyAction;
import sun.util.PreHashedMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
public class StandardCharsets extends CharsetProvider {
    private static final String packagePrefix = "sun.nio.cs.";
    private Map aliasMap;
    private Map cache;
    private Map classMap;
    private boolean initialized = false;
    static String[] aliases_SJIS = {"sjis", "shift_jis", "shift-jis", "ms_kanji", "x-sjis", "csShiftJIS"};
    static String[] aliases_MS932 = {"MS932", "windows-932", "csWindows31J"};

    static /* bridge */ /* synthetic */ Charset -$$Nest$mlookup(StandardCharsets standardCharsets, String str) {
        return standardCharsets.lookup(str);
    }

    static String[] aliases_JIS_X_0208_Solaris() {
        return null;
    }

    static String[] aliases_JIS_X_0212_Solaris() {
        return null;
    }

    static String[] aliases_US_ASCII() {
        return new String[]{"iso-ir-6", "ANSI_X3.4-1986", "ISO_646.irv:1991", "ASCII", "ISO646-US", "us", "IBM367", "cp367", "csASCII", "default", "646", "iso_646.irv:1983", "ANSI_X3.4-1968", "ascii7"};
    }

    static String[] aliases_UTF_8() {
        return new String[]{"UTF8", "unicode-1-1-utf-8"};
    }

    static String[] aliases_CESU_8() {
        return new String[]{"CESU8", "csCESU-8"};
    }

    static String[] aliases_UTF_16() {
        return new String[]{"UTF_16", "utf16", "unicode", "UnicodeBig"};
    }

    static String[] aliases_UTF_16BE() {
        return new String[]{"UTF_16BE", "ISO-10646-UCS-2", "X-UTF-16BE", "UnicodeBigUnmarked"};
    }

    static String[] aliases_UTF_16LE() {
        return new String[]{"UTF_16LE", "X-UTF-16LE", "UnicodeLittleUnmarked"};
    }

    static String[] aliases_UTF_16LE_BOM() {
        return new String[]{"UnicodeLittle"};
    }

    static String[] aliases_UTF_32() {
        return new String[]{"UTF_32", "UTF32"};
    }

    static String[] aliases_UTF_32LE() {
        return new String[]{"UTF_32LE", "X-UTF-32LE"};
    }

    static String[] aliases_UTF_32BE() {
        return new String[]{"UTF_32BE", "X-UTF-32BE"};
    }

    static String[] aliases_UTF_32LE_BOM() {
        return new String[]{"UTF_32LE_BOM", "UTF-32LE-BOM"};
    }

    static String[] aliases_UTF_32BE_BOM() {
        return new String[]{"UTF_32BE_BOM", "UTF-32BE-BOM"};
    }

    static String[] aliases_ISO_8859_1() {
        return new String[]{"iso-ir-100", "ISO_8859-1", "latin1", "l1", "IBM819", "cp819", "csISOLatin1", "819", "IBM-819", "ISO8859_1", "ISO_8859-1:1987", "ISO_8859_1", "8859_1", "ISO8859-1"};
    }

    static String[] aliases_ISO_8859_2() {
        return new String[]{"iso8859_2", "8859_2", "iso-ir-101", "ISO_8859-2", "ISO_8859-2:1987", "ISO8859-2", "latin2", "l2", "ibm912", "ibm-912", "cp912", "912", "csISOLatin2"};
    }

    static String[] aliases_ISO_8859_4() {
        return new String[]{"iso8859_4", "iso8859-4", "8859_4", "iso-ir-110", "ISO_8859-4", "ISO_8859-4:1988", "latin4", "l4", "ibm914", "ibm-914", "cp914", "914", "csISOLatin4"};
    }

    static String[] aliases_ISO_8859_5() {
        return new String[]{"iso8859_5", "8859_5", "iso-ir-144", "ISO_8859-5", "ISO_8859-5:1988", "ISO8859-5", "cyrillic", "ibm915", "ibm-915", "cp915", "915", "csISOLatinCyrillic"};
    }

    static String[] aliases_ISO_8859_7() {
        return new String[]{"iso8859_7", "8859_7", "iso-ir-126", "ISO_8859-7", "ISO_8859-7:1987", "ELOT_928", "ECMA-118", "greek", "greek8", "csISOLatinGreek", "sun_eu_greek", "ibm813", "ibm-813", "813", "cp813", "iso8859-7"};
    }

    static String[] aliases_ISO_8859_9() {
        return new String[]{"iso8859_9", "8859_9", "iso-ir-148", "ISO_8859-9", "ISO_8859-9:1989", "ISO8859-9", "latin5", "l5", "ibm920", "ibm-920", "920", "cp920", "csISOLatin5"};
    }

    static String[] aliases_ISO_8859_13() {
        return new String[]{"iso8859_13", "8859_13", "iso_8859-13", "ISO8859-13"};
    }

    static String[] aliases_ISO_8859_15() {
        return new String[]{"ISO_8859-15", "Latin-9", "csISO885915", "8859_15", "ISO-8859-15", "ISO8859_15", "ISO8859-15", "IBM923", "IBM-923", "cp923", "923", "LATIN0", "LATIN9", "L9", "csISOlatin0", "csISOlatin9", "ISO8859_15_FDIS"};
    }

    static String[] aliases_ISO_8859_16() {
        return new String[]{"iso-ir-226", "ISO_8859-16:2001", "ISO_8859-16", "ISO8859_16", "latin10", "l10", "csISO885916"};
    }

    static String[] aliases_KOI8_R() {
        return new String[]{"koi8_r", "koi8", "cskoi8r"};
    }

    static String[] aliases_KOI8_U() {
        return new String[]{"koi8_u"};
    }

    static String[] aliases_MS1250() {
        return new String[]{"cp1250", "cp5346"};
    }

    static String[] aliases_MS1251() {
        return new String[]{"cp1251", "cp5347", "ansi-1251"};
    }

    static String[] aliases_MS1252() {
        return new String[]{"cp1252", "cp5348", "ibm-1252", "ibm1252"};
    }

    static String[] aliases_MS1253() {
        return new String[]{"cp1253", "cp5349"};
    }

    static String[] aliases_MS1254() {
        return new String[]{"cp1254", "cp5350"};
    }

    static String[] aliases_MS1257() {
        return new String[]{"cp1257", "cp5353"};
    }

    static String[] aliases_IBM437() {
        return new String[]{"cp437", "ibm437", "ibm-437", "437", "cspc8codepage437", "windows-437"};
    }

    static String[] aliases_IBM737() {
        return new String[]{"cp737", "ibm737", "ibm-737", "737"};
    }

    static String[] aliases_IBM775() {
        return new String[]{"cp775", "ibm775", "ibm-775", "775"};
    }

    static String[] aliases_IBM850() {
        return new String[]{"cp850", "ibm-850", "ibm850", "850", "cspc850multilingual"};
    }

    static String[] aliases_IBM852() {
        return new String[]{"cp852", "ibm852", "ibm-852", "852", "csPCp852"};
    }

    static String[] aliases_IBM855() {
        return new String[]{"cp855", "ibm-855", "ibm855", "855", "cspcp855"};
    }

    static String[] aliases_IBM857() {
        return new String[]{"cp857", "ibm857", "ibm-857", "857", "csIBM857"};
    }

    static String[] aliases_IBM858() {
        return new String[]{"cp858", "ccsid00858", "cp00858", "858", "PC-Multilingual-850+euro", "ibm858", "ibm-858"};
    }

    static String[] aliases_IBM862() {
        return new String[]{"cp862", "ibm862", "ibm-862", "862", "csIBM862", "cspc862latinhebrew"};
    }

    static String[] aliases_IBM866() {
        return new String[]{"cp866", "ibm866", "ibm-866", "866", "csIBM866"};
    }

    static String[] aliases_IBM874() {
        return new String[]{"cp874", "ibm874", "ibm-874", "874"};
    }

    static String[] aliases_Big5() {
        return new String[]{"csBig5"};
    }

    static String[] aliases_EUC_TW() {
        return new String[]{"euc_tw", "euctw", "cns11643", "EUC-TW"};
    }

    static String[] aliases_Big5_HKSCS() {
        return new String[]{"Big5_HKSCS", "big5hk", "big5-hkscs", "big5hkscs"};
    }

    static String[] aliases_Big5_Solaris() {
        return new String[]{"Big5_Solaris"};
    }

    static String[] aliases_GBK() {
        return new String[]{"windows-936", "CP936"};
    }

    static String[] aliases_GB18030() {
        return new String[]{"gb18030-2000"};
    }

    static String[] aliases_EUC_CN() {
        return new String[]{"gb2312", "gb2312-80", "gb2312-1980", "euc-cn", "euccn", "x-EUC-CN", "EUC_CN"};
    }

    static String[] aliases_SJIS() {
        return aliases_SJIS;
    }

    static String[] aliases_MS932() {
        return aliases_MS932;
    }

    static String[] aliases_JIS_X_0201() {
        return new String[]{"JIS0201", "JIS_X0201", "X0201", "csHalfWidthKatakana"};
    }

    static String[] aliases_JIS_X_0208() {
        return new String[]{"JIS0208", "JIS_C6226-1983", "iso-ir-87", "x0208", "JIS_X0208-1983", "csISO87JISX0208"};
    }

    static String[] aliases_JIS_X_0212() {
        return new String[]{"JIS0212", "jis_x0212-1990", "x0212", "iso-ir-159", "csISO159JISX02121990"};
    }

    static String[] aliases_EUC_JP() {
        return new String[]{"euc_jp", "eucjis", "eucjp", "Extended_UNIX_Code_Packed_Format_for_Japanese", "csEUCPkdFmtjapanese", "x-euc-jp", "x-eucjp"};
    }

    static String[] aliases_EUC_JP_LINUX() {
        return new String[]{"euc_jp_linux", "euc-jp-linux"};
    }

    static String[] aliases_EUC_JP_Open() {
        return new String[]{"EUC_JP_Solaris", "eucJP-open"};
    }

    static String[] aliases_PCK() {
        return new String[]{"pck"};
    }

    static String[] aliases_EUC_KR() {
        return new String[]{"euc_kr", "ksc5601", "euckr", "ks_c_5601-1987", "ksc5601-1987", "ksc5601_1987", "ksc_5601", "csEUCKR", "5601"};
    }

    static String[] aliases_Johab() {
        return new String[]{"ksc5601-1992", "ksc5601_1992", "ms1361", "johab"};
    }

    static String[] aliases_ISO_8859_3() {
        return new String[]{"iso8859_3", "8859_3", "ISO_8859-3:1988", "iso-ir-109", "ISO_8859-3", "ISO8859-3", "latin3", "l3", "ibm913", "ibm-913", "cp913", "913", "csISOLatin3"};
    }

    static String[] aliases_ISO_8859_6() {
        return new String[]{"iso8859_6", "8859_6", "iso-ir-127", "ISO_8859-6", "ISO_8859-6:1987", "ISO8859-6", "ECMA-114", "ASMO-708", "arabic", "ibm1089", "ibm-1089", "cp1089", "1089", "csISOLatinArabic"};
    }

    static String[] aliases_ISO_8859_8() {
        return new String[]{"iso8859_8", "8859_8", "iso-ir-138", "ISO_8859-8", "ISO_8859-8:1988", "ISO8859-8", "cp916", "916", "ibm916", "ibm-916", "hebrew", "csISOLatinHebrew"};
    }

    static String[] aliases_ISO_8859_11() {
        return new String[]{"iso-8859-11", "iso8859_11"};
    }

    static String[] aliases_TIS_620() {
        return new String[]{"tis620", "tis620.2533"};
    }

    private static final class Aliases extends PreHashedMap {
        private static final int MASK = 1023;
        private static final int ROWS = 1024;
        private static final int SHIFT = 0;
        private static final int SIZE = 336;

        /* synthetic */ Aliases(StandardCharsets-IA r1) {
            this();
        }

        private Aliases() {
            super(1024, 336, 0, 1023);
        }

        protected void init(Object[] objArr) {
            objArr[1] = new Object[]{"csisolatin0", "iso-8859-15"};
            objArr[2] = new Object[]{"x0208", "x-jis0208", new Object[]{"csisolatin1", "iso-8859-1"}};
            objArr[3] = new Object[]{"csisolatin2", "iso-8859-2"};
            objArr[4] = new Object[]{"csisolatin3", "iso-8859-3"};
            objArr[5] = new Object[]{"csisolatin4", "iso-8859-4"};
            objArr[6] = new Object[]{"csisolatin5", "iso-8859-9"};
            objArr[10] = new Object[]{"csisolatin9", "iso-8859-15"};
            objArr[19] = new Object[]{"unicodelittle", "x-utf-16le-bom"};
            objArr[23] = new Object[]{"ksc5601-1987", "euc-kr"};
            objArr[24] = new Object[]{"iso646-us", "us-ascii"};
            objArr[25] = new Object[]{"jis_x0208-1983", "x-jis0208", new Object[]{"iso_8859-7:1987", "iso-8859-7"}};
            objArr[26] = new Object[]{"912", "iso-8859-2"};
            objArr[27] = new Object[]{"913", "iso-8859-3", new Object[]{"x0212", "jis_x0212-1990"}};
            objArr[28] = new Object[]{"914", "iso-8859-4"};
            objArr[29] = new Object[]{"915", "iso-8859-5"};
            objArr[30] = new Object[]{"916", "iso-8859-8"};
            objArr[35] = new Object[]{"latin10", "iso-8859-16"};
            objArr[49] = new Object[]{"ksc5601-1992", "x-johab"};
            objArr[55] = new Object[]{"ibm-1252", "windows-1252", new Object[]{"920", "iso-8859-9"}};
            objArr[58] = new Object[]{"923", "iso-8859-15"};
            objArr[86] = new Object[]{"csisolatincyrillic", "iso-8859-5", new Object[]{"8859_1", "iso-8859-1"}};
            objArr[87] = new Object[]{"8859_2", "iso-8859-2"};
            objArr[88] = new Object[]{"8859_3", "iso-8859-3"};
            objArr[89] = new Object[]{"8859_4", "iso-8859-4"};
            objArr[90] = new Object[]{"813", "iso-8859-7", new Object[]{"8859_5", "iso-8859-5"}};
            objArr[91] = new Object[]{"8859_6", "iso-8859-6"};
            objArr[92] = new Object[]{"8859_7", "iso-8859-7"};
            objArr[93] = new Object[]{"8859_8", "iso-8859-8"};
            objArr[94] = new Object[]{"8859_9", "iso-8859-9"};
            objArr[95] = new Object[]{"iso_8859-1:1987", "iso-8859-1"};
            objArr[96] = new Object[]{"819", "iso-8859-1"};
            objArr[98] = new Object[]{"5601", "euc-kr"};
            objArr[106] = new Object[]{"unicode-1-1-utf-8", "utf-8"};
            objArr[121] = new Object[]{"ecma-114", "iso-8859-6", new Object[]{"x-utf-16le", "utf-16le"}};
            objArr[125] = new Object[]{"ecma-118", "iso-8859-7"};
            objArr[127] = new Object[]{"ks_c_5601-1987", "euc-kr"};
            objArr[129] = new Object[]{"eucjis", "euc-jp"};
            objArr[134] = new Object[]{"asmo-708", "iso-8859-6", new Object[]{"koi8_r", "koi8-r"}};
            objArr[135] = new Object[]{"euc-jp-linux", "x-euc-jp-linux"};
            objArr[137] = new Object[]{"koi8_u", "koi8-u"};
            objArr[141] = new Object[]{"cp912", "iso-8859-2"};
            objArr[142] = new Object[]{"cp913", "iso-8859-3"};
            objArr[143] = new Object[]{"euc_tw", "x-euc-tw", new Object[]{"cp914", "iso-8859-4"}};
            objArr[144] = new Object[]{"cp915", "iso-8859-5"};
            objArr[145] = new Object[]{"cp916", "iso-8859-8"};
            objArr[151] = new Object[]{"jis0201", "jis_x0201"};
            objArr[158] = new Object[]{"jis0208", "x-jis0208"};
            objArr[164] = new Object[]{"x-eucjp", "euc-jp"};
            objArr[170] = new Object[]{"cp920", "iso-8859-9"};
            objArr[172] = new Object[]{"arabic", "iso-8859-6"};
            objArr[173] = new Object[]{"cp923", "iso-8859-15"};
            objArr[177] = new Object[]{"utf_32le_bom", "x-utf-32le-bom"};
            objArr[183] = new Object[]{"jis0212", "jis_x0212-1990"};
            objArr[185] = new Object[]{"iso_8859-8:1988", "iso-8859-8"};
            objArr[192] = new Object[]{"utf_16be", "utf-16be"};
            objArr[199] = new Object[]{"cspc8codepage437", "ibm437", new Object[]{"ansi-1251", "windows-1251"}};
            objArr[205] = new Object[]{"cp813", "iso-8859-7"};
            objArr[207] = new Object[]{"cp936", "gbk"};
            objArr[211] = new Object[]{"850", "ibm850", new Object[]{"cp819", "iso-8859-1"}};
            objArr[213] = new Object[]{"852", "ibm852"};
            objArr[216] = new Object[]{"pck", "x-pck", new Object[]{"855", "ibm855"}};
            objArr[217] = new Object[]{"cswindows31j", "windows-31j"};
            objArr[218] = new Object[]{"857", "ibm857", new Object[]{"iso-ir-6", "us-ascii"}};
            objArr[219] = new Object[]{"858", "ibm00858", new Object[]{"737", "x-ibm737"}};
            objArr[221] = new Object[]{"euc-tw", "x-euc-tw"};
            objArr[225] = new Object[]{"csascii", "us-ascii"};
            objArr[242] = new Object[]{"ms932", "windows-31j", new Object[]{"ibm1252", "windows-1252"}};
            objArr[244] = new Object[]{"862", "ibm862"};
            objArr[248] = new Object[]{"866", "ibm866"};
            objArr[253] = new Object[]{"x-utf-32be", "utf-32be"};
            objArr[254] = new Object[]{"iso_8859-2:1987", "iso-8859-2"};
            objArr[259] = new Object[]{"unicodebig", "utf-16"};
            objArr[269] = new Object[]{"iso8859_15_fdis", "iso-8859-15"};
            objArr[277] = new Object[]{"874", "x-ibm874"};
            objArr[280] = new Object[]{"unicodelittleunmarked", "utf-16le"};
            objArr[281] = new Object[]{"ibm-1089", "iso-8859-6"};
            objArr[283] = new Object[]{"iso8859_1", "iso-8859-1"};
            objArr[284] = new Object[]{"iso8859_2", "iso-8859-2"};
            objArr[285] = new Object[]{"iso8859_3", "iso-8859-3", new Object[]{"csiso885915", "iso-8859-15"}};
            objArr[286] = new Object[]{"csiso885916", "iso-8859-16", new Object[]{"iso8859_4", "iso-8859-4"}};
            objArr[287] = new Object[]{"iso8859_5", "iso-8859-5"};
            objArr[288] = new Object[]{"iso8859_6", "iso-8859-6"};
            objArr[289] = new Object[]{"iso8859_7", "iso-8859-7"};
            objArr[290] = new Object[]{"iso8859_8", "iso-8859-8"};
            objArr[291] = new Object[]{"iso8859_9", "iso-8859-9"};
            objArr[294] = new Object[]{"ibm912", "iso-8859-2"};
            objArr[295] = new Object[]{"ibm913", "iso-8859-3"};
            objArr[296] = new Object[]{"ibm914", "iso-8859-4"};
            objArr[297] = new Object[]{"ibm915", "iso-8859-5"};
            objArr[298] = new Object[]{"ibm916", "iso-8859-8"};
            objArr[305] = new Object[]{"iso_8859-13", "iso-8859-13"};
            objArr[307] = new Object[]{"iso_8859-15", "iso-8859-15"};
            objArr[308] = new Object[]{"iso_8859-16", "iso-8859-16"};
            objArr[312] = new Object[]{"greek8", "iso-8859-7", new Object[]{"646", "us-ascii"}};
            objArr[318] = new Object[]{"ms_kanji", "shift_jis"};
            objArr[321] = new Object[]{"ibm-912", "iso-8859-2"};
            objArr[322] = new Object[]{"ibm-913", "iso-8859-3", new Object[]{"csiso87jisx0208", "x-jis0208"}};
            objArr[323] = new Object[]{"ibm920", "iso-8859-9", new Object[]{"ibm-914", "iso-8859-4"}};
            objArr[324] = new Object[]{"ibm-915", "iso-8859-5"};
            objArr[325] = new Object[]{"ibm-916", "iso-8859-8", new Object[]{"l1", "iso-8859-1"}};
            objArr[326] = new Object[]{"cp850", "ibm850", new Object[]{"ibm923", "iso-8859-15", new Object[]{"l2", "iso-8859-2"}}};
            objArr[327] = new Object[]{"l3", "iso-8859-3", new Object[]{"cyrillic", "iso-8859-5"}};
            objArr[328] = new Object[]{"cp852", "ibm852", new Object[]{"l4", "iso-8859-4"}};
            objArr[329] = new Object[]{"l5", "iso-8859-9"};
            objArr[331] = new Object[]{"cp855", "ibm855"};
            objArr[333] = new Object[]{"cp857", "ibm857", new Object[]{"l9", "iso-8859-15"}};
            objArr[334] = new Object[]{"cp858", "ibm00858", new Object[]{"cp737", "x-ibm737"}};
            objArr[336] = new Object[]{"iso_8859_1", "iso-8859-1"};
            objArr[339] = new Object[]{"koi8", "koi8-r"};
            objArr[341] = new Object[]{"775", "ibm775"};
            objArr[345] = new Object[]{"iso_8859-9:1989", "iso-8859-9"};
            objArr[350] = new Object[]{"eucjp-open", "x-eucjp-open", new Object[]{"euccn", "gb2312", new Object[]{"ibm-920", "iso-8859-9"}}};
            objArr[352] = new Object[]{"1089", "iso-8859-6"};
            objArr[353] = new Object[]{"ibm-923", "iso-8859-15"};
            objArr[358] = new Object[]{"ibm813", "iso-8859-7"};
            objArr[359] = new Object[]{"cp862", "ibm862"};
            objArr[363] = new Object[]{"cp866", "ibm866"};
            objArr[364] = new Object[]{"ibm819", "iso-8859-1"};
            objArr[378] = new Object[]{"ansi_x3.4-1968", "us-ascii"};
            objArr[385] = new Object[]{"ibm-813", "iso-8859-7"};
            objArr[391] = new Object[]{"ibm-819", "iso-8859-1"};
            objArr[392] = new Object[]{"cp874", "x-ibm874"};
            objArr[393] = new Object[]{"extended_unix_code_packed_format_for_japanese", "euc-jp"};
            objArr[405] = new Object[]{"iso-ir-100", "iso-8859-1"};
            objArr[406] = new Object[]{"iso-ir-101", "iso-8859-2"};
            objArr[408] = new Object[]{"437", "ibm437"};
            objArr[410] = new Object[]{"iso-ir-226", "iso-8859-16"};
            objArr[414] = new Object[]{"iso-ir-109", "iso-8859-3", new Object[]{"iso_8859-3:1988", "iso-8859-3"}};
            objArr[417] = new Object[]{"iso-8859-11", "x-iso-8859-11"};
            objArr[421] = new Object[]{"iso-8859-15", "iso-8859-15"};
            objArr[422] = new Object[]{"csiso159jisx02121990", "jis_x0212-1990"};
            objArr[428] = new Object[]{"latin0", "iso-8859-15"};
            objArr[429] = new Object[]{"latin1", "iso-8859-1"};
            objArr[430] = new Object[]{"latin2", "iso-8859-2"};
            objArr[431] = new Object[]{"latin3", "iso-8859-3"};
            objArr[432] = new Object[]{"latin4", "iso-8859-4"};
            objArr[433] = new Object[]{"latin5", "iso-8859-9"};
            objArr[436] = new Object[]{"iso-ir-110", "iso-8859-4"};
            objArr[437] = new Object[]{"latin9", "iso-8859-15"};
            objArr[438] = new Object[]{"ansi_x3.4-1986", "us-ascii"};
            objArr[442] = new Object[]{"x-euc-cn", "gb2312"};
            objArr[443] = new Object[]{"utf-32be-bom", "x-utf-32be-bom"};
            objArr[449] = new Object[]{"sjis", "shift_jis"};
            objArr[455] = new Object[]{"euc_jp_linux", "x-euc-jp-linux"};
            objArr[456] = new Object[]{"cp775", "ibm775"};
            objArr[468] = new Object[]{"ibm1089", "iso-8859-6"};
            objArr[471] = new Object[]{"shift_jis", "shift_jis"};
            objArr[473] = new Object[]{"iso-ir-126", "iso-8859-7"};
            objArr[474] = new Object[]{"iso-ir-127", "iso-8859-6"};
            objArr[479] = new Object[]{"ibm850", "ibm850"};
            objArr[481] = new Object[]{"ibm852", "ibm852"};
            objArr[484] = new Object[]{"ibm855", "ibm855"};
            objArr[486] = new Object[]{"ibm857", "ibm857"};
            objArr[487] = new Object[]{"ibm858", "ibm00858", new Object[]{"ibm737", "x-ibm737"}};
            objArr[502] = new Object[]{"x-sjis", "shift_jis", new Object[]{"utf_16le", "utf-16le"}};
            objArr[506] = new Object[]{"iso-ir-138", "iso-8859-8", new Object[]{"ibm-850", "ibm850"}};
            objArr[508] = new Object[]{"ibm-852", "ibm852"};
            objArr[511] = new Object[]{"ibm-855", "ibm855"};
            objArr[512] = new Object[]{"ibm862", "ibm862"};
            objArr[513] = new Object[]{"ibm-857", "ibm857"};
            objArr[514] = new Object[]{"ibm-858", "ibm00858", new Object[]{"ibm-737", "x-ibm737"}};
            objArr[516] = new Object[]{"ibm866", "ibm866"};
            objArr[520] = new Object[]{"unicodebigunmarked", "utf-16be"};
            objArr[523] = new Object[]{"cp437", "ibm437"};
            objArr[524] = new Object[]{"utf16", "utf-16"};
            objArr[526] = new Object[]{"windows-932", "windows-31j"};
            objArr[530] = new Object[]{"windows-936", "gbk"};
            objArr[533] = new Object[]{"iso-ir-144", "iso-8859-5"};
            objArr[537] = new Object[]{"iso-ir-148", "iso-8859-9"};
            objArr[539] = new Object[]{"ibm-862", "ibm862"};
            objArr[543] = new Object[]{"ibm-866", "ibm866"};
            objArr[545] = new Object[]{"ibm874", "x-ibm874"};
            objArr[550] = new Object[]{"ksc_5601", "euc-kr"};
            objArr[555] = new Object[]{"big5hkscs", "big5-hkscs"};
            objArr[563] = new Object[]{"x-utf-32le", "utf-32le"};
            objArr[569] = new Object[]{"eucjp", "euc-jp", new Object[]{"iso-ir-159", "jis_x0212-1990"}};
            objArr[572] = new Object[]{"ibm-874", "x-ibm874"};
            objArr[573] = new Object[]{"iso_8859-4:1988", "iso-8859-4"};
            objArr[576] = new Object[]{"gb18030-2000", "gb18030"};
            objArr[577] = new Object[]{"default", "us-ascii"};
            objArr[582] = new Object[]{"utf32", "utf-32"};
            objArr[583] = new Object[]{"pc-multilingual-850+euro", "ibm00858"};
            objArr[588] = new Object[]{"elot_928", "iso-8859-7"};
            objArr[590] = new Object[]{"csisolatinhebrew", "iso-8859-8"};
            objArr[591] = new Object[]{"cshalfwidthkatakana", "jis_x0201"};
            objArr[593] = new Object[]{"csisolatingreek", "iso-8859-7"};
            objArr[598] = new Object[]{"csibm857", "ibm857"};
            objArr[602] = new Object[]{"euckr", "euc-kr"};
            objArr[609] = new Object[]{"ibm775", "ibm775"};
            objArr[617] = new Object[]{"cp1250", "windows-1250"};
            objArr[618] = new Object[]{"cp1251", "windows-1251"};
            objArr[619] = new Object[]{"cp1252", "windows-1252"};
            objArr[620] = new Object[]{"cp1253", "windows-1253"};
            objArr[621] = new Object[]{"cp1254", "windows-1254"};
            objArr[624] = new Object[]{"csibm862", "ibm862", new Object[]{"cp1257", "windows-1257"}};
            objArr[628] = new Object[]{"csibm866", "ibm866", new Object[]{"cesu8", "cesu-8"}};
            objArr[630] = new Object[]{"iso8859_11", "x-iso-8859-11"};
            objArr[631] = new Object[]{"euc_cn", "gb2312"};
            objArr[632] = new Object[]{"iso8859_13", "iso-8859-13"};
            objArr[634] = new Object[]{"iso8859_15", "iso-8859-15", new Object[]{"utf_32be", "utf-32be"}};
            objArr[635] = new Object[]{"iso8859_16", "iso-8859-16", new Object[]{"utf_32be_bom", "x-utf-32be-bom"}};
            objArr[636] = new Object[]{"ibm-775", "ibm775"};
            objArr[654] = new Object[]{"cp00858", "ibm00858"};
            objArr[661] = new Object[]{"x-euc-jp", "euc-jp"};
            objArr[669] = new Object[]{"8859_13", "iso-8859-13"};
            objArr[670] = new Object[]{"us", "us-ascii"};
            objArr[671] = new Object[]{"8859_15", "iso-8859-15"};
            objArr[676] = new Object[]{"ibm437", "ibm437"};
            objArr[679] = new Object[]{"cp367", "us-ascii"};
            objArr[685] = new Object[]{"cns11643", "x-euc-tw"};
            objArr[686] = new Object[]{"iso-10646-ucs-2", "utf-16be"};
            objArr[694] = new Object[]{"big5_hkscs", "big5-hkscs"};
            objArr[702] = new Object[]{"euc_jp_solaris", "x-eucjp-open"};
            objArr[703] = new Object[]{"ibm-437", "ibm437"};
            objArr[709] = new Object[]{"euc-cn", "gb2312"};
            objArr[710] = new Object[]{"iso8859-13", "iso-8859-13"};
            objArr[712] = new Object[]{"iso8859-15", "iso-8859-15"};
            objArr[731] = new Object[]{"iso-ir-87", "x-jis0208"};
            objArr[732] = new Object[]{"iso_8859-5:1988", "iso-8859-5"};
            objArr[733] = new Object[]{"ksc5601", "euc-kr", new Object[]{"unicode", "utf-16"}};
            objArr[760] = new Object[]{"big5hk", "big5-hkscs"};
            objArr[768] = new Object[]{"greek", "iso-8859-7"};
            objArr[771] = new Object[]{"ms1361", "x-johab"};
            objArr[774] = new Object[]{"ascii7", "us-ascii"};
            objArr[781] = new Object[]{"iso8859-1", "iso-8859-1"};
            objArr[782] = new Object[]{"iso8859-2", "iso-8859-2"};
            objArr[783] = new Object[]{"iso8859-3", "iso-8859-3", new Object[]{"cskoi8r", "koi8-r"}};
            objArr[784] = new Object[]{"jis_x0201", "jis_x0201", new Object[]{"iso8859-4", "iso-8859-4"}};
            objArr[785] = new Object[]{"iso8859-5", "iso-8859-5"};
            objArr[786] = new Object[]{"iso8859-6", "iso-8859-6"};
            objArr[787] = new Object[]{"iso8859-7", "iso-8859-7"};
            objArr[788] = new Object[]{"iso8859-8", "iso-8859-8"};
            objArr[789] = new Object[]{"iso8859-9", "iso-8859-9"};
            objArr[804] = new Object[]{"johab", "x-johab"};
            objArr[813] = new Object[]{"ccsid00858", "ibm00858"};
            objArr[818] = new Object[]{"cspc862latinhebrew", "ibm862"};
            objArr[827] = new Object[]{"tis620.2533", "tis-620"};
            objArr[832] = new Object[]{"ibm367", "us-ascii"};
            objArr[834] = new Object[]{"iso_8859-1", "iso-8859-1"};
            objArr[835] = new Object[]{"iso_8859-2", "iso-8859-2", new Object[]{"x-utf-16be", "utf-16be"}};
            objArr[836] = new Object[]{"iso_8859-3", "iso-8859-3", new Object[]{"sun_eu_greek", "iso-8859-7"}};
            objArr[837] = new Object[]{"iso_8859-16:2001", "iso-8859-16", new Object[]{"iso_8859-4", "iso-8859-4"}};
            objArr[838] = new Object[]{"iso_8859-5", "iso-8859-5"};
            objArr[839] = new Object[]{"iso_8859-6", "iso-8859-6"};
            objArr[840] = new Object[]{"gb2312-80", "gb2312", new Object[]{"cspcp852", "ibm852", new Object[]{"iso_8859-7", "iso-8859-7"}}};
            objArr[841] = new Object[]{"iso_8859-8", "iso-8859-8", new Object[]{"ksc5601_1987", "euc-kr", new Object[]{"shift-jis", "shift_jis"}}};
            objArr[842] = new Object[]{"iso_8859-9", "iso-8859-9"};
            objArr[843] = new Object[]{"cspcp855", "ibm855"};
            objArr[845] = new Object[]{"cp1089", "iso-8859-6"};
            objArr[846] = new Object[]{"windows-437", "ibm437"};
            objArr[849] = new Object[]{"ascii", "us-ascii"};
            objArr[850] = new Object[]{"euc_jp", "euc-jp"};
            objArr[863] = new Object[]{"cscesu-8", "cesu-8"};
            objArr[867] = new Object[]{"ksc5601_1992", "x-johab"};
            objArr[880] = new Object[]{"gb2312-1980", "gb2312"};
            objArr[881] = new Object[]{"utf8", "utf-8"};
            objArr[883] = new Object[]{"euc_kr", "euc-kr"};
            objArr[886] = new Object[]{"euctw", "x-euc-tw"};
            objArr[890] = new Object[]{"iso_8859-6:1987", "iso-8859-6"};
            objArr[891] = new Object[]{"csisolatinarabic", "iso-8859-6"};
            objArr[893] = new Object[]{"gb2312", "gb2312"};
            objArr[895] = new Object[]{"hebrew", "iso-8859-8"};
            objArr[896] = new Object[]{"iso_646.irv:1983", "us-ascii"};
            objArr[907] = new Object[]{"l10", "iso-8859-16"};
            objArr[909] = new Object[]{"cp5346", "windows-1250"};
            objArr[910] = new Object[]{"cp5347", "windows-1251"};
            objArr[911] = new Object[]{"cp5348", "windows-1252"};
            objArr[912] = new Object[]{"cp5349", "windows-1253"};
            objArr[918] = new Object[]{"tis620", "tis-620"};
            objArr[925] = new Object[]{"iso_646.irv:1991", "us-ascii"};
            objArr[929] = new Object[]{"big5_solaris", "x-big5-solaris"};
            objArr[933] = new Object[]{"csbig5", "big5"};
            objArr[934] = new Object[]{"cp5350", "windows-1254"};
            objArr[936] = new Object[]{"big5-hkscs", "big5-hkscs"};
            objArr[937] = new Object[]{"cp5353", "windows-1257"};
            objArr[944] = new Object[]{"latin-9", "iso-8859-15", new Object[]{"utf_32le", "utf-32le"}};
            objArr[956] = new Object[]{"jis_x0212-1990", "jis_x0212-1990"};
            objArr[957] = new Object[]{"utf_16", "utf-16"};
            objArr[962] = new Object[]{"csshiftjis", "shift_jis"};
            objArr[984] = new Object[]{"cseucpkdfmtjapanese", "euc-jp", new Object[]{"jis_c6226-1983", "x-jis0208"}};
            objArr[993] = new Object[]{"cspc850multilingual", "ibm850"};
            objArr[1002] = new Object[]{"cseuckr", "euc-kr"};
            objArr[1009] = new Object[]{"utf-32le-bom", "x-utf-32le-bom"};
            objArr[1015] = new Object[]{"utf_32", "utf-32"};
            objArr[1019] = new Object[]{"x0201", "jis_x0201"};
        }
    }

    private static final class Classes extends PreHashedMap {
        private static final int MASK = 63;
        private static final int ROWS = 64;
        private static final int SHIFT = 0;
        private static final int SIZE = 63;

        /* synthetic */ Classes(StandardCharsets-IA r1) {
            this();
        }

        private Classes() {
            super(64, 63, 0, 63);
        }

        protected void init(Object[] objArr) {
            objArr[0] = new Object[]{"ibm862", "IBM862"};
            objArr[1] = new Object[]{"euc-kr", "EUC_KR"};
            objArr[2] = new Object[]{"windows-31j", "MS932"};
            objArr[3] = new Object[]{"x-pck", "PCK"};
            objArr[4] = new Object[]{"ibm866", "IBM866", new Object[]{"utf-16le", "UTF_16LE"}};
            objArr[5] = new Object[]{"tis-620", "TIS_620", new Object[]{"utf-32", "UTF_32"}};
            objArr[6] = new Object[]{"windows-1250", "MS1250"};
            objArr[7] = new Object[]{"windows-1251", "MS1251"};
            objArr[8] = new Object[]{"x-big5-solaris", "Big5_Solaris", new Object[]{"windows-1252", "MS1252", new Object[]{"utf-32be", "UTF_32BE"}}};
            objArr[9] = new Object[]{"x-jis0208", "JIS_X_0208", new Object[]{"windows-1253", "MS1253"}};
            objArr[10] = new Object[]{"windows-1254", "MS1254"};
            objArr[11] = new Object[]{"gb18030", "GB18030", new Object[]{"utf-16", "UTF_16"}};
            objArr[12] = new Object[]{"x-iso-8859-11", "ISO_8859_11"};
            objArr[13] = new Object[]{"windows-1257", "MS1257"};
            objArr[14] = new Object[]{"utf-16be", "UTF_16BE"};
            objArr[15] = new Object[]{"x-johab", "Johab"};
            objArr[16] = new Object[]{"jis_x0201", "JIS_X_0201", new Object[]{"iso-8859-1", "ISO_8859_1"}};
            objArr[17] = new Object[]{"iso-8859-2", "ISO_8859_2"};
            objArr[18] = new Object[]{"iso-8859-3", "ISO_8859_3", new Object[]{"x-euc-tw", "EUC_TW", new Object[]{"utf-8", "UTF_8"}}};
            objArr[19] = new Object[]{"x-eucjp-open", "EUC_JP_Open", new Object[]{"iso-8859-4", "ISO_8859_4"}};
            objArr[20] = new Object[]{"iso-8859-5", "ISO_8859_5"};
            objArr[21] = new Object[]{"iso-8859-6", "ISO_8859_6"};
            objArr[22] = new Object[]{"x-ibm874", "IBM874", new Object[]{"iso-8859-7", "ISO_8859_7"}};
            objArr[23] = new Object[]{"iso-8859-8", "ISO_8859_8", new Object[]{"shift_jis", "SJIS"}};
            objArr[24] = new Object[]{"iso-8859-9", "ISO_8859_9"};
            objArr[28] = new Object[]{"x-ibm737", "IBM737"};
            objArr[31] = new Object[]{"ibm850", "IBM850"};
            objArr[32] = new Object[]{"euc-jp", "EUC_JP"};
            objArr[33] = new Object[]{"ibm852", "IBM852", new Object[]{"ibm775", "IBM775"}};
            objArr[34] = new Object[]{"us-ascii", "US_ASCII"};
            objArr[35] = new Object[]{"iso-8859-13", "ISO_8859_13"};
            objArr[36] = new Object[]{"ibm855", "IBM855", new Object[]{"ibm437", "IBM437"}};
            objArr[37] = new Object[]{"iso-8859-15", "ISO_8859_15"};
            objArr[38] = new Object[]{"ibm857", "IBM857", new Object[]{"iso-8859-16", "ISO_8859_16", new Object[]{"x-utf-32le-bom", "UTF_32LE_BOM"}}};
            objArr[39] = new Object[]{"ibm00858", "IBM858"};
            objArr[40] = new Object[]{"big5-hkscs", "Big5_HKSCS"};
            objArr[44] = new Object[]{"x-utf-16le-bom", "UTF_16LE_BOM"};
            objArr[47] = new Object[]{"cesu-8", "CESU_8"};
            objArr[48] = new Object[]{"gbk", "GBK", new Object[]{"x-utf-32be-bom", "UTF_32BE_BOM"}};
            objArr[53] = new Object[]{"big5", "Big5"};
            objArr[56] = new Object[]{"koi8-r", "KOI8_R"};
            objArr[59] = new Object[]{"koi8-u", "KOI8_U"};
            objArr[60] = new Object[]{"x-euc-jp-linux", "EUC_JP_LINUX", new Object[]{"jis_x0212-1990", "JIS_X_0212"}};
            objArr[61] = new Object[]{"gb2312", "EUC_CN"};
            objArr[62] = new Object[]{"utf-32le", "UTF_32LE"};
        }
    }

    private static final class Cache extends PreHashedMap {
        private static final int MASK = 63;
        private static final int ROWS = 64;
        private static final int SHIFT = 0;
        private static final int SIZE = 63;

        /* synthetic */ Cache(StandardCharsets-IA r1) {
            this();
        }

        private Cache() {
            super(64, 63, 0, 63);
        }

        protected void init(Object[] objArr) {
            objArr[0] = new Object[]{"ibm862", null};
            objArr[1] = new Object[]{"euc-kr", null};
            objArr[2] = new Object[]{"windows-31j", null};
            objArr[3] = new Object[]{"x-pck", null};
            objArr[4] = new Object[]{"ibm866", null, new Object[]{"utf-16le", null}};
            objArr[5] = new Object[]{"tis-620", null, new Object[]{"utf-32", null}};
            objArr[6] = new Object[]{"windows-1250", null};
            objArr[7] = new Object[]{"windows-1251", null};
            objArr[8] = new Object[]{"x-big5-solaris", null, new Object[]{"windows-1252", null, new Object[]{"utf-32be", null}}};
            objArr[9] = new Object[]{"x-jis0208", null, new Object[]{"windows-1253", null}};
            objArr[10] = new Object[]{"windows-1254", null};
            objArr[11] = new Object[]{"gb18030", null, new Object[]{"utf-16", null}};
            objArr[12] = new Object[]{"x-iso-8859-11", null};
            objArr[13] = new Object[]{"windows-1257", null};
            objArr[14] = new Object[]{"utf-16be", null};
            objArr[15] = new Object[]{"x-johab", null};
            objArr[16] = new Object[]{"jis_x0201", null, new Object[]{"iso-8859-1", null}};
            objArr[17] = new Object[]{"iso-8859-2", null};
            objArr[18] = new Object[]{"iso-8859-3", null, new Object[]{"x-euc-tw", null, new Object[]{"utf-8", null}}};
            objArr[19] = new Object[]{"x-eucjp-open", null, new Object[]{"iso-8859-4", null}};
            objArr[20] = new Object[]{"iso-8859-5", null};
            objArr[21] = new Object[]{"iso-8859-6", null};
            objArr[22] = new Object[]{"x-ibm874", null, new Object[]{"iso-8859-7", null}};
            objArr[23] = new Object[]{"iso-8859-8", null, new Object[]{"shift_jis", null}};
            objArr[24] = new Object[]{"iso-8859-9", null};
            objArr[28] = new Object[]{"x-ibm737", null};
            objArr[31] = new Object[]{"ibm850", null};
            objArr[32] = new Object[]{"euc-jp", null};
            objArr[33] = new Object[]{"ibm852", null, new Object[]{"ibm775", null}};
            objArr[34] = new Object[]{"us-ascii", null};
            objArr[35] = new Object[]{"iso-8859-13", null};
            objArr[36] = new Object[]{"ibm855", null, new Object[]{"ibm437", null}};
            objArr[37] = new Object[]{"iso-8859-15", null};
            objArr[38] = new Object[]{"ibm857", null, new Object[]{"iso-8859-16", null, new Object[]{"x-utf-32le-bom", null}}};
            objArr[39] = new Object[]{"ibm00858", null};
            objArr[40] = new Object[]{"big5-hkscs", null};
            objArr[44] = new Object[]{"x-utf-16le-bom", null};
            objArr[47] = new Object[]{"cesu-8", null};
            objArr[48] = new Object[]{"gbk", null, new Object[]{"x-utf-32be-bom", null}};
            objArr[53] = new Object[]{"big5", null};
            objArr[56] = new Object[]{"koi8-r", null};
            objArr[59] = new Object[]{"koi8-u", null};
            objArr[60] = new Object[]{"x-euc-jp-linux", null, new Object[]{"jis_x0212-1990", null}};
            objArr[61] = new Object[]{"gb2312", null};
            objArr[62] = new Object[]{"utf-32le", null};
        }
    }

    private String canonicalize(String str) {
        String str2 = (String) aliasMap().get(str);
        return str2 != null ? str2 : str;
    }

    private Map aliasMap() {
        Map map = this.aliasMap;
        if (map != null) {
            return map;
        }
        AbstractMap aliases = new Aliases(null);
        this.aliasMap = aliases;
        return aliases;
    }

    private Map classMap() {
        Map map = this.classMap;
        if (map != null) {
            return map;
        }
        AbstractMap classes = new Classes(null);
        this.classMap = classes;
        return classes;
    }

    private Map cache() {
        Map map = this.cache;
        if (map != null) {
            return map;
        }
        AbstractMap cache = new Cache(null);
        cache.put("utf-8", UTF_8.INSTANCE);
        cache.put("iso-8859-1", ISO_8859_1.INSTANCE);
        cache.put("us-ascii", US_ASCII.INSTANCE);
        cache.put("utf-16", java.nio.charset.StandardCharsets.UTF_16);
        cache.put("utf-16be", java.nio.charset.StandardCharsets.UTF_16BE);
        cache.put("utf-16le", java.nio.charset.StandardCharsets.UTF_16LE);
        this.cache = cache;
        return cache;
    }

    private static String toLower(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((('Z' - charAt) | (charAt - 'A')) >= 0) {
                StringBuilder sb = new StringBuilder(length);
                for (int i2 = 0; i2 < length; i2++) {
                    char charAt2 = str.charAt(i2);
                    if (((charAt2 - 'A') | ('Z' - charAt2)) >= 0) {
                        sb.append((char) (charAt2 + ' '));
                    } else {
                        sb.append(charAt2);
                    }
                }
                return sb.toString();
            }
        }
        return str;
    }

    private Charset lookup(String str) {
        init();
        if (str.equals("UTF-8")) {
            return UTF_8.INSTANCE;
        }
        if (str.equals("US-ASCII")) {
            return US_ASCII.INSTANCE;
        }
        if (str.equals("ISO-8859-1")) {
            return ISO_8859_1.INSTANCE;
        }
        String canonicalize = canonicalize(toLower(str));
        Charset charset = (Charset) cache().get(canonicalize);
        if (charset != null) {
            return charset;
        }
        String str2 = (String) classMap().get(canonicalize);
        if (str2 == null) {
            return null;
        }
        try {
            return cache(canonicalize, (Charset) Class.forName("sun.nio.cs." + str2, true, getClass().getClassLoader()).newInstance());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
            return null;
        }
    }

    private Charset cache(String str, Charset charset) {
        cache().put(str, charset);
        return charset;
    }

    public final Charset charsetForName(String str) {
        Charset lookup;
        synchronized (this) {
            lookup = lookup(str);
        }
        return lookup;
    }

    public final Iterator charsets() {
        Set keySet;
        synchronized (this) {
            init();
            keySet = classMap().keySet();
            aliasMap();
            cache();
        }
        return new 1(keySet);
    }

    class 1 implements Iterator {
        Iterator i;
        final /* synthetic */ Set val$charsetNames;

        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.-CC.$default$forEachRemaining(this, consumer);
        }

        1(Set set) {
            this.val$charsetNames = set;
            this.i = set.iterator();
        }

        public boolean hasNext() {
            return this.i.hasNext();
        }

        public Charset next() {
            return StandardCharsets.-$$Nest$mlookup(StandardCharsets.this, (String) this.i.next());
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void init() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        String privilegedGetProperty = GetPropertyAction.privilegedGetProperty("sun.nio.cs.map");
        if (privilegedGetProperty != null) {
            Map aliasMap = aliasMap();
            Map classMap = classMap();
            for (String str : privilegedGetProperty.split(",")) {
                if (str.equalsIgnoreCase("Windows-31J/Shift_JIS")) {
                    if (classMap.get("shift_jis") == null || classMap.get("windows-31j") == null) {
                        return;
                    }
                    String[] strArr = {"MS932", "windows-932", "csWindows31J", "shift-jis", "ms_kanji", "x-sjis", "csShiftJIS", "shift_jis"};
                    aliases_MS932 = strArr;
                    aliases_SJIS = new String[]{"sjis"};
                    for (String str2 : strArr) {
                        aliasMap.put(toLower(str2), "windows-31j");
                    }
                    cache().put("shift_jis", null);
                    return;
                }
            }
        }
    }
}
