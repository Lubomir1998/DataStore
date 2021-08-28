package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.myDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DatastoreUtil(context: Context) {
    val name = context.myDataStore.data.map {
        it[stringPreferencesKey("key1")] ?: "Dave"
    }

    val age = context.myDataStore.data.map {
        it[intPreferencesKey("key2")] ?: 1
    }
}