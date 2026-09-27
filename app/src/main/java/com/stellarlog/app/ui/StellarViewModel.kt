package com.stellarlog.app.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stellarlog.app.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class StellarViewModel(private val repo: ObservationRepository, private val prefs: PreferencesRepository) : ViewModel() {
 val observations = repo.observations.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
 val settings = prefs.settings.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSettings())
 fun save(item: Observation) = viewModelScope.launch { repo.save(item) }
 fun delete(item: Observation) = viewModelScope.launch { repo.delete(item) }
 fun favorite(id: Long) = viewModelScope.launch { repo.toggleFavorite(id) }
 fun theme(value: String) = viewModelScope.launch { prefs.setTheme(value) }
 fun language(value: String) = viewModelScope.launch { prefs.setLanguage(value) }
}