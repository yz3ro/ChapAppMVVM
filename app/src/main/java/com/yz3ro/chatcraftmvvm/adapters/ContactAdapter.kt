package com.yz3ro.chatcraftmvvm.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yz3ro.chatcraftmvvm.R
import com.yz3ro.chatcraftmvvm.databinding.ItemContactsBinding
import com.yz3ro.chatcraftmvvm.model.Users


class ContactAdapter(private val userList: List<Users>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(private val binding: ItemContactsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Users) {
            binding.apply {
                binding.textViewKisiAdi.text = user.username.toString()
                binding.textViewTelefon.text = user.PhoneNumber.toString()
            }

        }

        val click = binding.LayoutKisi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val user: Users = userList[position]
        holder.bind(user)

        holder.click.setOnClickListener {
            val bundle = bundleOf("ReceiverId" to user.uid)
            holder.itemView.findNavController().navigate(R.id.chat,bundle)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
