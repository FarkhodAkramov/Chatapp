package com.example.phonefirebase.send_sms_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phonefirebase.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {


    lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        val id = arguments?.getString("key")
        binding.numberTv.text = "$id"
        return binding.root
    }


}