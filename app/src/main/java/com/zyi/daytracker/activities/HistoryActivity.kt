// HistoryActivity.kt - 历史轨迹界面
package com.zyi.daytracker.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zyi.daytracker.R
import com.zyi.daytracker.data.TrackDatabase
import com.zyi.daytracker.data.TrackPoint
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrackAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        loadTodayTracks()
    }
    
    private fun loadTodayTracks() {
        Thread {
            val points = TrackDatabase.getInstance(this).trackDao().getTodayPoints()
            runOnUiThread {
                adapter = TrackAdapter(points)
                recyclerView.adapter = adapter
            }
        }.start()
    }
    
    inner class TrackAdapter(private val points: List<TrackPoint>) : 
        RecyclerView.Adapter<TrackAdapter.ViewHolder>() {
        
        private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val timeText: TextView = view.findViewById(R.id.timeText)
            val locationText: TextView = view.findViewById(R.id.locationText)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_track_point, parent, false)
            return ViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val point = points[position]
            holder.timeText.text = dateFormat.format(Date(point.timestamp))
            holder.locationText.text = "%.6f, %.6f".format(point.latitude, point.longitude)
        }
        
        override fun getItemCount() = points.size
    }
}