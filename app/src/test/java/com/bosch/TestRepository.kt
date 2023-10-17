package com.bosch

import com.bosch.domain.models.ApiResponse
import com.bosch.domain.models.Country
import com.bosch.domain.models.Currency
import com.bosch.domain.repository.RepositoryAbstraction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestRepository : RepositoryAbstraction {

    val countryList = createTestCountryList()

    override fun getCountries(): Flow<List<Country>> {
        return flow {
            emit(countryList)
        }
    }

    override suspend fun fetchRemoteCountries(): Flow<ApiResponse<Any>> {
        return flow {
            emit(ApiResponse.Success(true))
        }
    }

    override suspend fun pinCountry(country: Country) {

    }

    override suspend fun deleteCountries() {

    }

    private fun createTestCountryList(): List<Country> {
        val currencyList = mutableListOf<Currency>()
        currencyList.add(Currency("USD", "$"))
        currencyList.add(Currency("EURO", "€"))
        currencyList.add(Currency("Dinar", "RSD"))

        val countryList = mutableListOf<Country>()
        countryList.add(Country(0, "Germany", currencyList, "Berlin", 150_000.0, 84_000_000, false))
        countryList.add(Country(0, "France", currencyList, "Paris", 75_000.0, 47_000_000, false))
        countryList.add(Country(0, "Serbia", currencyList, "Belgrade", 20_000.0, 7_000_000, false))
        return countryList
    }
}