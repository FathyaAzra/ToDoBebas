package com.example.todobebas.notif

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AndroidNotifScheduler(
    private val context: Context
) : NotificationScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: Notification) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", "Reminder for Todo: ${item.notif_type}")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.notif_id, // Unique ID for this notification
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.notif_time * 1000,
            pendingIntent
        )
    }

    override fun cancel(item: Notification) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.notif_id,
            Intent(context, NotificationReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}
