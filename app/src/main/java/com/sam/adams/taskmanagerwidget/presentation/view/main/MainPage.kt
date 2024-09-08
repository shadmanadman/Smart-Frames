package com.sam.adams.taskmanagerwidget.presentation.view.main

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.sam.adams.taskmanagerwidget.R
import com.sam.adams.taskmanagerwidget.extentions.convertToConfigurationModel
import com.sam.adams.taskmanagerwidget.presentation.view.actions.Configurations
import com.sam.adams.taskmanagerwidget.presentation.view.actions.GetConfigurationClickAction
import com.sam.adams.taskmanagerwidget.presentation.view.actions.NOTHING_IS_RUNNING
import com.sam.adams.taskmanagerwidget.presentation.view.actions.SelectedTaskName
import com.sam.adams.taskmanagerwidget.presentation.view.actions.Status
import com.sam.adams.taskmanagerwidget.presentation.view.actions.VibrationTime
import com.sam.adams.taskmanagerwidget.presentation.view.actions.runGetConfiguration
import com.sam.adams.taskmanagerwidget.presentation.view.components.GlanceTasksItem
import com.sam.adams.taskmanagerwidget.presentation.view.theme.glanceBodyText14
import com.sam.adams.taskmanagerwidget.presentation.viewmodels.ConfigurationViewModel
import com.sam.adams.taskmanagerwidget.presentation.viewmodels.DataStore
import com.straiberry.android.widget.presentation.view.ui.theme.SimBlack


@Composable
fun MainPage() {
    val configurations = currentState(key = Configurations) ?: ""

    val status: String =
        DataStore(LocalContext.current).getStatus.collectAsState(initial = "").value!!
    val selectedTaskName: String =
        DataStore(LocalContext.current).getTaskName.collectAsState(initial = "").value!!

    if (configurations.isEmpty())
        runGetConfiguration(LocalContext.current)

    val configurationModel = configurations.convertToConfigurationModel()
    Column(
        modifier = GlanceModifier.fillMaxSize().background(ImageProvider(R.drawable.background))
            .cornerRadius(24.dp)
    ) {

        if (configurationModel.config.isNotEmpty())
            LazyVerticalGrid(
                gridCells = GridCells.Fixed(2),
                content = {
                    items(configurationModel.config.size) {
                        GlanceTasksItem(configurationModel = configurationModel.config[it])
                    }
                },
                modifier = GlanceModifier.fillMaxWidth().height(370.dp)
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
            )

        Text(
            text = "Status: $selectedTaskName $status",
            modifier = GlanceModifier.padding(12.dp),
            style = glanceBodyText14.copy(
                color = ColorProvider(
                    SimBlack
                )
            )
        )

        Box(modifier = GlanceModifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Button(
                text = "Get Configurations",
                onClick = actionRunCallback<GetConfigurationClickAction>(),
            )
        }

    }
}