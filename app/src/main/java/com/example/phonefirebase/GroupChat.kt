package com.example.phonefirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.phonefirebase.adapters.GroupMessageAdapter
import com.example.phonefirebase.databinding.FragmentGroupChatBinding
import com.example.phonefirebase.models.Group1
import com.example.phonefirebase.models.GroupMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.mobiler.firebaseauthg18.adapters.MessageAdapter
import uz.mobiler.firebaseauthg18.models.Message
import java.text.SimpleDateFormat
import java.util.*


class GroupChat : Fragment() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var list: ArrayList<GroupMessage>
    lateinit var groupMessageAdapter: GroupMessageAdapter
    lateinit var binding: FragmentGroupChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupChatBinding.inflate(layoutInflater, container, false)
        list = ArrayList()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("groups")
        var currentUser = firebaseAuth.currentUser
        var group = arguments?.getSerializable("key") as Group1
        binding.nameTv.text = group.name

        binding.sendBtn.setOnClickListener {
            if (binding.edit.text.toString().trim().isNotEmpty()) {
                val text = binding.edit.text.toString()
                val date = Date()


                val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
                val currentDate = simpleDateFormat.format(date)
                val message = GroupMessage(
                    text, currentUser?.uid, currentDate, false, "key"
                )
                val key = reference.push().key
                message.key = key

                reference.child("/${group.name}/messages/${message?.key}").setValue(message)
                binding.edit.text.clear()
            }
        }

        reference.child("/${group.name}/messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                        val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(GroupMessage::class.java)
                        if (value != null) {
                            list.add(value)
                        }
                    }
                    groupMessageAdapter = GroupMessageAdapter(list, currentUser.uid)
                    binding.chatRv.adapter = groupMessageAdapter

                    binding.chatRv.smoothScrollToPosition(list.size)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        binding.backbtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


}