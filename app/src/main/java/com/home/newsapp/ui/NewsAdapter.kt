package com.home.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.home.newsapp.data.News
import com.home.newsapp.databinding.RecyclerViewItemNewsBinding

class NewsAdapter(private val listener: OnItemClickListener) : ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            RecyclerViewItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class NewsViewHolder(private val binding: RecyclerViewItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val news = getItem(position)
                        listener.onItemClick(news)
                    }
                }
            }
        }

        fun bind(news: News) {
            binding.apply {
                Glide.with(itemView)
                    .load(news.imageUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(recyclerViewImage)

                recyclerViewTitle.text = news.title
                recyclerViewNewsSite.text = news.newsSite
                recyclerViewNewsDate.text = news.formattedDate

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(news: News)
    }

    class NewsComparator : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
    }
}