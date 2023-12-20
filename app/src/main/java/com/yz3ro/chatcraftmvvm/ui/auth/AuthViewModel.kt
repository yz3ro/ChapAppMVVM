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
            // Otomatik doğrulama tamamlandığında burası çalışır
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            // Doğrulama başarısız olduğunda burası çalışır
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // Doğrulama kodu başarıyla kullanıcının telefonuna gönderildi.
            _verificationCodeSent.value = verificationId
        }
    }
    fun signInWithPhoneAuthCredential(verificationCode: String) {
        // Telefon doğrulama referansını al
        val credential = PhoneAuthProvider.getCredential(_verificationCodeSent.value!!, verificationCode)

        // FirebaseAuth ile giriş yap
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Kullanıcı başarıyla giriş yaptı
                    _isVerificationSuccessful.value = true
                } else {
                    // Giriş başarısız, hata işlemleri burada yapılabilir
                    _isVerificationSuccessful.value = false
                }
            }
    }
}
