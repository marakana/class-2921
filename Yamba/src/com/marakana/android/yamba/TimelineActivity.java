package com.marakana.android.yamba;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.marakana.android.yamba.svc.YambaContract;


public class TimelineActivity extends ListActivity
    implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final String TAG = "TIME";

    private static final int TIMELINE_LOADER = 42;

    private static final String SORT = YambaContract.Timeline.Columns.TIMESTAMP + " DESC";

    private static final String[] PROJ = new String[] {
        YambaContract.Timeline.Columns.ID,
        YambaContract.Timeline.Columns.USER,
        YambaContract.Timeline.Columns.TIMESTAMP,
        YambaContract.Timeline.Columns.STATUS
    };

    private static final String[] FROM = new String[PROJ.length - 1];
    static { System.arraycopy(PROJ, 1, FROM, 0, FROM.length); }

    private static final int TO[] = new int [] {
        R.id.timeline_user,
        R.id.timeline_timestamp,
        R.id.timeline_status
    };

    class TimelineBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            int vid = view.getId();
            if (R.id.timeline_timestamp != vid) { return false; }

            String time = "long ago";
            long t = cursor.getLong(columnIndex);
            if (0 < t) { time = DateUtils.getRelativeTimeSpanString(t).toString(); }
            ((TextView) view).setText(time);

            return true;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                YambaContract.Timeline.URI,
                PROJ,
                null,
                null,
                SORT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cur) {
        Log.d(TAG, "Load finished");
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(cur);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        Log.d(TAG, "Load reset");
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        getListView().setBackgroundResource(R.drawable.bg);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.timeline_row,
                null,
                FROM,
                TO,
                0);

        adapter.setViewBinder(new TimelineBinder());
        setListAdapter(adapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);
    }
}
