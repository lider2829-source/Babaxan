package com.example.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.receiver.DailyReminderReceiver
import java.util.Calendar

object ReminderScheduler {

  private const val PREFS_NAME = "english_90_reminder_prefs"
  private const val KEY_ENABLED = "reminder_enabled"
  private const val KEY_HOUR = "reminder_hour"
  private const val KEY_MINUTE = "reminder_minute"

  private const val DEFAULT_HOUR = 20 // 20:00 (8:00 PM)
  private const val DEFAULT_MINUTE = 0

  private const val ALARM_REQUEST_CODE = 9002

  private fun getPrefs(context: Context): SharedPreferences {
    return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
  }

  fun isReminderEnabled(context: Context): Boolean {
    return getPrefs(context).getBoolean(KEY_ENABLED, false)
  }

  fun getReminderTime(context: Context): Pair<Int, Int> {
    val prefs = getPrefs(context)
    val hour = prefs.getInt(KEY_HOUR, DEFAULT_HOUR)
    val minute = prefs.getInt(KEY_MINUTE, DEFAULT_MINUTE)
    return Pair(hour, minute)
  }

  fun saveAndScheduleReminder(context: Context, enabled: Boolean, hour: Int, minute: Int) {
    getPrefs(context).edit()
      .putBoolean(KEY_ENABLED, enabled)
      .putInt(KEY_HOUR, hour)
      .putInt(KEY_MINUTE, minute)
      .apply()

    if (enabled) {
      scheduleAlarm(context, hour, minute)
    } else {
      cancelAlarm(context)
    }
  }

  fun rescheduleIfEnabled(context: Context) {
    if (isReminderEnabled(context)) {
      val (hour, minute) = getReminderTime(context)
      scheduleAlarm(context, hour, minute)
    }
  }

  fun scheduleNextDailyAlarm(context: Context) {
    if (isReminderEnabled(context)) {
      val (hour, minute) = getReminderTime(context)
      scheduleAlarm(context, hour, minute)
    }
  }

  private fun scheduleAlarm(context: Context, hour: Int, minute: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, DailyReminderReceiver::class.java).apply {
      action = "com.example.action.DAILY_STUDY_REMINDER"
    }

    val pendingIntent = PendingIntent.getBroadcast(
      context,
      ALARM_REQUEST_CODE,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
    )

    val calendar = Calendar.getInstance().apply {
      timeInMillis = System.currentTimeMillis()
      set(Calendar.HOUR_OF_DAY, hour)
      set(Calendar.MINUTE, minute)
      set(Calendar.SECOND, 0)
      set(Calendar.MILLISECOND, 0)

      // If scheduled time has already passed today, advance to tomorrow
      if (timeInMillis <= System.currentTimeMillis()) {
        add(Calendar.DAY_OF_YEAR, 1)
      }
    }

    val triggerAtMillis = calendar.timeInMillis

    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        alarmManager.setAndAllowWhileIdle(
          AlarmManager.RTC_WAKEUP,
          triggerAtMillis,
          pendingIntent
        )
      } else {
        alarmManager.set(
          AlarmManager.RTC_WAKEUP,
          triggerAtMillis,
          pendingIntent
        )
      }
    } catch (e: SecurityException) {
      // In case exact alarm permission is restricted, fallback to standard repeating alarm
      alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        triggerAtMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
      )
    }
  }

  fun cancelAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, DailyReminderReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
      context,
      ALARM_REQUEST_CODE,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
    )
    alarmManager.cancel(pendingIntent)
  }

  /**
   * Immediately fires a sample notification so the user can verify that notifications are working.
   */
  fun triggerTestNotification(context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "english_90_daily_reminders"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        channelId,
        "Kunlik Dars Eslatmalari (150 daqiqa)",
        NotificationManager.IMPORTANCE_HIGH
      ).apply {
        description = "90 kunlik intensiv ingliz tili darslarini o'z vaqtida bajarish eslatmalari"
      }
      notificationManager.createNotificationChannel(channel)
    }

    val openAppIntent = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    val pendingIntent = PendingIntent.getActivity(
      context,
      1002,
      openAppIntent,
      PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
    )

    val notification = NotificationCompat.Builder(context, channelId)
      .setSmallIcon(android.R.drawable.ic_dialog_info)
      .setContentTitle("✅ Eslatma muvaffaqiyatli ishlamoqda!")
      .setContentText("AlarmManager har kuni belgilangan vaqtda sizga 150 daqiqalik darsni eslatadi.")
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setAutoCancel(true)
      .setContentIntent(pendingIntent)
      .build()

    notificationManager.notify(9003, notification)
  }
}
