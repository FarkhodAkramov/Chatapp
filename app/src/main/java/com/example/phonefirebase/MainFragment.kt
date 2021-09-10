package com.example.phonefirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.phonefirebase.databinding.FragmentMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MainFragment : Fragment() {
    lateinit var pagerAdapter: com.example.phonefirebase.adapters.PagerAdapter
    lateinit var binding: FragmentMainBinding
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var list: ArrayList<Fragment>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        firebaseAuth = FirebaseAuth.getInstance()
        list = ArrayList()
        list.add(UserListFragment())
        list.add(GroupMassageFragment())
        pagerAdapter = com.example.phonefirebase.adapters.PagerAdapter(list, childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = pagerAdapter

        binding.imageView3.setOnClickListener {
            googleSignInClient.signOut()
            FirebaseAuth.getInstance()!!.uid?.let {
                FirebaseDatabase.getInstance().getReference("online")
                    .child(it).setValue(false)
            }
            findNavController().navigate(R.id.action_mainFragment_to_signFragment2)
        }
        return binding.root
    }


}