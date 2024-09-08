package com.sam.adams.taskmanagerwidget.data.repo

import com.sam.adams.taskmanagerwidget.data.networking.ConfigurationSuccessResponse
import com.sam.adams.taskmanagerwidget.data.networking.TasksApi
import com.sam.adams.taskmanagerwidget.domain.repo.TasksRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksRepo @Inject constructor(
    private val tasksApi: TasksApi
):TasksRepo {
    override suspend fun getConfigurations(url: String): ConfigurationSuccessResponse =
        tasksApi.getConfigurations(url)

    override suspend fun runTask(url: String) {
        tasksApi.runTasks(url)
    }
}