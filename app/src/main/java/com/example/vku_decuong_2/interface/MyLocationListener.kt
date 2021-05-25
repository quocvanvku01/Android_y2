package com.example.vku_decuong_2.`interface`

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.util.*
import kotlin.coroutines.coroutineContext


class MyLocationListener(val gcontext: Context): LocationListener {

    lateinit var editLocation: EditText
    lateinit var pb: ProgressBar
    val TAG: String = "DEBUG"

    override fun onLocationChanged(location: Location) {
        editLocation.setText("")
        pb.setVisibility(View.INVISIBLE)

        Log.d("Ct", "Location changed: Lat: " + location.getLatitude().toString() +
                " Lng: " + location.getLongitude())

        val longitude = "Longitude: " + location.getLongitude()
        Log.v(TAG, longitude)
        val latitude = "Latitude: " + location.getLatitude()
        Log.v(TAG, latitude)

        var cityName: String? = null
        val gcd = Geocoder(gcontext, Locale.getDefault())
        val addresses: List<Address>
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1)
            if (addresses.size > 0) {
                System.out.println(addresses[0].getLocality())
                cityName = addresses[0].getLocality()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val s: String = longitude + latitude + "City " + cityName
        Log.d("cityname", s)
        editLocation.setText(s)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }
}