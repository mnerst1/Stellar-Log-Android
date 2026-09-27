package com.stellarlog.app
import android.app.Application
import androidx.room.Room
import com.stellarlog.app.data.*
class StellarApplication : Application() {
 lateinit var observations: ObservationRepository; private set
 lateinit var preferences: PreferencesRepository; private set
 override fun onCreate() { super.onCreate(); val db = Room.databaseBuilder(this, StellarDatabase::class.java, "stellar-log.db").build(); observations = ObservationRepository(db.observationDao()); preferences = PreferencesRepository(this) }
}