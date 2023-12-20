package com.yz3ro.chatcraftmvvm.menu

import androidx.navigation.NavController
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.yz3ro.chatcraftmvvm.R

class Menu {
    companion object {
        fun setupBottomNavigation(navBar: ChipNavigationBar, navController: NavController) {
            navBar.setOnItemSelectedListener { id ->
                when (id) {
                    R.id.nav_messages -> {
                        navController.navigate(R.id.messages)
                    }
                    R.id.nav_contacts -> {
                        navController.navigate(R.id.contacts)
                    }
                    R.id.nav_settings -> {
                        navController.navigate(R.id.settings)
                    }
                }
            }
        }
    }
}
