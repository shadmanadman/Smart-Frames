package com.sam.adams.taskmanagerwidget.presentation.workers

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.runtime.collectAsState
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sam.adams.taskmanagerwidget.data.networking.ConfigurationSuccessResponse
import com.sam.adams.taskmanagerwidget.domain.usecase.RunTasksUseCase
import com.sam.adams.taskmanagerwidget.extentions.convertToSeconds
import com.sam.adams.taskmanagerwidget.extentions.convertToTasksList
import com.sam.adams.taskmanagerwidget.presentation.view.TaskManagerWidget
import com.sam.adams.taskmanagerwidget.presentation.view.actions.A_TASK_IS_FINISHED
import com.sam.adams.taskmanagerwidget.presentation.view.actions.A_TASK_IS_RUNNING
import com.sam.adams.taskmanagerwidget.presentation.view.actions.A_TASK_IS_WAITING
import com.sam.adams.taskmanagerwidget.presentation.view.actions.SelectedTask
import com.sam.adams.taskmanagerwidget.presentation.view.actions.Status
import com.sam.adams.taskmanagerwidget.presentation.view.actions.VibrationTime
import com.sam.adams.taskmanagerwidget.presentation.viewmodels.DataStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@HiltWorker
class RunTasksWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val runTasksUseCase: RunTasksUseCase
) : CoroutineWorker(context.applicationContext, workerParameters) {
    private val taskList = mutableListOf<ConfigurationSuccessResponse.Config.Task>()
    private var lastUrl = ""
    private var latestDelayTime = 0L
    private var latestVibrationTime = 0L

    override suspend fun doWork(): Result {
        return try {
            getTaskList(context)
            for (task in taskList) {
                lastUrl = task.url
                latestDelayTime = task.delay.toLong()
                latestVibrationTime = task.vibrationTime.toLong()
                try {
                    val response = runTasksUseCase.execute(task.url)
                } catch (_: Exception) {
                }
                if (latestVibrationTime > 0)
                    playBeepSound(latestVibrationTime.toInt())
                updateStatus(context, waitingMessage(latestDelayTime), latestVibrationTime)
                delay(latestDelayTime)
            }
            updateStatus(context, A_TASK_IS_FINISHED)
            Result.success()
        } catch (e: Exception) {
            updateStatus(context, A_TASK_IS_FINISHED)
            e.printStackTrace()
            Result.success()
        }
    }

    private fun playBeepSound(duration: Int) {
        val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 200)
        toneGen1.startTone(ToneGenerator.TONE_DTMF_1, duration)
    }

    private fun waitingMessage(lastDelayedTime: Long): String {
        return if (lastDelayedTime > 0)
            "$A_TASK_IS_WAITING ${lastDelayedTime.convertToSeconds()}"
        else
            A_TASK_IS_RUNNING
    }

    private suspend fun getTaskList(context: Context) {
        taskList.clear()
        taskList.addAll(DataStore(context).getSelectedTask.first()?.convertToTasksList()!!)
    }

    private suspend fun updateStatus(context: Context, status: String, vibrationTime: Long = 0) {
        DataStore(context).saveStatus(status)
        GlanceAppWidgetManager(context).getGlanceIds(TaskManagerWidget::class.java)
            .forEach { glanceId ->
                updateAppWidgetState(context, glanceId) { prefs ->
                    prefs[Status] = status
                    prefs[VibrationTime] = vibrationTime.toString()
                }
            }
        TaskManagerWidget().updateAll(context)
    }
}