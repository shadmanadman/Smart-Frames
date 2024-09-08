package com.sam.adams.taskmanagerwidget.presentation.view.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.currentState
import androidx.glance.layout.Box
import androidx.glance.layout.padding
import androidx.glance.unit.ColorProvider
import com.sam.adams.taskmanagerwidget.data.networking.ConfigurationSuccessResponse
import com.sam.adams.taskmanagerwidget.extentions.toStringJson
import com.sam.adams.taskmanagerwidget.extentions.vibrate
import com.sam.adams.taskmanagerwidget.presentation.view.actions.A_TASK_IS_RUNNING
import com.sam.adams.taskmanagerwidget.presentation.view.actions.VibrationTime
import com.sam.adams.taskmanagerwidget.presentation.view.actions.runTasks
import com.sam.adams.taskmanagerwidget.presentation.viewmodels.DataStore
import com.straiberry.android.widget.presentation.view.ui.theme.Blue600
import kotlinx.coroutines.runBlocking

@Composable
fun GlanceTasksItem(configurationModel: ConfigurationSuccessResponse.Config?) {
    val vibrationTime = currentState(key = VibrationTime) ?: "0"

    val context = LocalContext.current

    context.vibrate(vibrationTime.toLong())
    if (configurationModel != null)
        Box(modifier = GlanceModifier.padding(bottom = 10.dp)) {
            Button(
                text = configurationModel.name,
                onClick = {
                    runBlocking {
                        DataStore(context).saveSelectedTask(configurationModel.tasks.toStringJson())
                        DataStore(context).saveTaskName(configurationModel.name)
                        DataStore(context).saveStatus(A_TASK_IS_RUNNING)
                        Log.println(Log.VERBOSE,"A TASK BEGAN","${configurationModel.name} has began")
                        runTasks(context)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorProvider(
                        Blue600
                    )
                )

            )
        }

}


@Composable
fun TasksItem(
    configurationModel: ConfigurationSuccessResponse.Config?,
    context: Context
) {

    if (configurationModel != null)
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(bottom = 10.dp)) {
            Column {
                androidx.compose.material.Button(
                    onClick = {
                        runBlocking {
                            DataStore(context).saveSelectedTask(configurationModel.tasks.toStringJson())
                            DataStore(context).saveTaskName(configurationModel.name)
                            DataStore(context).saveStatus(A_TASK_IS_RUNNING)
                            Log.println(Log.VERBOSE,"A TASK BEGAN","${configurationModel.name} has began")
                            runTasks(context)
                        }
                    }, colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor =
                        Blue600
                    )
                ) {
                    androidx.compose.material.Text(text = configurationModel.name)
                }
            }

        }

}