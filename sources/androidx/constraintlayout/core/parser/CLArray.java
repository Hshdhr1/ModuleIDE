package androidx.constraintlayout.core.parser;

import java.util.Iterator;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes40.dex */
public class CLArray extends CLContainer {
    public CLArray(char[] content) {
        super(content);
    }

    public static CLElement allocate(char[] content) {
        return new CLArray(content);
    }

    protected String toJSON() {
        StringBuilder content = new StringBuilder(getDebugName() + "[");
        boolean first = true;
        for (int i = 0; i < this.mElements.size(); i++) {
            if (!first) {
                content.append(", ");
            } else {
                first = false;
            }
            content.append(((CLElement) this.mElements.get(i)).toJSON());
        }
        return content + "]";
    }

    protected String toFormattedJSON(int indent, int forceIndent) {
        StringBuilder json = new StringBuilder();
        String val = toJSON();
        if (forceIndent <= 0 && val.length() + indent < MAX_LINE) {
            json.append(val);
        } else {
            json.append("[\n");
            boolean first = true;
            Iterator it = this.mElements.iterator();
            while (it.hasNext()) {
                CLElement element = (CLElement) it.next();
                if (!first) {
                    json.append(",\n");
                } else {
                    first = false;
                }
                addIndent(json, BASE_INDENT + indent);
                json.append(element.toFormattedJSON(BASE_INDENT + indent, forceIndent - 1));
            }
            json.append("\n");
            addIndent(json, indent);
            json.append("]");
        }
        return json.toString();
    }
}
