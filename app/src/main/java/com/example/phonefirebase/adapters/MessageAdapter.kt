package uz.mobiler.firebaseauthg18.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phonefirebase.databinding.MessegeItemBinding
import com.example.phonefirebase.databinding.MessegetoItemBinding

import uz.mobiler.firebaseauthg18.models.Message

class   MessageAdapter(var list: List<Message>, var currentUserUid: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class FromVh(var messegeItemBinding: MessegetoItemBinding) :
        RecyclerView.ViewHolder(messegeItemBinding.root) {

        fun onBind(message: Message) {
            messegeItemBinding.tv1.text = message.message
            messegeItemBinding.tv2?.text = message.date
        }
    }

    inner class ToVh(var itemToBinding: MessegeItemBinding) :
        RecyclerView.ViewHolder(itemToBinding.root) {

        fun onBind(message: Message) {
            itemToBinding.tv1.text = message.message
            itemToBinding.tv2.text = message.date
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
                MessegeItemBinding.inflate(
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