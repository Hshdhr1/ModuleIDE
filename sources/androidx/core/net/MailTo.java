package androidx.core.net;

import android.net.Uri;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes6.dex */
public final class MailTo {
    private static final String BCC = "bcc";
    private static final String BODY = "body";
    private static final String CC = "cc";
    private static final String MAILTO = "mailto";
    public static final String MAILTO_SCHEME = "mailto:";
    private static final String SUBJECT = "subject";
    private static final String TO = "to";
    private HashMap mHeaders = new HashMap();

    private MailTo() {
    }

    public static boolean isMailTo(String uri) {
        return uri != null && uri.startsWith("mailto:");
    }

    public static boolean isMailTo(Uri uri) {
        return uri != null && "mailto".equals(uri.getScheme());
    }

    public static MailTo parse(String uri) throws ParseException {
        String address;
        String query;
        Preconditions.checkNotNull(uri);
        if (!isMailTo(uri)) {
            throw new ParseException("Not a mailto scheme");
        }
        int fragmentIndex = uri.indexOf(35);
        if (fragmentIndex != -1) {
            uri = uri.substring(0, fragmentIndex);
        }
        int queryIndex = uri.indexOf(63);
        if (queryIndex == -1) {
            address = Uri.decode(uri.substring("mailto:".length()));
            query = null;
        } else {
            address = Uri.decode(uri.substring("mailto:".length(), queryIndex));
            query = uri.substring(queryIndex + 1);
        }
        MailTo mailTo = new MailTo();
        if (query != null) {
            String[] queries = query.split("&");
            for (String queryParameter : queries) {
                String[] nameValueArray = queryParameter.split("=", 2);
                if (nameValueArray.length != 0) {
                    String queryParameterKey = Uri.decode(nameValueArray[0]).toLowerCase(Locale.ROOT);
                    String queryParameterValue = nameValueArray.length > 1 ? Uri.decode(nameValueArray[1]) : null;
                    mailTo.mHeaders.put(queryParameterKey, queryParameterValue);
                }
            }
        }
        String toParameter = mailTo.getTo();
        if (toParameter != null) {
            address = address + ", " + toParameter;
        }
        mailTo.mHeaders.put("to", address);
        return mailTo;
    }

    public static MailTo parse(Uri uri) throws ParseException {
        return parse(uri.toString());
    }

    public String getTo() {
        return (String) this.mHeaders.get("to");
    }

    public String getCc() {
        return (String) this.mHeaders.get("cc");
    }

    public String getBcc() {
        return (String) this.mHeaders.get("bcc");
    }

    public String getSubject() {
        return (String) this.mHeaders.get("subject");
    }

    public String getBody() {
        return (String) this.mHeaders.get("body");
    }

    public Map getHeaders() {
        return this.mHeaders;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("mailto:");
        sb.append('?');
        for (Map.Entry<String, String> header : this.mHeaders.entrySet()) {
            sb.append(Uri.encode((String) header.getKey()));
            sb.append('=');
            sb.append(Uri.encode((String) header.getValue()));
            sb.append('&');
        }
        return sb.toString();
    }
}
