package com.akshayashokcode.trendinggithubrepos.presentation.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.akshayashokcode.trendinggithubrepos.data.model.Item
import com.akshayashokcode.trendinggithubrepos.databinding.GithubRepoItemBinding
import com.akshayashokcode.trendinggithubrepos.util.AppUtils
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
            holder.binding.cardView.setCardBackgroundColor(Color.parseColor("#0096FF"))
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
              //  itemLanguage.text = item.language
                itemTime.text = AppUtils().getDate(item.createdAt)
                starCount.text=item.stargazersCount.toString()
                forkCount.text= item.forksCount.toString()

                Glide.with(itemProfileImg.context)
                    .load(item.owner.avatarUrl)
                    .into(itemProfileImg)

                if(item.language!=null){
                    imgLanguage.visibility=View.VISIBLE
                    itemLanguage.visibility=View.VISIBLE
                    itemLanguage.text = item.language

                }else{
                    imgLanguage.visibility=View.GONE
                    itemLanguage.visibility=View.GONE
                }

            }



        }
    }
}