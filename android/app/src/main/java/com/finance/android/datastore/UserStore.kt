package com.finance.android.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("User")
        val KEY_USER = stringPreferencesKey("user")
    }

    val getAccessToken: Flow<String> = context.dataStore.data.map {
        it[KEY_USER] ?: ""
    }

    suspend fun setAccessToken(accessToken: String) {
        context.dataStore.edit {
            it[KEY_USER] = accessToken
        }
    }

    suspend fun clearAccessToken() {
        context.dataStore.edit {
            it.remove(KEY_USER)
        }
    }
}