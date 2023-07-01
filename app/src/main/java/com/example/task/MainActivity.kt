package com.example.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task.databinding.ActivityMainBinding
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.Option
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "database_Check"
    private var viewModel: FacilityViewModel? = null
    lateinit var binding: ActivityMainBinding
    private var options: List<Option> = listOf()
    private var exclusion: List<Exclusion> = listOf()
    private var facilities: List<Facility> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[FacilityViewModel::class.java]
        viewModel?.getOptionsList()
        viewModel?.getFacilityResponse()
        viewModel?.getExclusionList()
        /*  observeExclusion()
         observeFacilities()
        observeOptions()*/
        binding.text.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                Log.d(TAG, "clicked ${facilities.size}  ${options.size}  ${exclusion.size}")
                viewModel?.getFacilityResponse()
            }

        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel?.facilityResponse?.collect { facilityResponse ->
                facilityResponse?.let {
                    viewModel?.savingDataToLocalDB(it)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {

/*
            viewModel?.facilitiesLis?.collect {
                Log.d(TAG,"facilitiesLis from db $it")
            }

            viewModel?.exclusionList?.collect {
                Log.d(TAG,"exclusionList from db $it")
            }*/

        }

    }

    private fun observeOptions() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel?.option?.collect {
            if (it != null) {
                options = it
            }
            //  Log.d(TAG,"options from db $it")
        }
    }

    private fun observeFacilities() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel?.facilitiesLis?.collect {
            if (it != null) {
                facilities = it
            }
        }
    }

    private fun observeExclusion() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel?.exclusionList?.collect {
            if (it != null) {
                exclusion = it
            }
        }
    }
}