// LocationTracker.kt - 位置追踪服务
package com.zyi.daytracker.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

class LocationTracker(
    private val context: Context,
    private val onLocationUpdate: (Location) -> Unit
) {
    private val fusedLocationClient: FusedLocationProviderClient = 
        LocationServices.getFusedLocationProviderClient(context)
    
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { location ->
                onLocationUpdate(location)
            }
        }
    }
    
    fun startTracking() {
        if (ContextCompat.checkSelfPermission(context, 
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            30000L // 30秒更新一次
        ).apply {
            setMinUpdateIntervalMillis(10000L) // 最快10秒
            setWaitForAccurateLocation(true)
        }.build()
        
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
    
    fun stopTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}