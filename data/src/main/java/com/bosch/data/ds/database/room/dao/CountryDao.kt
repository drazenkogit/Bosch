package com.bosch.data.ds.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bosch.data.ds.database.room.entities.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries ORDER BY pinned DESC, name ASC")
    fun getCountries(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countries WHERE pinned=1")
    suspend fun getPinnedCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countryEntities: List<CountryEntity>)

    @Update
    suspend fun updateCountry(countryEntity: CountryEntity)

    @Query("DELETE FROM countries")
    suspend fun deleteCountries()
}