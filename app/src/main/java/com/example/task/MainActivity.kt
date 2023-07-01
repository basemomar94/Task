package com.example.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task.databinding.ActivityMainBinding
import com.example.task.models.Option
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "demo_task"
    private var viewModel: FacilityViewModel? = null
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[FacilityViewModel::class.java]
        binding.text.setOnClickListener {
            Log.d(TAG, "clicked")
            viewModel?.getFacilityResponse()

        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel?.facilityResponse?.collect { facilityResponse ->
                facilityResponse?.let {
                    viewModel?.savingDataToLocalDB(it)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel?.option?.collect {
                Log.d(TAG,"options from db $it")
            }
        }

    }
}