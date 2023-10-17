package com.bosch.data.ds.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bosch.data.ds.database.room.dao.CountryDao
import com.bosch.data.ds.database.room.entities.CountryEntity
import com.bosch.data.ds.database.room.entities.CountryEntityTypeConverter

@Database(
    entities = [CountryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CountryEntityTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao
}