package com.bosch.data.repository

import com.bosch.data.ds.database.DatabaseDataSource
import com.bosch.data.ds.remote.RemoteDataSource
import com.bosch.data.ds.remote.models.CountryRemote
import com.bosch.domain.models.ApiResponse
import com.bosch.domain.models.Country
import com.bosch.domain.repository.RepositoryAbstraction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val dbDataSource: DatabaseDataSource,
    private val remoteDataSource: RemoteDataSource
) : RepositoryAbstraction {

    override fun getCountries(): Flow<List<Country>> {
        return dbDataSource.getCountries()
    }

    override suspend fun fetchRemoteCountries(): Flow<ApiResponse<Any>> {
        return flow {
            emit(ApiResponse.Loading())
            delay(200)
            try {
                val response = remoteDataSource.getCountries()
                if (response.isSuccessful && response.body() != null) {
                    dbDataSource.saveCountries(response.body() as List<CountryRemote>)
                    emit(ApiResponse.Success(true))
                } else {
                    emit(ApiResponse.Error(response.code(), response.errorBody().toString()))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Exception(e))
            }
        }
    }

    override suspend fun pinCountry(country: Country) {
        dbDataSource.pinCountry(country)
    }

    override suspend fun deleteCountries() {
        dbDataSource.deleteCountries()
    }
}
