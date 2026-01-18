package desugar.sun.nio.fs;

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
abstract class DesugarAbstractBasicFileAttributeView implements BasicFileAttributeView, DesugarDynamicFileAttributeView {
    private static final String CREATION_TIME_NAME = "creationTime";
    private static final String FILE_KEY_NAME = "fileKey";
    private static final String IS_DIRECTORY_NAME = "isDirectory";
    private static final String IS_OTHER_NAME = "isOther";
    private static final String IS_REGULAR_FILE_NAME = "isRegularFile";
    private static final String IS_SYMBOLIC_LINK_NAME = "isSymbolicLink";
    private static final String LAST_ACCESS_TIME_NAME = "lastAccessTime";
    private static final String LAST_MODIFIED_TIME_NAME = "lastModifiedTime";
    private static final String SIZE_NAME = "size";
    static final Set basicAttributeNames = DesugarUtil.newSet("size", "creationTime", "lastAccessTime", "lastModifiedTime", "fileKey", "isDirectory", "isRegularFile", "isSymbolicLink", "isOther");

    protected DesugarAbstractBasicFileAttributeView() {
    }

    public String name() {
        return "basic";
    }

    public void setAttribute(String attribute, Object value) throws IOException {
        if (attribute.equals("lastModifiedTime")) {
            setTimes((FileTime) value, null, null);
            return;
        }
        if (attribute.equals("lastAccessTime")) {
            setTimes(null, (FileTime) value, null);
            return;
        }
        if (attribute.equals("creationTime")) {
            setTimes(null, null, (FileTime) value);
            return;
        }
        throw new IllegalArgumentException("'" + name() + ":" + attribute + "' not recognized");
    }

    static class AttributesBuilder {
        private boolean copyAll;
        private Set names = new HashSet();
        private Map map = new HashMap();

        private AttributesBuilder(Set allowed, String[] requested) {
            for (String str : requested) {
                if (str.equals("*")) {
                    this.copyAll = true;
                } else {
                    if (!allowed.contains(str)) {
                        throw new IllegalArgumentException("'" + str + "' not recognized");
                    }
                    this.names.add(str);
                }
            }
        }

        static AttributesBuilder create(Set allowed, String[] requested) {
            return new AttributesBuilder(allowed, requested);
        }

        boolean match(String name) {
            return this.copyAll || this.names.contains(name);
        }

        void add(String name, Object value) {
            this.map.put(name, value);
        }

        Map unmodifiableMap() {
            return Collections.unmodifiableMap(this.map);
        }
    }

    final void addRequestedBasicAttributes(BasicFileAttributes attrs, AttributesBuilder builder) {
        if (builder.match("size")) {
            builder.add("size", Long.valueOf(attrs.size()));
        }
        if (builder.match("creationTime")) {
            builder.add("creationTime", attrs.creationTime());
        }
        if (builder.match("lastAccessTime")) {
            builder.add("lastAccessTime", attrs.lastAccessTime());
        }
        if (builder.match("lastModifiedTime")) {
            builder.add("lastModifiedTime", attrs.lastModifiedTime());
        }
        if (builder.match("fileKey")) {
            builder.add("fileKey", attrs.fileKey());
        }
        if (builder.match("isDirectory")) {
            builder.add("isDirectory", Boolean.valueOf(attrs.isDirectory()));
        }
        if (builder.match("isRegularFile")) {
            builder.add("isRegularFile", Boolean.valueOf(attrs.isRegularFile()));
        }
        if (builder.match("isSymbolicLink")) {
            builder.add("isSymbolicLink", Boolean.valueOf(attrs.isSymbolicLink()));
        }
        if (builder.match("isOther")) {
            builder.add("isOther", Boolean.valueOf(attrs.isOther()));
        }
    }

    public Map readAttributes(String[] requested) throws IOException {
        AttributesBuilder create = AttributesBuilder.create(basicAttributeNames, requested);
        addRequestedBasicAttributes(readAttributes(), create);
        return create.unmodifiableMap();
    }
}
