package com.example.vku_decuong_2.location

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.util.*
import java.util.jar.Manifest

class GetLocationCurrent {

    private lateinit var fusedLocation: FusedLocationProviderClient

    fun getLocation(atv: Activity) {
        fusedLocation = LocationServices.getFusedLocationProviderClient(atv)

        if (ActivityCompat.checkSelfPermission(atv, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocation.lastLocation.addOnCompleteListener(object : OnCompleteListener<Location> {
                override fun onComplete(p0: Task<Location>) {
                    val location: Location? = p0.getResult()

                    if(location != null) {
                        val geocoder: Geocoder = Geocoder(atv, Locale.getDefault())

                        val address: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        Log.d("latitude", address.get(0).latitude.toString())
                        Log.d("longitude", address.get(0).longitude.toString())
                        Log.d("Country", address.get(0).countryName.toString())

                    }

                }

            })

        } else {
            //ActivityCompat.requestPermissions(atv, )
        }


    }

}