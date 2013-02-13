package com.marakana.android.yamba;

import android.app.ListActivity;
import android.os.Bundle;


public class TimelineActivity extends ListActivity {
    private static final String TAG = "TIME";

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        getListView().setBackgroundResource(R.drawable.bg);
    }
}
