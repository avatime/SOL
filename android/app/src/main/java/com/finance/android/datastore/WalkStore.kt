package com.finance.android.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import java.time.LocalDate

class WalkStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Walk")
        val KEY_ACTIVE = intPreferencesKey("active")
    }

    fun isActive(): Flow<Boolean> {
        return getValue(KEY_ACTIVE).map { it == 1 }
    }

    suspend fun setActive(active: Boolean) {
        setValue(KEY_ACTIVE, if (active) 1 else 0)
    }

    suspend fun setCount(count: Int) {
        setValue(getCountKey(), count)
    }

    fun getCount(): Flow<Int> {
        return getValue(getCountKey())
    }

    private fun getCountKey(): Preferences.Key<Int> {
        return intPreferencesKey(
            "count-" + LocalDate.now().let {
                "${it.year}-${it.month}-${it.dayOfMonth}"
            }
        )
    }

    private fun getValue(key: Preferences.Key<Int>): Flow<Int> {
        return context.dataStore.data.map {
            it[key] ?: 0
        }
            .take(1)
    }

    private suspend fun setValue(
        key: Preferences.Key<Int>,
        value: Int
    ): WalkStore {
        context.dataStore.edit {
            it[key] = value
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
