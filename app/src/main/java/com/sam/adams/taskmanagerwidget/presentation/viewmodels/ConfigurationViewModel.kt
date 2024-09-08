package com.sam.adams.taskmanagerwidget.presentation.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sam.adams.taskmanagerwidget.data.networking.ConfigurationSuccessResponse
import com.sam.adams.taskmanagerwidget.data.networking.sampleJson
import com.sam.adams.taskmanagerwidget.domain.usecase.GetConfigurationsUseCase
import com.sam.adams.taskmanagerwidget.presentation.view.actions.CONFIGURATION_UPDATED
import com.sam.adams.taskmanagerwidget.presentation.workers.CONFIGURATION_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    private val getConfigurationsUseCase: GetConfigurationsUseCase
) : ViewModel() {


    private val isConfigurationUpdated = mutableStateOf(false)
    val configurations = mutableStateOf(ConfigurationSuccessResponse())


    fun isConfigurationUpdated() = isConfigurationUpdated.value

    fun getConfigurations(context: Context) {
        viewModelScope.launch {
            val gson = Gson()
            val configurationResponse = gson.fromJson(sampleJson, ConfigurationSuccessResponse::class.java)

            configurations.value = configurationResponse
            //configurations.value = getConfigurationsUseCase.execute(CONFIGURATION_URL)
            DataStore(context).saveStatus(CONFIGURATION_UPDATED)
            DataStore(context).saveTaskName("")
            isConfigurationUpdated.value = true
        }
    }

}