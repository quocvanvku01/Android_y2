package com.example.vku_decuong_2.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
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

        holder.tvNguoidangNews.setText(newsfeed.nguoidang)
        holder.tvTitleNews.setText(newsfeed.tieude)
        holder.tvContentNews.setText(newsfeed.noidung)

        newsfeed.liked = false

        holder.tvLikeNew.setOnClickListener {
            listener.OnLikeClick(newsfeed, holder.tvLikeNew)
        }

    }

}