package com.example.carworkshopbd.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.carworkshopbd.db.entity.Mechanic

@Dao
interface MechanicDao {

    @Insert
    suspend fun insertMechanic(mechanic: Mechanic)

    @Delete
    suspend fun deleteMechanic(mechanic: Mechanic)

    @Query("SELECT * FROM Mechanic")
    suspend fun getMechanics(): List<Mechanic>

    @Query("SELECT * FROM Mechanic WHERE id =:mechanicId")
    suspend fun getMechanic(mechanicId: Long): Mechanic
}