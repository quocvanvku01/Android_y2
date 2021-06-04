package com.example.vku_decuong_2

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import com.example.vku_decuong_2.api.ApiClient
import com.example.vku_decuong_2.data.LichSuDangNhap_Model
import com.example.vku_decuong_2.data.LoginRes
import com.example.vku_decuong_2.data.MonHocHomNay_Model
import com.example.vku_decuong_2.data.User_Model
import com.example.vku_decuong_2.location.FragmentMap
import com.example.vku_decuong_2.location.MapActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: ImageView

    private lateinit var btnSigninGoogle: LinearLayout
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var userModel : User_Model

    private var vitridangnhap: String = ""

    private lateinit var fusedLocation: FusedLocationProviderClient

    var RC_SIGN_IN: Int = 0

    private lateinit var tvAppName: TextView
    private lateinit var tvLogin: TextView
    private lateinit var tvChonVKU: TextView
    private lateinit var tvQuenMatKhau: TextView
    private lateinit var tvBtnLogin: TextView
    private lateinit var tvTaoTaiKhoan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        //edtEmail = findViewById(R.id.edt_email)
        //edtPassword = findViewById(R.id.edt_password)
        //btnLogin = findViewById(R.id.btn_login)

        //btnLogin.setOnClickListener {
            //var username: String = edtEmail.text.toString()
            //var password: String = edtPassword.text.toString()

            //if(validateLogin(username, password)) {
                //doLogin(username, password)
            //}
        //}

        getViTriDangNhap()

        btnSigninGoogle = findViewById(R.id.btn_signin_google)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSigninGoogle.setOnClickListener {
            signIn()
        }

        val fontBold = ResourcesCompat.getFont(this, R.font.jbmono_bold)
        val fontRegular = ResourcesCompat.getFont(this, R.font.jbmono_regular)

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        tvAppName = findViewById(R.id.tv_app_name)
        tvLogin = findViewById(R.id.tv_login)
        tvChonVKU = findViewById(R.id.tv_chon_vku)
        tvQuenMatKhau = findViewById(R.id.tv_quen_mat_khau)
        tvBtnLogin = findViewById(R.id.tv_btn_login)
        tvTaoTaiKhoan = findViewById(R.id.tv_tao_tai_khoan)

        edtEmail.typeface = fontBold
        edtPassword.typeface = fontBold
        tvAppName.typeface = fontBold
        tvLogin.typeface = fontBold
        tvChonVKU.typeface = fontBold
        tvQuenMatKhau.typeface = fontRegular
        tvBtnLogin.typeface = fontBold
        tvTaoTaiKhoan.typeface = fontBold


    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d("Success", "Login Success full")

            val token: String? = account?.idToken
            val provider: String = "google"
            val email_ldn: String? = account?.email

            var isLogin: SharedPreferences = applicationContext.getSharedPreferences("isLogin", 0)
            var editorLogin: SharedPreferences.Editor = isLogin.edit()
            editorLogin.putString("token_api", token)
            editorLogin.commit()



            if (token != null) {
                doLogin(provider, token)
            }

        } catch (e: ApiException) {
            Log.d("ERROR", "signInResult:failed code=" + e.statusCode)
        }
    }


    private fun validateLogin(username: String, pass: String): Boolean {
        if(username == null || username.trim().length == 0) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if(pass == null || pass.trim().length == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun doLogin(provider: String, token: String) {

        val call: Call<LoginRes> = ApiClient.getClient.login(provider, token)

        call.enqueue(object : Callback<LoginRes> {
            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                if (call!!.isCanceled()) {
                    Log.d("Error", "Call was cancelled forcefully")
                } else {
                    Log.d("Error", t!!.localizedMessage)
                }
            }

            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                if(response.isSuccessful) {
                    if(response?.body()?.success.equals("true")) {
                        Log.d("Response123", "Success "+response?.body()?.privider)
                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show();

                        userModel = response.body()!!.user

                        val privider: String? = response?.body()?.privider

                        val id_gv: Int? = userModel.id.toInt()

                        var isLogin: SharedPreferences = applicationContext.getSharedPreferences("isLogin", 0)
                        var editorLogin: SharedPreferences.Editor = isLogin.edit()
                        editorLogin.putString("isLogin", privider)
                        editorLogin.putString("name", userModel.hodem + " " + userModel.ten)
                        editorLogin.putString("email", response?.body()?.email)
                        editorLogin.putString("chucvu", response?.body()?.chucvu)

                        if (id_gv != null) {
                            editorLogin.putInt("idgv", id_gv)
                            Log.d("IDGV", id_gv.toString())
                        }
                        editorLogin.commit()

                        val calendar: Calendar = Calendar.getInstance()
                        var format: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy  HH:mm")
                        val ngaygio = format.format(calendar.time)
                        val tenthietbi:String = Build.MODEL

                        lichsudangnhap(tenthietbi, vitridangnhap, ngaygio, response?.body()?.email.toString(), token)

                        val intenLg = Intent(this@LoginActivity, StartLoadingData::class.java)
                        startActivity(intenLg)

                    } else {
                        Toast.makeText(this@LoginActivity, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        })
    }

    private fun lichsudangnhap(tenthietbi: String, vitri: String, ngaygio: String, email: String, token_api: String) {
        val call: Call<LichSuDangNhap_Model> = ApiClient.getClient.lichsudangnhap(tenthietbi, vitri, ngaygio, email, "Bearer "+token_api)

        call.enqueue(object : Callback<LichSuDangNhap_Model> {
            override fun onFailure(call: Call<LichSuDangNhap_Model>, t: Throwable) {
                if (call!!.isCanceled()) {
                    Log.d("Error", "Call was cancelled forcefully")
                } else {
                    Log.d("Error", t!!.localizedMessage)
                }
            }

            override fun onResponse(call: Call<LichSuDangNhap_Model>, response: Response<LichSuDangNhap_Model>) {
                if(response.isSuccessful) {
                    Log.d("LSDN", "Success")
                } else {
                    Toast.makeText(this@LoginActivity, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

        })
    }

    private fun getViTriDangNhap() {

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocation.lastLocation.addOnCompleteListener(object : OnCompleteListener<Location> {
                override fun onComplete(p0: Task<Location>) {
                    val location: Location? = p0.getResult()
                    if(location != null) {
                        val geocoder: Geocoder = Geocoder(baseContext, Locale.getDefault())
                        val address: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                        val diachi = address.get(0).getAddressLine(0)
                        var arr = diachi.split(",")

                        vitridangnhap = arr[arr.size-2].trim() + ", " + arr[arr.size-1].trim()
                        Log.d("Vitridangnhap", vitridangnhap)
                    }
                }
            })
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 44)
        }
    }

}