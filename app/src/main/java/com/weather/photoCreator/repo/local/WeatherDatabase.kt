package com.weather.photoCreator.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.photoCreator.models.DummyModel
import com.weather.photoCreator.repo.local.dao.WeatherDao

@Database(entities = [DummyModel::class] , version = 1 , exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}