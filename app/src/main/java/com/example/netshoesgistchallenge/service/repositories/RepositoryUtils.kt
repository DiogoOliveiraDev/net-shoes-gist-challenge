package com.example.netshoesgistchallenge.service.repositories

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object RepositoryUtils {
    private const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'"
    private const val ONE_HOUR_IN_SECONDS = 3600
    private const val MULTIPLIER_DELAY = 1000

    @SuppressLint("SimpleDateFormat")
    fun nowISOAsString() =
        SimpleDateFormat(
            ISO_DATE_FORMAT
        ).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.format(
            Date(System.currentTimeMillis() - ONE_HOUR_IN_SECONDS * MULTIPLIER_DELAY)
        ).toString()
}
