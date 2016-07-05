package com.eladnava.rebooty.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eladnava.rebooty.R;
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
}
