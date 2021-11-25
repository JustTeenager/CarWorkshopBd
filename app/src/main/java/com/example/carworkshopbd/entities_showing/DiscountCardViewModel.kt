package com.example.carworkshopbd.entities_showing

import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.DiscountCard
import kotlinx.coroutines.launch

class DiscountCardViewModel(
    private val db: CarWorkshopDB?
) : EntityShowingViewModel() {

    private fun getDiscountCardData() = viewModelScope.launch {
        _dataFlow.emit(db?.getDiscountCardDao()?.getDiscountCards() ?: emptyList())
    }

    override fun loadData() {
        getDiscountCardData()
    }

    override fun deleteItem(item: ShowableInList?) {
        viewModelScope.launch {
            db?.getDiscountCardDao()?.deleteDiscountCard(item as DiscountCard)
        }
        loadData()
    }
}