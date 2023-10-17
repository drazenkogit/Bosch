package com.bosch.data.ds.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bosch.domain.models.Currency

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "currencies") @TypeConverters(CountryEntityTypeConverter::class) val currencies: List<Currency>,
    @ColumnInfo(name = "capital") val capital: String,
    @ColumnInfo(name = "area") val area: Double,
    @ColumnInfo(name = "population") val population: Int,
    @ColumnInfo(name = "pinned") val pinned: Boolean
)