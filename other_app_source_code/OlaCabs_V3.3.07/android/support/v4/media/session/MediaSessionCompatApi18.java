package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompatApi14.Callback;
import com.olacabs.customer.p076d.br;

public class MediaSessionCompatApi18 {

    static class OnPlaybackPositionUpdateListener<T extends Callback> implements android.media.RemoteControlClient.OnPlaybackPositionUpdateListener {
        protected final T mCallback;

        public OnPlaybackPositionUpdateListener(T t) {
            this.mCallback = t;
        }

        public void onPlaybackPositionUpdate(long j) {
            this.mCallback.onSeekTo(j);
        }
    }

    public static Object createPlaybackPositionUpdateListener(Callback callback) {
        return new OnPlaybackPositionUpdateListener(callback);
    }

    public static void registerMediaButtonEventReceiver(Context context, PendingIntent pendingIntent) {
        ((AudioManager) context.getSystemService("audio")).registerMediaButtonEventReceiver(pendingIntent);
    }

    public static void unregisterMediaButtonEventReceiver(Context context, PendingIntent pendingIntent) {
        ((AudioManager) context.getSystemService("audio")).unregisterMediaButtonEventReceiver(pendingIntent);
    }

    public static void setState(Object obj, int i, long j, float f, long j2) {
        long j3 = 0;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (i == 3 && j > 0) {
            if (j2 > 0) {
                j3 = elapsedRealtime - j2;
                if (f > 0.0f && f != br.DEFAULT_BACKOFF_MULT) {
                    j3 = (long) (((float) j3) * f);
                }
            }
            j += j3;
        }
        ((RemoteControlClient) obj).setPlaybackState(MediaSessionCompatApi14.getRccStateFromState(i), j, f);
    }

    public static void setOnPlaybackPositionUpdateListener(Object obj, Object obj2) {
        ((RemoteControlClient) obj).setPlaybackPositionUpdateListener((android.media.RemoteControlClient.OnPlaybackPositionUpdateListener) obj2);
    }
}
