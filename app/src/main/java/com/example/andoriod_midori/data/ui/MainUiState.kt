package com.example.andoriod_midori.data.ui

import com.example.andoriod_midori.components.NavigationItem
import com.example.andoriod_midori.data.models.*

data class MainUiState(
    val userInfo: UserInfo = UserInfo.createSample(),
    val calendarData: WeeklyCalendarData = WeeklyCalendarData.createSampleWeek(),
    val musicData: TodayMusicData = TodayMusicData.createSample(),
    val mealData: MealData = MealData.createSample(),
    val announcementData: AnnouncementData = AnnouncementData.createSample(),
    val currentNavigation: NavigationItem = NavigationItem.HOME,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    companion object {
        fun initial() = MainUiState()
    }
}

sealed class MainUiAction {
    object RefreshData : MainUiAction()
    data class PlayMusic(val song: MusicData) : MainUiAction()
    object OpenLaundry : MainUiAction()
    object OpenChat : MainUiAction()
    object OpenNotifications : MainUiAction()
    data class NavigateTo(val item: NavigationItem) : MainUiAction()
} 