/*
 * Copyright (c) 2014. The Trustees of Indiana University.
 *
 * This version of the code is licensed under the MPL 2.0 Open Source license with additional
 * healthcare disclaimer. If the user is an entity intending to commercialize any application
 * that uses this code in a for-profit venture, please contact the copyright holder.
 */

package com.muzima.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.muzima.R;
import com.muzima.search.api.util.StringUtil;
import com.muzima.view.login.LoginActivity;
import com.muzima.view.preferences.SettingsActivity;

public class DefaultMenuDropDownHelper {
    public static int DEFAULT_MENU = R.menu.dashboard;
    private Activity activity;

    public DefaultMenuDropDownHelper(Activity activity){
        this.activity = activity;
    }

    public void launchLoginActivity(boolean isFirstLaunch) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(LoginActivity.isFirstLaunch, isFirstLaunch);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.finish();
                return true;
            case R.id.action_settings:
                intent = new Intent(activity, SettingsActivity.class);
                activity.startActivity(intent);
                return true;
            case R.id.action_help:
                intent = new Intent(activity, HelpActivity.class);
                activity.startActivity(intent);
                return true;
            case R.id.action_upload_editted_obs:
                Toast.makeText(activity,"Triggers upload of Obs data when selected and change status of the data to 'submitted'" +
                        " if upload is successful.",Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_logout:
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                String passwordKey = activity.getResources().getString(R.string.preference_password);
                settings.edit()
                        .putString(passwordKey, StringUtil.EMPTY)
                        .commit();

                launchLoginActivity(false);
                activity.finish();
                return true;
            default:
                return false;
        }
    }

    public void removeSettingsMenu(Menu menu) {
        MenuItem menuSettings = menu.findItem(R.id.action_settings);
        menuSettings.setVisible(false);
    }

    /**
     * Allow us to disable the upload obs menu item in places its not necessary
     * @param menu
     */
    public void removeUploadEdittedObsMenu(Menu menu) {
        MenuItem menuSettings = menu.findItem(R.id.action_upload_editted_obs);
        menuSettings.setVisible(false);
    }
}
