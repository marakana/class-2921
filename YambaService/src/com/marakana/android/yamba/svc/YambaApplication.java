package com.marakana.android.yamba.svc;


import java.util.List;

import android.app.Application;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;


/**
 * YambaApplication
 */
public class YambaApplication extends Application {
    private static final String TAG = "APP";

    public static final String DEFAULT_USER = "student";
    public static final String DEFAULT_PASSWORD = "password";
    public static final String DEFAULT_API_ROOT = "http://yamba.marakana.com/api";

    public static class SafeYambaClient {
        public static final int MAX_POSTS = 40;

        private final YambaClient rawClient;

        public SafeYambaClient(String usr, String pwd, String endpoint) {
            rawClient = new YambaClient(usr, pwd, endpoint);
        }

        public synchronized List<YambaClient.Status> getTimeline() throws YambaClientException {
            return rawClient.getTimeline(MAX_POSTS);
       }

        public synchronized void postStatus(String statusText) throws YambaClientException {
            rawClient.postStatus(statusText);
       }
    }

    private SafeYambaClient client;

    /**
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) { Log.d(TAG, "Application up!"); }
    }

    /**
     * Don't use an anonymous class to handle this event!
     * http://stackoverflow.com/questions/3799038/onsharedpreferencechanged-not-fired-if-change-occurs-in-separate-activity
    @Override
    public synchronized void onSharedPreferenceChanged(SharedPreferences key, String val) { }
    */

    public synchronized SafeYambaClient getClient() {
        if (null == client) {
            client = new SafeYambaClient(DEFAULT_USER, DEFAULT_PASSWORD, DEFAULT_API_ROOT);
            if (BuildConfig.DEBUG) {
                Log.d(
                    TAG,
                    "new client: " + DEFAULT_USER
                        + ", " + DEFAULT_PASSWORD
                        + ", " + DEFAULT_API_ROOT);
            }
        }
        return client;
    }
}
