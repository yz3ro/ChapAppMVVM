package com.yz3ro.chatcraftmvvm.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yz3ro.chatcraftmvvm.ui.auth.useradd.FirestoreRepository
import com.yz3ro.chatcraftmvvm.ui.auth.useradd.FirestoreViewModel
import com.yz3ro.chatcraftmvvm.ui.auth.useradd.FirestoreViewModelFactory
import com.yz3ro.chatcraftmvvm.R
import com.yz3ro.chatcraftmvvm.databinding.FragmentEnterOtpBinding

class EnterOtp : Fragment() {
    private lateinit var navController: NavController
    private var binding: FragmentEnterOtpBinding? = null
    private lateinit var authViewModel: AuthViewModel
    private lateinit var firestoreViewModel : FirestoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEnterOtpBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val factory = FirestoreViewModelFactory(FirestoreRepository())
        firestoreViewModel = ViewModelProvider(this, factory).get(FirestoreViewModel::class.java)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        binding?.imgbtnNext2?.setOnClickListener {

            val verificationCode = binding?.editText1?.text.toString()+binding?.editText2?.text.toString()+binding?.editText3?.text.toString()+binding?.editText4?.text.toString()+binding?.editText5?.text.toString()+binding?.editText6?.text.toString()

            authViewModel.signInWithPhoneAuthCredential(verificationCode)
            authViewModel.isVerificationSuccessful.observe(viewLifecycleOwner) { isSuccessful ->
                if (isSuccessful) {
                    firestoreViewModel.addUserToFirestore(name)
                    navController.navigate(R.id.action_enterOtp_to_messages)
                } else {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
