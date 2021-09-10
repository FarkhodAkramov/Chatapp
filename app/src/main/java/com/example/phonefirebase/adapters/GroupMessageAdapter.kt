package com.example.phonefirebase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phonefirebase.databinding.MessegeItemBinding
import com.example.phonefirebase.databinding.MessegeItemGroupBinding
import com.example.phonefirebase.databinding.MessegetoItemBinding
import com.example.phonefirebase.models.GroupMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import uz.mobiler.firebaseauthg18.models.User

class GroupMessageAdapter(var list: List<GroupMessage>, var currentUserUid: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class FromVh(var messegetoItemBinding: MessegetoItemBinding) :
        RecyclerView.ViewHolder(messegetoItemBinding.root) {
        fun onBind(message: GroupMessage) {
            messegetoItemBinding.tv1.text = message.message
            messegetoItemBinding.tv2.text = message.time
        }
    }

    inner class ToVh(var messegeItemBinding: MessegeItemGroupBinding) :
        RecyclerView.ViewHolder(messegeItemBinding.root) {

        fun onBind(message: GroupMessage) {
            messegeItemBinding.tv1.text = message.message
            messegeItemBinding.tv2.text = message.time
            var firebaseDatabase = FirebaseDatabase.getInstance()
            val reference = firebaseDatabase.getReference("users")
            var user: User? = null

            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(User::class.java)
                        if (value != null && value.uid == message?.fromUid) {
                            user = value
                            Picasso
                                .get()
                                .load(user?.photoUrl)
                                .into(messegeItemBinding.image)
                            messegeItemBinding.textView5.text = user?.name
                            break
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return FromVh(
                MessegetoItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ToVh(
                MessegeItemGroupBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            val fromVh = holder as FromVh
            fromVh.onBind(list[position])
        } else {
            val toVh = holder as ToVh
            toVh.onBind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].fromUid == currentUserUid) {
            return 1
        }
        return 2
    }

    override fun getItemCount(): Int {
        return list.size
    }
}