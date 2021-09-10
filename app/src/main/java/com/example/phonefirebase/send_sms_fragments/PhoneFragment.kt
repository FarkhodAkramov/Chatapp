package com.example.phonefirebase.send_sms_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.phonefirebase.R
import com.example.phonefirebase.databinding.FragmentPhoneBinding


class PhoneFragment : Fragment() {


    lateinit var binding: FragmentPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneBinding.inflate(layoutInflater, container, false)


        binding.enter.setOnClickListener {
            val bundle = Bundle()
            var number = binding.numberEt.text.toString()
            bundle.putString("key", number)


            findNavController().navigate(R.id.action_phoneFragment_to_smsFragment, bundle)
        }

        return binding.root
    }


}