package com.bosch.domain.usecases

import com.bosch.domain.repository.RepositoryAbstraction
import javax.inject.Inject

class DeleteCountriesUseCase @Inject constructor(private val repository: RepositoryAbstraction) {

    suspend operator fun invoke() {
        return repository.deleteCountries()
    }
}