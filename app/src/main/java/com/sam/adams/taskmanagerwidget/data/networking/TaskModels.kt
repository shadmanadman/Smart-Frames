package com.sam.adams.taskmanagerwidget.data.networking

import com.google.gson.annotations.SerializedName

data class ConfigurationSuccessResponse(
    @SerializedName("config")
    val config: List<Config> = listOf()
) {
    data class Config(
        @SerializedName("name")
        val name: String = "",
        @SerializedName("tasks")
        val tasks: List<Task> = listOf()
    ) {
        data class Task(
            @SerializedName("delay")
            val delay: Int = 0,
            @SerializedName("url")
            val url: String = "",
            @SerializedName("vibration_time")
            val vibrationTime: Int = 0
        )
    }
}


val sampleJson = """
        {
          "config": [
            {
              "name": "open child room window",
              "tasks": [
                {
                  "delay": 500,
                  "url": "http://iot-device/api/open-child-room-window",
                  "vibration_time": 200
                },
                {
                  "delay": 1000,
                  "url": "http://iot-device/api/close-child-room-window",
                  "vibration_time": 300
                }
              ]
            },
            {
              "name": "open living room door",
              "tasks": [
                {
                  "delay": 700,
                  "url": "http://iot-device/api/open-living-room-door",
                  "vibration_time": 150
                },
                {
                  "delay": 1200,
                  "url": "http://iot-device/api/close-living-room-door",
                  "vibration_time": 250
                }
              ]
            },
            {
              "name": "open kitchen window",
              "tasks": [
                {
                  "delay": 600,
                  "url": "http://iot-device/api/open-kitchen-window",
                  "vibration_time": 180
                },
                {
                  "delay": 1100,
                  "url": "http://iot-device/api/close-kitchen-window",
                  "vibration_time": 220
                }
              ]
            },
            {
              "name": "turn_off_kitchen_light",
              "tasks": [
                {
                  "delay": 300,
                  "url": "http://iot-device/api/turn-off-kitchen-light",
                  "vibration_time": 100
                }
              ]
            },
            {
              "name": "turn_on_kitchen_light",
              "tasks": [
                {
                  "delay": 200,
                  "url": "http://iot-device/api/turn-on-kitchen-light",
                  "vibration_time": 100
                }
              ]
            },
            {
              "name": "turn_off_air_conditioner",
              "tasks": [
                {
                  "delay": 400,
                  "url": "http://iot-device/api/turn-off-air-conditioner",
                  "vibration_time": 150
                }
              ]
            },
            {
              "name": "turn_on_air_conditioner",
              "tasks": [
                {
                  "delay": 400,
                  "url": "http://iot-device/api/turn-off-air-conditioner",
                  "vibration_time": 150
                }
              ]
            }
          ]
        }
        """.trimIndent()