package com.example.carworkshopbd.entities_inserting.values_insert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.*
import com.example.carworkshopbd.main.Table
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InsertViewModel(
    private val db: CarWorkshopDB?,
    private val table: Table
) : ViewModel() {

    var insertMethod: ((ShowableInList) -> Job)? = null

    init {
        when (table) {
            Table.None -> {
                insertMethod = null
            }
            Table.Automobile -> {
                insertMethod = ::insertToAutomobile
            }
            Table.CarWorkshop -> {
                insertMethod = ::insertToCarWorkshop
            }
            Table.Client -> {
                insertMethod = ::insertToClient
            }
            Table.DiscountCard -> {
                insertMethod = ::insertToDiscountCard
            }
            Table.Manager -> {
                insertMethod = ::insertToManager
            }
            Table.Mechanic -> {
                insertMethod = ::insertToMechanic
            }
            Table.Problem -> {
                insertMethod = ::insertToProblem
            }
        }
    }


    suspend fun getCorrectPairingNum(): Int {
        return withContext(viewModelScope.coroutineContext) {
            when (table) {
                Table.Automobile -> db?.getProblemDao()?.getProblems()?.size ?: 0
                Table.Mechanic, Table.Manager -> 1
                else -> 0
            }
        }
    }

    private fun insertToAutomobile(item: ShowableInList?) = viewModelScope.launch {
        db?.getAutomobileDao()?.insertAuto(item as Automobile)
    }

    private fun insertToCarWorkshop(item: ShowableInList?) = viewModelScope.launch {
        db?.getCarWorkshopDao()?.insertCarWorkshop(item as CarWorkshop)
    }

    private fun insertToClient(item: ShowableInList?) = viewModelScope.launch {
        db?.getClientDao()?.insertClient(item as Client)
    }

    private fun insertToDiscountCard(item: ShowableInList?) = viewModelScope.launch {
        db?.getDiscountCardDao()?.insertDiscountCard(item as DiscountCard)
    }

    private fun insertToManager(item: ShowableInList?) = viewModelScope.launch {
        db?.getManagerDao()?.insertManager(item as Manager)
    }

    private fun insertToMechanic(item: ShowableInList?) = viewModelScope.launch {
        db?.getMechanicDao()?.insertMechanic(item as Mechanic)
    }

    private fun insertToProblem(item: ShowableInList?) = viewModelScope.launch {
        db?.getProblemDao()?.insertProblem(item as Problem)
    }

    fun insert(item: ShowableInList?) {
        insertMethod?.let { item?.let { item -> it(item) } }
    }
}