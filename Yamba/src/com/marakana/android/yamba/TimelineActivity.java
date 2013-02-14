package com.marakana.android.yamba;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.marakana.android.yamba.svc.YambaContract;


public class TimelineActivity extends BaseActivity {
    private static final String TAG = "A_STATUS";
    private static final String DETAIL_FRAGMENT = "Yamba.DETAILS";


    private boolean usingFragments;

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        if (usingFragments) { launchDetailFragment(intent); }
        else { startActivity(intent); }
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Log.d(TAG, "in onCreate: " + state);

        setContentView(R.layout.activity_timeline);

        usingFragments = (null != findViewById(R.id.timeline_detail_fragment));

        if (usingFragments) {
            if (null == getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT)) {
                addFragment(state);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = new Intent(YambaContract.YAMBA_SERVICE);
        i.putExtra(YambaContract.SVC_PARAM_OP, YambaContract.SVC_OP_POLLING_ON);
        startService(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent i = new Intent(YambaContract.YAMBA_SERVICE);
        i.putExtra(YambaContract.SVC_PARAM_OP, YambaContract.SVC_OP_POLLING_OFF);
        startService(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ((android.R.id.home == id) || (R.id.menu_timeline == id)) { return true; }
        return super.onOptionsItemSelected(item);
    }

    private void addFragment(Bundle state) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction xact = fragmentManager.beginTransaction();

        Fragment frag = TimelineDetailFragment.newInstance(state);

        xact.add(R.id.timeline_detail_fragment, frag, DETAIL_FRAGMENT);
        xact.commit();
    }

    private void launchDetailFragment(Intent intent) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction xact = fragmentManager.beginTransaction();

        Fragment frag = TimelineDetailFragment.newInstance(intent.getExtras());
        xact.addToBackStack(null);

        xact.replace(R.id.timeline_detail_fragment, frag, DETAIL_FRAGMENT);
        xact.commit();
    }
}
