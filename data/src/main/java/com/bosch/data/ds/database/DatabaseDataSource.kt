package com.bosch.data.ds.database

import com.bosch.data.ds.database.room.dao.CountryDao
import com.bosch.data.ds.database.room.entities.CountryEntity
import com.bosch.data.ds.remote.models.CountryRemote
import com.bosch.domain.models.Country
import com.bosch.domain.models.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseDataSource @Inject constructor(
    private val countryDao: CountryDao,
) {

    fun getCountries(): Flow<List<Country>> {
        return countryDao.getCountries().map { countryEntityList ->
            countryEntityList.map {
                Country(
                    it.id,
                    it.name,
                    it.currencies,
                    it.capital,
                    it.area,
                    it.population,
                    it.pinned
                )
            }
        }
    }

    suspend fun saveCountries(remoteCountries: List<CountryRemote>) {
        val pinnedCountryNames = countryDao.getPinnedCountries().map {
            it.name
        }
        countryDao.deleteCountries()
        val countryEntities: List<CountryEntity> = remoteCountries.map { countryRemote ->
            val name = countryRemote.name?.official ?: "-"
            val currencies: List<Currency> = countryRemote.currencies.values.map { currencyRemote ->
                Currency(currencyRemote.name ?: "-", currencyRemote.symbol ?: "-")
            }
            val capital =
                if (countryRemote.capital?.isNotEmpty() == true) countryRemote.capital[0] else "-"
            CountryEntity(
                0,
                name,
                currencies,
                capital,
                countryRemote.area ?: 0.0,
                countryRemote.population ?: 0,
                pinnedCountryNames.contains(name)
            )
        }
        countryDao.insertCountries(countryEntities)
    }

    suspend fun pinCountry(country: Country) {
        val countryEntity = CountryEntity(
            country.id,
            country.name,
            country.currencies,
            country.capital,
            country.area,
            country.population,
            !country.pinned
        )
        countryDao.updateCountry(countryEntity)
    }

    suspend fun deleteCountries() {
        countryDao.deleteCountries()
    }
}