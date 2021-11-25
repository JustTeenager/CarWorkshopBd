package com.example.carworkshopbd.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.carworkshopbd.utils.SharedManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainViewModel : ViewModel() {

    private val _navigatorFlow = MutableSharedFlow<Table>(replay = 1)
    val navigatorFlow = _navigatorFlow.asSharedFlow()

    init {
        _navigatorFlow.tryEmit(Table.None)
    }

    fun navigateTo(table: Table) {
        _navigatorFlow.tryEmit(table)
    }

    fun getCorrectData(type: Int): List<Table> {
        Log.d("tut", "type is $type")
        return when (type) {
            SharedManager.MANAGER_TYPE -> listManager
            else -> listDirector
        }
    }

    val listManager: List<Table> = listOf(
        Table.CarWorkshop,
        Table.Client,
        Table.DiscountCard,
        Table.Mechanic,
    )
    val listDirector: List<Table> = listOf(
        Table.Automobile,
        Table.CarWorkshop,
        Table.Client,
        Table.DiscountCard,
        Table.Manager,
        Table.Mechanic,
        Table.Problem
    )

}