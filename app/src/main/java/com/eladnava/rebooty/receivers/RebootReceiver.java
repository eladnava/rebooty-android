package com.eladnava.rebooty.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.eladnava.rebooty.config.Logging;
import com.eladnava.rebooty.config.RootCommands;
import com.stericson.RootShell.RootShell;
import com.stericson.RootShell.execution.Command;

public class RebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Log phone reboot
        Log.d(Logging.TAG, "Rebooting the phone");

        // Prepare the reboot command
        Command rootCommand = new Command(0, RootCommands.REBOOT_COMMAND);

        try {
            // Execute the command
            RootShell.getShell(true).add(rootCommand);
        }
        catch (Exception exc) {
            // Log error to logcat
            Log.d(Logging.TAG, "Reboot command failed", exc);
        }
    }
}
