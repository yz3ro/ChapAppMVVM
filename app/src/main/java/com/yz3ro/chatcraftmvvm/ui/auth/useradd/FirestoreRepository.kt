package com.yz3ro.chatcraftmvvm.ui.auth.useradd

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun addUser(userName: String, phoneNumber: String, uid: String) {
        val users = hashMapOf(
            "uid" to uid,
            "numara" to phoneNumber,
            "ad" to userName,
        )

        firestore.collection("kullanicilar")
            .document(uid)
            .set(users)
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->

                Log.e("Firestore Hata", "Veri yazma hatası: ${e.message}")
            }
    }
}

