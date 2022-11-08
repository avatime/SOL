package com.finance.android.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class WalkStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Walk")
        val KEY_ACTIVE = stringPreferencesKey("active")
    }

    fun isActive(): Flow<Boolean> {
        return getValue(KEY_ACTIVE).map { it == "true" }
    }

    suspend fun setActive(active: Boolean) {
        setValue(KEY_ACTIVE, active.toString())
    }

    fun getValue(key: Preferences.Key<String>): Flow<String> {
        return context.dataStore.data.map {
            it[key] ?: ""
        }
            .take(1)
    }

    suspend fun setValue(
        key: Preferences.Key<String>,
        value: String
    ): WalkStore {
        if (value.isNotEmpty()) {
            context.dataStore.edit {
                it[key] = value
            }
        }
        return this
    }

    suspend fun clearValue(key: Preferences.Key<String>): WalkStore {
        context.dataStore.edit {
            it.remove(key)
        }
        return this
    }
}