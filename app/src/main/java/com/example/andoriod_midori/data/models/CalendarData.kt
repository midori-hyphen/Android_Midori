package com.example.andoriod_midori.data.models

data class CalendarDay(
    val dayOfWeek: String,
    val date: String,
    val isToday: Boolean = false
)

data class WeeklyCalendarData(
    val days: List<CalendarDay>
) {
    companion object {
        fun createSampleWeek(): WeeklyCalendarData {
            val weekDays = listOf("일", "월", "화", "수", "목", "금", "토")
            val dates = listOf("29", "30", "1", "2", "3", "4", "5")
            
            val days = weekDays.mapIndexed { index, dayOfWeek ->
                CalendarDay(
                    dayOfWeek = dayOfWeek,
                    date = dates[index],
                    isToday = index == 0
                )
            }
            
            return WeeklyCalendarData(days)
        }
    }
} 