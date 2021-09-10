package com.example.phonefirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.phonefirebase.databinding.FragmentChatingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import uz.mobiler.firebaseauthg18.adapters.MessageAdapter
import uz.mobiler.firebaseauthg18.models.Message
import uz.mobiler.firebaseauthg18.models.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatingFragment : Fragment() {
    lateinit var binding: FragmentChatingBinding

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var list: ArrayList<Message>

    lateinit var messageAdapter: MessageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatingBinding.inflate(layoutInflater, container, false)
        list = ArrayList()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")
        firebaseAuth = FirebaseAuth.getInstance()


        // to User
//        val user = intent.getSerializableExtra("user") as User
        val user = arguments?.getSerializable("key") as User
        // from User
        val currentUser = firebaseAuth.currentUser

        Picasso.get().load(user.photoUrl).into(binding.image)
        binding.nameTv.setText(user.name)

        binding.sendBtn.setOnClickListener {
            if (binding.edit.text.toString().trim().isNotEmpty()) {
                val text = binding.edit.text.toString()
                val date = Date()
                val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
                val currentDate = simpleDateFormat.format(date)
                val message = Message(text, currentUser.uid, user.uid, currentDate)

                val key = reference.push().key
                reference.child("${currentUser.uid}/messages/${user.uid}/$key").setValue(message)
                reference.child("${user.uid}/messages/${currentUser.uid}/$key").setValue(message)
                binding.edit.text.clear()
            }
        }
        reference.child("${currentUser.uid}/messages/${user.uid}")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(Message::class.java)
                        if (value != null) {
                            list.add(value)
                        }
                    }
                    messageAdapter = MessageAdapter(list, currentUser.uid)
                    binding.chatRv.adapter = messageAdapter

                    binding.chatRv.smoothScrollToPosition(list.size)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        binding.nameTv
        binding.backbtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }


}