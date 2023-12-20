package com.yz3ro.chatcraftmvvm.ui.messages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yz3ro.chatcraftmvvm.databinding.FragmentMessagesBinding
import com.yz3ro.chatcraftmvvm.menu.Menu

class Messages : Fragment() {
    private var binding: FragmentMessagesBinding? = null
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessagesBinding .inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        Menu.setupBottomNavigation(binding?.navbar ?: return, navController)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}