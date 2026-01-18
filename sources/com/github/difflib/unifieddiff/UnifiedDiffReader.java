package com.github.difflib.unifieddiff;

import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes57.dex */
public final class UnifiedDiffReader {
    private final InternalUnifiedDiffReader READER;
    private UnifiedDiffFile actualFile;
    private int new_ln;
    private int new_size;
    private int old_ln;
    private int old_size;
    static final Pattern UNIFIED_DIFF_CHUNK_REGEXP = Pattern.compile("^@@\\s+-(?:(\\d+)(?:,(\\d+))?)\\s+\\+(?:(\\d+)(?:,(\\d+))?)\\s+@@");
    static final Pattern TIMESTAMP_REGEXP = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}[T ]\\d{2}:\\d{2}:\\d{2}\\.\\d{3,})(?: [+-]\\d+)?");
    private static final Logger LOG = Logger.getLogger(UnifiedDiffReader.class.getName());
    private final UnifiedDiff data = new UnifiedDiff();
    private final UnifiedDiffLine DIFF_COMMAND = new UnifiedDiffLine(true, "^diff\\s", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda1(this));
    private final UnifiedDiffLine SIMILARITY_INDEX = new UnifiedDiffLine(true, "^similarity index (\\d+)%$", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda12(this));
    private final UnifiedDiffLine INDEX = new UnifiedDiffLine(true, "^index\\s[\\da-zA-Z]+\\.\\.[\\da-zA-Z]+(\\s(\\d+))?$", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda13(this));
    private final UnifiedDiffLine FROM_FILE = new UnifiedDiffLine(true, "^---\\s", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda14(this));
    private final UnifiedDiffLine TO_FILE = new UnifiedDiffLine(true, "^\\+\\+\\+\\s", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda15(this));
    private final UnifiedDiffLine RENAME_FROM = new UnifiedDiffLine(true, "^rename\\sfrom\\s(.+)$", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda16(this));
    private final UnifiedDiffLine RENAME_TO = new UnifiedDiffLine(true, "^rename\\sto\\s(.+)$", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda17(this));
    private final UnifiedDiffLine COPY_FROM = new UnifiedDiffLine(true, "^copy\\sfrom\\s(.+)$", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda18(this));
    private final UnifiedDiffLine COPY_TO = new UnifiedDiffLine(true, "^copy\\sto\\s(.+)$", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda19(this));
    private final UnifiedDiffLine NEW_FILE_MODE = new UnifiedDiffLine(true, "^new\\sfile\\smode\\s(\\d+)", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda20(this));
    private final UnifiedDiffLine DELETED_FILE_MODE = new UnifiedDiffLine(true, "^deleted\\sfile\\smode\\s(\\d+)", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda2(this));
    private final UnifiedDiffLine OLD_MODE = new UnifiedDiffLine(true, "^old\\smode\\s(\\d+)", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda3(this));
    private final UnifiedDiffLine NEW_MODE = new UnifiedDiffLine(true, "^new\\smode\\s(\\d+)", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda4(this));
    private final UnifiedDiffLine BINARY_ADDED = new UnifiedDiffLine(true, "^Binary\\sfiles\\s/dev/null\\sand\\sb/(.+)\\sdiffer", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda5(this));
    private final UnifiedDiffLine BINARY_DELETED = new UnifiedDiffLine(true, "^Binary\\sfiles\\sa/(.+)\\sand\\s/dev/null\\sdiffer", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda6(this));
    private final UnifiedDiffLine BINARY_EDITED = new UnifiedDiffLine(true, "^Binary\\sfiles\\sa/(.+)\\sand\\sb/(.+)\\sdiffer", (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda7(this));
    private final UnifiedDiffLine CHUNK = new UnifiedDiffLine(false, UNIFIED_DIFF_CHUNK_REGEXP, (BiConsumer) new UnifiedDiffReader$$ExternalSyntheticLambda8(this));
    private final UnifiedDiffLine LINE_NORMAL = new UnifiedDiffLine(this, "^\\s", new UnifiedDiffReader$$ExternalSyntheticLambda9(this));
    private final UnifiedDiffLine LINE_DEL = new UnifiedDiffLine(this, "^-", new UnifiedDiffReader$$ExternalSyntheticLambda10(this));
    private final UnifiedDiffLine LINE_ADD = new UnifiedDiffLine(this, "^\\+", new UnifiedDiffReader$$ExternalSyntheticLambda11(this));
    private List originalTxt = new ArrayList();
    private List revisedTxt = new ArrayList();
    private List addLineIdxList = new ArrayList();
    private List delLineIdxList = new ArrayList();
    private int delLineIdx = 0;
    private int addLineIdx = 0;

    public static /* synthetic */ void $r8$lambda$-HM87wxjGrRaPGrVUQBdTMsxpRE(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processDelLine(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$0p2uKimUGrpCJPYW8L3UcmLAtKw(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processRenameTo(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$2lqpYDyKT18O_dHkkSJJTK4_zwI(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processCopyFrom(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$CTKU-L6mvODoSFv5_3GWdLULSZA(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processDeletedFileMode(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$DsWp_D149lfRwvPxHQc6sWF0ajs(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processAddLine(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$GWT6-lIqrPGLVl2wdeNf0q2G5i4(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processNormalLine(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$HQtbkG8xP5gp-c6WCB82D3nYG_E(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processBinaryEdited(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$Q5SZYMlqk6y_AKh274hacpgxhhM(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processToFile(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$bbIfMLq7g9FwklyJFTpD2keZ9FI(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processBinaryAdded(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$bcAv0-0jEWcDP9uEZdFZOUMzya0(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processNewFileMode(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$eXYBhtfGiig5JRfZgu6GLoRr5is(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processNewMode(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$hqmYHN_tGsFjyKiByWK8mNMaraw(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processCopyTo(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$jZlMtxVfBD835RTjSnLgRtbpBzo(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processDiff(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$lWeGLuxVODZGklCc2-wXJSMbHZY(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processOldMode(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$mWvcMeasUitycmKzlDsuLFq1qrg(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processFromFile(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$pwTFFYUCwxqSyB0J2hYFu3FLn3E(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processIndex(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$qNUSfGZQXMbpng6Sx84SNJL2I14(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processBinaryDeleted(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$qi3pWiPNRNwS_vfssP5l6JgTN98(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processChunk(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$rBeEfFk7WfjdUuL7gIfZw2yxi2A(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processSimilarityIndex(matchResult, str);
    }

    public static /* synthetic */ void $r8$lambda$y33z0qTBRNTy-abRB-naOtIVlnQ(UnifiedDiffReader unifiedDiffReader, MatchResult matchResult, String str) {
        unifiedDiffReader.processRenameFrom(matchResult, str);
    }

    UnifiedDiffReader(Reader reader) {
        this.READER = new InternalUnifiedDiffReader(reader);
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0182, code lost:
    
        finalizeChunk();
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01b0, code lost:
    
        if (r25.READER.ready() == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01b8, code lost:
    
        if (r25.READER.ready() == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01be, code lost:
    
        if (r3.length() <= 0) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01c0, code lost:
    
        r3 = r3 + "\n";
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01cf, code lost:
    
        r3 = r3 + r25.READER.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01e5, code lost:
    
        r25.data.setTailTxt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01ec, code lost:
    
        return r25.data;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.github.difflib.unifieddiff.UnifiedDiff parse() throws java.io.IOException, com.github.difflib.unifieddiff.UnifiedDiffParserException {
        /*
            Method dump skipped, instructions count: 493
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.difflib.unifieddiff.UnifiedDiffReader.parse():com.github.difflib.unifieddiff.UnifiedDiff");
    }

    private String checkForNoNewLineAtTheEndOfTheFile(String str) throws IOException {
        if (!"\\ No newline at end of file".equals(str)) {
            return str;
        }
        this.actualFile.setNoNewLineAtTheEndOfTheFile(true);
        return this.READER.readLine();
    }

    static String[] parseFileNames(String str) {
        String[] split = str.split(" ");
        return new String[]{split[2].replaceAll("^a/", ""), split[3].replaceAll("^b/", "")};
    }

    public static UnifiedDiff parseUnifiedDiff(InputStream inputStream) throws IOException, UnifiedDiffParserException {
        return new UnifiedDiffReader(new BufferedReader(new InputStreamReader(inputStream))).parse();
    }

    private boolean processLine(String str, UnifiedDiffLine... unifiedDiffLineArr) throws UnifiedDiffParserException {
        if (str == null) {
            return false;
        }
        for (UnifiedDiffLine unifiedDiffLine : unifiedDiffLineArr) {
            if (unifiedDiffLine.processLine(str)) {
                LOG.fine("  >>> processed rule " + unifiedDiffLine.toString());
                return true;
            }
        }
        LOG.warning("  >>> no rule matched " + str);
        return false;
    }

    private boolean validLine(String str, UnifiedDiffLine... unifiedDiffLineArr) {
        if (str == null) {
            return false;
        }
        for (UnifiedDiffLine unifiedDiffLine : unifiedDiffLineArr) {
            if (unifiedDiffLine.validLine(str)) {
                LOG.fine("  >>> accepted rule " + unifiedDiffLine.toString());
                return true;
            }
        }
        return false;
    }

    private void initFileIfNecessary() {
        if (!this.originalTxt.isEmpty() || !this.revisedTxt.isEmpty()) {
            throw new IllegalStateException();
        }
        this.actualFile = null;
        UnifiedDiffFile unifiedDiffFile = new UnifiedDiffFile();
        this.actualFile = unifiedDiffFile;
        this.data.addFile(unifiedDiffFile);
    }

    private void processDiff(MatchResult matchResult, String str) {
        LOG.log(Level.FINE, "start {0}", str);
        String[] parseFileNames = parseFileNames(this.READER.lastLine());
        this.actualFile.setFromFile(parseFileNames[0]);
        this.actualFile.setToFile(parseFileNames[1]);
        this.actualFile.setDiffCommand(str);
    }

    private void processSimilarityIndex(MatchResult matchResult, String str) {
        this.actualFile.setSimilarityIndex(Integer.valueOf(matchResult.group(1)));
    }

    private void finalizeChunk() {
        if (this.originalTxt.isEmpty() && this.revisedTxt.isEmpty()) {
            return;
        }
        this.actualFile.getPatch().addDelta(new ChangeDelta(new Chunk(this.old_ln - 1, this.originalTxt, this.delLineIdxList), new Chunk(this.new_ln - 1, this.revisedTxt, this.addLineIdxList)));
        this.old_ln = 0;
        this.new_ln = 0;
        this.originalTxt.clear();
        this.revisedTxt.clear();
        this.addLineIdxList.clear();
        this.delLineIdxList.clear();
        this.delLineIdx = 0;
        this.addLineIdx = 0;
    }

    private void processNormalLine(MatchResult matchResult, String str) {
        String substring = str.substring(1);
        this.originalTxt.add(substring);
        this.revisedTxt.add(substring);
        this.delLineIdx++;
        this.addLineIdx++;
    }

    private void processAddLine(MatchResult matchResult, String str) {
        this.revisedTxt.add(str.substring(1));
        int i = this.addLineIdx + 1;
        this.addLineIdx = i;
        this.addLineIdxList.add(Integer.valueOf((this.new_ln - 1) + i));
    }

    private void processDelLine(MatchResult matchResult, String str) {
        this.originalTxt.add(str.substring(1));
        int i = this.delLineIdx + 1;
        this.delLineIdx = i;
        this.delLineIdxList.add(Integer.valueOf((this.old_ln - 1) + i));
    }

    private void processChunk(MatchResult matchResult, String str) {
        this.old_ln = toInteger(matchResult, 1, 1).intValue();
        this.old_size = toInteger(matchResult, 2, 1).intValue();
        this.new_ln = toInteger(matchResult, 3, 1).intValue();
        this.new_size = toInteger(matchResult, 4, 1).intValue();
        if (this.old_ln == 0) {
            this.old_ln = 1;
        }
        if (this.new_ln == 0) {
            this.new_ln = 1;
        }
    }

    private static Integer toInteger(MatchResult matchResult, int i, int i2) throws NumberFormatException {
        return Integer.valueOf(UnifiedDiffReader$$ExternalSyntheticBackport0.m(matchResult.group(i), "" + i2));
    }

    private void processIndex(MatchResult matchResult, String str) {
        LOG.log(Level.FINE, "index {0}", str);
        this.actualFile.setIndex(str.substring(6));
    }

    private void processFromFile(MatchResult matchResult, String str) {
        this.actualFile.setFromFile(extractFileName(str));
        this.actualFile.setFromTimestamp(extractTimestamp(str));
    }

    private void processToFile(MatchResult matchResult, String str) {
        this.actualFile.setToFile(extractFileName(str));
        this.actualFile.setToTimestamp(extractTimestamp(str));
    }

    private void processRenameFrom(MatchResult matchResult, String str) {
        this.actualFile.setRenameFrom(matchResult.group(1));
    }

    private void processRenameTo(MatchResult matchResult, String str) {
        this.actualFile.setRenameTo(matchResult.group(1));
    }

    private void processCopyFrom(MatchResult matchResult, String str) {
        this.actualFile.setCopyFrom(matchResult.group(1));
    }

    private void processCopyTo(MatchResult matchResult, String str) {
        this.actualFile.setCopyTo(matchResult.group(1));
    }

    private void processNewFileMode(MatchResult matchResult, String str) {
        this.actualFile.setNewFileMode(matchResult.group(1));
    }

    private void processDeletedFileMode(MatchResult matchResult, String str) {
        this.actualFile.setDeletedFileMode(matchResult.group(1));
    }

    private void processOldMode(MatchResult matchResult, String str) {
        this.actualFile.setOldMode(matchResult.group(1));
    }

    private void processNewMode(MatchResult matchResult, String str) {
        this.actualFile.setNewMode(matchResult.group(1));
    }

    private void processBinaryAdded(MatchResult matchResult, String str) {
        this.actualFile.setBinaryAdded(matchResult.group(1));
    }

    private void processBinaryDeleted(MatchResult matchResult, String str) {
        this.actualFile.setBinaryDeleted(matchResult.group(1));
    }

    private void processBinaryEdited(MatchResult matchResult, String str) {
        this.actualFile.setBinaryEdited(matchResult.group(1));
    }

    private String extractFileName(String str) {
        Matcher matcher = TIMESTAMP_REGEXP.matcher(str);
        if (matcher.find()) {
            str = str.substring(0, matcher.start());
        }
        return str.split("\t")[0].substring(4).replaceFirst("^(a|b|old|new)/", "").trim();
    }

    private String extractTimestamp(String str) {
        Matcher matcher = TIMESTAMP_REGEXP.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    final class UnifiedDiffLine {
        private final BiConsumer command;
        private final Pattern pattern;
        private final boolean stopsHeaderParsing;

        public UnifiedDiffLine(UnifiedDiffReader unifiedDiffReader, String str, BiConsumer biConsumer) {
            this(false, str, biConsumer);
        }

        public UnifiedDiffLine(boolean z, String str, BiConsumer biConsumer) {
            this.pattern = Pattern.compile(str);
            this.command = biConsumer;
            this.stopsHeaderParsing = z;
        }

        public UnifiedDiffLine(boolean z, Pattern pattern, BiConsumer biConsumer) {
            this.pattern = pattern;
            this.command = biConsumer;
            this.stopsHeaderParsing = z;
        }

        public boolean validLine(String str) {
            return this.pattern.matcher(str).find();
        }

        public boolean processLine(String str) throws UnifiedDiffParserException {
            Matcher matcher = this.pattern.matcher(str);
            if (!matcher.find()) {
                return false;
            }
            this.command.accept(matcher.toMatchResult(), str);
            return true;
        }

        public boolean isStopsHeaderParsing() {
            return this.stopsHeaderParsing;
        }

        public String toString() {
            return "UnifiedDiffLine{pattern=" + this.pattern + ", stopsHeaderParsing=" + this.stopsHeaderParsing + '}';
        }
    }
}
