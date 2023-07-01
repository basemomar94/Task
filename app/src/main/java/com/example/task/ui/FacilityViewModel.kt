package com.example.task.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.task.data.repository.MainRepository
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.FacilityResponse
import com.example.task.models.Option
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacilityViewModel @Inject constructor(
    private val repository: MainRepository
) :
    androidx.lifecycle.ViewModel() {

    private val TAG = "demo_task"
    private val _facilityResponseResponse: MutableStateFlow<FacilityResponse?> =
        MutableStateFlow(null)

    val facilityResponse = _facilityResponseResponse

    private val _options: MutableStateFlow<List<Option>?> =
        MutableStateFlow(null)
    val option = _options


    private val _facilitiesList: MutableStateFlow<List<Facility>?> =
        MutableStateFlow(null)
    val facilitiesLis = _facilitiesList

    private val _exclusionList: MutableStateFlow<List<Exclusion>?> =
        MutableStateFlow(null)
    val exclusionList = _exclusionList

    fun getFacilityResponse() = viewModelScope.launch {
        Log.d(TAG, "getting on view model")
        try {
            _facilityResponseResponse.emit(repository.getRetailsData())

        } catch (e: Exception) {
            Log.d(TAG, "getting facilities error ${e.message}")
        }
    }

    fun addOptionsList(option: List<Option>) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "options ${option.size}")
        repository.insertOptions(option)
    }

    fun addExclusion(exclusionsList: List<Exclusion>) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "exclusionsList ${exclusionsList.size}")
        repository.insertExclusion(exclusionsList)
    }

    fun addFacilities(facilitiesList: List<Facility>) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "facilitiesList ${facilitiesList.size}")
        repository.insertFacilities(facilitiesList)
    }


    fun getOptionsList() = viewModelScope.launch(Dispatchers.IO) {
        option.emit(repository.getOptions())
    }

    fun getFacilitiesListFromLocal() = viewModelScope.launch(Dispatchers.IO) {
        _facilitiesList.value = repository.getFacilities()
    }

    fun getExclusionList() = viewModelScope.launch(Dispatchers.IO) {
        _exclusionList.value = repository.getExclusion()
    }

    fun getDataFromDb() = viewModelScope.launch(Dispatchers.IO) {
        getOptionsList()
        getFacilitiesListFromLocal()
        getExclusionList()
    }

    fun savingDataToLocalDB(facilityResponse: FacilityResponse) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("dao_bug", "adding to db ${facilityResponse.exclusions}")
            // val facility = repository.getRetailsData()
            facilityResponse.facilities.forEach { _facility ->
                val optionsList = _facility.options
                addOptionsList(optionsList)
            }
            facilityResponse.exclusions.forEach { exclusions ->
                addExclusion(exclusions)
            }
            Log.d(
                "dao_bug",
                "from view model ${facilityResponse.facilities.size}  ${facilityResponse.facilities}}"
            )
            addFacilities(facilityResponse.facilities)
        }


}