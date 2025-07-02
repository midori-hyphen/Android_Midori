package com.example.andoriod_midori.components.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.andoriod_midori.data.models.WidgetType

data class WidgetOption(
    val type: WidgetType,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val backgroundColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetPickerBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onWidgetSelected: (WidgetType) -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = bottomSheetState,
            modifier = modifier
        ) {
            WidgetPickerContent(
                onWidgetSelected = { widgetType ->
                    onWidgetSelected(widgetType)
                    onDismiss()
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun WidgetPickerContent(
    onWidgetSelected: (WidgetType) -> Unit,
    modifier: Modifier = Modifier
) {
    val availableWidgets = listOf(
        WidgetOption(
            type = WidgetType.LARGE_MUSIC,
            title = "큰 기상송",
            description = "두 개의 앨범을 보여주는 큰 위젯",
            icon = Icons.Default.MusicNote,
            backgroundColor = Color(0xFFCCB7E5)
        ),
        WidgetOption(
            type = WidgetType.SMALL_MUSIC,
            title = "작은 기상송",
            description = "한 개의 앨범을 보여주는 작은 위젯",
            icon = Icons.Default.MusicNote,
            backgroundColor = Color(0xFF89BFCA)
        ),
        WidgetOption(
            type = WidgetType.SMALL_ANNOUNCEMENT,
            title = "작은 안내",
            description = "간단한 안내사항을 보여주는 작은 위젯",
            icon = Icons.Default.Announcement,
            backgroundColor = Color(0xFFFFBE8D)
        ),
        WidgetOption(
            type = WidgetType.MEAL,
            title = "조식",
            description = "오늘의 급식 메뉴를 보여주는 위젯",
            icon = Icons.Default.Restaurant,
            backgroundColor = Color(0xFF89D6FF)
        ),
        WidgetOption(
            type = WidgetType.LARGE_ANNOUNCEMENT,
            title = "큰 안내",
            description = "자세한 안내사항을 보여주는 큰 위젯",
            icon = Icons.Default.Announcement,
            backgroundColor = Color(0xFFFFBE8D)
        )
    )

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "위젯 추가",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "홈 화면에 추가할 위젯을 선택하세요",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(availableWidgets) { widget ->
                WidgetOptionCard(
                    option = widget,
                    onClick = { onWidgetSelected(widget.type) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun WidgetOptionCard(
    option: WidgetOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(option.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = option.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = option.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 16.sp
                )
            }
        }
    }
} 