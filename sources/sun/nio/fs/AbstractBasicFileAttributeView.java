package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
abstract class AbstractBasicFileAttributeView implements BasicFileAttributeView, DynamicFileAttributeView {
    private static final String CREATION_TIME_NAME = "creationTime";
    private static final String FILE_KEY_NAME = "fileKey";
    private static final String IS_DIRECTORY_NAME = "isDirectory";
    private static final String IS_OTHER_NAME = "isOther";
    private static final String IS_REGULAR_FILE_NAME = "isRegularFile";
    private static final String IS_SYMBOLIC_LINK_NAME = "isSymbolicLink";
    private static final String LAST_ACCESS_TIME_NAME = "lastAccessTime";
    private static final String LAST_MODIFIED_TIME_NAME = "lastModifiedTime";
    private static final String SIZE_NAME = "size";
    static final Set basicAttributeNames = Util.newSet("size", "creationTime", "lastAccessTime", "lastModifiedTime", "fileKey", "isDirectory", "isRegularFile", "isSymbolicLink", "isOther");

    protected AbstractBasicFileAttributeView() {
    }

    public String name() {
        return "basic";
    }

    public void setAttribute(String str, Object obj) throws IOException {
        if (str.equals("lastModifiedTime")) {
            setTimes((FileTime) obj, null, null);
            return;
        }
        if (str.equals("lastAccessTime")) {
            setTimes(null, (FileTime) obj, null);
            return;
        }
        if (str.equals("creationTime")) {
            setTimes(null, null, (FileTime) obj);
            return;
        }
        throw new IllegalArgumentException("'" + name() + ":" + str + "' not recognized");
    }

    static class AttributesBuilder {
        private boolean copyAll;
        private Set names = new HashSet();
        private Map map = new HashMap();

        private AttributesBuilder(Set set, String[] strArr) {
            for (String str : strArr) {
                if (str.equals("*")) {
                    this.copyAll = true;
                } else {
                    if (!set.contains(str)) {
                        throw new IllegalArgumentException("'" + str + "' not recognized");
                    }
                    this.names.add(str);
                }
            }
        }

        static AttributesBuilder create(Set set, String[] strArr) {
            return new AttributesBuilder(set, strArr);
        }

        boolean match(String str) {
            return this.copyAll || this.names.contains(str);
        }

        void add(String str, Object obj) {
            this.map.put(str, obj);
        }

        Map unmodifiableMap() {
            return Collections.unmodifiableMap(this.map);
        }
    }

    final void addRequestedBasicAttributes(BasicFileAttributes basicFileAttributes, AttributesBuilder attributesBuilder) {
        if (attributesBuilder.match("size")) {
            attributesBuilder.add("size", Long.valueOf(basicFileAttributes.size()));
        }
        if (attributesBuilder.match("creationTime")) {
            attributesBuilder.add("creationTime", basicFileAttributes.creationTime());
        }
        if (attributesBuilder.match("lastAccessTime")) {
            attributesBuilder.add("lastAccessTime", basicFileAttributes.lastAccessTime());
        }
        if (attributesBuilder.match("lastModifiedTime")) {
            attributesBuilder.add("lastModifiedTime", basicFileAttributes.lastModifiedTime());
        }
        if (attributesBuilder.match("fileKey")) {
            attributesBuilder.add("fileKey", basicFileAttributes.fileKey());
        }
        if (attributesBuilder.match("isDirectory")) {
            attributesBuilder.add("isDirectory", Boolean.valueOf(basicFileAttributes.isDirectory()));
        }
        if (attributesBuilder.match("isRegularFile")) {
            attributesBuilder.add("isRegularFile", Boolean.valueOf(basicFileAttributes.isRegularFile()));
        }
        if (attributesBuilder.match("isSymbolicLink")) {
            attributesBuilder.add("isSymbolicLink", Boolean.valueOf(basicFileAttributes.isSymbolicLink()));
        }
        if (attributesBuilder.match("isOther")) {
            attributesBuilder.add("isOther", Boolean.valueOf(basicFileAttributes.isOther()));
        }
    }

    public Map readAttributes(String[] strArr) throws IOException {
        AttributesBuilder create = AttributesBuilder.create(basicAttributeNames, strArr);
        addRequestedBasicAttributes(readAttributes(), create);
        return create.unmodifiableMap();
    }
}
