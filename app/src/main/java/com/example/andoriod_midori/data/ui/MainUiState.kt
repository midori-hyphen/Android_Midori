package com.example.andoriod_midori.data.ui

import androidx.compose.runtime.Stable
import com.example.andoriod_midori.components.NavigationItem
import com.example.andoriod_midori.data.models.*

@Stable
data class MainUiState(
    val userInfo: UserInfo = UserInfo.createSample(),
    val calendarData: WeeklyCalendarData = WeeklyCalendarData.createSampleWeek(),
    val musicData: TodayMusicData = TodayMusicData.createSample(),
    val mealData: MealData = MealData.createSample(),
    val announcementData: AnnouncementData = AnnouncementData.createSample(),
    val widgets: List<WidgetData> = emptyList(),
    val currentNavigation: NavigationItem = NavigationItem.HOME,
    val loadingState: UiState<Unit> = UiState.Loading,
    val widgetOperationState: UiState<Unit> = UiState.Idle,
    val isEditMode: Boolean = false,
    val showWidgetPicker: Boolean = false,
    val showWidgetSettings: Boolean = false,
    val selectedWidgetForSettings: WidgetData? = null
) {
    companion object {
        fun initial() = MainUiState(loadingState = UiState.Loading)
    }
    
    val isLoading: Boolean get() = loadingState.isLoading()
    val hasError: Boolean get() = loadingState.isError() || widgetOperationState.isError()
    val errorMessage: String? get() = loadingState.getErrorOrNull()?.message 
        ?: widgetOperationState.getErrorOrNull()?.message
}

sealed interface MainUiAction {
    
    data object RefreshData : MainUiAction
    data object ClearError : MainUiAction
    
    data class NavigateTo(val item: NavigationItem) : MainUiAction
    
    data class PlayMusic(val song: MusicData) : MainUiAction
    
    data object OpenLaundry : MainUiAction
    data object OpenChat : MainUiAction
    data object OpenNotifications : MainUiAction
    
    data object ToggleEditMode : MainUiAction
    data object EnableEditMode : MainUiAction
    data object DisableEditMode : MainUiAction
    
    data object ShowWidgetPicker : MainUiAction
    data object HideWidgetPicker : MainUiAction
    
    data class ShowWidgetSettings(val widget: WidgetData) : MainUiAction
    data object HideWidgetSettings : MainUiAction
    data class UpdateWidgetSettings(val widgetId: String, val settings: WidgetSettings) : MainUiAction
    
    data class AddWidget(val widgetType: WidgetData.WidgetType) : MainUiAction
    data class UpdateWidget(val widget: WidgetData) : MainUiAction
    data class DeleteWidget(val widgetId: String) : MainUiAction
    data class ReorderWidgets(val widgets: List<WidgetData>) : MainUiAction
    data class ResizeWidget(val widgetId: String, val newType: WidgetData.WidgetType) : MainUiAction
    
    data class HandleError(val error: MidoriError) : MainUiAction
} 