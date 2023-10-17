package com.bosch.ui.vm

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bosch.domain.models.ApiResponse
import com.bosch.domain.models.Country
import com.bosch.domain.usecases.DeleteCountriesUseCase
import com.bosch.domain.usecases.FetchRemoteCountriesUseCase
import com.bosch.domain.usecases.GetCountriesUseCase
import com.bosch.domain.usecases.PinCountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMainActivity @Inject constructor(
    private val fetchRemoteCountriesUseCase: FetchRemoteCountriesUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val pinCountryUseCase: PinCountryUseCase,
    private val deleteCountriesUseCase: DeleteCountriesUseCase
) : ViewModel() {

    /**
     * Selected Country for Details screen
     */
    private val _selectedCountryLiveData = MutableLiveData<Country>()
    val selectedCountryLiveData: LiveData<Country> get() = _selectedCountryLiveData
    fun setSelectedCountry(country: Country) {
        _selectedCountryLiveData.value = country
    }

    /**
     * Country list from Database
     */
    val countriesLiveData: LiveData<List<Country>> = getCountriesUseCase().asLiveData()

    /**
     * State holder for fetching countries via API call
     */
    private val _fetchCountriesRemoteLiveData = MutableLiveData<ApiResponse<Any>>()
    val fetchCountriesRemoteLiveData: LiveData<ApiResponse<Any>> get() = _fetchCountriesRemoteLiveData

    init {
        fetchRemoteCountries(null)
    }

    fun fetchRemoteCountries(view: View?) {
        viewModelScope.launch {
            fetchRemoteCountriesUseCase(Dispatchers.IO).collect {
                _fetchCountriesRemoteLiveData.value = it
            }
        }
    }

    fun pinCountry(country: Country) {
        viewModelScope.launch {
            pinCountryUseCase(country)
        }
    }

    fun deleteCountries() {
        viewModelScope.launch {
            deleteCountriesUseCase()
        }
    }
}