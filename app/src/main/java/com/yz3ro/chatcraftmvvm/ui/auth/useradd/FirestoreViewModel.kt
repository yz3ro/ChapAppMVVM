package com.yz3ro.chatcraftmvvm.ui.auth.useradd

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class FirestoreViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun addUserToFirestore(name: String?) {
        val currentUser = auth.currentUser
        if (currentUser != null && name != null) {
            val phoneNumber = currentUser.phoneNumber
            val uid = currentUser.uid


            if (phoneNumber != null) {
                firestoreRepository.addUser(name, phoneNumber, uid)
            }
        }
    }
}

