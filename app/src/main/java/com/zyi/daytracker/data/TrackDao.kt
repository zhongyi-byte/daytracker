// TrackDao.kt - 数据库访问对象
package com.zyi.daytracker.data

import androidx.room.*

@Dao
interface TrackDao {
    
    @Insert
    fun insert(point: TrackPoint): Long
    
    @Insert
    fun insertAll(points: List<TrackPoint>)
    
    @Query("SELECT * FROM track_points WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp ASC")
    fun getPointsForTimeRange(startTime: Long, endTime: Long): List<TrackPoint>
    
    @Query("SELECT * FROM track_points WHERE date(timestamp / 1000, 'unixepoch') = date('now') ORDER BY timestamp ASC")
    fun getTodayPoints(): List<TrackPoint>
    
    @Query("SELECT * FROM track_points ORDER BY timestamp DESC LIMIT 1")
    fun getLastPoint(): TrackPoint?
    
    @Query("SELECT COUNT(*) FROM track_points WHERE date(timestamp / 1000, 'unixepoch') = date('now')")
    fun getTodayPointCount(): Int
    
    @Delete
    fun delete(point: TrackPoint)
    
    @Query("DELETE FROM track_points WHERE timestamp < :timestamp")
    fun deleteBefore(timestamp: Long)
}