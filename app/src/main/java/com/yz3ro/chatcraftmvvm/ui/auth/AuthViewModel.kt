package com.yz3ro.chatcraftmvvm.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _verificationCodeSent = MutableLiveData<String>()
    val verificationCodeSent: LiveData<String>
        get() = _verificationCodeSent

    private val _isVerificationSuccessful = MutableLiveData<Boolean>()
    val isVerificationSuccessful: LiveData<Boolean>
        get() = _isVerificationSuccessful

    fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
            .setCallbacks(verificationCallbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val verificationCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(p0: FirebaseException) {

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {

            _verificationCodeSent.value = verificationId
        }
    }
    fun signInWithPhoneAuthCredential(verificationCode: String) {

        val credential = PhoneAuthProvider.getCredential(_verificationCodeSent.value!!, verificationCode)


        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    _isVerificationSuccessful.value = true
                } else {

                    _isVerificationSuccessful.value = false
                }
            }
    }
}
