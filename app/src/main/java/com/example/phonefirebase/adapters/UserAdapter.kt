package uz.mobiler.firebaseauthg18.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phonefirebase.databinding.UserItemBinding
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import uz.mobiler.firebaseauthg18.models.User

class UserAdapter(var list: List<User>, var itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var itemUserBinding: UserItemBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {
        lateinit var reference: DatabaseReference
        lateinit var firebaseDatabase: FirebaseDatabase
        fun onBind(user: User) {
            Picasso.get().load(user.photoUrl).into(itemUserBinding.image)
            itemUserBinding.textView4.text = user.name

            itemView.setOnClickListener {
                itemClickListener.onItemClick(user)
            }
            firebaseDatabase = FirebaseDatabase.getInstance()
            reference = firebaseDatabase.getReference("online")
            reference.child(user.uid!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue(Boolean::class.java)
                    if (value!!) {
                        itemUserBinding.tv2.text = "Online"

                    } else {
                        itemUserBinding.tv2.text = "Last seen recently"

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }
}