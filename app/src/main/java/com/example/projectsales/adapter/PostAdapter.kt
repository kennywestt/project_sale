package com.example.projectsales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsales.databinding.RegisterItemBinding
import com.example.projectsales.model.PostItemModel
import com.bumptech.glide.Glide

class PostAdapter (private val items:List<PostItemModel>):
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val binding : RegisterItemBinding = RegisterItemBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class PostViewHolder(private val binding: RegisterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postItemModel: PostItemModel) {
            binding.heading.text = postItemModel.heading
            binding.date.text = postItemModel.date
            binding.inputDetail.text = postItemModel.inputDetail
            binding.salePrice.text = postItemModel.salePrice
            Glide.with(binding.postPhoto.context)
                .load(postItemModel.postPhoto)
                .into(binding.postPhoto)
            binding.saleExist.text = postItemModel.saleExist

        }

    }
}