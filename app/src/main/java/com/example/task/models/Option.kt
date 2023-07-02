package com.example.task.models

import androidx.room.Entity
import androidx.room.Ignore

@Entity
data class Option(
    var facilityOptionId: String = "",
    var icon: String = "",
    @androidx.room.PrimaryKey
    var id: String = "",
    var name: String = "",
    @Ignore
    var isSelected: Boolean = false,
    @Ignore
    var isEnabled: Boolean = true
)
