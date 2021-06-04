package com.example.vku_decuong_2.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vku_decuong_2.R
import com.example.vku_decuong_2.`interface`.OnClickLike
import com.example.vku_decuong_2.data.NewsFeed_Model

class NewsFeed_Adapter(var listNf: ArrayList<NewsFeed_Model>, var context: Context, var listener: OnClickLike):
        RecyclerView.Adapter<NewsFeed_Adapter.ViewHolderNf>() {

    lateinit var note: NewsFeed_Model

    inner class ViewHolderNf(itemview: View): RecyclerView.ViewHolder(itemview) {
        var tvNguoidangNews: TextView = itemview.findViewById(R.id.tv_nguoidang_news)
        var tvTitleNews : TextView = itemView.findViewById(R.id.tv_title_news)
        var tvContentNews : TextView = itemview.findViewById(R.id.tv_content_news)
        var tvLikeNew : TextView = itemview.findViewById(R.id.tv_like_new)
        var tvNgaygioNewsfeed: TextView = itemview.findViewById(R.id.tv_ngaygio_newsfeed)
        var tvBinhLuan : TextView = itemview.findViewById(R.id.tv_binh_luan)
        var tvChiaSe : TextView = itemview.findViewById(R.id.tv_chia_se)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNf {
        var layoutinflater : LayoutInflater = LayoutInflater.from(parent.context)
        var view = layoutinflater.inflate(R.layout.item_news_feed, parent, false)

        return ViewHolderNf(view)
    }

    override fun getItemCount(): Int {
        return listNf.size
    }

    override fun onBindViewHolder(holder: ViewHolderNf, position: Int) {
        var newsfeed : NewsFeed_Model = listNf[position]
        if (newsfeed == null) {
            return
        }

        val fontRegular = ResourcesCompat.getFont(context!!, R.font.jbmono_regular)!!
        val fontBold = ResourcesCompat.getFont(context!!, R.font.jbmono_bold)!!

        holder.tvNguoidangNews.setText(newsfeed.nguoidang)
        holder.tvTitleNews.setText(newsfeed.tieude)
        holder.tvContentNews.setText(newsfeed.noidung)
        holder.tvNgaygioNewsfeed.setText(newsfeed.ngaygio)

        holder.tvContentNews.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        holder.tvTitleNews.justificationMode = JUSTIFICATION_MODE_INTER_WORD

        holder.tvNguoidangNews.typeface = fontBold
        holder.tvTitleNews.typeface = fontBold
        holder.tvContentNews.typeface = fontRegular
        holder.tvNgaygioNewsfeed.typeface = fontRegular
        holder.tvLikeNew.typeface = fontRegular
        holder.tvBinhLuan.typeface = fontRegular
        holder.tvChiaSe.typeface = fontRegular

        newsfeed.liked = false

        holder.tvLikeNew.setOnClickListener {
            listener.OnLikeClick(newsfeed, holder.tvLikeNew)
        }

    }

}