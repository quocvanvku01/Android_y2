package com.example.vku_decuong_2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.data.KeHoachGiangDay_Model
import com.example.vku_decuong_2.data.LichSuDangNhap_Model

class LichSuDangNhap_Adapter(var listLsdn: ArrayList<LichSuDangNhap_Model>, var context: Context):
    RecyclerView.Adapter<LichSuDangNhap_Adapter.ViewHolderLsdn>() {

    inner class ViewHolderLsdn(itemview: View): RecyclerView.ViewHolder(itemview) {
        var tvTenthietbi : TextView = itemView.findViewById(R.id.tv_tenthietbi)
        var tvVitringaygio : TextView = itemView.findViewById(R.id.tv_vitringaygio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLsdn {
        var layoutinflater : LayoutInflater = LayoutInflater.from(parent.context)
        var view = layoutinflater.inflate(R.layout.item_lich_su_dang_nhap, parent, false)
        return ViewHolderLsdn(view)
    }

    override fun getItemCount(): Int {
        return listLsdn.size
    }

    override fun onBindViewHolder(holder: ViewHolderLsdn, position: Int) {
        var lichsudangnhap : LichSuDangNhap_Model = listLsdn[position]
        if (lichsudangnhap == null) {
            return
        }

        holder.tvTenthietbi.setText(lichsudangnhap.tenthietbi)
        holder.tvVitringaygio.setText(lichsudangnhap.vitri + " * " + lichsudangnhap.ngaygio)
    }

}