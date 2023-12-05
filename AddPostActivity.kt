package com.example.projectsales

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsales.databinding.ActivityAddPostBinding
import com.example.projectsales.model.PostItemModel
import com.example.projectsales.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadPostButton.setOnClickListener {
            uploadPostToFirestore()
        }
    }

    private fun uploadPostToFirestore() {
        val postTitle = binding.postTitle.text.toString()
        val postDescription = binding.postDescription.text.toString()
        val postPrice = binding.postPrice.text.toString()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            // Get user data from Firestore
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userData = document.toObject(UserData::class.java)
                        if (userData != null) {
                            val userName = userData.name

                            // Create PostItemModel
                            val postItem = PostItemModel(
                                heading = postTitle,
                                userName = userName,
                                post = postDescription,
                                salePrice = postPrice
                                // Add other fields as needed...
                            )

                            // Upload to Firestore
                            db.collection("posts")
                                .add(postItem)
                                .addOnSuccessListener {
                                    // Post added successfully
                                    // You can perform additional actions if needed

                                    // Go back to HomeFragment
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("fragmentToLoad", "homeFragment")
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener {
                                    // Handle errors
                                }
                        }
                    }
                }
                .addOnFailureListener {
                    // Handle errors
                }
        }
    }
}