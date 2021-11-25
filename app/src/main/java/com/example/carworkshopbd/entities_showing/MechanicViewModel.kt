package com.example.carworkshopbd.entities_showing

import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.Mechanic
import kotlinx.coroutines.launch

class MechanicViewModel(
    private val db: CarWorkshopDB?
) : EntityShowingViewModel() {

    private fun getMechanicData() = viewModelScope.launch {
        _dataFlow.emit(db?.getMechanicDao()?.getMechanics() ?: emptyList())
    }

    override fun loadData() {
        getMechanicData()
    }

    override fun deleteItem(item: ShowableInList?) {
        viewModelScope.launch {
            db?.getMechanicDao()?.deleteMechanic(item as Mechanic)
        }
        loadData()
    }
}