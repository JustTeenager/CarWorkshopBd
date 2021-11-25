package com.example.carworkshopbd.db.entity.releation

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carworkshopbd.db.entity.Automobile
import com.example.carworkshopbd.db.entity.Mechanic

@Entity
data class MechanicAndAutomobile(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @Embedded
    val automobile: Automobile,
    @Embedded
    val mechanic: Mechanic
)
