package com.weather.photoCreator.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Test")
data class DummyModel(
    @PrimaryKey
    val id: Int
)
