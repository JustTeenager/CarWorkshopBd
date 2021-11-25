package com.example.carworkshopbd.auth

import androidx.lifecycle.ViewModel
import com.example.carworkshopbd.utils.SharedManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthViewModel(
    private val sharedManager: SharedManager
) : ViewModel() {

    val openFlow: SharedFlow<Boolean>
        get() = _openFlow.asSharedFlow()
    private val _openFlow: MutableSharedFlow<Boolean> = MutableSharedFlow(1)

    fun openMainFragment(type: Int, enterPassword: String) {
        val truePassword = when (type) {
            0 -> sharedManager.managerPassword
            else -> sharedManager.directorPassword
        }
        sharedManager.isAuth = enterPassword == truePassword
        sharedManager.typeUser = type
        _openFlow.tryEmit(enterPassword == truePassword)
    }
}