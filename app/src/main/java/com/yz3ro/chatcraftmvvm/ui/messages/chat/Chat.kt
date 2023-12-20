package com.yz3ro.chatcraftmvvm.ui.messages.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yz3ro.chatcraftmvvm.adapters.MessageAdapter
import com.yz3ro.chatcraftmvvm.databinding.FragmentChatBinding
import com.yz3ro.chatcraftmvvm.ui.Contacts.ContactsFactory
import com.yz3ro.chatcraftmvvm.ui.Contacts.ContactsRepository
import com.yz3ro.chatcraftmvvm.ui.Contacts.ContactsViewModel

class Chat : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var binding: FragmentChatBinding
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this, ChatViewModelFactory(ChatRepository())).get(
            ChatViewModel::class.java)
        val receiverId = arguments?.getString("ReceiverId") ?: ""

        messageAdapter = MessageAdapter(emptyList())
        binding.recChat.adapter = messageAdapter
        binding.recChat.layoutManager = LinearLayoutManager(requireContext())

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            messageAdapter.messages = messages
            messageAdapter.notifyDataSetChanged()
            binding.recChat.scrollToPosition(messages.size - 1)
        }

        viewModel.listenForMessages(receiverId)

        binding.btnMsg.setOnClickListener {
            val text = binding.etMsg.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.sendMessage(receiverId, text)
                binding.etMsg.text.clear()
            }
        }
    }
}
