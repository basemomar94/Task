package com.example.task.models


data class FacilityResponse(
    val exclusions: List<List<Exclusion>>,
    val facilities: List<Facility>
)