package com.example.andoriod_midori.components.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.andoriod_midori.data.models.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetSettingsBottomSheet(
    isVisible: Boolean,
    widgetItem: WidgetItem,
    settings: WidgetSettings,
    onDismiss: () -> Unit,
    onSettingsChanged: (WidgetSettings) -> Unit,
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Column(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text(
                            text = widgetItem.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "위젯 설정",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                item {
                    Column {
                        Text(
                            text = "제목",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        var titleText by remember { mutableStateOf(settings.title) }
                        
                        OutlinedTextField(
                            value = titleText,
                            onValueChange = { newTitle ->
                                titleText = newTitle
                                val updatedSettings = when (settings) {
                                    is WidgetSettings.MusicWidgetSettings -> settings.copy(title = newTitle)
                                    is WidgetSettings.MealWidgetSettings -> settings.copy(title = newTitle)
                                    is WidgetSettings.AnnouncementWidgetSettings -> settings.copy(title = newTitle)
                                }
                                onSettingsChanged(updatedSettings)
                            },
                            placeholder = { Text("위젯 제목을 입력하세요") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        )
                    }
                }

                item {
                    Column {
                        Text(
                            text = "색상",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(WidgetColorTheme.values()) { theme ->
                                SimpleColorButton(
                                    theme = theme,
                                    isSelected = settings.backgroundColor == theme.color || 
                                               (settings.backgroundColor == null && theme == WidgetColorTheme.DEFAULT),
                                    onSelected = { 
                                        val color = if (theme == WidgetColorTheme.DEFAULT) null else theme.color
                                        val updatedSettings = when (settings) {
                                            is WidgetSettings.MusicWidgetSettings -> settings.copy(backgroundColor = color)
                                            is WidgetSettings.MealWidgetSettings -> settings.copy(backgroundColor = color)
                                            is WidgetSettings.AnnouncementWidgetSettings -> settings.copy(backgroundColor = color)
                                        }
                                        onSettingsChanged(updatedSettings)
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun SettingsCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            content()
        }
    }
}

@Composable
fun SimpleColorButton(
    theme: WidgetColorTheme,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable { onSelected() }
            .background(
                if (theme == WidgetColorTheme.DEFAULT) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    theme.color
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                )
            }
        }
    }
} 