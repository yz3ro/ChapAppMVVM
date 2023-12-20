package com.yz3ro.chatcraftmvvm.ui.Contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.yz3ro.chatcraftmvvm.model.Users

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel()  {
    private val _userList = MutableLiveData<List<Users>>()
    val userList: LiveData<List<Users>> get() = _userList


    fun addContact(userName: String, number: String) {
        repository.addContact(userName, number)
    }

    fun fetchUserList() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            repository.listenToUserList(user.uid) { userList ->
                _userList.postValue(userList)
            }
        }
    }
}