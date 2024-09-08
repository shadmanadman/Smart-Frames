package com.sam.adams.taskmanagerwidget.presentation.view.actions

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.sam.adams.taskmanagerwidget.presentation.view.TaskManagerWidget
import com.sam.adams.taskmanagerwidget.presentation.viewmodels.DataStore
import com.sam.adams.taskmanagerwidget.presentation.workers.GetConfigurationWorker
import com.sam.adams.taskmanagerwidget.presentation.workers.RunTasksWorker

val Configurations = stringPreferencesKey("configurations")

val SelectedTask = stringPreferencesKey("selectedTask")
val SelectedTaskName = stringPreferencesKey("selectedTaskName")
val Status = stringPreferencesKey("status")
val VibrationTime = stringPreferencesKey("vibrationTime")

const val GET_CONFIGURATION_WORKER_NAME = "GetConfiguration"
const val RUN_TASKS_WORKER_NAME = "RunTasksWorker"

const val A_TASK_IS_RUNNING = "is Running"
const val NOTHING_IS_RUNNING = "Nothing"
const val A_TASK_IS_WAITING = "is Waiting"
const val A_TASK_IS_FINISHED = "is Finished"
const val CONFIGURATION_UPDATED = "Configuration updated"

class GetConfigurationClickAction : ActionCallback{
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        runGetConfiguration(context)
        TaskManagerWidget().updateAll(context)
    }
}

suspend fun getConfigurations(context: Context,configurations:String){
    GlanceAppWidgetManager(context).getGlanceIds(TaskManagerWidget::class.java).forEach {glanceId->
        updateAppWidgetState(context,glanceId){prefs->
            prefs[Configurations] = configurations
        }
    }
    TaskManagerWidget().updateAll(context)
}


fun runGetConfiguration(context: Context) {
    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            GET_CONFIGURATION_WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(GetConfigurationWorker::class.java)
        )
}

fun runTasks(context: Context) {
    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            RUN_TASKS_WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(RunTasksWorker::class.java)
        )
}