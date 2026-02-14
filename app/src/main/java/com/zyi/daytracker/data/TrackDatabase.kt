// TrackDatabase.kt - Room 数据库
package com.zyi.daytracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TrackPoint::class], version = 1, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    
    abstract fun trackDao(): TrackDao
    
    companion object {
        @Volatile
        private var INSTANCE: TrackDatabase? = null
        
        fun getInstance(context: Context): TrackDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        
        private fun buildDatabase(context: Context): TrackDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TrackDatabase::class.java,
                "daytracker.db"
            ).build()
        }
    }
}