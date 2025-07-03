package com.example.andoriod_midori.data.repository

import com.example.andoriod_midori.data.models.WidgetData
import com.example.andoriod_midori.data.models.UserInfo
import com.example.andoriod_midori.domain.common.Result
import com.example.andoriod_midori.data.ui.MidoriError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface MainRepository {
    fun getWidgets(): Flow<Result<List<WidgetData>>>
    suspend fun addWidget(widget: WidgetData): Result<Unit>
    suspend fun updateWidget(widget: WidgetData): Result<Unit>
    suspend fun deleteWidget(widgetId: String): Result<Unit>
    suspend fun reorderWidgets(widgets: List<WidgetData>): Result<Unit>
    fun getUserInfo(): Flow<Result<UserInfo>>
}

@Singleton
class MainRepositoryImpl @Inject constructor() : MainRepository {

    private val mutex = Mutex()
    
    private val _widgetsFlow = MutableStateFlow<Result<List<WidgetData>>>(Result.Loading)
    private var widgetsCache: MutableList<WidgetData> = mutableListOf()
    
    private val _userInfoFlow = MutableStateFlow<Result<UserInfo>>(Result.Loading)
    private var userInfoCache: UserInfo? = null
    
    init {
        initializeData()
    }

    private fun initializeData() {
        Timber.d("Initializing repository data")
        
        widgetsCache.apply {
            clear()
            add(WidgetData.Music(
                id = "music_widget_1",
                type = WidgetData.WidgetType.MUSIC_BIG
            ))
            add(WidgetData.Meal(
                id = "meal_widget_1", 
                type = WidgetData.WidgetType.MEAL
            ))
            add(WidgetData.Announcement(
                id = "announcement_widget_1",
                type = WidgetData.WidgetType.ANNOUNCEMENT_SMALL
            ))
        }
        _widgetsFlow.value = Result.Success(widgetsCache.toList())
        
        userInfoCache = UserInfo.createSample()
        _userInfoFlow.value = Result.Success(userInfoCache!!)
        
        Timber.d("Repository initialized with ${widgetsCache.size} widgets")
    }

    override fun getWidgets(): Flow<Result<List<WidgetData>>> {
        return _widgetsFlow.asStateFlow()
    }

    override suspend fun addWidget(widget: WidgetData): Result<Unit> {
        return mutex.withLock {
            try {
                Timber.d("Adding widget: ${widget.id}")
                
                if (widgetsCache.any { it.id == widget.id }) {
                    return Result.Error(
                        MidoriError.WidgetError(widget.id, "Widget already exists")
                    )
                }
                
                delay(100)
                
                widgetsCache.add(widget)
                _widgetsFlow.value = Result.Success(widgetsCache.toList())
                
                Timber.d("Successfully added widget: ${widget.id}")
                Result.Success(Unit)
                
            } catch (e: Exception) {
                Timber.e(e, "Failed to add widget: ${widget.id}")
                Result.Error(MidoriError.NetworkError("Failed to add widget"))
            }
        }
    }

    override suspend fun updateWidget(widget: WidgetData): Result<Unit> {
        return mutex.withLock {
            try {
                Timber.d("Updating widget: ${widget.id}")
                
                val index = widgetsCache.indexOfFirst { it.id == widget.id }
                if (index == -1) {
                    return Result.Error(
                        MidoriError.WidgetError(widget.id, "Widget not found")
                    )
                }
                
                delay(100)
                
                widgetsCache[index] = widget
                _widgetsFlow.value = Result.Success(widgetsCache.toList())
                
                Timber.d("Successfully updated widget: ${widget.id}")
                Result.Success(Unit)
                
            } catch (e: Exception) {
                Timber.e(e, "Failed to update widget: ${widget.id}")
                Result.Error(MidoriError.NetworkError("Failed to update widget"))
            }
        }
    }

    override suspend fun deleteWidget(widgetId: String): Result<Unit> {
        return mutex.withLock {
            try {
                Timber.d("Deleting widget: $widgetId")
                
                val removed = widgetsCache.removeAll { it.id == widgetId }
                if (!removed) {
                    return Result.Error(
                        MidoriError.WidgetError(widgetId, "Widget not found")
                    )
                }
                
                delay(100)
                
                _widgetsFlow.value = Result.Success(widgetsCache.toList())
                
                Timber.d("Successfully deleted widget: $widgetId")
                Result.Success(Unit)
                
            } catch (e: Exception) {
                Timber.e(e, "Failed to delete widget: $widgetId")
                Result.Error(MidoriError.NetworkError("Failed to delete widget"))
            }
        }
    }

    override suspend fun reorderWidgets(widgets: List<WidgetData>): Result<Unit> {
        return mutex.withLock {
            try {
                Timber.d("Reordering ${widgets.size} widgets")
                
                val existingIds = widgetsCache.map { it.id }.toSet()
                val newIds = widgets.map { it.id }.toSet()
                
                if (existingIds != newIds) {
                    return Result.Error(
                        MidoriError.DataError("Widget list mismatch during reorder")
                    )
                }
                
                delay(150)
                
                widgetsCache.clear()
                widgetsCache.addAll(widgets)
                _widgetsFlow.value = Result.Success(widgetsCache.toList())
                
                Timber.d("Successfully reordered widgets")
                Result.Success(Unit)
                
            } catch (e: Exception) {
                Timber.e(e, "Failed to reorder widgets")
                Result.Error(MidoriError.NetworkError("Failed to reorder widgets"))
            }
        }
    }

    override fun getUserInfo(): Flow<Result<UserInfo>> {
        return _userInfoFlow.asStateFlow()
    }
    
    suspend fun refreshData(): Result<Unit> {
        return mutex.withLock {
            try {
                Timber.d("Refreshing repository data")
                
                delay(500)
                
                _widgetsFlow.value = Result.Success(widgetsCache.toList())
                userInfoCache?.let { 
                    _userInfoFlow.value = Result.Success(it)
                }
                
                Timber.d("Data refresh completed")
                Result.Success(Unit)
                
            } catch (e: Exception) {
                Timber.e(e, "Failed to refresh data")
                Result.Error(MidoriError.NetworkError("Failed to refresh data"))
            }
        }
    }
    
    fun clearCache() {
        Timber.d("Clearing repository cache")
    }
} 