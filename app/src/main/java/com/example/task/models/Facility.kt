package com.example.task.models

import androidx.room.Entity

@Entity(tableName = "facility")
data class Facility(
    val facility_id: String,
    val name: String,
    val options: List<Option>
)