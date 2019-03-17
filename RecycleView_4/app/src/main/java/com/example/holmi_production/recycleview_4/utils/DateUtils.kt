package com.example.holmi_production.recycleview_4.utils

import android.support.annotation.IntRange
import java.text.DateFormat

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

class DateUtils {
    companion object {
        val currentMonth: Int
            @IntRange(from = 0, to = 11)
            get() {
                val now = Date(System.currentTimeMillis())
                val calendar = GregorianCalendar()
                calendar.time = now
                return calendar.get(Calendar.MONTH)
            }

        val currentYear: Int
            @IntRange(from = 0)
            get() {
                val now = Date(System.currentTimeMillis())
                val calendar = GregorianCalendar()
                calendar.time = now
                return calendar.get(Calendar.YEAR)
            }

        val currentDay: Int
            @IntRange(from = 0)
            get() {
                val now = Date(System.currentTimeMillis())
                val calendar = GregorianCalendar()
                calendar.time = now
                return calendar.get(Calendar.DAY_OF_MONTH)
            }
    }

    fun buildDate(@IntRange(from = 1, to = 31) dayOfMonth: Int): Date {
        return buildDate(dayOfMonth, currentMonth)
    }

    private fun buildDate(
        @IntRange(from = 1, to = 31) dayOfMonth: Int,
        @IntRange(from = 0, to = 11) month: Int,
        @IntRange(from = 0) year: Int = currentYear
    ): Date {
        val calendar = GregorianCalendar()
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    fun formatDate(date: Date): String {
        return DateFormat.getDateInstance(SimpleDateFormat.LONG, Locale("ru")).format(date)
    }

}