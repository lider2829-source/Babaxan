package com.example.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.util.ReminderScheduler

/**
 * BroadcastReceiver triggered by AlarmManager to remind users of their
 * 150-minute daily English intensive study session.
 */
class DailyReminderReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
      // Re-schedule alarm if device reboots
      ReminderScheduler.rescheduleIfEnabled(context)
      return
    }

    showStudyReminderNotification(context)

    // Re-schedule for next day to ensure reliability across all OEM Android power savers
    ReminderScheduler.scheduleNextDailyAlarm(context)
  }

  private fun showStudyReminderNotification(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "english_90_daily_reminders"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        channelId,
        "Kunlik Dars Eslatmalari (150 daqiqa)",
        NotificationManager.IMPORTANCE_HIGH
      ).apply {
        description = "90 kunlik intensiv ingliz tili darslarini o'z vaqtida bajarish eslatmalari"
        enableLights(true)
        enableVibration(true)
      }
      notificationManager.createNotificationChannel(channel)
    }

    val openAppIntent = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    val pendingIntent = PendingIntent.getActivity(
      context,
      1001,
      openAppIntent,
      PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
    )

    val notification = NotificationCompat.Builder(context, channelId)
      .setSmallIcon(android.R.drawable.ic_dialog_info)
      .setContentTitle("⏰ 90 Kunlik Intensiv: Dars Vaqti!")
      .setContentText("Bugungi 150 daqiqalik darsni (Lugʻat, Eshitish, Nutq, Grammatika, Test) bajaring va B2 darajaga intiling!")
      .setStyle(
        NotificationCompat.BigTextStyle().bigText(
          "Bugungi 150 daqiqalik intensiv mashg'ulot kutmoqda!\n\n" +
          "1. 📖 Lug'at va Iboralar (30 min)\n" +
          "2. 🎧 Eshitish va Shadowing (30 min)\n" +
          "3. 🗣️ Nutq va Hayajonni Yengish (30 min)\n" +
          "4. ✍️ Jonli Gap Qoliplari (30 min)\n" +
          "5. 📝 Kunlik Test va Sinov (30 min)\n\n" +
          "Davomiylik va intizom — ravon gapirish kalitidir!"
        )
      )
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setAutoCancel(true)
      .setContentIntent(pendingIntent)
      .build()

    notificationManager.notify(NOTIFICATION_ID, notification)
  }

  companion object {
    const val NOTIFICATION_ID = 9001
  }
}
