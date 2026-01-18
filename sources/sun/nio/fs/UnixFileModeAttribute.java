package sun.nio.fs;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Iterator;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
class UnixFileModeAttribute {
    static final int ALL_PERMISSIONS = 511;
    static final int ALL_READWRITE = 438;
    static final int TEMPFILE_PERMISSIONS = 448;

    private UnixFileModeAttribute() {
    }

    static int toUnixMode(Set set) {
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            PosixFilePermission posixFilePermission = (PosixFilePermission) it.next();
            posixFilePermission.getClass();
            switch (1.$SwitchMap$java$nio$file$attribute$PosixFilePermission[posixFilePermission.ordinal()]) {
                case 1:
                    i |= 256;
                    break;
                case 2:
                    i |= 128;
                    break;
                case 3:
                    i |= 64;
                    break;
                case 4:
                    i |= 32;
                    break;
                case 5:
                    i |= 16;
                    break;
                case 6:
                    i |= 8;
                    break;
                case 7:
                    i |= 4;
                    break;
                case 8:
                    i |= 2;
                    break;
                case 9:
                    i |= 1;
                    break;
            }
        }
        return i;
    }

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$java$nio$file$attribute$PosixFilePermission;

        static {
            int[] iArr = new int[PosixFilePermission.values().length];
            $SwitchMap$java$nio$file$attribute$PosixFilePermission = iArr;
            try {
                iArr[PosixFilePermission.OWNER_READ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$nio$file$attribute$PosixFilePermission[PosixFilePermission.OWNER_WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$nio$file$attribute$PosixFilePermission[PosixFilePermission.OWNER_EXECUTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$nio$file$attribute$PosixFilePermission[PosixFilePermission.GROUP_READ.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$nio$file$attribute$PosixFilePermission[PosixFilePermission.GROUP_WRITE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$nio$file$attribute$PosixFilePermission[PosixFilePermission.GROUP_EXECUTE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$nio$file$attribute$PosixFilePermission[PosixFilePermission.OTHERS_READ.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$nio$file$attribute$PosixFilePermission[PosixFilePermission.OTHERS_WRITE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$java$nio$file$attribute$PosixFilePermission[PosixFilePermission.OTHERS_EXECUTE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    static int toUnixMode(int i, FileAttribute... fileAttributeArr) {
        for (FileAttribute fileAttribute : fileAttributeArr) {
            String name = fileAttribute.name();
            if (!name.equals("posix:permissions") && !name.equals("unix:permissions")) {
                throw new UnsupportedOperationException("'" + fileAttribute.name() + "' not supported as initial attribute");
            }
            i = toUnixMode((Set) fileAttribute.value());
        }
        return i;
    }
}
