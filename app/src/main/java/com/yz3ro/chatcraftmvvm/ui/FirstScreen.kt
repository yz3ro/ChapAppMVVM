package com.yz3ro.chatcraftmvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yz3ro.chatcraftmvvm.R
import com.yz3ro.chatcraftmvvm.databinding.FragmentFirstScreenBinding

class FirstScreen : Fragment() {
    private var binding: FragmentFirstScreenBinding? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding?.FirstButton?.setOnClickListener {
            navController.navigate(R.id.action_firstScreen_to_enterName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

