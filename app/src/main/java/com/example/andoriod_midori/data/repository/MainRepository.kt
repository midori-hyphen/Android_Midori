package com.example.andoriod_midori.data.repository

import com.example.andoriod_midori.data.models.*
import kotlinx.coroutines.delay

interface MainRepository {
    suspend fun getUserInfo(): UserInfo
    suspend fun getCalendarData(): WeeklyCalendarData
    suspend fun getMusicData(): TodayMusicData
    suspend fun getMealData(): MealData
    suspend fun getAnnouncementData(): AnnouncementData
}

class MainRepositoryImpl : MainRepository {
    
    override suspend fun getUserInfo(): UserInfo {
        delay(100)
        return UserInfo.createSample()
    }
    
    override suspend fun getCalendarData(): WeeklyCalendarData {
        delay(100)
        return WeeklyCalendarData.createSampleWeek()
    }
    
    override suspend fun getMusicData(): TodayMusicData {
        delay(100)
        return TodayMusicData.createSample()
    }
    
    override suspend fun getMealData(): MealData {
        delay(100)
        return MealData.createSample()
    }
    
    override suspend fun getAnnouncementData(): AnnouncementData {
        delay(100)
        return AnnouncementData.createSample()
    }
} 