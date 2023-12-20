package com.yz3ro.chatcraftmvvm.ui.settings.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditFactory (private val repository: EditRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}