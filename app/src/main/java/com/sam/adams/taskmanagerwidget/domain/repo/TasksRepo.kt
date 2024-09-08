package com.sam.adams.taskmanagerwidget.domain.repo

import com.sam.adams.taskmanagerwidget.data.networking.ConfigurationSuccessResponse

interface TasksRepo {
    suspend fun getConfigurations(url:String) : ConfigurationSuccessResponse
    suspend fun runTask(url:String)
}