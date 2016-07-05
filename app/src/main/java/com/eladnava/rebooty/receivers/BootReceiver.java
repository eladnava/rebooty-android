package com.eladnava.rebooty.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.eladnava.rebooty.config.Logging;
import com.eladnava.rebooty.util.RebootScheduler;
import com.stericson.RootShell.RootShell;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Make sure the right intent was provided
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // Log phone startup
            Log.d(Logging.TAG, "Boot completed");

            // Got root?
            if (RootShell.isRootAvailable() && RootShell.isAccessGiven()) {
                // Reschedule reboot (without toast)
                RebootScheduler.rescheduleReboot(context, false);
            }
        }
    }
}
