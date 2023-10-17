package com.bosch.data.di

import com.bosch.data.repository.CountryRepository
import com.bosch.domain.repository.RepositoryAbstraction
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(countryRepository: CountryRepository): RepositoryAbstraction
}