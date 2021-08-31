package ru.geekbrains.notes.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.geekbrains.notes.R

class Navigation(private val fragmentManager: FragmentManager) {
    fun addFragment(fragment: Fragment?, useBackStack: Boolean) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.notes_container, fragment!!)
        if (useBackStack) {
            fragmentTransaction.addToBackStack("")
        }
        fragmentTransaction.commit()
    }
}