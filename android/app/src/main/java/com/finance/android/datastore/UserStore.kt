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

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("User")
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val KEY_USER_ID = stringPreferencesKey("user_id")
        val KEY_USER_NAME = stringPreferencesKey("user_name")
        val KEY_PASSWORD = stringPreferencesKey("password")
        val KEY_USE_BIO = stringPreferencesKey("use_bio")
        val KEY_PHONE_NUMBER = stringPreferencesKey("phone_number")
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
    ): UserStore {
        if (value.isNotEmpty()) {
            context.dataStore.edit {
                it[key] = value
            }
        }
        return this
    }

    suspend fun clearValue(key: Preferences.Key<String>): UserStore {
        context.dataStore.edit {
            it.remove(key)
        }
        return this
    }
}
