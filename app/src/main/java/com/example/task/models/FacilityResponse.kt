package com.example.task.models


open class FacilityResponse(
    val exclusions: List<List<Exclusion>>,
    val facilities: List<Facility>
)