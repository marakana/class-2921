/* $Id: $
   Copyright 2013, G. Blake Meike

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.marakana.android.yamba;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class BaseActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ((android.R.id.home == id) || (R.id.menu_timeline == id)) {
            startActivity(
                    new Intent(this, TimelineActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            return true;
        }

        if (R.id.menu_status == id) {
            startActivity(
                    new Intent(this, StatusActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            return true;
        }

       return super.onOptionsItemSelected(item);
    }
}
