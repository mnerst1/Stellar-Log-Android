package com.stellarlog.app.data
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
private val Context.stellarPrefs by preferencesDataStore(name = "stellar_settings")
class PreferencesRepository(private val context: Context) {
 private val themeKey = stringPreferencesKey("theme"); private val langKey = stringPreferencesKey("language")
 val settings = context.stellarPrefs.data.map { AppSettings(it[themeKey] ?: "system", it[langKey] ?: "en") }
 suspend fun setTheme(value: String) { context.stellarPrefs.edit { it[themeKey] = value } }
 suspend fun setLanguage(value: String) { context.stellarPrefs.edit { it[langKey] = value } }
}
data class AppSettings(val theme: String = "system", val language: String = "en")