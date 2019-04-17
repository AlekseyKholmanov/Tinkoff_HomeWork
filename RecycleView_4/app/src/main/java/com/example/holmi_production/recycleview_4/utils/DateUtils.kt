package com.example.holmi_production.recycleview_4.utils

import android.support.annotation.IntRange
import com.example.holmi_production.recycleview_4.NewsItems.HeaderItem
import com.example.holmi_production.recycleview_4.NewsItems.ListItem
import com.example.holmi_production.recycleview_4.NewsItems.NewsItem
import com.example.holmi_production.recycleview_4.db.entity.News
import java.text.DateFormat

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        var calendar = GregorianCalendar()
        var now = Date(System.currentTimeMillis())
        val currentMonth: Int
            @IntRange(from = 0, to = 11)
            get() {
                calendar.time = now
                return calendar.get(Calendar.MONTH)
            }

        val currentYear: Int
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

     fun setHeader(events: Map<Date, List<News>>): ArrayList<ListItem> {
        val news: ArrayList<ListItem> = arrayListOf()
        for (date in events.keys) {
            val header = HeaderItem(date)
            news.add(header)
            for (event in events.getValue(date)) {
                val item = NewsItem(event)
                news.add(item)
            }
        }
        return news
    }

     fun toMap(events: List<News>?): Map<Date, List<News>> {
        val map = TreeMap<Date, MutableList<News>>()
        if (events != null) {
            for (event in events) {
                var value: MutableList<News>? = map[event.date]
                if (value == null) {
                    value = ArrayList()
                    map[event.date] = value
                }
                value.add(event)
            }
        }
        return map.descendingMap()
    }
    fun reformateItem(items: List<News>?): ArrayList<ListItem> {
        return setHeader(toMap(items))
    }
}