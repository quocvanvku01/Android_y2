package com.example.vku_decuong_2.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.directions.route.*
import com.example.vku_decuong_2.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import java.util.*


class FragmentMap : Fragment() {

    private lateinit var mView: View

    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var clinet: FusedLocationProviderClient

    private lateinit var lc: Location



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false)

        supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        supportMapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(p0: GoogleMap?) {
                p0?.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
                    override fun onMapClick(latLng: LatLng) {
                        val markerOptions: MarkerOptions = MarkerOptions()
                        markerOptions.position(latLng)
                        markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
                        Log.d("latitude111", latLng.latitude.toString())
                        Log.d("longitude111", latLng.longitude.toString())
                        p0.clear()
                        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                        p0.addMarker(markerOptions)
                    }

                })
            }
        })

        clinet = LocationServices.getFusedLocationProviderClient(activity!!)

        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val task: Task<Location> = clinet.lastLocation
            task.addOnSuccessListener(object : OnSuccessListener<Location> {
                override fun onSuccess(location: Location?) {
                    if(location != null) {
                        supportMapFragment.getMapAsync(object : OnMapReadyCallback{
                            override fun onMapReady(googleMap: GoogleMap?) {
                                var latLng: LatLng = LatLng(location.latitude, location.longitude)
                                var option : MarkerOptions = MarkerOptions().position(latLng).title("I am there")

                                Log.d("latitude_current", latLng.latitude.toString())
                                Log.d("longitude_current", latLng.longitude.toString())

                                val geocoder: Geocoder = Geocoder(activity!!, Locale.getDefault())
                                val address: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                                val diachi = address.get(0).getAddressLine(0)
                                var arr = diachi.split(",")

                                vitridn = arr[arr.size-2].trim() + ", " + arr[arr.size-1].trim()

                                Log.d("vitri", vitridn)

                                getViTriDangNhap()

                                googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                                googleMap?.addMarker(option)
                            }

                        })
                    }
                }

            })
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 44)
        }



        return mView
    }

    fun getViTriDangNhap(): String {
        return vitridn
    }

    companion object {

        private var vitridn: String = ""


    }



}