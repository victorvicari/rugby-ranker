package com.ricknout.rugbyranker.news.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.ricknout.rugbyranker.news.vo.WorldRugbyNews
import kotlinx.android.synthetic.main.list_item_world_rugby_news.view.*

class WorldRugbyNewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(news: WorldRugbyNews) {
        news.newsImageUrl.let { itemView.newsImageView.load(it) }
        itemView.newsTitleTextView.text = news.newsTitle
    }

}