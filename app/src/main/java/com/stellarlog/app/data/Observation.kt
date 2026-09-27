package com.stellarlog.app.data
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "observations")
data class Observation(@PrimaryKey(autoGenerate = true) val id: Long = 0, val targetId: String, val targetName: String, val category: String, val observedAt: Long = System.currentTimeMillis(), val location: String = "", val equipment: String = "", val rating: Int = 3, val notes: String = "", val isFavorite: Boolean = false)