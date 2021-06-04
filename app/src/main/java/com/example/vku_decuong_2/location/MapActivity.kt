package com.example.vku_decuong_2.location

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vku_decuong_2.R
import java.util.*

class MapActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar?.hide()

        val fragmentmap: Fragment = FragmentMap()

        supportFragmentManager.beginTransaction().replace(R.id.fml_map, fragmentmap).commit()

        val latitude1 = 16.4918883
        val longitude1 = 107.3971867
        val latitude2 = 16.545104
        val longitude2 = 107.454253

        distance(latitude1, longitude1, latitude2, longitude2)

        val results = FloatArray(10)
        Location.distanceBetween(latitude1, longitude1, latitude2, longitude2, results)

        val m = results[0].toInt()
        val strM = m.toString() + ""
        if (strM.length > 6)
        {
            var kkm = m * 1.0f / (1000 * 1000)
            kkm = Math.round(kkm * 100) * 1.0f / 10
            Log.d("Distance", kkm.toString() + "kkm")
        } else if (strM.length > 3)
        {
            var km = m * 1.0f / 1000
            km = Math.round(km * 100) * 1.0f / 10
            Log.d("Distance", km.toString() + "km")
        }

        Log.d("Distance", m.toString())

    }

    fun distance(lat1 : Double, long1 : Double, lat2 : Double, long2 : Double) {
        var longDiff = long1 - long2
        var distance: Double = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(longDiff))

        distance = Math.acos(distance)

        distance = rad2deg(distance)

        distance = distance * 60 * 1.1515

        distance = distance * 1.609344

        Log.d("CurrentTinhtoan", String.format(Locale.US, "%2f Kilometers", distance))

    }

    private fun rad2deg(distance: Double): Double {
        return (distance * 180.0 / Math.PI)
    }

    private fun deg2rad(lat1: Double): Double {
        return (lat1*Math.PI/180.0)
    }


}