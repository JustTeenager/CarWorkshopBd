package com.example.carworkshopbd.db.dao

import androidx.room.*
import com.example.carworkshopbd.db.entity.Automobile
import com.example.carworkshopbd.db.entity.Problem
import com.example.carworkshopbd.db.entity.releation.AutoWithProblems

@Dao
interface AutomobileDao {

    @Query("SELECT * FROM Automobile")
    suspend fun getAutomobiles(): List<Automobile>

    @Insert
    suspend fun insertAuto(automobile: Automobile)

    @Delete
    suspend fun deleteAuto(automobile: Automobile)

    @Query("SELECT * FROM Automobile WHERE autoId =:autoId")
    suspend fun getAuto(autoId: Long): Automobile

    @Query("SELECT * FROM Problem WHERE problemId IN (:problemsId)")
    suspend fun getProblemsInList(problemsId: List<Long>): List<Problem>

    @Transaction
    suspend fun getAutoWithProblems(autoId: Long): AutoWithProblems {
        val auto = getAuto(autoId)
        val problems = if (auto.problemsId.isNotEmpty()) {
            getProblemsInList(auto.problemsId)
        } else null
        return AutoWithProblems(
            autoId = autoId,
            model = auto.model,
            regNumber = auto.regNumber,
            color = auto.color,
            problems = problems,
            lastRepair = auto.lastRepair
        )
    }
}