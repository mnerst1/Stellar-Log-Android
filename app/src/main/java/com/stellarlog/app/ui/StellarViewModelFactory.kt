package com.stellarlog.app.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stellarlog.app.data.*
class StellarViewModelFactory(private val repo: ObservationRepository, private val prefs: PreferencesRepository) : ViewModelProvider.Factory {
 @Suppress("UNCHECKED_CAST") override fun <T : ViewModel> create(modelClass: Class<T>): T = StellarViewModel(repo, prefs) as T
}