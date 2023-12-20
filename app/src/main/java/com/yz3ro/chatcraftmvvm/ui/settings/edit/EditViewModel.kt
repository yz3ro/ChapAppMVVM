package com.yz3ro.chatcraftmvvm.ui.settings.edit

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yz3ro.chatcraftmvvm.model.Users
import kotlinx.coroutines.launch

class EditViewModel(private val repository: EditRepository) : ViewModel() {

    fun updateUserDisplayName(yeniAd: String) {
        viewModelScope.launch {
            repository.updateUserDisplayName(yeniAd)
        }
    }

    fun updateFirestoreDisplayName(yeniAd: String) {
        viewModelScope.launch {
            repository.updateFirestoreDisplayName(yeniAd)
        }
    }


    fun getUserProfile(): MutableLiveData<Users?> {
        return repository.getUserProfile()
    }


    fun uploadImage(ProfilePhotoURL: Uri) {
        viewModelScope.launch {
            repository.uploadImage(ProfilePhotoURL)
        }
    }
}