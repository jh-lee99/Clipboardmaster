package com.jhlee.clipboardmaster

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class MyNotificationListenerService : NotificationListenerService() {

    private val TAG = "MyNotificationListenerService"

    // 알림 읽으면 로그로 남김 // 리스트에 추가하는 형식으로 바꿔야함
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val packageName: String = sbn?.packageName ?: "Null"
        val extras = sbn?.notification?.extras

        val extraTitle: String = extras?.get(Notification.EXTRA_TITLE).toString()
        val extraText: String = extras?.get(Notification.EXTRA_TEXT).toString()
        val extraBigText: String = extras?.get(Notification.EXTRA_BIG_TEXT).toString()
        val extraInfoText: String = extras?.get(Notification.EXTRA_INFO_TEXT).toString()
        val extraSubText: String = extras?.get(Notification.EXTRA_SUB_TEXT).toString()
        val extraSummaryText: String = extras?.get(Notification.EXTRA_SUMMARY_TEXT).toString()

        Log.d(
            TAG, "onNotificationPosted:\n" +
                    "PackageName: $packageName" +
                    "Title: $extraTitle\n" +
                    "Text: $extraText\n" +
                    "BigText: $extraBigText\n" +
                    "InfoText: $extraInfoText\n" +
                    "SubText: $extraSubText\n" +
                    "SummaryText: $extraSummaryText\n"
        )
    }
}