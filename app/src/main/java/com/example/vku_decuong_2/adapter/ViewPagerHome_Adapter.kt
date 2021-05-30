package com.example.vku_decuong_2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.vku_decuong_2.StartLoadingData
import com.example.vku_decuong_2.home.fragment.FragmentHome
import com.example.vku_decuong_2.home.fragment.FragmentTaiKhoan
import com.example.vku_decuong_2.home.fragment.Fragment_1
import com.example.vku_decuong_2.home.fragment.Fragment_2

class ViewPagerHome_Adapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return FragmentHome()
            1 -> return Fragment_1()
            2 -> return Fragment_2()
            3 -> return FragmentTaiKhoan()
            else -> return FragmentHome()
        }
    }

    override fun getCount(): Int {
        return 4
    }
}