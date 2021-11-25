package com.example.carworkshopbd.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal

class CarDbConverter {

    @TypeConverter
    fun convertListToString(list: List<Long>): String = Gson().toJson(list)

    @TypeConverter
    fun convertStringToList(string: String): List<Long> =
        Gson().fromJson(string, object : TypeToken<List<Long>>() {}.type)

    @TypeConverter
    fun convertDecimalToString(decimal: BigDecimal): String = Gson().toJson(decimal)

    @TypeConverter
    fun convertStringToDecimal(string: String): BigDecimal =
        Gson().fromJson(string, object : TypeToken<BigDecimal>() {}.type)
}