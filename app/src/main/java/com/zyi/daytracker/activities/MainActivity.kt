// MainActivity.kt - 使用高德地图
package com.zyi.daytracker.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.PolylineOptions
import com.amap.api.maps.model.MyLocationStyle
import com.zyi.daytracker.R
import com.zyi.daytracker.data.TrackPoint
import com.zyi.daytracker.location.LocationTracker

class MainActivity : AppCompatActivity() {
    
    private lateinit var mapView: MapView
    private var aMap: AMap? = null
    private lateinit var locationTracker: LocationTracker
    private val trackPoints = mutableListOf<TrackPoint>()
    
    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 1001
        private const val AMAP_API_KEY = "YOUR_AMAP_API_KEY" // 需要替换
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // 初始化高德地图
        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        
        initMap()
        checkLocationPermission()
    }
    
    private fun initMap() {
        aMap = mapView.map
        aMap?.apply {
            // 设置定位样式
            val myLocationStyle = MyLocationStyle()
            myLocationStyle.interval(2000)
            myLocationStyle.showMyLocation(true)
            myLocationStyle = myLocationStyle
            
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
            
            // 显示定位蓝点
            isMyLocationEnabled = true
        }
    }
    
    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(this, 
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                startTracking()
            }
            else -> {
                requestLocationPermission()
            }
        }
    }
    
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST
        )
    }
    
    private fun startTracking() {
        locationTracker = LocationTracker(this) { location ->
            val point = TrackPoint(
                latitude = location.latitude,
                longitude = location.longitude,
                timestamp = System.currentTimeMillis(),
                accuracy = location.accuracy
            )
            trackPoints.add(point)
            updateMap()
        }
        locationTracker.startTracking()
    }
    
    private fun updateMap() {
        if (trackPoints.size < 2) return
        
        val polylineOptions = PolylineOptions()
            .width(12f)
            .color(ContextCompat.getColor(this, R.color.track_color))
        
        trackPoints.forEach { point ->
            polylineOptions.add(LatLng(point.latitude, point.longitude))
        }
        
        aMap?.addPolyline(polylineOptions)
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST && 
            grantResults.isNotEmpty() && 
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startTracking()
        }
    }
    
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        if (::locationTracker.isInitialized) {
            locationTracker.stopTracking()
        }
    }
}