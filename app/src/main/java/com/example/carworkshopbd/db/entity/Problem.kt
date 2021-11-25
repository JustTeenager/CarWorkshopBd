package com.example.carworkshopbd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carworkshopbd.ShowableInList
import java.math.BigDecimal

@Entity(tableName = "Problem")
data class Problem(
    @PrimaryKey(autoGenerate = true)
    val problemId: Long? = null,
    val description: String,
    val cost: BigDecimal
) : ShowableInList {
    override fun getTitle(): String {
        return "Цена: $cost"
    }

    override fun getUniqueId(): Long? = problemId

    override fun getDetails(): String = description

    override fun getNum(): String = problemId.toString()

    override fun isFoundByQuery(query: String): Boolean {
        return description == query || cost.toString() == query
    }

    companion object {
        fun newInstance(
            firstString: String,
            secondString: String,
        ) = Problem(
            description = firstString,
            cost = BigDecimal(secondString)
        )
    }
}
