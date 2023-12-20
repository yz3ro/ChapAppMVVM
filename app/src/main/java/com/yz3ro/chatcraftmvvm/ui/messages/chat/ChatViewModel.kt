package com.yz3ro.chatcraftmvvm.ui.messages.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yz3ro.chatcraftmvvm.model.Message

class ChatViewModel(private val repository: ChatRepository) : ViewModel()  {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    fun sendMessage(receiverId: String, text: String) {
        val senderId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        repository.sendMessage(senderId, receiverId, text)
    }
    fun listenForMessages(receiverId: String) {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        repository.listenForMessages(currentUserUid, currentUserUid, receiverId) { messages ->
            _messages.postValue(messages)
        }
    }
}