package com.example.phonefirebase

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.phonefirebase.adapters.GroupAdapter
import com.example.phonefirebase.databinding.DialogBinding
import com.example.phonefirebase.databinding.FragmentGoupMassageBinding
import com.example.phonefirebase.models.Group1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.security.acl.Group


class GroupMassageFragment : Fragment() {

    lateinit var binding: FragmentGoupMassageBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var groups: ArrayList<Group1>
    lateinit var groupAdapter: GroupAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoupMassageBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("groups")

        groups = ArrayList()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                groups.clear()
                var children = snapshot.children
                for (child in children) {
                    val value = child.getValue(Group1::class.java)
                    groups.add(value!!)
                }

                groupAdapter = GroupAdapter(groups, object : GroupAdapter.OnItemClickListener {

                    override fun onItemClick(group1: Group1) {
                        val bundle = Bundle()
                        bundle.putSerializable("key", group1)
                        findNavController().navigate(R.id.groupChat, bundle)

                    }
                })
                binding.rv.adapter = groupAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        binding.addbtn.setOnClickListener {
            var dialogBuilder = AlertDialog.Builder(requireContext())
            var dialog = dialogBuilder.create()
            var bindingDialog =
                DialogBinding.inflate(LayoutInflater.from(requireContext()), binding.root, false)
            bindingDialog.save.setOnClickListener {
                var groupName = bindingDialog.et1.text.toString()
                var groupDescription = bindingDialog.et2.text.toString()
                var group = Group1(groupName, groupDescription)

                reference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var children = snapshot.children
                        var b = false

                        for (child in children) {
                            var value = child.getValue(Group1::class.java)
                            if (value != null && value?.name == group?.name) {
                                b = true
                                break
                            }
                        }

                        if (!b) {
                            reference.child(group?.name!!).setValue(group)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

                dialog.dismiss()
            }

            dialog.setView(bindingDialog.root)
            dialog.show()
        }

        return binding.root
    }


}