package com.eladnava.rebooty.logic;

import android.util.Log;

import com.eladnava.rebooty.config.Logging;
import com.eladnava.rebooty.config.RootCommands;
import com.stericson.RootShell.RootShell;
import com.stericson.RootShell.execution.Command;

public class RebootHandler {
    public static void rebootPhone() {
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
