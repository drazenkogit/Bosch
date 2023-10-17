package com.bosch.data.ds.database.room.entities

import androidx.room.TypeConverter
import com.bosch.domain.models.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CountryEntityTypeConverter() {

    private val gson = Gson()

    @TypeConverter
    fun stringToCurrencyList(data: String): List<Currency> {
        return gson.fromJson(data, object : TypeToken<List<Currency>>() {}.type)
    }

    @TypeConverter
    fun currencyListToString(list: List<Currency>): String {
        return gson.toJson(list)
    }
}