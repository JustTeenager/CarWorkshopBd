package com.example.carworkshopbd.entities_showing

import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.Manager
import kotlinx.coroutines.launch

class ManagerViewModel(
    private val carWorkshopDB: CarWorkshopDB?
) : EntityShowingViewModel() {

    private fun getManagers() = viewModelScope.launch {
        _dataFlow.emit(carWorkshopDB?.getManagerDao()?.getManagers() ?: emptyList())
    }

    override fun loadData() {
        getManagers()
    }

    override fun deleteItem(item: ShowableInList?) {
        viewModelScope.launch {
            carWorkshopDB?.getManagerDao()?.deleteManager(item as Manager)
        }
        loadData()
    }
}