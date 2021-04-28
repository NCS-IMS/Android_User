package com.ncs.imsUser.GISManager

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GetMylocation {

    fun getLocation(context: Context): HashMap<String, Double> {
        var lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps = hashMapOf<String, Double>()
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        } else {
            val location: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            gps["longitude"] = location!!.longitude
            gps["latitude"] = location!!.latitude
        }
        return gps
    }
}