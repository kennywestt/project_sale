package com.example.projectsales

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsales.databinding.ActivityReadBinding
import com.example.projectsales.model.PostItemModel

class ReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postItem = intent.getSerializableExtra("postItem") as PostItemModel

        // Use postItem data as needed
        binding.titleTextView.text = postItem.heading
        binding.nameTextView.text = postItem.userName
        binding.descriptionView.text = postItem.post
        binding.priceView.text = postItem.salePrice
        // Set other views as needed
    }
}