package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import com.newrelic.agent.android.instrumentation.JSONObjectInstrumentation;
import java.io.IOException;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import p004b.p005a.p006a.p007a.p008a.p013d.EventTransform;

class SessionEventTransform implements EventTransform<SessionEvent> {
    static final String ADVERTISING_ID_KEY = "advertisingId";
    static final String ANDROID_ID_KEY = "androidId";
    static final String APP_BUNDLE_ID_KEY = "appBundleId";
    static final String APP_VERSION_CODE_KEY = "appVersionCode";
    static final String APP_VERSION_NAME_KEY = "appVersionName";
    static final String BETA_DEVICE_TOKEN_KEY = "betaDeviceToken";
    static final String BUILD_ID_KEY = "buildId";
    static final String CUSTOM_ATTRIBUTES = "customAttributes";
    static final String CUSTOM_TYPE = "customType";
    static final String DETAILS_KEY = "details";
    static final String DEVICE_MODEL_KEY = "deviceModel";
    static final String EXECUTION_ID_KEY = "executionId";
    static final String INSTALLATION_ID_KEY = "installationId";
    static final String OS_VERSION_KEY = "osVersion";
    static final String TIMESTAMP_KEY = "timestamp";
    static final String TYPE_KEY = "type";

    SessionEventTransform() {
    }

    public byte[] toBytes(SessionEvent sessionEvent) throws IOException {
        JSONObject buildJsonForEvent = buildJsonForEvent(sessionEvent);
        return (!(buildJsonForEvent instanceof JSONObject) ? buildJsonForEvent.toString() : JSONObjectInstrumentation.toString(buildJsonForEvent)).getBytes(HTTP.UTF_8);
    }

    @TargetApi(9)
    public JSONObject buildJsonForEvent(SessionEvent sessionEvent) throws IOException {
        try {
            JSONObject jSONObject = new JSONObject();
            SessionEventMetadata sessionEventMetadata = sessionEvent.sessionEventMetadata;
            jSONObject.put(APP_BUNDLE_ID_KEY, sessionEventMetadata.appBundleId);
            jSONObject.put(EXECUTION_ID_KEY, sessionEventMetadata.executionId);
            jSONObject.put(INSTALLATION_ID_KEY, sessionEventMetadata.installationId);
            jSONObject.put(ANDROID_ID_KEY, sessionEventMetadata.androidId);
            jSONObject.put(ADVERTISING_ID_KEY, sessionEventMetadata.advertisingId);
            jSONObject.put(BETA_DEVICE_TOKEN_KEY, sessionEventMetadata.betaDeviceToken);
            jSONObject.put(BUILD_ID_KEY, sessionEventMetadata.buildId);
            jSONObject.put(OS_VERSION_KEY, sessionEventMetadata.osVersion);
            jSONObject.put(DEVICE_MODEL_KEY, sessionEventMetadata.deviceModel);
            jSONObject.put(APP_VERSION_CODE_KEY, sessionEventMetadata.appVersionCode);
            jSONObject.put(APP_VERSION_NAME_KEY, sessionEventMetadata.appVersionName);
            jSONObject.put(TIMESTAMP_KEY, sessionEvent.timestamp);
            jSONObject.put(TYPE_KEY, sessionEvent.type.toString());
            jSONObject.put(DETAILS_KEY, new JSONObject(sessionEvent.details));
            jSONObject.put(CUSTOM_TYPE, sessionEvent.customType);
            jSONObject.put(CUSTOM_ATTRIBUTES, new JSONObject(sessionEvent.customAttributes));
            return jSONObject;
        } catch (Throwable e) {
            if (VERSION.SDK_INT >= 9) {
                throw new IOException(e.getMessage(), e);
            }
            throw new IOException(e.getMessage());
        }
    }
}
