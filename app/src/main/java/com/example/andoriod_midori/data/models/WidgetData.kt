package com.example.andoriod_midori.data.models

import androidx.compose.runtime.Stable
import java.util.UUID

data class MusicData(
    val title: String,
    val artist: String,
    val albumArt: String? = null
)

data class TodayMusicData(
    val songs: List<MusicData>
) {
    companion object {
        fun createSample() = TodayMusicData(
            songs = listOf(
                MusicData(
                    title = "ライラック - Lilac",
                    artist = "Mrs. GREEN APPLE",
                    albumArt = "앨범 커버2.png"
                ),
                MusicData(
                    title = "개화 (Flowering)",
                    artist = "LUCY",
                    albumArt = "앨범 커버.png"
                )
            )
        )
    }
}

data class MealData(
    val mealType: String,
    val menu: String
) {
    companion object {
        fun createSample() = MealData(
            mealType = "조식",
            menu = "차조밥\n부대찌개\n어묵파프리카볶음\n로제전담\n깍두기"
        )
    }
}

data class AnnouncementData(
    val title: String,
    val content: String,
    val actionButtonText: String? = null
) {
    companion object {
        fun createSample() = AnnouncementData(
            title = "안내",
            content = "여름 방학 퇴실 안내\n세탁실 점검 안내",
            actionButtonText = "세탁하기"
        )
    }
}

data class UserInfo(
    val roomNumber: String,
    val name: String
) {
    val greeting: String
        get() = "${roomNumber} ${name}님 안녕하세요!"
    
    companion object {
        fun createSample() = UserInfo(
            roomNumber = "415호",
            name = "미도리"
        )
    }
}

enum class WidgetType {
    LARGE_MUSIC,
    SMALL_MUSIC,
    SMALL_ANNOUNCEMENT,
    MEAL,
    LARGE_ANNOUNCEMENT
}

sealed class WidgetData {
    abstract val id: String
    abstract val type: WidgetType
    
    enum class WidgetType {
        MUSIC_BIG,
        MUSIC_SMALL,
        MEAL,
        ANNOUNCEMENT_SMALL,
        ANNOUNCEMENT_BIG
    }
    
    data class Music(
        override val id: String,
        override val type: WidgetType,
        val settings: WidgetSettings.MusicWidgetSettings? = null
    ) : WidgetData()
    
    data class Meal(
        override val id: String,
        override val type: WidgetType,
        val settings: WidgetSettings.MealWidgetSettings? = null
    ) : WidgetData()
    
    data class Announcement(
        override val id: String,
        override val type: WidgetType,
        val settings: WidgetSettings.AnnouncementWidgetSettings? = null
    ) : WidgetData()
    
    fun copy(settings: WidgetSettings?): WidgetData {
        return when (this) {
            is Music -> this.copy(settings = settings as? WidgetSettings.MusicWidgetSettings)
            is Meal -> this.copy(settings = settings as? WidgetSettings.MealWidgetSettings)
            is Announcement -> this.copy(settings = settings as? WidgetSettings.AnnouncementWidgetSettings)
        }
    }
}

@Stable
data class WidgetItem(
    val id: String = UUID.randomUUID().toString(),
    val type: WidgetType,
    val title: String,
    val isRemovable: Boolean = true,
    val settings: WidgetSettings? = null
) {
    companion object {
        fun createDefaultWidgets(): List<WidgetItem> {
            val widgets = mutableListOf<WidgetItem>()
            
            val largeMusicWidget = WidgetItem(
                type = WidgetType.LARGE_MUSIC,
                title = "오늘의 기상송",
                isRemovable = true
            )
            widgets.add(largeMusicWidget.copy(
                settings = WidgetSettingsFactory.createDefault(largeMusicWidget.id, largeMusicWidget.type)
            ))
            
            val smallMusicWidget = WidgetItem(
                type = WidgetType.SMALL_MUSIC,
                title = "작은 기상송",
                isRemovable = true
            )
            widgets.add(smallMusicWidget.copy(
                settings = WidgetSettingsFactory.createDefault(smallMusicWidget.id, smallMusicWidget.type)
            ))
            
            val smallAnnouncementWidget = WidgetItem(
                type = WidgetType.SMALL_ANNOUNCEMENT,
                title = "작은 안내",
                isRemovable = true
            )
            widgets.add(smallAnnouncementWidget.copy(
                settings = WidgetSettingsFactory.createDefault(smallAnnouncementWidget.id, smallAnnouncementWidget.type)
            ))
            
            val mealWidget = WidgetItem(
                type = WidgetType.MEAL,
                title = "조식",
                isRemovable = true
            )
            widgets.add(mealWidget.copy(
                settings = WidgetSettingsFactory.createDefault(mealWidget.id, mealWidget.type)
            ))
            
            val largeAnnouncementWidget = WidgetItem(
                type = WidgetType.LARGE_ANNOUNCEMENT,
                title = "큰 안내",
                isRemovable = true
            )
            widgets.add(largeAnnouncementWidget.copy(
                settings = WidgetSettingsFactory.createDefault(largeAnnouncementWidget.id, largeAnnouncementWidget.type)
            ))
            
            return widgets
        }
    }
} 