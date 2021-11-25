package com.example.carworkshopbd.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.carworkshopbd.R

fun FragmentManager.addTo(fragment: Fragment) {
    beginTransaction()
        .replace(R.id.fragmentContainerId, fragment)
        .addToBackStack("nameOfFragment")
        .commit()
}

fun FragmentManager.replaceTo(fragment: Fragment) {
    beginTransaction()
        .replace(R.id.fragmentContainerId, fragment)
        .commit()
}

fun FragmentManager.replaceClearedStackTo(fragment: Fragment) {
    for (i in 0..backStackEntryCount) popBackStack()
    beginTransaction()
        .replace(R.id.fragmentContainerId, fragment)
        .commit()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}