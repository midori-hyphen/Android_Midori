package com.example.andoriod_midori.components.widgets

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.andoriod_midori.data.models.*
import com.example.andoriod_midori.utils.*

@Composable
fun EditableWidget(
    widgetItem: WidgetItem,
    isEditMode: Boolean,
    onLongPress: () -> Unit,
    onDelete: (String) -> Unit,
    musicData: TodayMusicData,
    mealData: MealData, 
    announcementData: AnnouncementData,
    onPlayClick: (MusicData) -> Unit = {},
    onActionClick: () -> Unit = {},
    onWidgetClick: (WidgetItem) -> Unit = {},
    onSizeChanged: (String, WidgetType) -> Unit = { _, _ -> },
    dragDropState: MutableState<DragDropState>? = null,
    onDragStart: (String) -> Unit = {},
    onDragEnd: (String, Offset) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wiggle")
    val wiggleRotation by infiniteTransition.animateFloat(
        initialValue = -0.5f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wiggle_rotation"
    )

    val combinedModifier = modifier
        .then(
            if (isEditMode) Modifier.rotate(wiggleRotation) else Modifier
        )
        .then(
            if (dragDropState != null) {
                Modifier.dragEffect(widgetItem.id, dragDropState)
            } else Modifier
        )
        .then(
            if (isEditMode && dragDropState != null) {
                Modifier.dragGesture(
                    itemId = widgetItem.id,
                    dragDropState = dragDropState,
                    onDragStart = onDragStart,
                    onDragEnd = onDragEnd
                )
            } else {
                Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onWidgetClick(widgetItem) },
                        onLongPress = { onLongPress() }
                    )
                }
            }
        )

    ResizableWidget(
        widgetItem = widgetItem,
        isEditMode = isEditMode,
        onSizeChanged = { newType -> onSizeChanged(widgetItem.id, newType) },
        modifier = combinedModifier
    ) {
        Box {
            when (widgetItem.type) {
                WidgetType.LARGE_MUSIC -> {
                    MusicWidget(
                        musicData = musicData,
                        onPlayClick = onPlayClick
                    )
                }
                WidgetType.SMALL_MUSIC -> {
                    SmallMusicWidget(
                        musicData = musicData.songs.getOrNull(1) ?: MusicData("개화 (Flowering)", "LUCY", "앨범 커버.png"),
                        onPlayClick = { musicData.songs.getOrNull(1)?.let(onPlayClick) }
                    )
                }
                WidgetType.SMALL_ANNOUNCEMENT -> {
                    AnnouncementWidget(
                        announcementData = announcementData,
                        onActionClick = onActionClick
                    )
                }
                WidgetType.MEAL -> {
                    MealWidget(mealData = mealData)
                }
                WidgetType.LARGE_ANNOUNCEMENT -> {
                    LargeAnnouncementWidget()
                }
            }

            if (isEditMode && widgetItem.isRemovable) {
                FloatingActionButton(
                    onClick = { onDelete(widgetItem.id) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                        .size(28.dp)
                        .zIndex(50f),
                    shape = CircleShape,
                    containerColor = Color.Red,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "삭제",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WidgetRow(
    leftWidget: WidgetItem?,
    rightWidget: WidgetItem?, 
    isEditMode: Boolean,
    onLongPress: () -> Unit,
    onDelete: (String) -> Unit,
    musicData: TodayMusicData,
    mealData: MealData,
    announcementData: AnnouncementData,
    onPlayClick: (MusicData) -> Unit = {},
    onActionClick: () -> Unit = {},
    onWidgetClick: (WidgetItem) -> Unit = {},
    onSizeChanged: (String, WidgetType) -> Unit = { _, _ -> },
    dragDropState: MutableState<DragDropState>? = null,
    onDragStart: (String) -> Unit = {},
    onDragEnd: (String, Offset) -> Unit = { _, _ -> }
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (leftWidget != null && rightWidget != null) {
            Arrangement.spacedBy(8.dp)
        } else {
            Arrangement.Center
        }
    ) {
        leftWidget?.let { widget ->
            EditableWidget(
                widgetItem = widget,
                isEditMode = isEditMode,
                onLongPress = onLongPress,
                onDelete = onDelete,
                musicData = musicData,
                mealData = mealData,
                announcementData = announcementData,
                onPlayClick = onPlayClick,
                onActionClick = onActionClick,
                onWidgetClick = onWidgetClick,
                onSizeChanged = onSizeChanged,
                dragDropState = dragDropState,
                onDragStart = onDragStart,  
                onDragEnd = onDragEnd
            )
        }
        
        rightWidget?.let { widget ->
            EditableWidget(
                widgetItem = widget,
                isEditMode = isEditMode,
                onLongPress = onLongPress,
                onDelete = onDelete,
                musicData = musicData,
                mealData = mealData,
                announcementData = announcementData,
                onPlayClick = onPlayClick,
                onActionClick = onActionClick,
                onWidgetClick = onWidgetClick,
                onSizeChanged = onSizeChanged,
                dragDropState = dragDropState,
                onDragStart = onDragStart,
                onDragEnd = onDragEnd
            )
        }
    }
} 