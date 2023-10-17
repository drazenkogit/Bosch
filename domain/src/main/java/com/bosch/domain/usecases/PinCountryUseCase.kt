package com.bosch.domain.usecases

import com.bosch.domain.models.Country
import com.bosch.domain.repository.RepositoryAbstraction
import javax.inject.Inject

class PinCountryUseCase @Inject constructor(private val repository: RepositoryAbstraction) {

    suspend operator fun invoke(country: Country) {
        return repository.pinCountry(country)
    }
}