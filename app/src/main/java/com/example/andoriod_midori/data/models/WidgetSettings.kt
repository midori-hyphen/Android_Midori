package com.example.andoriod_midori.data.models

import androidx.compose.ui.graphics.Color

sealed class WidgetSettings {
    abstract val widgetId: String
    abstract val title: String
    abstract val backgroundColor: Color?
    
    data class MusicWidgetSettings(
        override val widgetId: String,
        override val title: String = "오늘의 기상송",
        override val backgroundColor: Color? = null,
        val showAlbumArt: Boolean = true,
        val showArtistName: Boolean = true,
        val autoPlay: Boolean = false,
        val volume: Float = 0.8f,
        val displayMode: MusicDisplayMode = MusicDisplayMode.DUAL_ALBUM
    ) : WidgetSettings()
    
    data class MealWidgetSettings(
        override val widgetId: String,
        override val title: String = "조식",
        override val backgroundColor: Color? = null,
        val showMealType: Boolean = true,
        val showCalories: Boolean = false,
        val mealTimeAlert: Boolean = true,
        val fontSize: MealFontSize = MealFontSize.MEDIUM
    ) : WidgetSettings()
    
    data class AnnouncementWidgetSettings(
        override val widgetId: String,
        override val title: String = "안내",
        override val backgroundColor: Color? = null,
        val showIcon: Boolean = true,
        val enableNotifications: Boolean = true,
        val maxLines: Int = 3,
        val buttonText: String = "자세히 보기"
    ) : WidgetSettings()
}

enum class MusicDisplayMode(val displayName: String) {
    SINGLE_ALBUM("한 개 앨범"),
    DUAL_ALBUM("두 개 앨범"),
    LIST_VIEW("목록 보기")
}

enum class MealFontSize(val displayName: String, val scale: Float) {
    SMALL("작게", 0.8f),
    MEDIUM("보통", 1.0f),
    LARGE("크게", 1.2f)
}

enum class WidgetColorTheme(val displayName: String, val color: Color) {
    DEFAULT("기본", Color.Transparent),
    PURPLE("보라색", Color(0xFFCCB7E5)),
    BLUE("파란색", Color(0xFF89BFCA)), 
    ORANGE("주황색", Color(0xFFFFBE8D)),
    SKY_BLUE("하늘색", Color(0xFF89D6FF)),
    GREEN("초록색", Color(0xFF90EE90)),
    PINK("분홍색", Color(0xFFFFB6C1))
}

object WidgetSettingsFactory {
    fun createDefault(widgetId: String, type: WidgetType): WidgetSettings {
        return when (type) {
            WidgetType.LARGE_MUSIC -> WidgetSettings.MusicWidgetSettings(
                widgetId = widgetId,
                displayMode = MusicDisplayMode.DUAL_ALBUM
            )
            WidgetType.SMALL_MUSIC -> WidgetSettings.MusicWidgetSettings(
                widgetId = widgetId,
                title = "작은 기상송",
                displayMode = MusicDisplayMode.SINGLE_ALBUM
            )
            WidgetType.MEAL -> WidgetSettings.MealWidgetSettings(widgetId = widgetId)
            WidgetType.SMALL_ANNOUNCEMENT -> WidgetSettings.AnnouncementWidgetSettings(
                widgetId = widgetId,
                title = "작은 안내",
                maxLines = 2
            )
            WidgetType.LARGE_ANNOUNCEMENT -> WidgetSettings.AnnouncementWidgetSettings(
                widgetId = widgetId,
                title = "큰 안내",
                maxLines = 5
            )
        }
    }
} 