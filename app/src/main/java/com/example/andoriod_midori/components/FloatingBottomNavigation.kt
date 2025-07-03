package com.example.andoriod_midori.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.andoriod_midori.ui.theme.MidoriSpacing
import com.example.andoriod_midori.ui.theme.NavigationBackground
import com.example.andoriod_midori.ui.theme.NavigationBorder
import com.example.andoriod_midori.utils.Constants

enum class NavigationItem(val iconPath: String) {
    HOME(Constants.Assets.HOME_ICON),
    FAVORITE(Constants.Assets.HEART_ICON),
    CALENDAR(Constants.Assets.CALENDAR_ICON),
    PROFILE(Constants.Assets.PERSON_ICON)
}

@Composable
fun FloatingBottomNavigation(
    currentSelected: NavigationItem = NavigationItem.HOME,
    onItemClick: (NavigationItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(start = 42.dp, end = 42.dp, bottom = 45.dp)
            .height(Constants.Dimensions.NAV_BAR_HEIGHT.dp),
        shape = RoundedCornerShape(Constants.Dimensions.NAV_BAR_RADIUS.dp),
        colors = CardDefaults.cardColors(
            containerColor = NavigationBackground
        ),
        border = BorderStroke(Constants.Dimensions.NAV_BAR_BORDER.dp, NavigationBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MidoriSpacing.L),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                iconPath = NavigationItem.HOME.iconPath,
                isSelected = currentSelected == NavigationItem.HOME,
                onClick = { onItemClick(NavigationItem.HOME) }
            )
            NavItem(
                iconPath = NavigationItem.FAVORITE.iconPath,
                isSelected = currentSelected == NavigationItem.FAVORITE,
                onClick = { onItemClick(NavigationItem.FAVORITE) }
            )
            NavItem(
                iconPath = NavigationItem.CALENDAR.iconPath,
                isSelected = currentSelected == NavigationItem.CALENDAR,
                onClick = { onItemClick(NavigationItem.CALENDAR) }
            )
            NavItem(
                iconPath = NavigationItem.PROFILE.iconPath,
                isSelected = currentSelected == NavigationItem.PROFILE,
                onClick = { onItemClick(NavigationItem.PROFILE) }
            )
        }
    }
}

@Composable
private fun NavItem(
    iconPath: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(Constants.Dimensions.NAV_BUTTON_SIZE.dp)
    ) {
        AsyncImage(
            model = iconPath,
            contentDescription = null,
            modifier = Modifier.size(Constants.Dimensions.NAV_ICON_SIZE.dp),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                if (isSelected) MaterialTheme.colorScheme.primary 
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
    }
} 