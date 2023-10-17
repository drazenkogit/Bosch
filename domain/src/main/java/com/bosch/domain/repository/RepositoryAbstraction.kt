package com.bosch.domain.repository

import com.bosch.domain.models.ApiResponse
import com.bosch.domain.models.Country
import kotlinx.coroutines.flow.Flow

interface RepositoryAbstraction {
    fun getCountries(): Flow<List<Country>>
    suspend fun fetchRemoteCountries(): Flow<ApiResponse<Any>>
    suspend fun pinCountry(country: Country)
    suspend fun deleteCountries()
}