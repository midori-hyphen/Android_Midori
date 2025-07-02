package com.example.andoriod_midori.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.andoriod_midori.ui.theme.MidoriSpacing
import com.example.andoriod_midori.utils.Constants

@Composable
fun TopBar(
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MidoriSpacing.M),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = Constants.Strings.APP_NAME,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        IconButton(onClick = onNotificationClick) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "알림",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
} 