package com.example.vku_decuong_2.taikhoan

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import com.example.vku_decuong_2.R


class SettingTK : AppCompatActivity() {

    private lateinit var darkModeSwitch: Switch
    private lateinit var notificationSwitch: Switch

    private lateinit var tvLichSuDangNhap: TextView

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var tvSetting: TextView
    private lateinit var tvNotifi: TextView
    private lateinit var tvTaiKhoan: TextView
    private lateinit var tvNgonNgu: TextView
    private lateinit var tvLanguage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_t_k)
        supportActionBar?.hide()

        val fontRegular = ResourcesCompat.getFont(this, R.font.jbmono_regular)
        val fontBold = ResourcesCompat.getFont(this, R.font.jbmono_bold)

        tvSetting = findViewById(R.id.tv_setting)
        tvNotifi = findViewById(R.id.tv_notifi)
        tvTaiKhoan = findViewById(R.id.tv_tai_khoan)
        tvNgonNgu = findViewById(R.id.tv_ngon_ngu)

        tvSetting.typeface = fontBold
        tvNotifi.typeface = fontBold
        tvTaiKhoan.typeface = fontBold
        tvNgonNgu.typeface = fontBold

        if(DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        tvLichSuDangNhap = findViewById(R.id.tv_lichsudangnhap)
        tvLichSuDangNhap.typeface = fontBold

        tvLichSuDangNhap.setOnClickListener {
            val inten_lsdn = Intent(this, LichSuDangNhap::class.java)
            startActivity(inten_lsdn)
        }

        darkModeSwitch = findViewById(R.id.darkModeSwitch)
        notificationSwitch = findViewById(R.id.notificationSwitch)

        tvLanguage = findViewById(R.id.tv_language)

        tvLanguage.typeface = fontBold
        darkModeSwitch.typeface = fontBold
        notificationSwitch.typeface = fontBold

        setDarkModeSwitch()

        setNotificationSwitch()

    }

    private fun setDarkModeSwitch() {

        darkModeSwitch.isChecked = DarkModePrefManager(this).isNightMode()

        darkModeSwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                val darkModePrefManager = DarkModePrefManager(this@SettingTK)
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode())
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
            }
        })
    }

    private fun setNotificationSwitch() {

        sharedPreferences = getSharedPreferences("key", 0)
        val editor = sharedPreferences.edit()

        val setCheckedNoti: Boolean = sharedPreferences.getBoolean("switchValue", false)

        if (setCheckedNoti == true) {
            notificationSwitch.isChecked = true
        }

        if(setCheckedNoti == false) {
            notificationSwitch.isChecked = false
        }


        notificationSwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                val showNotifications: Boolean = sharedPreferences.getBoolean("switchValue", false)

                if (notificationSwitch.isChecked() != showNotifications) {
                    editor.putBoolean("switchValue",!showNotifications).apply()
                }

            }
        })

    }
}