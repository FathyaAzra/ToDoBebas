package com.example.todobebas

class NotificationWorker {
}

//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.graphics.Color
//import androidx.core.app.NotificationCompat
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//
//class NotificationWorker(context: Context, workerParams: WorkerParameters) :
//    Worker(context, workerParams) {
//
//    override fun doWork(): Result {
//        // Notification details
//        val channelId = "scheduled_notifications"
//        val channelName = "Scheduled Notifications"
//        val notificationId = 1
//        val notificationManager =
//            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Create Notification Channel for API 26+
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId,
//                channelName,
//                NotificationManager.IMPORTANCE_HIGH
//            ).apply {
//                enableLights(true)
//                lightColor = Color.BLUE
//                enableVibration(true)
//            }
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        // Build and show notification
//        val notification = NotificationCompat.Builder(applicationContext, channelId)
//            .setSmallIcon(R.drawable.iofiwalkhom)
//            .setContentTitle("Scheduled Notification")
//            .setContentText("This is your scheduled notification.")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .build()
//
//        notificationManager.notify(notificationId, notification)
//
//        return Result.success()
//    }
//}