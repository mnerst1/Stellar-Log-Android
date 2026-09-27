package com.stellarlog.app.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao interface ObservationDao {
 @Query("SELECT * FROM observations ORDER BY observedAt DESC") fun observeAll(): Flow<List<Observation>>
 @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(item: Observation): Long
 @Delete suspend fun delete(item: Observation)
 @Query("UPDATE observations SET isFavorite = NOT isFavorite WHERE id = :id") suspend fun toggleFavorite(id: Long)
}