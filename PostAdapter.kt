package com.example.projectsales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsales.databinding.RegisterItemBinding
import com.example.projectsales.model.PostItemModel


class PostAdapter(private val postItems: List<PostItemModel>, private val onItemClickListener: (position: Int) -> Unit) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(private val binding: RegisterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postItem: PostItemModel) {
            binding.heading.text = postItem.heading
            binding.userName.text = postItem.userName
            binding.post.text = postItem.post
            binding.salePrice.text = postItem.salePrice

            // 아이템 클릭 처리
            itemView.setOnClickListener {
                onItemClickListener.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = RegisterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postItems[position])
    }

    override fun getItemCount(): Int {
        return postItems.size
    }
}