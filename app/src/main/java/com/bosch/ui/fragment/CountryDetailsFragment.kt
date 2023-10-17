package com.bosch.ui.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.bosch.R
import com.bosch.databinding.CountryDetailsFragmentBinding
import com.bosch.ui.vm.ViewModelMainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale


@AndroidEntryPoint
class CountryDetailsFragment : BaseFragment() {

    private val viewModelMainActivity: ViewModelMainActivity by activityViewModels()

    private lateinit var binding: CountryDetailsFragmentBinding

    override fun getLayoutResource(): Int {
        return R.layout.country_details_fragment
    }

    override fun onCreateView(b: ViewDataBinding, savedInstanceState: Bundle?) {
        binding = (b as CountryDetailsFragmentBinding)

        viewModelMainActivity.selectedCountryLiveData.observe(viewLifecycleOwner) {
            binding.countryDetailsNameText.text = it.name
            binding.countryDetailsCapitalText.text = it.capital
            binding.countryDetailsAreaText.text = "${doubleToStringNoDecimal(it.area)} km2"
            binding.countryDetailsPopulationText.text = doubleToStringNoDecimal(it.population.toDouble())
            val sb = StringBuilder()
            for (currency in it.currencies) {
                sb.append(currency.name + " (" + currency.symbol + ")\n")
            }
            binding.countryDetailsCurrenciesText.text = sb.toString()
        }
    }

    private fun doubleToStringNoDecimal(d: Double): String {
        val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
        formatter.applyPattern("#,###,###,###")
        return formatter.format(d)
    }
}