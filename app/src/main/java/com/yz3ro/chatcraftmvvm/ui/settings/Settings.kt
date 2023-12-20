package com.yz3ro.chatcraftmvvm.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.yz3ro.chatcraftmvvm.R
import com.yz3ro.chatcraftmvvm.databinding.FragmentSettingsBinding
import com.yz3ro.chatcraftmvvm.menu.Menu
import com.yz3ro.chatcraftmvvm.ui.settings.edit.EditFactory
import com.yz3ro.chatcraftmvvm.ui.settings.edit.EditRepository
import com.yz3ro.chatcraftmvvm.ui.settings.edit.EditViewModel


class Settings : Fragment() {
    private var binding: FragmentSettingsBinding? = null
    lateinit var navController: NavController
    private lateinit var viewModel: EditViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding .inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        Menu.setupBottomNavigation(binding?.navbar ?: return, navController)
        val repository = EditRepository()
        viewModel = ViewModelProvider(this, EditFactory(repository)).get(EditViewModel::class.java)
        viewModel.getUserProfile().observe(viewLifecycleOwner) { Users ->
            Users?.let {
                if (!it.ProfilePhotoURL.isNullOrEmpty()) {
                    Glide.with(requireContext())
                        .load(it.ProfilePhotoURL)
                        .into(binding!!.SettingsPp)
                } else {

                }
            }
        }
        binding!!.editProfile.setOnClickListener {
            navController.navigate(R.id.action_settings_to_editProfile)
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}