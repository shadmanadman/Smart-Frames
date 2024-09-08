package com.sam.adams.taskmanagerwidget.extentions

import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.google.gson.Gson
import com.sam.adams.taskmanagerwidget.data.networking.ConfigurationSuccessResponse


fun String.convertToConfigurationModel(): ConfigurationSuccessResponse {
    return Gson().fromJson(this, ConfigurationSuccessResponse::class.java)
        ?: ConfigurationSuccessResponse()
}

fun ConfigurationSuccessResponse.convertToString(): String {
    return Gson().toJson(this)
}

fun String.convertToTasksList(): List<ConfigurationSuccessResponse.Config.Task> {
    return Gson().fromJson(this, Array<ConfigurationSuccessResponse.Config.Task>::class.java)
        .asList()
}

fun List<ConfigurationSuccessResponse.Config.Task>.toStringJson(): String {
    return Gson().toJson(this)
}

fun Context.vibrate(vibrationTime: Long) {
    if (vibrationTime > 0L) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            this.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    vibrationTime,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(vibrationTime)
        }
    }
}

fun Long.convertToSeconds(): String {
    return ("${this / 1000L}s").toString()
}