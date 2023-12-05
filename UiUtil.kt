package com.example.projectsales.util

import android.content.Context
import android.widget.Toast

object UiUtil {

    fun showToast(context : Context, message : String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
object UiUtil2 {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}