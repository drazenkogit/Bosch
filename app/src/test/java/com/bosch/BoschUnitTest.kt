package com.bosch

import com.bosch.domain.models.Country
import com.bosch.domain.models.Currency
import com.bosch.domain.usecases.DeleteCountriesUseCase
import com.bosch.domain.usecases.FetchRemoteCountriesUseCase
import com.bosch.domain.usecases.GetCountriesUseCase
import com.bosch.domain.usecases.PinCountryUseCase
import com.bosch.ui.vm.ViewModelMainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)

@RunWith(MockitoJUnitRunner::class)
class BoschUnitTest {

    private lateinit var repo: TestRepository

    private lateinit var viewModel: ViewModelMainActivity

    private lateinit var fetchRemoteCountriesUseCase: FetchRemoteCountriesUseCase
    private lateinit var getCountriesUseCase: GetCountriesUseCase
    private lateinit var pinCountryUseCase: PinCountryUseCase
    private lateinit var deleteCountriesUseCase: DeleteCountriesUseCase

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun initMocks() {
        Dispatchers.setMain(mainThreadSurrogate)

        repo = TestRepository()

        fetchRemoteCountriesUseCase = FetchRemoteCountriesUseCase(repo)
        getCountriesUseCase = GetCountriesUseCase(repo)
        pinCountryUseCase = PinCountryUseCase(repo)
        deleteCountriesUseCase = DeleteCountriesUseCase(repo)

        viewModel = ViewModelMainActivity(
            fetchRemoteCountriesUseCase,
            getCountriesUseCase,
            pinCountryUseCase,
            deleteCountriesUseCase
        )
    }

    @Test
    fun testViewModelGetCountries(): Unit = runBlocking {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            getCountriesUseCase().collect {
                assertEquals(expectedCountryList(), it)
            }
        }
    }

    private fun expectedCountryList(): List<Country> {
        val currencyList = mutableListOf<Currency>()
        currencyList.add(Currency("USD", "$"))
        currencyList.add(Currency("EURO", "€"))
        currencyList.add(Currency("Dinar", "RSD"))

        val countryList = mutableListOf<Country>()
        countryList.add(Country(0, "Germany", currencyList, "Berlin", 150_000.0, 84_000_000, false))
        countryList.add(Country(0, "France", currencyList, "Paris", 75_000.0, 47_000_000, false))
        countryList.add(Country(0, "Serbia", currencyList, "Belgrade", 20_000.0, 7_000_000, false))
        return countryList
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}