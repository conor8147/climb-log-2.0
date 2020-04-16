package com.coneill.climbit.controller

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun doShortVibrate(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(VIBRATE_DURATION)
    }
}

private const val VIBRATE_DURATION = 50L


