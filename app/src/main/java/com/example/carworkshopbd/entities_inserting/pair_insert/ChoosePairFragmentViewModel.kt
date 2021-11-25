package com.example.carworkshopbd.entities_inserting.pair_insert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.Automobile
import com.example.carworkshopbd.db.entity.Manager
import com.example.carworkshopbd.db.entity.Mechanic
import com.example.carworkshopbd.entities_inserting.values_insert.InsertFragment
import com.example.carworkshopbd.main.Table
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ChoosePairFragmentViewModel(
    private val db: CarWorkshopDB?,
    private val table: Table
) : ViewModel() {

    private val _dataFlow = MutableSharedFlow<List<ShowableInList>>()
    val dataFlow = _dataFlow.asSharedFlow()

    fun loadCorrectData() = viewModelScope.launch {
        when (table) {
            Table.Manager -> {
                db?.getCarWorkshopDao()?.getCarWorkshops()?.let { _dataFlow.emit(it) }
            }
            Table.Mechanic -> {
                db?.getCarWorkshopDao()?.getCarWorkshops()?.let { _dataFlow.emit(it) }
            }
            Table.Automobile -> {
                db?.getProblemDao()?.getProblems()?.let { _dataFlow.emit(it) }
            }
            else -> return@launch
        }
    }

    fun insertCorrectItem(tempItem: InsertFragment.TempEntity, list: List<Long>) =
        viewModelScope.launch {
            val item = tempItem.getCorrectItemFromTable(table)
            when (table) {
                Table.Manager -> {
                    (item as Manager).workId = list.firstOrNull()
                    db?.getManagerDao()?.insertManager(item)
                }
                Table.Mechanic -> {
                    (item as Mechanic).workId = list.firstOrNull()
                    db?.getMechanicDao()?.insertMechanic(item)
                }
                Table.Automobile -> {
                    (item as Automobile).problemsId = list
                    db?.getAutomobileDao()?.insertAuto(item)
                }
                else -> return@launch
            }
        }
}