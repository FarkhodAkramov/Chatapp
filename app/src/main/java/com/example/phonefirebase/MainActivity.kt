package com.example.phonefirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStop() {
        FirebaseAuth.getInstance()!!.uid?.let {
            FirebaseDatabase.getInstance().getReference("online")
                .child(it).setValue(false)
        }
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance()!!.uid?.let {
            FirebaseDatabase.getInstance().getReference("online")
                .child(it).setValue(true)
        }
    }
}