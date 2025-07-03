package com.example.andoriod_midori.components.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.andoriod_midori.data.models.TodayMusicData
import com.example.andoriod_midori.data.models.MusicData
import com.example.andoriod_midori.ui.theme.MusicWidgetBackground
import com.example.andoriod_midori.utils.Constants

@Composable
fun MusicWidget(
    musicData: TodayMusicData = TodayMusicData.createSample(),
    onPlayClick: (MusicData) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(318.dp)
            .height(149.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(
                    color = MusicWidgetBackground,
                    shape = RoundedCornerShape(15.dp)
                )
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(
                    color = Color(0x99000000),
                    shape = RoundedCornerShape(15.dp)
                )
                .blur(1.5.dp)
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.White.copy(alpha = 0.1f),
                            Color.Transparent,
                        ),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .clip(RoundedCornerShape(14.dp))
                .border(
                    width = 0.5.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(14.dp)
                )
        )
        
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(11.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "오늘의 기상송",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                AsyncImage(
                    model = Constants.Assets.LISTENING_DINOSAURS,
                    contentDescription = "Listening Dinosaurs",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(72.dp)
                        .height(72.dp)
                )
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                musicData.songs.getOrNull(0)?.let { song ->
                    MusicItemColumn(
                        musicData = song,
                        onPlayClick = { onPlayClick(song) }
                    )
                }
                
                musicData.songs.getOrNull(1)?.let { song ->
                    MusicItemColumn(
                        musicData = song,
                        onPlayClick = { onPlayClick(song) }
                    )
                }
            }
        }
    }
}

@Composable
fun MusicItemColumn(
    musicData: MusicData,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(88.dp)
    ) {
        Card(
            onClick = onPlayClick,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .height(76.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray
            )
        ) {
            val assetUri = musicData.albumArt?.let { "file:///android_asset/$it" }
            
            AsyncImage(
                model = assetUri,
                contentDescription = "Album Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                onError = { error ->
                    println("Failed to load image: ${musicData.albumArt}, error: ${error.result.throwable}")
                },
                onSuccess = {
                    println("Successfully loaded image: ${musicData.albumArt}")
                }
            )
        }
        
        Column(
            horizontalAlignment = if (musicData.title.contains("ライラック")) Alignment.CenterHorizontally else Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = musicData.title,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textAlign = if (musicData.title.contains("ライラック")) TextAlign.Center else TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = musicData.artist,
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                textAlign = if (musicData.title.contains("ライラック")) TextAlign.Center else TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SmallMusicWidget(
    musicData: MusicData,
    onPlayClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(155.dp)
            .height(149.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(
                    color = Color(0xFF89BFCA),
                    shape = RoundedCornerShape(15.dp)
                )
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(
                    color = Color(0x99000000),
                    shape = RoundedCornerShape(15.dp)
                )
                .blur(1.5.dp)
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.White.copy(alpha = 0.1f),
                            Color.Transparent,
                        ),
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .clip(RoundedCornerShape(14.dp))
                .border(
                    width = 0.5.dp,
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(14.dp)
                )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "오늘의 기상송",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp,
                letterSpacing = (-0.32).sp
            )
            
            Card(
                onClick = onPlayClick,
                modifier = Modifier
                    .size(85.dp)
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray
                )
            ) {
                val assetUri = musicData.albumArt?.let { "file:///android_asset/$it" }
                
                AsyncImage(
                    model = assetUri,
                    contentDescription = "Album Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = musicData.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    letterSpacing = (-0.28).sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = musicData.artist,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp,
                    letterSpacing = (-0.24).sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
} 