package com.example.projectsales.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectsales.AddPostActivity
import com.example.projectsales.ReadActivity
import com.example.projectsales.adapter.PostAdapter
import com.example.projectsales.databinding.FragmentHomeBinding

import com.example.projectsales.model.PostItemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val db = FirebaseFirestore.getInstance()
    private val postItems: MutableList<PostItemModel> = mutableListOf()
    private lateinit var postItemAdapter: PostAdapter
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize RecyclerView
        postItemAdapter = PostAdapter(postItems) { position ->
            val postItem = postItems[position]
            val intent = Intent(requireContext(), ReadActivity::class.java)
            intent.putExtra("postItem", postItem)
            startActivity(intent)
        }

        binding.postRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.postRecyclerView.adapter = postItemAdapter

        // Load data from Firestore
        loadPostItems()

        // Handle addPost button click
        binding.addPost.setOnClickListener {
            startActivity(
                Intent(requireContext(), AddPostActivity::class.java)
            )
        }

        return binding.root
    }

    private fun loadPostItems() {
        db.collection("posts")
            .get()
            .addOnSuccessListener { result ->
                postItems.clear()
                for (document in result) {
                    val postItem = document.toObject(PostItemModel::class.java)
                    postItems.add(postItem)
                }
                postItemAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }

    companion object {
        const val READ_POST_REQUEST_CODE = 1
        const val ADD_POST_REQUEST_CODE = 2
    }
}