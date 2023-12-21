package com.yz3ro.chatcraftmvvm.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yz3ro.chatcraftmvvm.R
import com.yz3ro.chatcraftmvvm.databinding.FragmentEnterNameBinding

class EnterName : Fragment() {
    lateinit var navController: NavController
    private var binding: FragmentEnterNameBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEnterNameBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        binding?.imgbtnNext?.setOnClickListener {
            val name = binding?.txtAd?.text.toString().trim()
            sharedPreferences.edit().putString("name", name).apply()
            navController.navigate(R.id.action_enterName_to_enterNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
