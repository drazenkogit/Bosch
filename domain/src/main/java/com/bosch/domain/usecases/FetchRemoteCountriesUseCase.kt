package com.bosch.domain.usecases

import com.bosch.domain.models.ApiResponse
import com.bosch.domain.repository.RepositoryAbstraction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchRemoteCountriesUseCase @Inject constructor(private val repository: RepositoryAbstraction) {

    suspend operator fun invoke(coroutineDispatcher: CoroutineDispatcher): Flow<ApiResponse<Any>> {
        return withContext(coroutineDispatcher) {
            repository.fetchRemoteCountries()
        }
    }
}