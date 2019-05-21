package com.example.holmi_production.recycleview_4.utils

import android.support.annotation.IntRange
import com.example.holmi_production.recycleview_4.NewsItems.HeaderContainer
import com.example.holmi_production.recycleview_4.NewsItems.NewsContainer
import com.example.holmi_production.recycleview_4.NewsItems.NewsType
import com.example.holmi_production.recycleview_4.model.NewsItemTitle
import java.text.DateFormat

import java.text.SimpleDateFormat
import java.util.*


object DateUtils {
    var calendar = GregorianCalendar()
    var now = Date(System.currentTimeMillis())
    private val currentMonth: Int
        @IntRange(from = 0, to = 11)
        get() {
            calendar.time = now
            return calendar.get(Calendar.MONTH)
        }

    private val currentYear: Int
        @IntRange(from = 0)
        get() {

            calendar.time = now
            return calendar.get(Calendar.YEAR)
        }

    val currentDay: Int
        @IntRange(from = 0)
        get() {
            calendar.time = now
            return calendar.get(Calendar.DAY_OF_MONTH)
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

    fun convertToDate(date: Long): Date {
        calendar.time = Date(date)
        return DateUtils.buildDate(
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR)
        )
    }

    fun formatDate(timeinMilis: Long): String {
        return DateFormat.getDateInstance(SimpleDateFormat.LONG, Locale("ru")).format(Date(timeinMilis))
    }

    private fun setHeader(events: Map<Long, List<NewsItemTitle>>): ArrayList<NewsContainer> {
        val news: ArrayList<NewsContainer> = arrayListOf()
        for (date in events.keys) {
            val header = HeaderContainer(date)
            news.add(header)
            for (event in events.getValue(date)) {
                val item = NewsType(event)
                news.add(item)
            }
        }
        return news
    }

    private fun toMap(events: List<NewsItemTitle>?): Map<Long, List<NewsItemTitle>> {
        val map = TreeMap<Long, MutableList<NewsItemTitle>>()
        if (events != null) {
            for (event in events) {
                var value: MutableList<NewsItemTitle>? = map[event.date.timeInMilliseconds]
                if (value == null) {
                    value = ArrayList()
                    map[event.date.timeInMilliseconds] = value
                }
                value.add(event)
            }
        }
        return map.descendingMap()
    }

    fun reformateItem(items: List<NewsItemTitle>?): ArrayList<NewsContainer> {
        return setHeader(toMap(items))
    }
}