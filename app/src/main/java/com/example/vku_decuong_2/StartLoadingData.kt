package com.example.vku_decuong_2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.vku_decuong_2.Service.MyService
import com.example.vku_decuong_2.`interface`.RetrofitResponseListener
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.MonHocHomNay_Model
import com.example.vku_decuong_2.data.MonHoc_Model
import com.example.vku_decuong_2.home.fragment.FragmentHome
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


class StartLoadingData : AppCompatActivity() {

    private lateinit var pcbStatus: ProgressBar

    private var getIdgv: Int = 0
    private var token_api: String = ""

    var asyncTasksLoadingData: AsyncTasksLoadingData? = null

    private var check_1: Int = 0
    private var check_2: Int = 0

    private lateinit var setIsLogin: SharedPreferences

    private lateinit var fusedLocation: FusedLocationProviderClient

    private var distanofschol: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_loading_data)
        supportActionBar?.hide()

        getLocation()

        pcbStatus = findViewById(R.id.prb_Status)

        setIsLogin = getSharedPreferences("isLogin", 0)
        token_api = setIsLogin?.getString("token_api", "").toString()

        asyncTasksLoadingData = AsyncTasksLoadingData(this)
        asyncTasksLoadingData?.execute()

    }

    inner class AsyncTasksLoadingData(val context: Activity) : AsyncTask<String, Int, String>() {

        override fun doInBackground(vararg params: String?): String {
            var pi: Int = 0
            while (true) {
                if (check_1 == 1 && check_2 == 1) {
                    Thread.sleep(1500)
                    break
                }

                pi ++;
                publishProgress(pi)
                Thread.sleep(500)

            }
            return "Success"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val number: Int? = values[0]

            if (number != null) {
                pcbStatus?.progress = number
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val intent_home = Intent(context, MainActivity::class.java)
            startActivity(intent_home)
        }

        override fun onPreExecute() {
            super.onPreExecute()
            Toast.makeText(this.context, "Start", Toast.LENGTH_SHORT).show()

            val setIsLogin = getSharedPreferences("isLogin", 0)
            val getIsLogin = setIsLogin?.getString("isLogin", "")
            getIdgv = setIsLogin?.getInt("idgv", 0)!!

            if(getIsLogin.equals("")) {
                val intentLogin = Intent(context, LoginActivity::class.java)
                startActivity(intentLogin)
            } else {
                Toast.makeText(context, getIsLogin, Toast.LENGTH_SHORT).show()
                getDataLhn(getIdgv)
                getDataMonHoc(getIdgv)
            }

        }

    }

    private fun getDataLhn(getIdgv: Int) {
        val call: Call<List<MonHocHomNay_Model>> = ApiClient.getClient.getDataLhn(getIdgv, "Bearer "+token_api)
        call.enqueue(object: Callback<List<MonHocHomNay_Model>> {
            override fun onResponse(call: Call<List<MonHocHomNay_Model>>?, response : Response<List<MonHocHomNay_Model>>? ) {
                if (response!!.isSuccessful) {
                    list_Lhn_Loading = ArrayList<MonHocHomNay_Model>(response!!.body())
                    //Log.d("abc", list_Lhn_Loading.get(0).tenmon)
                    getDataLhnLoading()
                    retrofitResponse_1()
                } else {
                    retrofitResponseFailure()
                }
            }
            override fun onFailure(call: Call<List<MonHocHomNay_Model>>?, t : Throwable ? ) {
                if (call!!.isCanceled()) {
                    Log.d("Error", "Call was cancelled forcefully")
                } else {
                    Log.d("Error", t!!.localizedMessage)
                }
            }
        })
    }

    fun getDataMonHoc(getIdgv: Int) {

        val call: Call<List<MonHoc_Model>> = ApiClient.getClient.getDataMonHoc(getIdgv,"Bearer "+token_api)
        call.enqueue(object: Callback<List<MonHoc_Model>> {
            override fun onResponse(call: Call<List<MonHoc_Model>>?, response : Response<List<MonHoc_Model>>? ) {

                if (response!!.isSuccessful) {
                    list_Monhoc_Loading = ArrayList<MonHoc_Model>(response!!.body())
                    getDataMhLoading()
                    retrofitResponse_2()
                } else {
                    retrofitResponseFailure()
                }
            }
            override fun onFailure(call: Call<List<MonHoc_Model>>?, t : Throwable ? ) {
                if (call!!.isCanceled()) {
                    Log.d("Error", "Call was cancelled forcefully")
                } else {
                    Log.d("Error", t!!.localizedMessage)
                }
            }

        })
    }

    companion object {

        var list_Lhn_Loading : ArrayList<MonHocHomNay_Model> = ArrayList<MonHocHomNay_Model>()
        var list_Monhoc_Loading : ArrayList<MonHoc_Model> = ArrayList<MonHoc_Model>()

        fun getDataLhnLoading(): ArrayList<MonHocHomNay_Model> {
            list_Lhn_Loading.forEach { Log.d("Load", it.tenmon) }
            var a : ArrayList<MonHocHomNay_Model> = ArrayList<MonHocHomNay_Model>()
            a = list_Lhn_Loading
            return a
        }

        fun getDataMhLoading(): ArrayList<MonHoc_Model> {
            var b : ArrayList<MonHoc_Model> = ArrayList<MonHoc_Model>()
            b = list_Monhoc_Loading
            return b
        }

        fun getCountMonHoc(): Int {
            return list_Monhoc_Loading.size
        }

    }

    private fun retrofitResponse_1() {
        check_1 = 1
    }

    private fun retrofitResponse_2() {
        check_2 = 1

        var b = Bundle()
        b.putSerializable("key", list_Monhoc_Loading)
        b.putSerializable("locationlatlong", distanofschol)
        val intent_service = Intent(this, MyService::class.java)
        intent_service.putExtras(b)
        //baseContext.startForegroundService(intent_service)
        startService(intent_service)
    }

    private fun retrofitResponseFailure() {
        Log.d("Error Res", "Error")
    }

    private fun getLocation() {
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocation.lastLocation.addOnCompleteListener(object : OnCompleteListener<Location> {
                override fun onComplete(p0: Task<Location>) {
                    val location: Location? = p0.getResult()
                    if(location != null) {

                        var lat1: Double = location.latitude
                        var long1: Double = location.longitude
                        var lat2: Double = 15.974996553016677
                        var long2: Double = 108.25322914693861

                        distanofschol = distance(lat1, long1, lat2, long2).toString()

                    }
                }
            })
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 44)
        }
    }

    private fun distance(lat1 : Double, long1 : Double, lat2 : Double, long2 : Double): Double {
        var longDiff = long1 - long2
        var distance: Double = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(longDiff))

        distance = Math.acos(distance)

        distance = rad2deg(distance)

        distance = distance * 60 * 1.1515

        distance = distance * 1.609344

        return distance

//        Log.d("CurrentTinhtoan", String.format(Locale.US, "%2f Kilometers", distance))

    }

    private fun rad2deg(distance: Double): Double {
        return (distance * 180.0 / Math.PI)
    }

    private fun deg2rad(lat1: Double): Double {
        return (lat1*Math.PI/180.0)
    }



}