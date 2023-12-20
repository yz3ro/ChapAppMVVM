package com.yz3ro.chatcraftmvvm.ui.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun startPhoneNumberVerification(
        phoneNumber: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Telefon numarasını ekleyin, uluslararası biçimde olmalıdır
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Zaman aşımı süresi
            .setCallbacks(callbacks) // Doğrulama durumu geri çağırma
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        onComplete: (Boolean) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }
}