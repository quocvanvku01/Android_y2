package com.example.vku_decuong_2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.vku_decuong_2.adapter.ViewPagerHome_Adapter
import com.example.vku_decuong_2.home.DanhSachMonHoc
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogout: Button
    private lateinit var btnDanhSachMonHoc: LinearLayout

    private lateinit var bottomNavi: AnimatedBottomBar
    private lateinit var mViewPagerMain: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        bottomNavi = findViewById(R.id.bottom_bar)
        mViewPagerMain = findViewById(R.id.viewpager_main)

        setupViewPager()

        bottomNavi.setOnTabInterceptListener(object : AnimatedBottomBar.OnTabInterceptListener {
            override fun onTabIntercepted(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ): Boolean {

                when(newTab.id) {
                    R.id.tab_home -> mViewPagerMain.currentItem = 0
                    R.id.tab_1 -> mViewPagerMain.currentItem = 1
                    R.id.tab_taikhoan -> mViewPagerMain.currentItem = 2
                    else -> mViewPagerMain.currentItem = 0
                }

                return true
            }
        })

    }

    private fun setupViewPager() {
        var viewPagerAdapter: ViewPagerHome_Adapter = ViewPagerHome_Adapter(supportFragmentManager
            , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        mViewPagerMain.adapter = viewPagerAdapter

        mViewPagerMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavi.selectTabById(R.id.tab_home)
                    1 -> bottomNavi.selectTabById(R.id.tab_1)
                    2 -> bottomNavi.selectTabById(R.id.tab_taikhoan)
                    else -> bottomNavi.selectTabById(R.id.tab_home)
                }
            }

        })
    }
}