package com.sam.adams.taskmanagerwidget.presentation.view

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import com.sam.adams.taskmanagerwidget.presentation.view.main.MainPage

class TaskManagerWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            MainPage()
        }
    }
}

class TaskManagerWidgetReceiver(override val glanceAppWidget: GlanceAppWidget = TaskManagerWidget()) :
    GlanceAppWidgetReceiver()