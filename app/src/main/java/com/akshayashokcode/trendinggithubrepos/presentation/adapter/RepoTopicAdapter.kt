package com.akshayashokcode.trendinggithubrepos.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akshayashokcode.trendinggithubrepos.databinding.TopicItemBinding

class RepoTopicAdapter : RecyclerView.Adapter<RepoTopicAdapter.TopicViewHolder>() {
    private val topicList = ArrayList<String>()

    fun setList(topics: List<String>) {
        topicList.clear()
        topicList.addAll(topics)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TopicViewHolder {
        val binding = TopicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topicList[position]
        holder.bind(topic)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    inner class TopicViewHolder(private val binding: TopicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: String) {
            binding.topic.text = topic
        }
    }

}