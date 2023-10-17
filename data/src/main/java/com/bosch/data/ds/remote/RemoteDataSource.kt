package com.bosch.data.ds.remote

import com.bosch.data.ds.remote.models.CountryRemote
import com.bosch.data.ds.remote.services.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getCountries(): Response<List<CountryRemote>> {
        return apiService.getCountries()
    }
}