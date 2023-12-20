package com.yz3ro.chatcraftmvvm.ui.settings.edit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yz3ro.chatcraftmvvm.databinding.FragmentEditProfileBinding
import com.yz3ro.chatcraftmvvm.model.Users

class EditProfile : Fragment() {
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var viewModel: EditViewModel
    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // EditRepository ve EditViewModel'ı başlat
        val repository = EditRepository()
        viewModel = ViewModelProvider(this, EditFactory(repository)).get(EditViewModel::class.java)

        // EditViewModel'dan gelen kullanıcı verilerini gözlemle
        viewModel.getUserProfile().observe(viewLifecycleOwner) { users ->
            users?.let {
                if (!it.ProfilePhotoURL.isNullOrEmpty()) {
                    // Firestore'dan çekilen belgede profilFotoURL varsa,
                    // bu URL'yi kullanarak ImageView içine fotoğrafı yükle
                    Glide.with(requireContext())
                        .load(it.ProfilePhotoURL)
                        .into(binding.EditProfilePp)
                } else {
                    // Firestore'dan çekilen belgede profilFotoURL yoksa,
                    // varsayılan bir fotoğrafı yükleyebilir veya başka bir işlem yapabilirsiniz
                }
            }
        }

        // "Onayla" butonuna tıklama işlemleri
        binding.btnOnayla.setOnClickListener {
            val newName = binding.editTextKisiAdi.text.toString()
            viewModel.updateUserDisplayName(newName)
        }

        // Profil fotoğrafı üzerine tıklama işlemleri
        binding.EditProfilePp.setOnClickListener {
            launchGallery()
        }
    }

    // Galeriye gitmek için kullanılan fonksiyon
    private fun launchGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    // Galeriden seçilen resmin sonucunu işleme
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            // Seçilen resmin Uri'sini al
            val ProfilePhotoURL: Uri = data.data!!

            // EditViewModel'da tanımlanan fonksiyonu kullanarak resmi yükle
            viewModel.uploadImage(ProfilePhotoURL)
        }
    }
}

