package com.yz3ro.chatcraftmvvm.ui.Contacts

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yz3ro.chatcraftmvvm.model.Users
import com.yz3ro.chatcraftmvvm.utils.utils

class ContactsRepository(var context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val currentUser = auth.currentUser
    fun addContact(userName: String, number: String) {

        if (currentUser != null) {

            db.collection("kullanicilar")
                .whereEqualTo("numara", number)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        utils.logError("ContactMessage", "Bu numara ile eşleşen kullanıcı bulunamadı. Numara: $number")
                    } else {
                        for (document in documents) {
                            val contactUid = document.getString("uid")
                            if (contactUid != null) {
                                val contact = hashMapOf(
                                    "username" to userName,
                                    "PhoneNumber" to number,
                                    "uid" to contactUid
                                )

                                db.collection("kullanicilar")
                                    .document(currentUser.uid)
                                    .collection("kisiler")
                                    .add(contact)
                                    .addOnSuccessListener {
                                        utils.logError("ContactMessage", "Firestore ekleme başarılı.")
                                    }
                                    .addOnFailureListener {
                                        utils.logError("ContactMessage", "Firestore ekleme başarısız.")
                                    }
                            } else {
                                utils.logError("ContactMessage", "Kişi UID'si alınamadı.")
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    utils.logError("ContactMessage", "Kişi aranırken hata oluştu: ${e.message}")
                }
        } else {
            utils.logError("ContactMessage", "Kullanıcı oturumu açık değil veya hata oluştu.")
        }
    }
    fun listenToUserList(userId: String, onComplete: (List<Users>) -> Unit) {
        db.collection("kullanicilar").document(userId).collection("kisiler")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("firestore error", error.message.toString())
                    return@addSnapshotListener
                }

                val userList = value?.documents?.mapNotNull {
                    it.toObject(Users::class.java)
                } ?: emptyList()

                onComplete(userList)
            }
    }
}
