package com.example.todobebas.notif

interface NotificationScheduler {
    fun schedule(item : Notification)
    fun cancel(item: Notification)
}