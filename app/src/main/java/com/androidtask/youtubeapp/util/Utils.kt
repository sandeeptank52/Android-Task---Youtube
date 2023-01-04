package com.androidtask.youtubeapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object Utils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun viewsCount(views: Int): String {
        return when {
            views >= 1000000000 -> {
                val formattedViews = views / 1000000
                "${formattedViews}B views"
            }
            views >= 1000000 -> {
                val formattedViews = views / 10000
                "${formattedViews}M views"
            }
            views >= 1000 -> {
                val formattedViews = views / 10000
                "${formattedViews}K views"
            }
            else -> {
                "$views views"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convert(publishedDay: String): String {
        val formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val publishedAt = LocalDateTime.parse(publishedDay, formatPattern)

        val currentDate = LocalDateTime.now().withNano(0)

        val differenceInSeconds = ChronoUnit.SECONDS.between(publishedAt, currentDate)
        val differenceInDays = ChronoUnit.DAYS.between(publishedAt, currentDate)
        val differenceInMonths = ChronoUnit.MONTHS.between(publishedAt, currentDate)
        return findDifference(differenceInSeconds, differenceInDays, differenceInMonths)
    }

    private fun findDifference(differenceInSeconds: Long, differenceInDays: Long, differenceInMonths:Long): String {
        val hours = differenceInSeconds / 3600
        when(differenceInDays) {
            in 21..31 -> {
                return "3 weeks ago"
            }
            in 14..20 -> {
                return "2 weeks ago"
            }
            in 2..13 -> {
                return "$differenceInDays days ago"
            }
            in 0..1 -> {
                return "$hours hours ago"
            }
        }
        if (differenceInMonths in 0..1) {
            return "$differenceInMonths month ago"
        }
        return "$differenceInMonths months ago"
    }
}