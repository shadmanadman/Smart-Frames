package com.sam.adams.taskmanagerwidget.data.networking

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface TasksApi {
    @GET
    suspend fun getConfigurations(@Url url :String)
            : ConfigurationSuccessResponse

    @GET
    suspend fun runTasks(@Url url :String)
}