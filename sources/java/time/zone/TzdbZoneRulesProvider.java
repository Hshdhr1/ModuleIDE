package java.time.zone;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes56.dex */
final class TzdbZoneRulesProvider extends ZoneRulesProvider {
    private List regionIds;
    private final Map regionToRules = new ConcurrentHashMap();
    private String versionId;

    public TzdbZoneRulesProvider() {
        try {
            load(new DataInputStream(new BufferedInputStream(TzdbZoneRulesProvider.class.getClassLoader().getResource(System.getProperty("jre.tzdb.dat", "j$/time/zone/tzdb.dat")).openStream())));
        } catch (Exception e) {
            throw new ZoneRulesException("Unable to load TZDB time-zone rules", e);
        }
    }

    protected Set provideZoneIds() {
        return new HashSet(this.regionIds);
    }

    protected ZoneRules provideRules(String str, boolean z) {
        Object obj = this.regionToRules.get(str);
        if (obj == null) {
            throw new ZoneRulesException("Unknown time-zone ID: " + str);
        }
        try {
            if (obj instanceof byte[]) {
                obj = Ser.read(new DataInputStream(new ByteArrayInputStream((byte[]) obj)));
                this.regionToRules.put(str, obj);
            }
            return (ZoneRules) obj;
        } catch (Exception e) {
            throw new ZoneRulesException("Invalid binary time-zone data: TZDB:" + str + ", version: " + this.versionId, e);
        }
    }

    protected NavigableMap provideVersions(String str) {
        TreeMap treeMap = new TreeMap();
        ZoneRules rules = getRules(str, false);
        if (rules != null) {
            treeMap.put(this.versionId, rules);
        }
        return treeMap;
    }

    private void load(DataInputStream dataInputStream) throws Exception {
        if (dataInputStream.readByte() != 1) {
            throw new StreamCorruptedException("File format not recognised");
        }
        if (!"TZDB".equals(dataInputStream.readUTF())) {
            throw new StreamCorruptedException("File format not recognised");
        }
        short readShort = dataInputStream.readShort();
        for (int i = 0; i < readShort; i++) {
            this.versionId = dataInputStream.readUTF();
        }
        int readShort2 = dataInputStream.readShort();
        String[] strArr = new String[readShort2];
        for (int i2 = 0; i2 < readShort2; i2++) {
            strArr[i2] = dataInputStream.readUTF();
        }
        this.regionIds = Arrays.asList(strArr);
        int readShort3 = dataInputStream.readShort();
        Object[] objArr = new Object[readShort3];
        for (int i3 = 0; i3 < readShort3; i3++) {
            byte[] bArr = new byte[dataInputStream.readShort()];
            dataInputStream.readFully(bArr);
            objArr[i3] = bArr;
        }
        for (int i4 = 0; i4 < readShort; i4++) {
            short readShort4 = dataInputStream.readShort();
            this.regionToRules.clear();
            for (int i5 = 0; i5 < readShort4; i5++) {
                this.regionToRules.put(strArr[dataInputStream.readShort()], objArr[dataInputStream.readShort() & 65535]);
            }
        }
    }

    public String toString() {
        return "TZDB[" + this.versionId + "]";
    }
}
