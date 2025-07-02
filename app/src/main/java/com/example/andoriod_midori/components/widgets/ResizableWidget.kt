package com.example.andoriod_midori.components.widgets

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.andoriod_midori.data.models.*
import kotlin.math.abs
import kotlin.math.sqrt

data class ResizeState(
    val isResizing: Boolean = false,
    val dragOffset: Offset = Offset.Zero,
    val previewType: WidgetType? = null
)

@Composable
fun ResizableWidget(
    widgetItem: WidgetItem,
    isEditMode: Boolean,
    onSizeChanged: (WidgetType) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var resizeState by remember { mutableStateOf(ResizeState()) }
    val density = LocalDensity.current
    
    val resizeThreshold = with(density) { 30.dp.toPx() }
    
    val canResize = when (widgetItem.type) {
        WidgetType.SMALL_MUSIC -> true
        WidgetType.LARGE_MUSIC -> true
        WidgetType.SMALL_ANNOUNCEMENT -> true
        WidgetType.LARGE_ANNOUNCEMENT -> true
        else -> false
    }
    
    val getTargetType = { dragOffset: Offset ->
        val dragDistance = sqrt(dragOffset.x * dragOffset.x + dragOffset.y * dragOffset.y)
        
        if (dragDistance > resizeThreshold) {
            when (widgetItem.type) {
                WidgetType.SMALL_MUSIC -> WidgetType.LARGE_MUSIC
                WidgetType.LARGE_MUSIC -> WidgetType.SMALL_MUSIC
                WidgetType.SMALL_ANNOUNCEMENT -> WidgetType.LARGE_ANNOUNCEMENT
                WidgetType.LARGE_ANNOUNCEMENT -> WidgetType.SMALL_ANNOUNCEMENT
                else -> null
            }
        } else {
            null
        }
    }

    Box(modifier = modifier) {
        content()
        
        if (isEditMode && canResize) {
            ResizeHandle(
                isVisible = !resizeState.isResizing,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 8.dp, y = 8.dp)
                    .zIndex(45f)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                resizeState = resizeState.copy(
                                    isResizing = true,
                                    dragOffset = offset
                                )
                            },
                            onDragEnd = {
                                val targetType = getTargetType(resizeState.dragOffset)
                                if (targetType != null && targetType != widgetItem.type) {
                                    onSizeChanged(targetType)
                                }
                                resizeState = ResizeState()
                            },
                            onDrag = { change, dragAmount ->
                                val newOffset = resizeState.dragOffset + dragAmount
                                val targetType = getTargetType(newOffset)
                                
                                resizeState = resizeState.copy(
                                    dragOffset = newOffset,
                                    previewType = targetType
                                )
                            }
                        )
                    }
            )
            
            if (resizeState.isResizing && resizeState.previewType != null) {
                ResizePreviewIndicator(
                    currentType = widgetItem.type,
                    targetType = resizeState.previewType!!,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(60f)
                )
            }
        }
    }
}

@Composable
fun ResizeHandle(
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 0.7f else 0f,
        animationSpec = tween(200),
        label = "handle_alpha"
    )
    
    Box(
        modifier = modifier
            .size(20.dp)
            .alpha(alpha),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(3) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.8f))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResizePreviewIndicator(
    currentType: WidgetType,
    targetType: WidgetType,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "preview_scale"
    )
    
    val isExpanding = when {
        currentType == WidgetType.SMALL_MUSIC && targetType == WidgetType.LARGE_MUSIC -> true
        currentType == WidgetType.SMALL_ANNOUNCEMENT && targetType == WidgetType.LARGE_ANNOUNCEMENT -> true
        else -> false
    }
    
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.OpenInFull,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isExpanding) "크게 변경" else "작게 변경",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

fun WidgetType.getResizeOptions(): List<WidgetType> {
    return when (this) {
        WidgetType.SMALL_MUSIC -> listOf(WidgetType.LARGE_MUSIC)
        WidgetType.LARGE_MUSIC -> listOf(WidgetType.SMALL_MUSIC)
        WidgetType.SMALL_ANNOUNCEMENT -> listOf(WidgetType.LARGE_ANNOUNCEMENT)
        WidgetType.LARGE_ANNOUNCEMENT -> listOf(WidgetType.SMALL_ANNOUNCEMENT)
        else -> emptyList()
    }
}

fun WidgetType.isResizable(): Boolean {
    return getResizeOptions().isNotEmpty()
} 