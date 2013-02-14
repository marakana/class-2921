package com.marakana.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class StatusActivity extends Activity {
    private static final String TAG = "A_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate: " + savedInstanceState);

        setContentView(R.layout.activity_status);
    }
}
