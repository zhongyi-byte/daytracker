// TrackPoint.kt - 轨迹点数据模型
package com.zyi.daytracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_points")
data class TrackPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long,
    val accuracy: Float,
    val altitude: Double = 0.0,
    val speed: Float = 0f
)