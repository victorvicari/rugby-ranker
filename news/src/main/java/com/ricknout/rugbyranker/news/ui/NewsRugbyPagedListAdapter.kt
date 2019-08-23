package com.ricknout.rugbyranker.news.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ricknout.rugbyranker.news.R
import com.ricknout.rugbyranker.news.vo.WorldRugbyNews

class NewsRugbyPagedListAdapter(
        private val newsList: List<WorldRugbyNews>
) : RecyclerView.Adapter<WorldRugbyNewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorldRugbyNewsViewHolder =
        WorldRugbyNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_world_rugby_news, parent, false))

    override fun onBindViewHolder(holder: WorldRugbyNewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WorldRugbyNews>() {
            override fun areItemsTheSame(oldItem: WorldRugbyNews, newItem: WorldRugbyNews) = oldItem.newsId == newItem.newsId
            override fun areContentsTheSame(oldItem: WorldRugbyNews, newItem: WorldRugbyNews) = oldItem == newItem
        }
    }
}