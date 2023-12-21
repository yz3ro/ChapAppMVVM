package com.yz3ro.chatcraftmvvm.ui.Contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yz3ro.chatcraftmvvm.R
import com.yz3ro.chatcraftmvvm.adapters.ContactAdapter
import com.yz3ro.chatcraftmvvm.databinding.FragmentContactsBinding
import com.yz3ro.chatcraftmvvm.menu.Menu
import com.yz3ro.chatcraftmvvm.model.Users


class Contacts : Fragment() {
    private var binding: FragmentContactsBinding? = null
    lateinit var navController: NavController
    private lateinit var userArrayList: ArrayList<Users>
    private lateinit var ContactsRecyclerView: RecyclerView
    private lateinit var ContactAdapter: ContactAdapter
    private lateinit var viewModel: ContactsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =FragmentContactsBinding .inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        Menu.setupBottomNavigation(binding?.navbar ?: return, navController)
        ContactsRecyclerView = binding!!.ContactsRec
        ContactsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        ContactsRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        ContactAdapter = ContactAdapter(userArrayList)
        ContactsRecyclerView.adapter = ContactAdapter

        val repository = ContactsRepository(requireContext())
        viewModel = ViewModelProvider(this, ContactsFactory(repository)).get(ContactsViewModel::class.java)



        observeViewModel()


        viewModel.fetchUserList()

        binding?.addContact?.setOnClickListener {
            navController.navigate(R.id.action_contacts_to_addContact)
        }
    }
    private fun observeViewModel() {
        viewModel.userList.observe(viewLifecycleOwner, Observer { userList ->
            userList?.let {
                userArrayList.clear()
                userArrayList.addAll(it)
                ContactAdapter.notifyDataSetChanged()
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}