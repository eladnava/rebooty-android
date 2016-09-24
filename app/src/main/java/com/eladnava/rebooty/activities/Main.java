package com.eladnava.rebooty.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.eladnava.rebooty.R;
import com.eladnava.rebooty.logic.RebootHandler;
import com.eladnava.rebooty.util.RebootScheduler;
import com.stericson.RootShell.RootShell;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize app UI
        setContentView(R.layout.activity_main);

        // Make sure we have root access (display dialog otherwise)
        if (verifyRootAccessAvailable()) {
            // Reschedule reboot task
            RebootScheduler.rescheduleReboot(this, true);
        }
    }

    private boolean verifyRootAccessAvailable() {
        // No "su" binary?
        if (!RootShell.isRootAvailable()) {
            // Show fatal error dialog
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_root)
                    .setMessage(R.string.no_root_desc)
                    .setPositiveButton(R.string.ok, null)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // End the activity
                            finish();
                        }
                    })
                    .create().show();

            // No root
            return false;
        }

        // No root access given?
        if (!RootShell.isAccessGiven()) {
            // Show fatal error dialog
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_root_access)
                    .setMessage(R.string.no_root_access_desc)
                    .setPositiveButton(R.string.ok, null)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // End the activity
                            finish();
                        }
                    })
                    .create().show();

            // No root access
            return false;
        }

        // We're good!
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu - this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks by ID
        switch (item.getItemId()) {
            // Reboot
            case R.id.action_reboot:
                RebootHandler.rebootPhone();
                return true;
        }

        // Don't consume the event
        return super.onOptionsItemSelected(item);
    }
}
