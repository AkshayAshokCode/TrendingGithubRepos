package com.akshayashokcode.trendinggithubrepos.presentation.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akshayashokcode.trendinggithubrepos.data.model.Item
import com.akshayashokcode.trendinggithubrepos.databinding.GithubRepoItemBinding
import com.akshayashokcode.trendinggithubrepos.util.AppUtils
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import javax.inject.Inject


class GitHubRepoAdapter @Inject constructor(private var repoTopicAdapter: RepoTopicAdapter) :
    RecyclerView.Adapter<GitHubRepoAdapter.GitHubViewHolder>() {

    private val itemList = ArrayList<Item>()
    private var tempItemList = ArrayList<Item>()
    private var selectedItem = -1
    private var isSearched = false
    private val viewPool = RecyclerView.RecycledViewPool()

    fun setSearchedList(items: List<Item>) {
        if (!isSearched && itemList.isNotEmpty()) {
            tempItemList.clear()
            tempItemList = itemList
            itemList.clear()
            isSearched = true
        }
        itemList.addAll(items)
        Log.d("MainActivity", "ItemList ${itemList.size}")
        notifyDataSetChanged()
    }

    fun setList(items: List<Item>) {
        if (isSearched && tempItemList.isNotEmpty()) {
            itemList.clear()
            itemList.addAll(tempItemList)
            tempItemList.clear()
            isSearched = false
        } else {
            itemList.addAll(items)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GitHubViewHolder {
        val binding =
            GithubRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GitHubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitHubViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            selectedItem = item.id
            notifyDataSetChanged()
        }

        if (selectedItem == item.id) {
            holder.binding.cardView.setCardBackgroundColor(Color.parseColor("#6CBB3C"))
        } else {
            holder.binding.cardView.setCardBackgroundColor(Color.WHITE)
        }

        if (item.topics != null) {
            holder.binding.apply {
                val childLayoutManager = FlexboxLayoutManager(recyclerViewTopics.context)
                recyclerViewTopics.layoutManager = childLayoutManager

                recyclerViewTopics.apply {
                    layoutManager = childLayoutManager
                    adapter = repoTopicAdapter
                    setRecycledViewPool(viewPool)
                    repoTopicAdapter.setList(item.topics)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class GitHubViewHolder(val binding: GithubRepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                itemTitle.text = item.fullName
                itemDesc.text = item.description
                itemTime.text = AppUtils().getDate(item.createdAt)
                starCount.text = item.stargazersCount.toString()
                forkCount.text = item.forksCount.toString()

                Glide.with(itemProfileImg.context)
                    .load(item.owner.avatarUrl)
                    .into(itemProfileImg)

                if (item.language != null) {
                    imgLanguage.visibility = View.VISIBLE
                    itemLanguage.visibility = View.VISIBLE
                    itemLanguage.text = item.language

                } else {
                    imgLanguage.visibility = View.GONE
                    itemLanguage.visibility = View.GONE
                }
            }
        }
    }
}