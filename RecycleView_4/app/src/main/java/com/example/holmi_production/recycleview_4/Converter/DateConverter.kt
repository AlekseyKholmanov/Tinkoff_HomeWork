package com.example.holmi_production.recycleview_4.Converter

import android.arch.persistence.room.TypeConverter
import java.util.*

public class DateConverter{

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}