package com.stellarlog.app.data
import kotlinx.coroutines.flow.Flow
class ObservationRepository(private val dao: ObservationDao) {
 val observations: Flow<List<Observation>> = dao.observeAll()
 suspend fun save(item: Observation) = dao.insert(item)
 suspend fun delete(item: Observation) = dao.delete(item)
 suspend fun toggleFavorite(id: Long) = dao.toggleFavorite(id)
}