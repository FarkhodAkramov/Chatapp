package com.example.phonefirebase

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.phonefirebase.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed({
           if (firebaseAuth.currentUser==null){
                              findNavController().navigate(R.id.action_splashFragment_to_signFragment)

           }
            else {
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
//               firebaseAuth.currentUser.
            }
        },3000)

        return binding.root
    }


}