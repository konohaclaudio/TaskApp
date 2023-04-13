package com.example.taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }


//    fun login (){
//        val retrofitClient = NetworUtils.getRetrofitInstance("")
//        val endpoint = retrofitClient.create(Endpoint::class.java)

//        val callback = endpoint.authentication()
//
//        callback.enqueue(object : Callback<User> {
//            override fun onResponse (call)
//        }
    }
//}