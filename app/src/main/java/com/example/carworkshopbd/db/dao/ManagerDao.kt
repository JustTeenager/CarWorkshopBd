package com.example.carworkshopbd.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.carworkshopbd.db.entity.Manager

@Dao
interface ManagerDao {

    @Delete
    suspend fun deleteManager(manager: Manager)

    @Insert
    suspend fun insertManager(manager: Manager)

    @Query("SELECT * FROM Manager")
    suspend fun getManagers(): List<Manager>

    @Query("SELECT * FROM Manager WHERE managerId =:managerId")
    suspend fun getManager(managerId: Long): Manager
}