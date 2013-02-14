package com.marakana.android.yamba;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


public class TimelineActivity extends BaseActivity {
    private static final String TAG = "A_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate: " + savedInstanceState);

        setContentView(R.layout.activity_timeline);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ((android.R.id.home == id) || (R.id.menu_timeline == id)) { return true; }
        return super.onOptionsItemSelected(item);
    }
}
