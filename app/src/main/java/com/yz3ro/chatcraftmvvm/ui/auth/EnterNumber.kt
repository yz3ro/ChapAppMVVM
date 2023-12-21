package com.yz3ro.chatcraftmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yz3ro.chatcraftmvvm.R
import com.yz3ro.chatcraftmvvm.databinding.FragmentEnterNumberBinding

class EnterNumber : Fragment() {
    private lateinit var navController: NavController
    private var binding: FragmentEnterNumberBinding? = null
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterNumberBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        binding?.imgbtnNext1?.setOnClickListener {
            val phoneNumber =
                binding?.ccp?.selectedCountryCodeWithPlus + binding?.txtNum?.text.toString()
            println(phoneNumber)
            if (phoneNumber.isNotBlank()) {
                authViewModel.startPhoneNumberVerification(phoneNumber)
            } else {

            }

        }
        authViewModel.verificationCodeSent.observe(viewLifecycleOwner) { verificationId ->

            navController.navigate(R.id.action_enterNumber_to_enterOtp)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

