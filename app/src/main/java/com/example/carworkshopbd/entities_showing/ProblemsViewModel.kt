package com.example.carworkshopbd.entities_showing

import androidx.lifecycle.viewModelScope
import com.example.carworkshopbd.ShowableInList
import com.example.carworkshopbd.db.CarWorkshopDB
import com.example.carworkshopbd.db.entity.Problem
import kotlinx.coroutines.launch

class ProblemsViewModel(
    private val db: CarWorkshopDB?
) : EntityShowingViewModel() {

    private fun getProblemsData() = viewModelScope.launch {
        _dataFlow.emit(db?.getProblemDao()?.getProblems() ?: emptyList())
    }

    override fun loadData() {
        getProblemsData()
    }

    override fun deleteItem(item: ShowableInList?) {
        viewModelScope.launch {
            db?.getProblemDao()?.deleteProblem(item as Problem)
        }
        loadData()
    }
}