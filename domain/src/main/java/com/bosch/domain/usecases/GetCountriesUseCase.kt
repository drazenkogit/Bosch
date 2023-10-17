package com.bosch.domain.usecases

import com.bosch.domain.models.Country
import com.bosch.domain.repository.RepositoryAbstraction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(private val repository: RepositoryAbstraction) {

    operator fun invoke(): Flow<List<Country>> {
        return repository.getCountries()
    }
}