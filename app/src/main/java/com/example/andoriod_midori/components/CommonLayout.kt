package com.example.andoriod_midori.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.andoriod_midori.R
import com.example.andoriod_midori.ui.theme.MidoriSpacing
import com.example.andoriod_midori.ui.theme.ChatButtonBackground
import com.example.andoriod_midori.ui.theme.ChatButtonBorder
import com.example.andoriod_midori.utils.Constants

@Composable
fun CommonLayout(
    currentNavigation: NavigationItem = NavigationItem.HOME,
    onNotificationClick: () -> Unit = {},
    onNavigationClick: (NavigationItem) -> Unit = {},
    onChatClick: () -> Unit = {},
    content: LazyListScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            contentPadding = PaddingValues(horizontal = 42.dp, vertical = MidoriSpacing.M)
        ) {
            item { 
                TopBar(onNotificationClick = onNotificationClick)
            }
            item { 
                Spacer(modifier = Modifier.height(MidoriSpacing.L)) 
            }
            content()
        }
        
        FloatingChatButton(
            onClick = onChatClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 42.dp, bottom = 121.dp)
        )
        
        FloatingBottomNavigation(
            currentSelected = currentNavigation,
            onItemClick = onNavigationClick,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun FloatingChatButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(Constants.Dimensions.CHAT_BUTTON_WIDTH.dp)
            .height(Constants.Dimensions.CHAT_BUTTON_HEIGHT.dp),
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = ChatButtonBackground
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, ChatButtonBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = Constants.Assets.MIDORI_ICON,
                contentDescription = "MIDORI Character",
                modifier = Modifier
                    .size(Constants.Dimensions.ICON_SIZE_SMALL.dp)
                    .graphicsLayer { rotationY = 180f }
            )
            
            Spacer(modifier = Modifier.width(3.dp))
            
            Text(
                                    text = stringResource(R.string.chat),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = ChatButtonBorder,
                lineHeight = 14.sp,
                letterSpacing = (-0.5).sp,
                textAlign = TextAlign.Center
            )
        }
    }
} 