package com.example.projectsales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectsales.databinding.ActivityMainBinding
import com.example.projectsales.presentation.FragmentChat
import com.example.projectsales.presentation.FragmentHome
import com.example.projectsales.presentation.FragmentMyPage
import com.example.projectsales.util.UiUtil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(FragmentHome())

        binding.bottomNavBar.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId) {
                R.id.bottom_menu_home->{
                    replaceFragment(FragmentHome())
                    UiUtil.UiUtil2.showToast(this,"Home")
                }
                R.id.bottom_menu_chat->{
                    replaceFragment(FragmentChat())
                    UiUtil.UiUtil2.showToast(this,"chat")
                }
                R.id.bottom_menu_mypage->{
                    replaceFragment(FragmentMyPage())
                    UiUtil.UiUtil2.showToast(this,"mypage")


                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()

    }
}