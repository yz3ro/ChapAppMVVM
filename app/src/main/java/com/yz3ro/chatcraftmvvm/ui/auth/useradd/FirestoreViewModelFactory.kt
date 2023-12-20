package com.yz3ro.chatcraftmvvm.ui.auth.useradd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FirestoreViewModelFactory(private val firestoreRepository: FirestoreRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirestoreViewModel::class.java)) {
            return FirestoreViewModel(firestoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
