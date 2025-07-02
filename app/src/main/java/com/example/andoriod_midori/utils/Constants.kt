package com.example.andoriod_midori.utils

object Constants {
    object Assets {
        const val MIDORI_ICON = "file:///android_asset/icons.svg"
        const val MIRIM_LOGO = "file:///android_asset/mirimlogo.svg"
        const val HOME_ICON = "file:///android_asset/name=Home.svg"
        const val HEART_ICON = "file:///android_asset/name=Heart.svg"
        const val CALENDAR_ICON = "file:///android_asset/name=_Calender.svg"
        const val PERSON_ICON = "file:///android_asset/name=Person.svg"
        const val LISTENING_DINOSAURS = "file:///android_asset/name=ListeningDinosaurs.svg"
        const val MEALBOARD_ICON = "file:///android_asset/name=Mealboard.svg"
        const val ANOUNCE_ICON = "file:///android_asset/anounce.svg"
        const val ALBUM_COVER_1 = "file:///android_asset/앨범 커버.png"
        const val ALBUM_COVER_2 = "file:///android_asset/앨범 커버2.png"
    }
    
    object Dimensions {
        const val WIDGET_HEIGHT_SMALL = 120
        const val WIDGET_HEIGHT_MEDIUM = 140
        const val MUSIC_WIDGET_WIDTH = 318
        const val MUSIC_WIDGET_HEIGHT = 149
        const val MUSIC_WIDGET_RADIUS = 15
        const val MUSIC_WIDGET_PADDING = 11
        const val MUSIC_WIDGET_BLUR = 1.5f
        const val ALBUM_COVER_SIZE = 70
        const val WIDGET_CORNER_RADIUS = 16
        const val CALENDAR_DATE_SIZE = 32
        const val ICON_SIZE_SMALL = 16
        const val ICON_SIZE_MEDIUM = 24
        const val ICON_SIZE_LARGE = 48
        const val ICON_SIZE_XLARGE = 60
        const val PLAY_BUTTON_SIZE = 50
        const val BUTTON_HEIGHT = 36
        const val CHAT_BUTTON_WIDTH = 127
        const val CHAT_BUTTON_HEIGHT = 42
        
        const val SPLASH_LOGO_SIZE = 200
        const val SPLASH_BOTTOM_PADDING = 352
        const val SPLASH_LOGO_TEXT_SPACING = 16
        
        const val NAV_BUTTON_SIZE = 40
        const val NAV_ICON_SIZE = 24
        const val NAV_BAR_HEIGHT = 60
        const val NAV_BAR_RADIUS = 30
        const val NAV_BAR_BORDER = 1.5f
    }
    
    object Timing {
        const val SPLASH_DELAY = 1500L
    }
    
    object Strings {
        const val APP_NAME = "MIDORI"
        const val LOGIN_MESSAGE = "기숙사 일정부터 세탁실 현황, 외출 신청까지!"
        const val LOGIN_SUBTITLE = "스마트한 기숙사 생활을 경험해보세요."
        const val LOGIN_BUTTON = "미림마고 계정으로 로그인"
        const val GREETING = "415호 미도리님 안녕하세요!"
        const val TODAY_MUSIC = "오늘의 기상송"
        const val BREAKFAST = "조식"
        const val ANNOUNCEMENT = "안내"
        const val CHAT = "채팅하기"
        const val LAUNDRY = "세탁하기"
        const val PLAY = "재생"
    }
    
    object Data {
        val WEEK_DAYS = listOf("일", "월", "화", "수", "목", "금", "토")
        val SAMPLE_DATES = listOf("29", "30", "1", "2", "3", "4", "5")
        const val SAMPLE_SONG_TITLE = "개화 (Flowering)"
        const val SAMPLE_ARTIST = "LUCY"
        const val SAMPLE_MEAL = "차조밥\n부대찌개\n어묵파프리카볶음\n로제전담\n깍두기"
        const val SAMPLE_ANNOUNCEMENT = "여름 방학 퇴실 안내\n세탁실 점검 안내"
        const val ROOM_NUMBER = "415호"
        const val USER_NAME = "미도리"
    }
} 