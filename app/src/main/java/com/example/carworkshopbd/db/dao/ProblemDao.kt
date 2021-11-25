package com.example.carworkshopbd.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.carworkshopbd.db.entity.Problem

@Dao
interface ProblemDao {

    @Insert
    suspend fun insertProblem(problem: Problem)

    @Delete
    suspend fun deleteProblem(problem: Problem)

    @Query("SELECT * FROM Problem")
    suspend fun getProblems(): List<Problem>

    @Query("SELECT * FROM Problem WHERE problemId=:problemId")
    suspend fun getProblem(problemId: Long): Problem
}