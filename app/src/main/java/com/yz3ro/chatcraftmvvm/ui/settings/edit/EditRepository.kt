package com.yz3ro.chatcraftmvvm.ui.settings.edit

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.yz3ro.chatcraftmvvm.model.Users
import kotlinx.coroutines.tasks.await

class EditRepository {
    private val userProfileLiveData = MutableLiveData<Users?>()
    private val storageReference = FirebaseStorage.getInstance().reference
    private val db = FirebaseFirestore.getInstance()

    suspend fun updateUserDisplayName(yeniAd: String) {
        val kullanici = FirebaseAuth.getInstance().currentUser
        kullanici?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setDisplayName(yeniAd)
                .build()
        )?.await()
    }

    fun getUserProfile(): MutableLiveData<Users?> {

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userDocRef = FirebaseFirestore.getInstance().collection("kullanicilar").document(userId ?: "")
        userDocRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                // Hata durumu
                return@addSnapshotListener
            }

            // Firestore'dan çekilen belgeyi UserProfile nesnesine dönüştür
            val userProfile = snapshot?.toObject(Users::class.java)

            // LiveData'ya güncellenmiş UserProfile'ı gönder
            userProfileLiveData.value = userProfile
        }

        return userProfileLiveData
    }

    suspend fun updateFirestoreDisplayName(yeniAd: String) {
        val kullanici = FirebaseAuth.getInstance().currentUser
        kullanici?.uid?.let { uid ->
            val userDocRef = db.collection("kullanicilar").document(uid)
            userDocRef.update("ad", yeniAd).await()
        }
    }

    suspend fun updateImageUrlInFirestore(ProfilePhotoURL: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.uid?.let { uid ->
            val db = FirebaseFirestore.getInstance()
            val userDocRef = db.collection("kullanicilar").document(uid)

            val updatedData = hashMapOf(
                "ProfilePhotoURL" to ProfilePhotoURL
            )

            userDocRef.update(updatedData as Map<String, Any>).await()
        }
    }

    suspend fun uploadImage(selectedImageUri: Uri) {
        val storageReference = FirebaseStorage.getInstance().reference
        val user = FirebaseAuth.getInstance().currentUser
        user?.uid?.let { uid ->
            val imagePath = "profilImages/$uid.jpg"
            val imageRef = storageReference.child(imagePath)

            try {
                // Resmi Firebase Storage'a yükle
                imageRef.putFile(selectedImageUri).await()

                // Yükleme başarılı olduysa Firestore'dan profil fotoğrafının URL'sini al
                val imageUrl = imageRef.downloadUrl.await().toString()

                // Firestore'daki kullanıcının profil fotoğrafını güncelle
                updateImageUrlInFirestore(imageUrl)
            } catch (e: Exception) {
                // Hata durumu
                throw e
            }
        }
    }
}