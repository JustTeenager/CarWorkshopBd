package com.example.carworkshopbd.db.entity.releation

import com.example.carworkshopbd.db.entity.Problem

data class AutoWithProblems(
    val autoId: Long? = null,
    val model: String,
    val regNumber: String,
    val color: String,
    val problems: List<Problem>? = null,
    val lastRepair: String,
)