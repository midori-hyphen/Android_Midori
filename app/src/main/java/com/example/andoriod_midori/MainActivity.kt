package com.example.andoriod_midori

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.andoriod_midori.components.CommonLayout
import com.example.andoriod_midori.components.widgets.*
import com.example.andoriod_midori.data.models.*
import com.example.andoriod_midori.data.ui.MainUiAction
import com.example.andoriod_midori.data.ui.MainUiState
import com.example.andoriod_midori.presentation.MainViewModel
import com.example.andoriod_midori.ui.theme.Andoriod_midoriTheme
import com.example.andoriod_midori.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    var selectedWidgetForSettings by remember { mutableStateOf<WidgetItem?>(null) }
    var showWidgetSettings by remember { mutableStateOf(false) }
    
    val dragDropState = rememberDragDropState()
    
    val onWidgetClick: (WidgetItem) -> Unit = { widget ->
        if (!uiState.isEditMode) {
            selectedWidgetForSettings = widget
            showWidgetSettings = true
        }
    }
    
    val onWidgetSettingsChanged: (WidgetSettings) -> Unit = { newSettings ->
        val updatedWidget = uiState.widgets.find { it.id == newSettings.widgetId }
        updatedWidget?.let { widget ->
            val newWidget = widget.copy(
                settings = when (widget) {
                    is WidgetData.Music -> newSettings as? WidgetSettings.MusicWidgetSettings
                    is WidgetData.Meal -> newSettings as? WidgetSettings.MealWidgetSettings  
                    is WidgetData.Announcement -> newSettings as? WidgetSettings.AnnouncementWidgetSettings
                }
            )
            viewModel.onAction(MainUiAction.UpdateWidget(newWidget))
        }
    }
    
    val onSizeChanged: (String, WidgetType) -> Unit = { widgetId, newType ->
        val widget = uiState.widgets.find { it.id == widgetId }
        widget?.let { existingWidget ->
            val dataWidgetType = when (newType) {
                WidgetType.LARGE_MUSIC -> WidgetData.WidgetType.MUSIC_BIG
                WidgetType.SMALL_MUSIC -> WidgetData.WidgetType.MUSIC_SMALL
                WidgetType.MEAL -> WidgetData.WidgetType.MEAL
                WidgetType.SMALL_ANNOUNCEMENT -> WidgetData.WidgetType.ANNOUNCEMENT_SMALL
                WidgetType.LARGE_ANNOUNCEMENT -> WidgetData.WidgetType.ANNOUNCEMENT_BIG
            }
            val updatedWidget = when (existingWidget) {
                is WidgetData.Music -> existingWidget.copy(type = dataWidgetType)
                is WidgetData.Announcement -> existingWidget.copy(type = dataWidgetType)
                else -> existingWidget
            }
            viewModel.onAction(MainUiAction.UpdateWidget(updatedWidget))
        }
    }
    
    val onDragEnd: (String, List<WidgetData>) -> Unit = { _, reorderedWidgets ->
        viewModel.onAction(MainUiAction.ReorderWidgets(reorderedWidgets))
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    
    uiState.errorMessage?.let { errorMessage ->
        LaunchedEffect(errorMessage) {
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (uiState.isEditMode) {
                    Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { 
                                viewModel.onAction(MainUiAction.ToggleEditMode)
                            },
                            onLongPress = {
                                viewModel.onAction(MainUiAction.ShowWidgetPicker)
                            }
                        )
                    }
                } else Modifier
            )
    ) {
        CommonLayout(
            currentNavigation = uiState.currentNavigation,
            onNotificationClick = { viewModel.onAction(MainUiAction.OpenNotifications) },
            onNavigationClick = { navigationItem ->
                viewModel.onAction(MainUiAction.NavigateTo(navigationItem))
            },
            onChatClick = { viewModel.onAction(MainUiAction.OpenChat) }
        ) {
            item { 
                WeeklyCalendarWidget(calendarData = uiState.calendarData)
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            item { 
                GreetingMessage(userInfo = uiState.userInfo)
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            renderWidgets(
                widgets = uiState.widgets,
                isEditMode = uiState.isEditMode,
                musicData = uiState.musicData,
                mealData = uiState.mealData,
                announcementData = uiState.announcementData,
                onEditModeChange = { viewModel.onAction(MainUiAction.ToggleEditMode) },
                onDeleteWidget = { widgetId -> 
                    viewModel.onAction(MainUiAction.DeleteWidget(widgetId))
                },
                onPlayClick = { song -> viewModel.onAction(MainUiAction.PlayMusic(song)) },
                onActionClick = { viewModel.onAction(MainUiAction.OpenLaundry) },
                onWidgetClick = onWidgetClick,
                onSizeChanged = onSizeChanged,
                dragDropState = if (uiState.isEditMode) dragDropState else null,
                onDragEnd = onDragEnd
            )
        }

        if (uiState.isEditMode) {
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
            isVisible = uiState.showWidgetPicker,
            onDismiss = { viewModel.onAction(MainUiAction.HideWidgetPicker) },
            onWidgetSelected = { widgetType ->
                val dataWidgetType = when (widgetType) {
                    WidgetType.LARGE_MUSIC -> WidgetData.WidgetType.MUSIC_BIG
                    WidgetType.SMALL_MUSIC -> WidgetData.WidgetType.MUSIC_SMALL
                    WidgetType.MEAL -> WidgetData.WidgetType.MEAL
                    WidgetType.SMALL_ANNOUNCEMENT -> WidgetData.WidgetType.ANNOUNCEMENT_SMALL
                    WidgetType.LARGE_ANNOUNCEMENT -> WidgetData.WidgetType.ANNOUNCEMENT_BIG
                }
                viewModel.onAction(MainUiAction.AddWidget(dataWidgetType))
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

private fun LazyListScope.renderWidgets(
    widgets: List<WidgetData>,
    isEditMode: Boolean,
    musicData: TodayMusicData,
    mealData: MealData,
    announcementData: AnnouncementData,
    onEditModeChange: () -> Unit,
    onDeleteWidget: (String) -> Unit,
    onPlayClick: (MusicData) -> Unit,
    onActionClick: () -> Unit,
    onWidgetClick: (WidgetItem) -> Unit,
    onSizeChanged: (String, WidgetType) -> Unit,
    dragDropState: MutableState<DragDropState>?,
    onDragEnd: (String, List<WidgetData>) -> Unit
) {
    val processedWidgets = mutableSetOf<Int>()
    
    widgets.forEachIndexed { index, widget ->
        if (index in processedWidgets) return@forEachIndexed
        
        val widgetItem = widget.toWidgetItem()
        
        when (widget.type) {
            WidgetData.WidgetType.MUSIC_BIG -> {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        EditableWidget(
                            widgetItem = widgetItem,
                            isEditMode = isEditMode,
                            onLongPress = onEditModeChange,
                            onDelete = onDeleteWidget,
                            musicData = musicData,
                            mealData = mealData,
                            announcementData = announcementData,
                            onPlayClick = onPlayClick,
                            onActionClick = onActionClick,
                            onWidgetClick = onWidgetClick,
                            onSizeChanged = onSizeChanged,
                            dragDropState = dragDropState,
                            onDragStart = { },
                            onDragEnd = { _, _ -> }
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
            
            WidgetData.WidgetType.MUSIC_SMALL, WidgetData.WidgetType.ANNOUNCEMENT_SMALL -> {
                val nextWidget = widgets.getOrNull(index + 1)
                val canPair = nextWidget?.type == WidgetData.WidgetType.MUSIC_SMALL || 
                             nextWidget?.type == WidgetData.WidgetType.ANNOUNCEMENT_SMALL
                
                if (canPair && nextWidget != null) {
                    item {
                        WidgetRow(
                            leftWidget = widgetItem,
                            rightWidget = nextWidget.toWidgetItem(),
                            isEditMode = isEditMode,
                            onLongPress = onEditModeChange,
                            onDelete = onDeleteWidget,
                            musicData = musicData,
                            mealData = mealData,
                            announcementData = announcementData,
                            onPlayClick = onPlayClick,
                            onActionClick = onActionClick,
                            onWidgetClick = onWidgetClick,
                            onSizeChanged = onSizeChanged,
                            dragDropState = dragDropState,
                            onDragStart = { },
                            onDragEnd = { _, _ -> }
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
                                widgetItem = widgetItem,
                                isEditMode = isEditMode,
                                onLongPress = onEditModeChange,
                                onDelete = onDeleteWidget,
                                musicData = musicData,
                                mealData = mealData,
                                announcementData = announcementData,
                                onPlayClick = onPlayClick,
                                onActionClick = onActionClick,
                                onWidgetClick = onWidgetClick,
                                onSizeChanged = onSizeChanged,
                                dragDropState = dragDropState,
                                onDragStart = { },
                                onDragEnd = { _, _ -> }
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
            
            WidgetData.WidgetType.MEAL, WidgetData.WidgetType.ANNOUNCEMENT_BIG -> {
                item {
                    EditableWidget(
                        widgetItem = widgetItem,
                        isEditMode = isEditMode,
                        onLongPress = onEditModeChange,
                        onDelete = onDeleteWidget,
                        musicData = musicData,
                        mealData = mealData,
                        announcementData = announcementData,
                        onPlayClick = onPlayClick,
                        onActionClick = onActionClick,
                        onWidgetClick = onWidgetClick,
                        onSizeChanged = onSizeChanged,
                        dragDropState = dragDropState,
                        onDragStart = { },
                        onDragEnd = { _, _ -> }
                    )
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
        }
    }
}

private fun WidgetData.toWidgetItem(): WidgetItem {
    return when (this) {
        is WidgetData.Music -> WidgetItem(
            id = this.id,
            type = when (this.type) {
                WidgetData.WidgetType.MUSIC_BIG -> WidgetType.LARGE_MUSIC
                WidgetData.WidgetType.MUSIC_SMALL -> WidgetType.SMALL_MUSIC
                else -> WidgetType.SMALL_MUSIC
            },
            title = this.settings?.title ?: "음악",
            settings = this.settings
        )
        is WidgetData.Meal -> WidgetItem(
            id = this.id,
            type = WidgetType.MEAL,
            title = this.settings?.title ?: "식단",
            settings = this.settings
        )
        is WidgetData.Announcement -> WidgetItem(
            id = this.id,
            type = when (this.type) {
                WidgetData.WidgetType.ANNOUNCEMENT_BIG -> WidgetType.LARGE_ANNOUNCEMENT
                WidgetData.WidgetType.ANNOUNCEMENT_SMALL -> WidgetType.SMALL_ANNOUNCEMENT
                else -> WidgetType.SMALL_ANNOUNCEMENT
            },
            title = this.settings?.title ?: "안내",
            settings = this.settings
        )
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
    
        }
    }
} 