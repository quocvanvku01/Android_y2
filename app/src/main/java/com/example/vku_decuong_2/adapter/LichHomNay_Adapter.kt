package com.example.vku_decuong_2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.data.DanhSachMonHoc_Model
import com.example.vku_decuong_2.data.KeHoachGiangDay
import com.example.vku_decuong_2.data.MonHocHomNay_Model


class LichHomNay_Adapter(var listLhn: ArrayList<MonHocHomNay_Model>, var context: Context)
    : RecyclerView.Adapter<LichHomNay_Adapter.ViewHolder>() {

    inner class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        var TvPhongHoc : TextView = itemView.findViewById(R.id.tv_phonghoc)
        var TvTiet: TextView = itemView.findViewById(R.id.tv_tiet)
        var TvTenHocPhan : TextView = itemView.findViewById(R.id.tv_tenhocphan)
        var TvThoiGian: TextView = itemView.findViewById(R.id.tv_thoigian)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutinflater : LayoutInflater = LayoutInflater.from(parent.context)
        var view = layoutinflater.inflate(R.layout.item_lich_ngay_hom_nay, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listLhn.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var lichhomnay : MonHocHomNay_Model = listLhn[position]
        if (lichhomnay == null) {
            return
        }

        holder.TvPhongHoc.setText(lichhomnay.phonghoc)
        holder.TvTiet.setText(lichhomnay.tiet)
        holder.TvTenHocPhan.setText(lichhomnay.tenmon)
        holder.TvThoiGian.setText(lichhomnay.thoigian)

    }

}