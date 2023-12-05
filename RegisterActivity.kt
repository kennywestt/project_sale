package com.example.projectsales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.projectsales.databinding.ActivityRegisterBinding
import com.example.projectsales.model.UserData
import com.example.projectsales.util.UiUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            register()
        }

        binding.goToLoginBtn.setOnClickListener {// 로그인 버튼 누르면 로그인 액티비티로
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

    fun register(){ //회원가입 함수
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()
        val name = binding.nameInput.text.toString()
        val birth =binding.birthdateInput.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.setError("이메일 오류")
            return
        }
        if(password.length>14){
            binding.passwordInput.setError("13자가 최대입니다.")
            return
        }
        if(password!=confirmPassword){
            binding.confirmPasswordInput.setError("비밀번호가 맞지 않습니다.")
            return
        }
        if(name.length>14){
            binding.nameInput.setError("13자가 최대입니다.")
            return
        }
        if(birth.length != 8 || !TextUtils.isDigitsOnly(birth)){
            binding.birthdateInput.setError("숫자만 입력해주세요")
            return
        }
        registerWithFirebase(email,password,name,birth)

    }

    fun registerWithFirebase(email : String, password : String, name : String, birth : String){ //회원가입 - 파이어베이스
        setInProgress(true) //버튼을 눌렀을 때 회원가입 진행 중 표시
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password
        ).addOnSuccessListener {
                it.user?.let {user->
                    val userData = UserData( user.uid, email, name, birth)
                    // Firestore에 사용자 데이터 저장
                    Firebase.firestore.collection("users")
                        .document(user.uid)
                        .set(userData).addOnSuccessListener {
                            UiUtil.showToast(applicationContext, "회원가입 완료")
                            setInProgress(false)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
            }
        }.addOnFailureListener { e ->
            // 회원가입 실패
            UiUtil.showToast(applicationContext, "회원가입 실패: ${e.message}")
            setInProgress(false)
            Log.e("Firestore", "Firestore 저장 실패", e)
            }
    }
}