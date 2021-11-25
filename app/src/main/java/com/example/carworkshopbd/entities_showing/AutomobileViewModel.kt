package com.example.carworkshopbd.entities_showing

import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.Automobile
import kotlinx.coroutines.launch

class AutomobileViewModel(
    private val db: CarWorkshopDB?
) : EntityShowingViewModel() {

    private fun getAutomobileList() = viewModelScope.launch {
        _dataFlow.emit(db?.getAutomobileDao()?.getAutomobiles() ?: emptyList())
    }

    override fun loadData() {
        getAutomobileList()
    }

    override fun deleteItem(item: ShowableInList?) {
        viewModelScope.launch {
            db?.getAutomobileDao()?.deleteAuto(item as Automobile)
        }
        loadData()
    }
}