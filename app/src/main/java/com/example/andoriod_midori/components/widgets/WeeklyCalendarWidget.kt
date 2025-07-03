package com.example.andoriod_midori.components.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.andoriod_midori.data.models.CalendarDay
import com.example.andoriod_midori.data.models.WeeklyCalendarData
import com.example.andoriod_midori.ui.theme.MidoriSpacing
import com.example.andoriod_midori.utils.Constants

@Composable
fun WeeklyCalendarWidget(
    calendarData: WeeklyCalendarData = WeeklyCalendarData.createSampleWeek(),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            calendarData.days.forEach { day ->
                Text(
                    text = day.dayOfWeek,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        
        Spacer(modifier = Modifier.height(MidoriSpacing.XS))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            calendarData.days.forEach { day ->
                CalendarDateItem(
                    day = day,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun CalendarDateItem(
    day: CalendarDay,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(MidoriSpacing.XXS),
        contentAlignment = Alignment.Center
    ) {
        if (day.isToday) {
            Box(
                modifier = Modifier
                    .size(Constants.Dimensions.CALENDAR_DATE_SIZE.dp)
                    .background(
                        MaterialTheme.colorScheme.onBackground,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.background
                )
            }
        } else {
            Text(
                text = day.date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
} 