package com.marakana.android.yamba;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marakana.android.yamba.svc.YambaContract;


public class StatusActivity extends Activity {
    public static final String TAG = "STATUS";

    private static final int MIN_CHARS = 0;
    private static final int WARN_CHARS = 10;
    private static final int MAX_CHARS = 140;

    static Poster poster;

    // if two different threads share access to the same mutable state
    // they must do it holding the same lock.
    static class Poster extends AsyncTask<String, Void, Void> {
        private final ContentResolver resolver;

        public Poster(ContentResolver resolver) { this.resolver = resolver; }

        @Override
        protected Void doInBackground(String... args) {
            String status = args[0];
            if (BuildConfig.DEBUG) { Log.d(TAG, "posting status: " + status); }

            ContentValues vals = new ContentValues();
            vals.put(YambaContract.Posts.Columns.STATUS, status);

            resolver.insert(YambaContract.Posts.URI, vals);

            return null;
        }

        @Override
        protected void onCancelled() { poster = null; }

        @Override
        protected void onCancelled(Void result) { poster = null; }

        @Override
        protected void onPostExecute(Void result) { poster = null; }
    }


    private TextView count;
    private EditText status;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate: " + savedInstanceState);

        setContentView(R.layout.activity_status);

        count = (TextView) findViewById(R.id.status_count);

        status = (EditText) findViewById(R.id.status_text);
        status.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(CharSequence cs, int s, int c, int a) { }
            @Override
            public void onTextChanged(CharSequence cs, int s, int b, int c) { setCount(); }
        });

        ((Button) findViewById(R.id.status_submit)).setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v) { submit(); }
                });
    }

    void submit() {
        String msg = status.getText().toString();
        if (TextUtils.isEmpty(msg)) { return; }

        if (null != poster) { return; }
        poster = new Poster(getContentResolver());

        status.setText("");

        poster.execute(msg);
    }

    void setCount() {
        int n = MAX_CHARS - status.getText().toString().length();
        count.setText(String.valueOf(n));

        int color = Color.GREEN;
        if (MIN_CHARS >= n) { color = Color.RED; }
        else if (WARN_CHARS >= n) { color = Color.YELLOW; }
        count.setTextColor(color);
    }
}
