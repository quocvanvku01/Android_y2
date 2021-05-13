package com.example.vku_decuong_2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.data.KeHoachGiangDay_Model

class KeHoachGiangDay_Adapter(var listKhgd: ArrayList<KeHoachGiangDay_Model>, var context: Context):
    RecyclerView.Adapter<KeHoachGiangDay_Adapter.ViewHolderKhgd>() {

    inner class ViewHolderKhgd(itemview: View): RecyclerView.ViewHolder(itemview) {
        var TvBuoi : TextView = itemView.findViewById(R.id.tv_buoi)
        var TvNoidung : TextView = itemView.findViewById(R.id.tv_noidung)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderKhgd {
        var layoutinflater : LayoutInflater = LayoutInflater.from(parent.context)
        var view = layoutinflater.inflate(R.layout.item_noidung_buoihoc, parent, false)
        return ViewHolderKhgd(view)
    }

    override fun getItemCount(): Int {
        return listKhgd.size
    }

    override fun onBindViewHolder(holder: ViewHolderKhgd, position: Int) {
        var kehoachgiangday : KeHoachGiangDay_Model = listKhgd[position]
        if (kehoachgiangday == null) {
            return
        }

        holder.TvBuoi.setText(kehoachgiangday.buoi)
        holder.TvNoidung.setText(kehoachgiangday.noidung)
    }

}