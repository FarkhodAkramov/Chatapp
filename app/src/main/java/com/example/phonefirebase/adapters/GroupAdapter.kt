package com.example.phonefirebase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phonefirebase.databinding.ItemBinding
import com.example.phonefirebase.models.Group1

class GroupAdapter(var list: ArrayList<Group1>, var itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<GroupAdapter.Vh>() {

    inner class Vh(var itemBinding: ItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(group: Group1) {
            itemBinding.groupNameTv.text = group.name

            itemBinding.root.setOnClickListener{
                itemClickListener.onItemClick(group)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupAdapter.Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(group1: Group1)
    }


}