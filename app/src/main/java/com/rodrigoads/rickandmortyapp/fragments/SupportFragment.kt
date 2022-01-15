package com.rodrigoads.rickandmortyapp.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.rodrigoads.rickandmortyapp.R

class SupportFragment {
    fun startFragment(fragment: Fragment, activity: FragmentActivity?, saved: Boolean = false) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (saved) {
            transaction?.replace(R.id.mainFragmentContainerView, fragment)
            transaction?.addToBackStack(null)
        } else {
            transaction?.replace(R.id.mainFragmentContainerView, fragment)
        }
        transaction?.commit()
    }
}