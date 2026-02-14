// TrackingService.kt - 后台追踪服务
package com.zyi.daytracker.location

import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.zyi.daytracker.R
import com.zyi.daytracker.activities.MainActivity

class TrackingService : Service() {
    
    private lateinit var locationTracker: LocationTracker
    
    companion object {
        const val CHANNEL_ID = "daytracker_tracking_channel"
        const val NOTIFICATION_ID = 1001
    }
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
        
        locationTracker = LocationTracker(this) { location ->
            // 保存位置点
        }
        locationTracker.startTracking()
        
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        if (::locationTracker.isInitialized) {
            locationTracker.stopTracking()
        }
    }
    
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "轨迹记录",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "正在记录您的位置轨迹"
        }
        
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("DayTracker")
            .setContentText("正在记录您的轨迹...")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
}