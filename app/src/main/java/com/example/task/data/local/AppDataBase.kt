package com.example.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.task.models.Exclusion
import com.example.task.models.Facility
import com.example.task.models.Option
import com.example.task.models.OptionsConverter

@Database(
    entities = [Exclusion::class, Facility::class, Option::class], version = 1
)
@TypeConverters(OptionsConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val dao: AppDao
}