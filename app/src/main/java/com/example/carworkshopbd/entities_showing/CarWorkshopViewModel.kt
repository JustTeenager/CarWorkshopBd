package com.example.carworkshopbd.entities_showing

import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.CarWorkshop
import kotlinx.coroutines.launch

class CarWorkshopViewModel(
    private val db: CarWorkshopDB?
) : EntityShowingViewModel() {

    private fun getCarWorkshopData() = viewModelScope.launch {
        _dataFlow.emit(db?.getCarWorkshopDao()?.getCarWorkshops() ?: emptyList())
    }

    override fun loadData() {
        getCarWorkshopData()
    }

    override fun deleteItem(item: ShowableInList?) {
        viewModelScope.launch {
            db?.getCarWorkshopDao()?.deleteCarWorkshop(item as CarWorkshop)
        }
        loadData()
    }
}