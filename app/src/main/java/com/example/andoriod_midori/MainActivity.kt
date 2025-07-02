package com.example.andoriod_midori

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.andoriod_midori.components.CommonLayout
import com.example.andoriod_midori.components.NavigationItem
import com.example.andoriod_midori.components.widgets.*
import com.example.andoriod_midori.data.models.*
import com.example.andoriod_midori.data.repository.MainRepositoryImpl
import com.example.andoriod_midori.data.ui.MainUiAction
import com.example.andoriod_midori.data.ui.MainUiState
import com.example.andoriod_midori.ui.theme.Andoriod_midoriTheme
import com.example.andoriod_midori.utils.*
import kotlinx.coroutines.launch
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Andoriod_midoriTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var uiState by remember { mutableStateOf(MainUiState.initial()) }
    val repository = remember { MainRepositoryImpl() }
    val scope = rememberCoroutineScope()
    
    var isEditMode by remember { mutableStateOf(false) }
    var widgets by remember { mutableStateOf(WidgetItem.createDefaultWidgets()) }
    var showWidgetPicker by remember { mutableStateOf(false) }
    
    var selectedWidgetForSettings by remember { mutableStateOf<WidgetItem?>(null) }
    var showWidgetSettings by remember { mutableStateOf(false) }
    
    val dragDropState = rememberDragDropState()
    
    val handleAction: (MainUiAction) -> Unit = { action ->
        when (action) {
            MainUiAction.RefreshData -> {
                scope.launch {
                    uiState = uiState.copy(isLoading = true)
                    try {
                        val newUserInfo = repository.getUserInfo()
                        val newCalendarData = repository.getCalendarData()
                        val newMusicData = repository.getMusicData()
                        val newMealData = repository.getMealData()
                        val newAnnouncementData = repository.getAnnouncementData()
                        
                        uiState = uiState.copy(
                            userInfo = newUserInfo,
                            calendarData = newCalendarData,
                            musicData = newMusicData,
                            mealData = newMealData,
                            announcementData = newAnnouncementData,
                            isLoading = false,
                            error = null
                        )
                    } catch (e: Exception) {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
            }
            is MainUiAction.PlayMusic -> {
            }
            MainUiAction.OpenLaundry -> {
            }
            MainUiAction.OpenChat -> {
            }
            MainUiAction.OpenNotifications -> {
            }
            is MainUiAction.NavigateTo -> {
                uiState = uiState.copy(currentNavigation = action.item)
            }
        }
    }

    val addWidget: (WidgetType) -> Unit = { widgetType ->
        val newWidget = WidgetItem(
            type = widgetType,
            title = when (widgetType) {
                WidgetType.LARGE_MUSIC -> "큰 기상송"
                WidgetType.SMALL_MUSIC -> "작은 기상송"
                WidgetType.SMALL_ANNOUNCEMENT -> "작은 안내"
                WidgetType.MEAL -> "조식"
                WidgetType.LARGE_ANNOUNCEMENT -> "큰 안내"
            },
            settings = WidgetSettingsFactory.createDefault(UUID.randomUUID().toString(), widgetType)
        )
        widgets = widgets + newWidget
    }
    
    val onWidgetSettingsChanged: (WidgetSettings) -> Unit = { newSettings ->
        widgets = widgets.map { widget ->
            if (widget.id == newSettings.widgetId) {
                widget.copy(
                    title = newSettings.title,
                    settings = newSettings
                )
            } else {
                widget
            }
        }
    }
    
    val onWidgetClick: (WidgetItem) -> Unit = { widget ->
        if (!isEditMode) {
            selectedWidgetForSettings = widget
            showWidgetSettings = true
        }
    }
    
    val onSizeChanged: (String, WidgetType) -> Unit = { widgetId, newType ->
        widgets = widgets.map { widget ->
            if (widget.id == widgetId) {
                widget.copy(
                    type = newType,
                    settings = widget.settings?.let { settings ->
                        when {
                            newType == WidgetType.LARGE_MUSIC && settings is WidgetSettings.MusicWidgetSettings -> 
                                settings.copy(displayMode = MusicDisplayMode.DUAL_ALBUM)
                            newType == WidgetType.SMALL_MUSIC && settings is WidgetSettings.MusicWidgetSettings -> 
                                settings.copy(displayMode = MusicDisplayMode.SINGLE_ALBUM)
                            newType == WidgetType.LARGE_ANNOUNCEMENT && settings is WidgetSettings.AnnouncementWidgetSettings -> 
                                settings.copy(maxLines = 5)
                            newType == WidgetType.SMALL_ANNOUNCEMENT && settings is WidgetSettings.AnnouncementWidgetSettings -> 
                                settings.copy(maxLines = 2)
                            else -> WidgetSettingsFactory.createDefault(widgetId, newType)
                        }
                    } ?: WidgetSettingsFactory.createDefault(widgetId, newType)
                )
            } else {
                widget
            }
        }
    }
    
    val onDragStart: (String) -> Unit = { widgetId ->
    }
    
    val onDragEnd: (String, Offset) -> Unit = { draggedWidgetId, dragOffset ->
        val draggedIndex = widgets.indexOfFirst { it.id == draggedWidgetId }
        if (draggedIndex != -1) {
            val newIndex = calculateDropIndex(
                dragOffset = dragOffset,
                itemHeight = 200f,
                totalItems = widgets.size,
                containerHeight = 1000f
            )
            
            if (draggedIndex != newIndex) {
                widgets = widgets.move(draggedIndex, newIndex)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (isEditMode) {
                    Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { 
                                isEditMode = false 
                            },
                            onLongPress = {
                                showWidgetPicker = true
                            }
                        )
                    }
                } else Modifier
            )
    ) {
        CommonLayout(
            currentNavigation = uiState.currentNavigation,
            onNotificationClick = { handleAction(MainUiAction.OpenNotifications) },
            onNavigationClick = { navigationItem ->
                handleAction(MainUiAction.NavigateTo(navigationItem))
            },
            onChatClick = { handleAction(MainUiAction.OpenChat) }
        ) {
            item { 
                WeeklyCalendarWidget(calendarData = uiState.calendarData)
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            item { 
                GreetingMessage(userInfo = uiState.userInfo)
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            val processedWidgets = mutableListOf<Int>()
            widgets.forEachIndexed { index, widget ->
                if (index in processedWidgets) return@forEachIndexed
                
                when (widget.type) {
                    WidgetType.LARGE_MUSIC -> {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                EditableWidget(
                                    widgetItem = widget,
                                    isEditMode = isEditMode,
                                    onLongPress = { isEditMode = true },
                                    onDelete = { id -> 
                                        widgets = widgets.filter { it.id != id }
                                    },
                                    musicData = uiState.musicData,
                                    mealData = uiState.mealData,
                                    announcementData = uiState.announcementData,
                                    onPlayClick = { song -> handleAction(MainUiAction.PlayMusic(song)) },
                                    onActionClick = { handleAction(MainUiAction.OpenLaundry) },
                                    onWidgetClick = onWidgetClick,
                                    onSizeChanged = onSizeChanged,
                                    dragDropState = if (isEditMode) dragDropState else null,
                                    onDragStart = onDragStart,
                                    onDragEnd = onDragEnd
                                )
                            }
                        }
                        item { Spacer(modifier = Modifier.height(12.dp)) }
                    }
                    
                    WidgetType.SMALL_MUSIC, WidgetType.SMALL_ANNOUNCEMENT -> {
                        val nextWidget = widgets.getOrNull(index + 1)
                        val canPair = nextWidget?.type == WidgetType.SMALL_MUSIC || 
                                     nextWidget?.type == WidgetType.SMALL_ANNOUNCEMENT
                        
                        if (canPair && nextWidget != null) {
                            item {
                                WidgetRow(
                                    leftWidget = widget,
                                    rightWidget = nextWidget,
                                    isEditMode = isEditMode,
                                    onLongPress = { isEditMode = true },
                                    onDelete = { id -> 
                                        widgets = widgets.filter { it.id != id }
                                    },
                                    musicData = uiState.musicData,
                                    mealData = uiState.mealData,
                                    announcementData = uiState.announcementData,
                                    onPlayClick = { song -> handleAction(MainUiAction.PlayMusic(song)) },
                                    onActionClick = { handleAction(MainUiAction.OpenLaundry) },
                                    onWidgetClick = onWidgetClick,
                                    onSizeChanged = onSizeChanged,
                                    dragDropState = if (isEditMode) dragDropState else null,
                                    onDragStart = onDragStart,
                                    onDragEnd = onDragEnd
                                )
                            }
                            processedWidgets.add(index + 1)
                        } else {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    EditableWidget(
                                        widgetItem = widget,
                                        isEditMode = isEditMode,
                                        onLongPress = { isEditMode = true },
                                        onDelete = { id -> 
                                            widgets = widgets.filter { it.id != id }
                                        },
                                        musicData = uiState.musicData,
                                        mealData = uiState.mealData,
                                        announcementData = uiState.announcementData,
                                        onPlayClick = { song -> handleAction(MainUiAction.PlayMusic(song)) },
                                        onActionClick = { handleAction(MainUiAction.OpenLaundry) },
                                        onWidgetClick = onWidgetClick,
                                        onSizeChanged = onSizeChanged,
                                        dragDropState = if (isEditMode) dragDropState else null,
                                        onDragStart = onDragStart,
                                        onDragEnd = onDragEnd
                                    )
                                }
                            }
                        }
                        item { Spacer(modifier = Modifier.height(12.dp)) }
                    }
                    
                    WidgetType.MEAL, WidgetType.LARGE_ANNOUNCEMENT -> {
                        item {
                            EditableWidget(
                                widgetItem = widget,
                                isEditMode = isEditMode,
                                onLongPress = { isEditMode = true },
                                onDelete = { id -> 
                                    widgets = widgets.filter { it.id != id }
                                },
                                musicData = uiState.musicData,
                                mealData = uiState.mealData,
                                announcementData = uiState.announcementData,
                                onPlayClick = { song -> handleAction(MainUiAction.PlayMusic(song)) },
                                onActionClick = { handleAction(MainUiAction.OpenLaundry) },
                                onWidgetClick = onWidgetClick,
                                onSizeChanged = onSizeChanged,
                                dragDropState = if (isEditMode) dragDropState else null,
                                onDragStart = onDragStart,
                                onDragEnd = onDragEnd
                            )
                        }
                        item { Spacer(modifier = Modifier.height(12.dp)) }
                    }
                }
            }
        }

        if (isEditMode) {
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
                    .zIndex(25f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.8f)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "터치하여 완료",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
        
        WidgetPickerBottomSheet(
            isVisible = showWidgetPicker,
            onDismiss = { showWidgetPicker = false },
            onWidgetSelected = { widgetType ->
                addWidget(widgetType)
            }
        )
        
        selectedWidgetForSettings?.let { widget ->
            widget.settings?.let { settings ->
                WidgetSettingsBottomSheet(
                    isVisible = showWidgetSettings,
                    widgetItem = widget,
                    settings = settings,
                    onDismiss = { 
                        showWidgetSettings = false
                        selectedWidgetForSettings = null
                    },
                    onSettingsChanged = onWidgetSettingsChanged
                )
            }
        }
    }
}

@Composable
fun GreetingMessage(
    userInfo: UserInfo,
    modifier: Modifier = Modifier
) {
    Text(
        text = userInfo.greeting,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.padding(vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Andoriod_midoriTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen()
        }
    }
} 