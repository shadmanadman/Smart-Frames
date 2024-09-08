package com.sam.adams.taskmanagerwidget.presentation.workers

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sam.adams.taskmanagerwidget.domain.usecase.GetConfigurationsUseCase
import com.sam.adams.taskmanagerwidget.extentions.convertToString
import com.sam.adams.taskmanagerwidget.presentation.view.TaskManagerWidget
import com.sam.adams.taskmanagerwidget.presentation.view.actions.CONFIGURATION_UPDATED
import com.sam.adams.taskmanagerwidget.presentation.view.actions.Status
import com.sam.adams.taskmanagerwidget.presentation.view.actions.getConfigurations
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val CONFIGURATION_URL = "https://www.vivalavida.be/screens/configuration.json"

@HiltWorker
class GetConfigurationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val getConfigurationsUseCase: GetConfigurationsUseCase
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            getConfigurations(
                context,
                getConfigurationsUseCase.execute(CONFIGURATION_URL).convertToString()
            )
            updateStatus(context, CONFIGURATION_UPDATED)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun updateStatus(context: Context,status:String){
        GlanceAppWidgetManager(context).getGlanceIds(TaskManagerWidget::class.java).forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[Status] = status
            }
        }
        TaskManagerWidget().updateAll(context)
    }
}