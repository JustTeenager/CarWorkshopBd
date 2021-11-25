package com.example.carworkshopbd.entities_showing

import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.Client
import kotlinx.coroutines.launch

class ClientViewModel(
    private val carWorkshopDB: CarWorkshopDB?
) : EntityShowingViewModel() {

    private fun getClientData() = viewModelScope.launch {
        _dataFlow.emit(carWorkshopDB?.getClientDao()?.getClients() ?: emptyList())
    }

    override fun loadData() {
        getClientData()
    }

    override fun deleteItem(item: ShowableInList?) {
        viewModelScope.launch {
            carWorkshopDB?.getClientDao()?.deleteClientAll(item as Client)
        }
        loadData()
    }
}