package com.crashlytics.android.core;

import com.newrelic.agent.android.instrumentation.JSONObjectInstrumentation;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import p004b.p005a.p006a.p007a.Fabric;
import p004b.p005a.p006a.p007a.p008a.p010b.CommonUtils;

class MetaDataStore {
    private static final String KEYDATA_SUFFIX = "keys";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String METADATA_EXT = ".meta";
    private static final String USERDATA_SUFFIX = "user";
    private static final Charset UTF_8;
    private final File filesDir;

    /* renamed from: com.crashlytics.android.core.MetaDataStore.1 */
    static class C01441 extends JSONObject {
        final /* synthetic */ UserMetaData val$userData;

        C01441(UserMetaData userMetaData) throws JSONException {
            this.val$userData = userMetaData;
            put(MetaDataStore.KEY_USER_ID, this.val$userData.id);
            put(MetaDataStore.KEY_USER_NAME, this.val$userData.name);
            put(MetaDataStore.KEY_USER_EMAIL, this.val$userData.email);
        }
    }

    static {
        UTF_8 = Charset.forName(HTTP.UTF_8);
    }

    public MetaDataStore(File file) {
        this.filesDir = file;
    }

    public void writeUserData(String str, UserMetaData userMetaData) {
        Closeable bufferedWriter;
        Throwable e;
        File userDataFileForSession = getUserDataFileForSession(str);
        Closeable closeable = null;
        try {
            String userDataToJson = userDataToJson(userMetaData);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userDataFileForSession), UTF_8));
            try {
                bufferedWriter.write(userDataToJson);
                bufferedWriter.flush();
                CommonUtils.m167a(bufferedWriter, "Failed to close user metadata file.");
            } catch (Exception e2) {
                e = e2;
                try {
                    Fabric.m512h().m482e(CrashlyticsCore.TAG, "Error serializing user metadata.", e);
                    CommonUtils.m167a(bufferedWriter, "Failed to close user metadata file.");
                } catch (Throwable th) {
                    e = th;
                    closeable = bufferedWriter;
                    CommonUtils.m167a(closeable, "Failed to close user metadata file.");
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            bufferedWriter = null;
            Fabric.m512h().m482e(CrashlyticsCore.TAG, "Error serializing user metadata.", e);
            CommonUtils.m167a(bufferedWriter, "Failed to close user metadata file.");
        } catch (Throwable th2) {
            e = th2;
            CommonUtils.m167a(closeable, "Failed to close user metadata file.");
            throw e;
        }
    }

    public UserMetaData readUserData(String str) {
        Closeable fileInputStream;
        Throwable e;
        File userDataFileForSession = getUserDataFileForSession(str);
        if (!userDataFileForSession.exists()) {
            return UserMetaData.EMPTY;
        }
        try {
            fileInputStream = new FileInputStream(userDataFileForSession);
            try {
                UserMetaData jsonToUserData = jsonToUserData(CommonUtils.m155a((InputStream) fileInputStream));
                CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
                return jsonToUserData;
            } catch (Exception e2) {
                e = e2;
                try {
                    Fabric.m512h().m482e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
                    CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
                    return UserMetaData.EMPTY;
                } catch (Throwable th) {
                    e = th;
                    CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
            Fabric.m512h().m482e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
            return UserMetaData.EMPTY;
        } catch (Throwable th2) {
            e = th2;
            fileInputStream = null;
            CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
            throw e;
        }
    }

    public void writeKeyData(String str, Map<String, String> map) {
        Closeable bufferedWriter;
        Throwable e;
        File keysFileForSession = getKeysFileForSession(str);
        Closeable closeable = null;
        try {
            String keysDataToJson = keysDataToJson(map);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(keysFileForSession), UTF_8));
            try {
                bufferedWriter.write(keysDataToJson);
                bufferedWriter.flush();
                CommonUtils.m167a(bufferedWriter, "Failed to close key/value metadata file.");
            } catch (Exception e2) {
                e = e2;
                try {
                    Fabric.m512h().m482e(CrashlyticsCore.TAG, "Error serializing key/value metadata.", e);
                    CommonUtils.m167a(bufferedWriter, "Failed to close key/value metadata file.");
                } catch (Throwable th) {
                    e = th;
                    closeable = bufferedWriter;
                    CommonUtils.m167a(closeable, "Failed to close key/value metadata file.");
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            bufferedWriter = null;
            Fabric.m512h().m482e(CrashlyticsCore.TAG, "Error serializing key/value metadata.", e);
            CommonUtils.m167a(bufferedWriter, "Failed to close key/value metadata file.");
        } catch (Throwable th2) {
            e = th2;
            CommonUtils.m167a(closeable, "Failed to close key/value metadata file.");
            throw e;
        }
    }

    public Map<String, String> readKeyData(String str) {
        Closeable fileInputStream;
        Throwable e;
        File keysFileForSession = getKeysFileForSession(str);
        if (!keysFileForSession.exists()) {
            return Collections.emptyMap();
        }
        try {
            fileInputStream = new FileInputStream(keysFileForSession);
            try {
                Map<String, String> jsonToKeysData = jsonToKeysData(CommonUtils.m155a((InputStream) fileInputStream));
                CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
                return jsonToKeysData;
            } catch (Exception e2) {
                e = e2;
                try {
                    Fabric.m512h().m482e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
                    CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
                    return Collections.emptyMap();
                } catch (Throwable th) {
                    e = th;
                    CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
            Fabric.m512h().m482e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
            return Collections.emptyMap();
        } catch (Throwable th2) {
            e = th2;
            fileInputStream = null;
            CommonUtils.m167a(fileInputStream, "Failed to close user metadata file.");
            throw e;
        }
    }

    private File getUserDataFileForSession(String str) {
        return new File(this.filesDir, str + USERDATA_SUFFIX + METADATA_EXT);
    }

    private File getKeysFileForSession(String str) {
        return new File(this.filesDir, str + KEYDATA_SUFFIX + METADATA_EXT);
    }

    private static UserMetaData jsonToUserData(String str) throws JSONException {
        JSONObject init = JSONObjectInstrumentation.init(str);
        return new UserMetaData(valueOrNull(init, KEY_USER_ID), valueOrNull(init, KEY_USER_NAME), valueOrNull(init, KEY_USER_EMAIL));
    }

    private static String userDataToJson(UserMetaData userMetaData) throws JSONException {
        return new C01441(userMetaData).toString();
    }

    private static Map<String, String> jsonToKeysData(String str) throws JSONException {
        JSONObject init = JSONObjectInstrumentation.init(str);
        Map<String, String> hashMap = new HashMap();
        Iterator keys = init.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            hashMap.put(str2, valueOrNull(init, str2));
        }
        return hashMap;
    }

    private static String keysDataToJson(Map<String, String> map) throws JSONException {
        JSONObject jSONObject = new JSONObject(map);
        return !(jSONObject instanceof JSONObject) ? jSONObject.toString() : JSONObjectInstrumentation.toString(jSONObject);
    }

    private static String valueOrNull(JSONObject jSONObject, String str) {
        return !jSONObject.isNull(str) ? jSONObject.optString(str, null) : null;
    }
}
