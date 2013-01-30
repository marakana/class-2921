package com.marakana.android.yamba;

import android.app.ListActivity;
import android.os.Bundle;
import com.marakana.android.yamba.svc.YambaServiceHelper;


public class TimelineActivity extends ListActivity {
    private static final String TAG = "TIME";

    private YambaServiceHelper yamba;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        getListView().setBackgroundResource(R.drawable.bg);

        yamba = YambaServiceHelper.getInstance();
    }

    @Override
    protected void onPause() {
        yamba.stopPolling(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        yamba.startPolling(this);
    }
}
