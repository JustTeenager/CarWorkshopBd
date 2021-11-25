package com.example.carworkshopbd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carworkshopbd.ShowableInList

@Entity(tableName = "Automobile")
data class Automobile(
    @PrimaryKey(autoGenerate = true)
    val autoId: Long? = null,
    val model: String,
    val regNumber: String,
    val color: String,
    var problemsId: List<Long> = emptyList(),
    val lastRepair: String,
) : ShowableInList {
    override fun getUniqueId(): Long? = autoId

    override fun getTitle(): String = model

    override fun getDetails(): String {
        return "номер: $regNumber, цвет: $color"
    }

    override fun getNum(): String = autoId.toString()

    override fun isFoundByQuery(query: String): Boolean {
        return model == query
                || regNumber == query
                || color == query
                || lastRepair == query
    }

    companion object {
        fun newInstance(
            firstString: String,
            secondString: String,
            thirdString: String,
            fourthString: String
        ) = Automobile(
            model = firstString,
            regNumber = secondString,
            color = thirdString,
            lastRepair = fourthString,
        )
    }
}
