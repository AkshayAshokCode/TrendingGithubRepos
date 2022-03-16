package com.akshayashokcode.trendinggithubrepos.presentation.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akshayashokcode.trendinggithubrepos.data.model.Item
import com.akshayashokcode.trendinggithubrepos.databinding.GithubRepoItemBinding
import com.bumptech.glide.Glide

class GitHubRepoAdapter : RecyclerView.Adapter<GitHubRepoAdapter.GitHubViewHolder>() {

    private val itemList = ArrayList<Item>()
    private var selectedItem = -1

   /* fun setSearchedList(items: List<Item>) {
        //on adding it, old data is replaced when scrolled
        // if removed, searched items gets added below it
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }*/
    fun setList(items: List<Item>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }
  /*  fun clearList(){
        itemList.clear()
    }
*/

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
            holder.binding.cardView.setCardBackgroundColor(Color.GREEN)
        } else {
            holder.binding.cardView.setCardBackgroundColor(Color.WHITE)
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
                itemTime.text = item.createdAt
                itemLanguage.text = item.language
            }
            Glide.with(binding.itemProfileImg.context)
                .load(item.owner.avatarUrl)
                .into(binding.itemProfileImg)


        }
    }
}