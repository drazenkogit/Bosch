package com.bosch.domain.models

data class Country(
    val id: Int,
    val name: String,
    val currencies: List<Currency>,
    val capital: String,
    val area: Double,
    val population: Int,
    val pinned: Boolean
)

data class Currency(
    val name: String,
    val symbol: String
)