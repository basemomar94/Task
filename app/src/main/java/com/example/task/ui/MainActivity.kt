package com.example.task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.databinding.ActivityMainBinding
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.Option
import com.example.task.ui.adapters.FacilityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "database_Check"
    private var viewModel: FacilityViewModel? = null
    lateinit var binding: ActivityMainBinding
    private var facilityAdapter: FacilityAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[FacilityViewModel::class.java]
        getRemoteFacilities()
        observeRemoteFacilities()
        getLocalFacilities()
        observeLocalFacilities()

    }

    private fun getRemoteFacilities() {
        viewModel?.getFacilityResponse()
    }

    private fun observeRemoteFacilities() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel?.facilityResponse?.collect { facilityResponse ->
                Log.d(TAG, facilityResponse.toString())
                facilityResponse?.let {
                    Log.d("dao_bug", "got from observer $it")
                    viewModel?.savingDataToLocalDB(it)
                }
            }
        }
    }

    private fun getLocalFacilities() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel?.getFacilitiesListFromLocal()

    }

    private fun observeLocalFacilities() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel?.facilitiesLis?.collect {facilityList->
            facilityList?.let {
                populateRv(it)
            }
        }
    }

    private fun populateRv(list: List<Facility>) {
        facilityAdapter = FacilityAdapter(list, this)
        binding.recyclerview.adapter = facilityAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)
    }


}