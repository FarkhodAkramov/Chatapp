package com.example.phonefirebase

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.phonefirebase.databinding.FragmentUserListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import uz.mobiler.firebaseauthg18.adapters.UserAdapter
import uz.mobiler.firebaseauthg18.models.User


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UserListFragment : Fragment() {


    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference

    //    val user = intent.getSerializableExtra("user") as User
    lateinit var firebaseAuth: FirebaseAuth

    private val TAG = "UserListFragment"

    lateinit var list: ArrayList<User>
    lateinit var userAdapter: UserAdapter
    lateinit var binding: FragmentUserListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        list = ArrayList()
        val currentUser = firebaseAuth.currentUser

        reference = firebaseDatabase.getReference("users")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(User::class.java)
                    if (value != null && value.uid != currentUser.uid) {

                        list.add(value)

                    }
                }
                userAdapter = UserAdapter(list, object : UserAdapter.OnItemClickListener {
                    override fun onItemClick(user: User) {
                        val bundle = Bundle()

                        bundle.putSerializable("key", user)
                        findNavController().navigate(R.id.chatingFragment, bundle)



                    }
                })
                binding.rv.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        return binding.root
    }


}