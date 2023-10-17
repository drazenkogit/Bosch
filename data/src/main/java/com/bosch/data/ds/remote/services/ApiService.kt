package com.bosch.data.ds.remote.services

import com.bosch.data.ds.remote.models.CountryRemote
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("all?fields=name,capital,area,currencies,population")
    suspend fun getCountries(): Response<List<CountryRemote>>
}