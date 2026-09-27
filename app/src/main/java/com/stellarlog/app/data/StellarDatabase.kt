package com.stellarlog.app.data
import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Observation::class], version = 1, exportSchema = true)
abstract class StellarDatabase : RoomDatabase() { abstract fun observationDao(): ObservationDao }