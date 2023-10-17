package com.bosch.data.ds.remote.models

data class CountryRemote(
    val name: NameRemote?,
    val currencies: HashMap<String, CurrencyRemote>,
    val capital: List<String>?,
    val area: Double?,
    val population: Int?,
)

data class NameRemote(
    val official: String?,
)

data class CurrencyRemote(
    val name: String?,
    val symbol: String?
)