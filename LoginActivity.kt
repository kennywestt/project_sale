package com.example.projectsales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.projectsales.databinding.ActivityLoginBinding
import com.example.projectsales.util.UiUtil
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            login()
        }

        binding.goToRegisterBtn.setOnClickListener {// 로그인 버튼 누르면 로그인 액티비티로
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }

    fun setInProgress(inProgress : Boolean){ //Progress 함수
        if(inProgress){
            binding.progressBar.visibility = View.VISIBLE
            binding.submitBtn.visibility = View.GONE
        }else {
            binding.progressBar.visibility = View.GONE
            binding.submitBtn.visibility = View.VISIBLE
        }
    }

    fun login(){
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.setError("이메일 오류")
            return
        }
        if(password.length>14){
            binding.passwordInput.setError("13자가 최대입니다.")
            return
        }
        loginWithFirebase(email,password)
    }

    fun loginWithFirebase(email :String, password : String){
        setInProgress(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email,
            password
        ).addOnSuccessListener {
            UiUtil.showToast(this, "로그인 완료")
            setInProgress(false)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener { e ->
            UiUtil.showToast(applicationContext, "로그인 실패: ${e.message}")
            setInProgress(false)
            Log.e("Firestore", "Firestore 실패", e)
        }
    }
}