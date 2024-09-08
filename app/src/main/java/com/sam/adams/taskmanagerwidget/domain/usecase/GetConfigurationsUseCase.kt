package com.sam.adams.taskmanagerwidget.domain.usecase

import com.sam.adams.taskmanagerwidget.data.networking.TasksApi
import com.sam.adams.taskmanagerwidget.domain.repo.TasksRepo
import javax.inject.Inject

class GetConfigurationsUseCase @Inject constructor(private val tasksApi:TasksApi){
    suspend fun execute(url:String) = tasksApi.getConfigurations(url)
}