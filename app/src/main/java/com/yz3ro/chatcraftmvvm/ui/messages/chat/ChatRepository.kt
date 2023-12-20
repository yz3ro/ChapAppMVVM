package com.yz3ro.chatcraftmvvm.ui.messages.chat

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.yz3ro.chatcraftmvvm.model.Message
import java.util.Date

class ChatRepository {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun sendMessage(senderId: String, receiverId: String, text: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userUID = currentUser?.uid
        if (userUID != null) {
            val message = Message(senderId, receiverId, text, FieldValue.serverTimestamp())
            getChatReference(senderId, receiverId).collection("messages")
                .add(message)
                .addOnSuccessListener {
                    Log.d("FirestoreRepository", "sendMessage: Message sent successfully.")
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreRepository", "sendMessage: ${e.message}")
                }
        }
        setLastMessageAndTime(senderId, receiverId, text, Date())
    }

    fun listenForMessages(
        currentUserUid: String,
        senderId: String,
        receiverId: String,
        listener: (List<Message>) -> Unit
    ) {
        val chatId1 = "$senderId-$receiverId"
        val chatId2 = "$receiverId-$senderId"

        getChatReference(senderId, receiverId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot1, exception1 ->
                if (exception1 != null) {
                    Log.e("FirestoreRepository", "listenForMessages: ${exception1.message}")
                    return@addSnapshotListener
                }

                getChatReference(receiverId, senderId).collection("messages")
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener { snapshot2, exception2 ->
                        if (exception2 != null) {
                            Log.e("FirestoreRepository", "listenForMessages: ${exception2.message}")
                            return@addSnapshotListener
                        }

                        val messages = mutableListOf<Message>()

                        snapshot1?.documents?.forEach { document ->
                            val message = document.toObject(Message::class.java)
                            message?.let { messages.add(it) }
                        }

                        snapshot2?.documents?.forEach { document ->
                            val message = document.toObject(Message::class.java)
                            message?.let { messages.add(it) }
                        }

                        val filteredMessages = messages.filter {
                            (it.senderId == currentUserUid && it.receiverId == receiverId) ||
                                    (it.senderId == receiverId && it.receiverId == currentUserUid)
                        }

                        listener(filteredMessages)
                    }
            }
    }

    private fun getChatReference(senderId: String, receiverId: String): DocumentReference {
        val chatId = "$senderId-$receiverId"
        return firestore.collection("chats").document(chatId)
    }

    private fun setLastMessageAndTime(senderId: String, receiverId: String, lastMessage: String, lastMessageTime: Date) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userUID = currentUser?.uid
        if (userUID != null) {
            val chatId = "$senderId-$receiverId"

            val data = hashMapOf(
                "lastMessage" to lastMessage,
                "lastMessageTime" to lastMessageTime
            )

            getChatReference(senderId, receiverId)
                .set(data)
                .addOnSuccessListener {
                    Log.d("FirestoreRepository", "setLastMessageAndTime: Last message and time set successfully.")
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreRepository", "setLastMessageAndTime: ${e.message}")
                }
        }
    }
}