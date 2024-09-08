package com.sam.adams.taskmanagerwidget.presentation.viewmodels

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.sam.adams.taskmanagerwidget.presentation.view.actions.SelectedTask
import com.sam.adams.taskmanagerwidget.presentation.view.actions.SelectedTaskName
import com.sam.adams.taskmanagerwidget.presentation.view.actions.Status
import com.sam.adams.taskmanagerwidget.presentation.view.actions.VibrationTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(val context: Context) {

    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("screens")
    }


    val getSelectedTask: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[SelectedTask] ?: ""
        }

    suspend fun saveSelectedTask(selectedTask: String) {
        context.dataStore.edit { preferences ->
            preferences[SelectedTask] = selectedTask
        }
    }

    val getStatus: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[Status] ?: ""
        }

    suspend fun saveStatus(status: String) {
        context.dataStore.edit { preferences ->
            preferences[Status] = status
        }
    }

    val getTaskName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[SelectedTaskName] ?: ""
        }

    suspend fun saveTaskName(taskName: String) {
        context.dataStore.edit { preferences ->
            preferences[SelectedTaskName] = taskName
        }
    }
}