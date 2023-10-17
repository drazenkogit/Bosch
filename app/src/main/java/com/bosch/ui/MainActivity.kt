package com.bosch.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.bosch.R
import com.bosch.databinding.MainActivityBinding
import com.bosch.domain.models.ApiResponse
import com.bosch.ui.vm.ViewModelMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModelMailActivity: ViewModelMainActivity by viewModels()

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        viewModelMailActivity.selectedCountryLiveData.observe(this) {
            navController.navigate(R.id.action_countryListFragment_to_countryDetailsFragment)
        }

        viewModelMailActivity.fetchCountriesRemoteLiveData.observe(this) {
            when (it) {
                is ApiResponse.Error -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }

                is ApiResponse.Exception -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }

                is ApiResponse.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }

                is ApiResponse.Success -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}