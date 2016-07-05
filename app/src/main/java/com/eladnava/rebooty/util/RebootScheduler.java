package com.eladnava.rebooty.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.eladnava.rebooty.config.Logging;
import com.eladnava.rebooty.receivers.RebootReceiver;

import java.util.Calendar;

public class RebootScheduler {
    public static void rescheduleReboot(Context context, boolean toast) {
        // Get alarm manager
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

        // Get a pending intent to run the reboot broadcast receiver
        PendingIntent rebootIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, RebootReceiver.class), 0);

        // Cancel any old reboot jobs
        alarmManager.cancel(rebootIntent);

        // Find out when the reboot should occur
        long rebootTimestamp = getRebootTimestamp();

        // Avoid boot loop -- don't reschedule a reboot if less than 5 minutes left
        if (rebootTimestamp < System.currentTimeMillis() - (1000 * 60 * 5)) {
            return;
        }

        // Reschedule a new reboot job
        alarmManager.set(AlarmManager.RTC_WAKEUP, rebootTimestamp, rebootIntent);

        // Success message
        String message = "Scheduled reboot in " + ((rebootTimestamp - System.currentTimeMillis()) / 1000 / 60 / 60) + " hours";

        // Log success
        Log.d(Logging.TAG, message);

        // Show toast?
        if (toast) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    private static long getRebootTimestamp() {
        // Create new calendar instance
        Calendar midnightCalendar = Calendar.getInstance();

        // Make this configurable in the future
        int dailyRebootHour = 3;

        // Did the specified hour already pass?
        if (midnightCalendar.get(Calendar.HOUR_OF_DAY) > dailyRebootHour) {
            // Add a day so it happens tomorrow
            midnightCalendar.add(Calendar.DATE, 1);
        }

        // Set the time to the daily reboot hour
        midnightCalendar.set(Calendar.MINUTE, 0);
        midnightCalendar.set(Calendar.SECOND, 0);
        midnightCalendar.set(Calendar.HOUR_OF_DAY, dailyRebootHour);

        // Get the target date and time as a unix timestamp (in ms)
        return midnightCalendar.getTimeInMillis();
    }
}
