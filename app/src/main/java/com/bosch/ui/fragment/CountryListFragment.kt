package com.bosch.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosch.R
import com.bosch.databinding.CountryListFragmentBinding
import com.bosch.ui.adapter.CountryAdapter
import com.bosch.ui.vm.ViewModelMainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CountryListFragment : BaseFragment() {

    private val viewModelMailActivity: ViewModelMainActivity by activityViewModels()

    @Inject
    lateinit var countryAdapter: CountryAdapter

    private lateinit var binding: CountryListFragmentBinding

    override fun getLayoutResource(): Int {
        return R.layout.country_list_fragment
    }

    override fun onCreateView(b: ViewDataBinding, savedInstanceState: Bundle?) {
        binding = (b as CountryListFragmentBinding)
        binding.lifecycleOwner = this
        binding.fragment = this
        binding.recyclerView.adapter = countryAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        countryAdapter.setItemDetailsListener {
            viewModelMailActivity.setSelectedCountry(it)
        }

        countryAdapter.setItemPinListener {
            viewModelMailActivity.pinCountry(it)
        }

        viewModelMailActivity.countriesLiveData.observe(viewLifecycleOwner) {
            countryAdapter.updateList(it)
        }
    }

    fun fetchRemoteCountries(view: View) {
        viewModelMailActivity.fetchRemoteCountries(view)
    }

    fun deleteCountries(view: View) {
        viewModelMailActivity.deleteCountries()
    }
}