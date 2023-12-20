package com.yz3ro.chatcraftmvvm.ui.Contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.yz3ro.chatcraftmvvm.databinding.FragmentAddContactBinding


class AddContact : Fragment() {
    private lateinit var viewModel: ContactsViewModel
    private var binding: FragmentAddContactBinding? = null
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentAddContactBinding .inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = ContactsRepository(requireContext())
        viewModel = ViewModelProvider(this, ContactsFactory(repository)).get(ContactsViewModel::class.java)
        val btnSave = binding?.btnOnayla
        val editTextNumber = binding?.editTextTelefon
        val editTextName = binding?.editTextKisiAdi
        val countryCodePicker = binding?.countyCodePicker

        btnSave?.setOnClickListener {

            val kisiAdi = editTextName?.text.toString()
            val telefon = countryCodePicker?.selectedCountryCodeWithPlus+editTextNumber?.text.toString()
            println(telefon)
            println(kisiAdi)
            viewModel.addContact(kisiAdi,telefon)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}