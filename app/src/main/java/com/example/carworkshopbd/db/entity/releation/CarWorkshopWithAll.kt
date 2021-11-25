package com.example.carworkshopbd.db.entity.releation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.carworkshopbd.db.entity.CarWorkshop
import com.example.carworkshopbd.db.entity.Client
import com.example.carworkshopbd.db.entity.Manager
import com.example.carworkshopbd.db.entity.Mechanic

data class CarWorkshopWithAll(
    @Embedded
    val workshop: CarWorkshop,
    @Relation(
        parentColumn = "id",
        entityColumn = "workId"
    )
    val mechanics: List<Mechanic>,
    @Relation(
        parentColumn = "id",
        entityColumn = "workId"
    )
    val clients: List<Client>,
    @Relation(
        parentColumn = "id",
        entityColumn = "workId"
    )
    val managers: List<Manager>
)
