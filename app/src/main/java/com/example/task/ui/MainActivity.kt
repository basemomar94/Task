package com.example.task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.databinding.ActivityMainBinding
import com.example.task.models.Option
import com.example.task.ui.adapters.OptionsAdapter
import com.example.task.util.DataConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OptionsAdapter.OptionsInterface {
    private val TAG = "database_Check"
    private var viewModel: FacilityViewModel? = null
    lateinit var binding: ActivityMainBinding
    private var typeAdapter: OptionsAdapter? = null
    private var roomsAdapter: OptionsAdapter? = null
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
        viewModel?.facilitiesLis?.collect { facilityList ->
            facilityList?.let {
                val optionsList =
                    it.filter { it.name == DataConstants.PROPERTY_TYPE }.firstOrNull()?.options
                val roomsList =
                    it.filter { it.name == DataConstants.NUMBER_OF_ROOMS }.firstOrNull()?.options
                withContext(Dispatchers.Main) {
                    populateTypeRv(optionsList)
                    populateRoomRv(roomsList)

                }
                //populateOptionsRv(roomsList, binding.roomsRv)

            }
        }
    }

    private fun populateTypeRv(list: List<Option>?) {
        typeAdapter = OptionsAdapter(list ?: listOf(), this, this, DataConstants.PROPERTY_TYPE)
        binding.propertyRv.adapter = typeAdapter
        binding.propertyRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.propertyRv.setHasFixedSize(true)
    }

    private fun populateRoomRv(list: List<Option>?) {
        roomsAdapter = OptionsAdapter(list ?: listOf(), this, this, DataConstants.NUMBER_OF_ROOMS)
        binding.roomsRv.adapter = roomsAdapter
        binding.roomsRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.roomsRv.setHasFixedSize(true)
    }

    override fun selectingTypeItem(option: Option, position: Int, list: List<Option>, type: String) {
        unSelectingOtherOptions(option, list, typeAdapter)

        Log.d(TAG, "type ${list.map { it.isSelected }}   ${option.name}")


    }

    override fun selectingRoomItem(
        option: Option,
        position: Int,
        list: List<Option>,
        type: String
    ) {
        unSelectingOtherOptions(option, list, roomsAdapter)
    }

    private fun unSelectingOtherOptions(selectedOption: Option, list: List<Option>?, adapter: OptionsAdapter?) {
        list?.forEachIndexed { index, option ->
            if (option != selectedOption) {
                option.isSelected = false
            } else {
                option.isSelected = !option.isSelected

            }
        }

        adapter?.notifyDataSetChanged()
    }


}