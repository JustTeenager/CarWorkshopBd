package com.example.carworkshopbd.entities_inserting.pair_insert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.Client
import com.example.carworkshopbd.entities_inserting.values_insert.InsertFragment
import com.example.carworkshopbd.main.Table
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class ChooseMultiPairViewModel(
    private val db: CarWorkshopDB?
) : ViewModel() {

    private val _autoFlow = MutableSharedFlow<List<ShowableInList>>()
    val autoFlow = _autoFlow.asSharedFlow()

    private val _discountDataFlow = MutableSharedFlow<List<ShowableInList>>()
    val discountFlow = _discountDataFlow.asSharedFlow()

    private val _workshopFlow = MutableSharedFlow<List<ShowableInList>>()
    val workshopFlow = _workshopFlow.asSharedFlow()

    fun loadData() = viewModelScope.launch {
        db?.let {
            val autoJob = launch { _autoFlow.emit(db.getAutomobileDao().getAutomobiles()) }
            val discountJob =
                launch { _discountDataFlow.emit(db.getDiscountCardDao().getDiscountCards()) }
            val workJob = launch { _workshopFlow.emit(db.getCarWorkshopDao().getCarWorkshops()) }
            joinAll(autoJob, discountJob, workJob)
        }
    }

    fun insertData(
        tempItem: InsertFragment.TempEntity,
        autoId: Long?,
        discountId: Long?,
        workId: Long?
    ) = viewModelScope.launch {
        val item = (tempItem.getCorrectItemFromTable(Table.Client) as Client).apply {
            this.autoId = autoId
            this.discountId = discountId
            this.workId = workId
        }
        db?.getClientDao()?.insertClient(item)
    }
}