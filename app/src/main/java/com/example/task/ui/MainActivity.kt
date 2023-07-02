package com.example.task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ActivityMainBinding
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.Option
import com.example.task.ui.adapters.OptionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OptionsAdapter.OptionsInterface {
    private val TAG = "database_Check"
    private var viewModel: FacilityViewModel? = null
    private lateinit var binding: ActivityMainBinding
    private var typeAdapter: OptionsAdapter? = null
    private var roomsAdapter: OptionsAdapter? = null
    private var otherAdapter: OptionsAdapter? = null
    private var propertyTypeList: List<Option>? = null
    private var roomsList: List<Option>? = null
    private var otherFacilitiesList: List<Option>? = null
    private var selectedType: Option? = null
    private var selectedRoom: Option? = null
    private var selectedOtherFacility: Option? = null

    private var exclusionsList: List<List<Exclusion>>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[FacilityViewModel::class.java]
        getRemoteFacilities()
        observeRemoteFacilitiesResponse()
        getLocalFacilities()
        observeLocalFacilities()
        initAdapter()
        getExclusions()
        observeExclusions()

    }

    private fun getRemoteFacilities() {
        viewModel?.getFacilityResponse()
    }

    private fun observeRemoteFacilitiesResponse() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel?.facilityResponse?.collect { facilityResponse ->
                facilityResponse?.let {
                    Log.d("dao_bug", "got from observer ${it.exclusions}")
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
                initRvs(it)
            }
        }
    }

    private suspend fun initRvs(facilitiesList: List<Facility>) = withContext(Dispatchers.Main) {
        propertyTypeList =
            facilitiesList.firstOrNull { it.facility_id.toIntOrNull() == 1 }?.options
        roomsList =
            facilitiesList.firstOrNull { it.facility_id.toIntOrNull() == 2 }?.options
        otherFacilitiesList =
            facilitiesList.firstOrNull { it.facility_id.toIntOrNull() == 3 }?.options
        populateRv(propertyTypeList, typeAdapter, binding.propertyRv)
        populateRv(roomsList, roomsAdapter, binding.roomsRv)
        populateRv(otherFacilitiesList, otherAdapter, binding.othersRv)

    }

    private fun initAdapter() {
        typeAdapter = OptionsAdapter(listOf(), this, this, 1)
        roomsAdapter = OptionsAdapter(listOf(), this, this, 2)
        otherAdapter = OptionsAdapter(listOf(), this, this, 3)
    }

    private fun populateRv(
        list: List<Option>?,
        adapter: OptionsAdapter?,
        recyclerView: RecyclerView
    ) {
        recyclerView.adapter = adapter
        adapter?.updateList(list)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
    }

    override fun selectingTypeItem(
        option: Option,
        position: Int,
        list: List<Option>,
        facilityId: Int
    ) {
        unSelectingOtherOptions(option, list, typeAdapter)
        selectedType = option
    }

    override fun selectingRoomItem(
        option: Option,
        position: Int,
        list: List<Option>,
        facilityId: Int
    ) {
        unSelectingOtherOptions(option, list, roomsAdapter)
        selectedRoom = option
    }

    override fun selectingOtherItem(
        option: Option,
        position: Int,
        list: List<Option>,
        facilityId: Int
    ) {
        unSelectingOtherOptions(option, list, otherAdapter)
        selectedOtherFacility = option
    }

    private fun unSelectingOtherOptions(
        selectedOption: Option,
        list: List<Option>?,
        adapter: OptionsAdapter?
    ) {
        list?.forEach { option ->
            if (option != selectedOption) {
                option.isSelected = false
            } else {
                option.isSelected = !option.isSelected

            }
        }

        adapter?.notifyDataSetChanged()
        validateSelection(selectedOption)


    }

    private fun getExclusions() {
        viewModel?.getExclusionList()
    }

    private fun observeExclusions() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel?.exclusionList?.collect {
            exclusionsList = it?.exclusions

        }
    }

    private fun validateSelection(selected: Option): MutableList<Exclusion> {
        Log.d(TAG, "validate ${exclusionsList}")
        val disabledOptions: MutableList<Exclusion> = mutableListOf()
        Log.d(TAG,"selected $selected")
        for (list1 in exclusionsList!!) {
            Log.d(TAG,"list 1 $list1")
            val currentObject = list1.filter { it.facility_id == selected.facilityOptionId && it.options_id == selected.id }
            Log.d(TAG, "out side condition $currentObject")
            if (currentObject.isNotEmpty()) {
                Log.d(TAG, "inside condition $currentObject")
                val remainingOptions = list1.toMutableList()
                remainingOptions.removeIf { it.facility_id == selected.facilityOptionId && it.options_id == selected.id }
                disabledOptions.addAll(remainingOptions)
                Log.d(TAG, "remaining list $remainingOptions")
            }
        }
        Log.d(TAG,"disabled options $disabledOptions")
        return disabledOptions
    }
}








