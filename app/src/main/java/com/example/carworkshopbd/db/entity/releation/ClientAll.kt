package com.example.carworkshopbd.db.entity.releation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.carworkshopbd.db.entity.Automobile
import com.example.carworkshopbd.db.entity.Client
import com.example.carworkshopbd.db.entity.DiscountCard

data class ClientAll(
    @Embedded
    val client: Client,
    @Relation(
        parentColumn = "autoId",
        entityColumn = "autoId"
    )
    val autoWithProblems: Automobile? = null,
    @Relation(
        parentColumn = "discountId",
        entityColumn = "id"
    )
    val discountCard: DiscountCard? = null
)
