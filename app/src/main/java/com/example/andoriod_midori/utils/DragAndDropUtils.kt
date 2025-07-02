package com.example.andoriod_midori.utils

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

data class DragDropState(
    val draggedItem: String? = null,
    val dragOffset: Offset = Offset.Zero,
    val isDragging: Boolean = false
)

@Composable
fun rememberDragDropState(): MutableState<DragDropState> {
    return remember { mutableStateOf(DragDropState()) }
}

fun Modifier.dragGesture(
    itemId: String,
    dragDropState: MutableState<DragDropState>,
    onDragStart: (String) -> Unit = {},
    onDragEnd: (String, Offset) -> Unit = { _, _ -> },
    onDrag: (String, Offset) -> Unit = { _, _ -> }
): Modifier = composed {
    val density = LocalDensity.current
    
    this.pointerInput(itemId) {
        detectDragGestures(
            onDragStart = { offset ->
                dragDropState.value = dragDropState.value.copy(
                    draggedItem = itemId,
                    isDragging = true,
                    dragOffset = offset
                )
                onDragStart(itemId)
            },
            onDragEnd = {
                val currentState = dragDropState.value
                onDragEnd(itemId, currentState.dragOffset)
                dragDropState.value = DragDropState()
            },
            onDrag = { change, dragAmount ->
                val currentState = dragDropState.value
                val newOffset = currentState.dragOffset + dragAmount
                dragDropState.value = currentState.copy(
                    dragOffset = newOffset
                )
                onDrag(itemId, newOffset)
            }
        )
    }
}

fun Modifier.dragEffect(
    itemId: String,
    dragDropState: State<DragDropState>
): Modifier = composed {
    val currentState = dragDropState.value
    val isDraggedItem = currentState.draggedItem == itemId
    
    this
        .zIndex(if (isDraggedItem) 10f else 0f)
        .graphicsLayer {
            if (isDraggedItem) {
                scaleX = 1.05f
                scaleY = 1.05f
                alpha = 0.9f
                shadowElevation = 8f
            }
        }
        .offset {
            if (isDraggedItem) {
                IntOffset(
                    currentState.dragOffset.x.roundToInt(),
                    currentState.dragOffset.y.roundToInt()
                )
            } else {
                IntOffset.Zero
            }
        }
}

fun <T> List<T>.move(fromIndex: Int, toIndex: Int): List<T> {
    if (fromIndex == toIndex) return this
    
    val mutableList = this.toMutableList()
    val item = mutableList.removeAt(fromIndex)
    mutableList.add(toIndex, item)
    return mutableList
}

fun calculateDropIndex(
    dragOffset: Offset,
    itemHeight: Float,
    totalItems: Int,
    containerHeight: Float
): Int {
    val relativeY = dragOffset.y
    val estimatedIndex = (relativeY / itemHeight).roundToInt()
    return estimatedIndex.coerceIn(0, totalItems - 1)
} 