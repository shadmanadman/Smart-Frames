package com.sam.adams.taskmanagerwidget.presentation.view.configuration

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sam.adams.taskmanagerwidget.presentation.view.components.TasksItem
import com.sam.adams.taskmanagerwidget.presentation.viewmodels.ConfigurationViewModel
import com.sam.adams.taskmanagerwidget.presentation.viewmodels.DataStore
import com.straiberry.android.widget.presentation.view.ui.theme.Blue600
import com.straiberry.android.widget.presentation.view.ui.theme.White200
import com.straiberry.android.widget.presentation.view.ui.theme.WidgetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfigureActivity : ComponentActivity() {

    private var appWidgetId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        setContent {
            WidgetTheme {
                val context = LocalContext.current
                val configurationViewModel: ConfigurationViewModel = hiltViewModel()
                val status: String =
                    DataStore(context).getStatus.collectAsState(initial = "").value!!
                val selectedTaskName: String =
                    DataStore(context).getTaskName.collectAsState(initial = "").value!!

                if (configurationViewModel.isConfigurationUpdated().not()) {
                    configurationViewModel.getConfigurations(context)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = White200
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        if (configurationViewModel.configurations.value.config.isNotEmpty())
                            androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
                                columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
                                content = {
                                    items(configurationViewModel.configurations.value.config.size) {
                                        Column {
                                            TasksItem(
                                                configurationModel = configurationViewModel.configurations.value.config[it],
                                                context
                                            )
                                        }
                                    }

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1F)
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 20.dp,
                                        bottom = 20.dp
                                    )
                            )

                        androidx.compose.material.Text(
                            text = "Status: $selectedTaskName $status",
                            modifier = Modifier.padding(12.dp),
                            style = TextStyle(fontSize = 14.sp)
                        )

                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 40.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            androidx.compose.material.Button(
                                onClick = {
                                    configurationViewModel.getConfigurations(context)
                                }, colors = androidx.compose.material.ButtonDefaults.buttonColors(
                                    backgroundColor =
                                    Blue600
                                )
                            ) {
                                androidx.compose.material.Text(text = "Get configurations")
                            }
                        }

                    }
                }
            }
        }


        onBackPressedDispatcher.addCallback(this) {
            sendOkForWidgetOnBackPressed(this@ConfigureActivity, appWidgetId = appWidgetId)
        }

    }


    override fun onPause() {
        sendOkForWidgetOnBackPressed(this@ConfigureActivity, appWidgetId = appWidgetId)
        super.onPause()
    }
}

fun sendOkForWidgetOnBackPressed(activity: Activity, appWidgetId: Int) {

    val resultValue = Intent()
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    activity.setResult(ComponentActivity.RESULT_OK, resultValue)
    activity.finish()
}

