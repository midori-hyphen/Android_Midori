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
import com.example.andoriod_midori.data.models.MealData
import com.example.andoriod_midori.utils.Constants

@Composable
fun MealWidget(
    mealData: MealData = MealData.createSample(),
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
                    color = Color(0xFF89D6FF),
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
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = mealData.mealType,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp,
                    letterSpacing = (-0.4).sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                AsyncImage(
                    model = Constants.Assets.MEALBOARD_ICON,
                    contentDescription = "Meal Board",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(80.dp)
                )
            }
            
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "차조밥",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    letterSpacing = (-0.28).sp,
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "부대찌개",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    letterSpacing = (-0.28).sp,
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "어묵파프리카볶음",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    letterSpacing = (-0.28).sp,
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "로제전담",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    letterSpacing = (-0.28).sp,
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "깍두기",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    letterSpacing = (-0.28).sp,
                    textAlign = TextAlign.Right,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
} 