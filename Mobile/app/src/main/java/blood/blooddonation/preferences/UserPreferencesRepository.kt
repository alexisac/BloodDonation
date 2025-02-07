package blood.blooddonation.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val id = stringPreferencesKey("id")
        val token = stringPreferencesKey("token")
        val finished = booleanPreferencesKey("finished")
    }

    private fun mapUserPreferences(preferences: Preferences) = UserPreferences(
        id = preferences[PreferencesKeys.id] ?: "",
        token = preferences[PreferencesKeys.token] ?: "",
        finish = preferences[PreferencesKeys.finished]?: false
    )

    suspend fun saveToken(userPreferences: UserPreferences) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.id] = userPreferences.id
            preferences[PreferencesKeys.token] = userPreferences.token
            preferences[PreferencesKeys.finished] = userPreferences.finish
        }
    }

    suspend fun saveTrigger(trigger: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.finished] = trigger
        }
    }

    suspend fun getId(): String {
        return dataStore.data
            .map { preferences -> preferences[PreferencesKeys.id] ?: "" }
            .first()
    }

    val userPreferencesStream: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { mapUserPreferences(it) }
}