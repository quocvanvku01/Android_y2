package com.example.vku_decuong_2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.data.DanhSachMonHoc_Model
import com.example.vku_decuong_2.data.KeHoachGiangDay
import kotlin.random.Random


class DanhSachMonHoc_Adapter(var listDsmh: ArrayList<DanhSachMonHoc_Model>, var context: Context)
    : RecyclerView.Adapter<DanhSachMonHoc_Adapter.ViewHolder>() {

    inner class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        var TvCmgd : TextView = itemView.findViewById(R.id.tv_dsmh)
        var lnMh: LinearLayout = itemview.findViewById(R.id.ln_monhoc)
        var tvMonHoc: TextView = itemview.findViewById(R.id.tv_mon_hoc)
        var tvXemChiTiet: TextView = itemview.findViewById(R.id.tvXemChiTiet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutinflater : LayoutInflater = LayoutInflater.from(parent.context)
        var view = layoutinflater.inflate(R.layout.item_monhoc, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDsmh.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var danhsachmonhoc : DanhSachMonHoc_Model = listDsmh[position]
        if (danhsachmonhoc == null) {
            return
        }

        val fontRegular = ResourcesCompat.getFont(context!!, R.font.jbmono_regular)!!
        val fontBold = ResourcesCompat.getFont(context!!, R.font.jbmono_bold)!!

        holder.TvCmgd.setText(danhsachmonhoc.tenmon)
        holder.lnMh.setBackgroundResource(R.drawable.boder_5)

        holder.TvCmgd.typeface = fontBold
        holder.tvMonHoc.typeface = fontRegular
        holder.tvXemChiTiet.typeface = fontRegular

        var gIddecuong: String = danhsachmonhoc.id_decuong

        holder.itemView.setOnClickListener{
            Toast.makeText(context, danhsachmonhoc.id_decuong, Toast.LENGTH_SHORT).show()
            val intent_khdg = Intent(context, KeHoachGiangDay::class.java)
            intent_khdg.putExtra("id_decuong", gIddecuong)
            context.startActivity(intent_khdg)
        }

    }

}