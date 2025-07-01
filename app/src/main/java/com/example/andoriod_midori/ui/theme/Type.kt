package com.example.andoriod_midori.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.andoriod_midori.R

val PretendardFont = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_bold, FontWeight.Bold)
)

val SairaStencilOneFont = FontFamily(
    Font(R.font.saira_stencil_one_regular, FontWeight.Normal)
)

val MidoriTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = SairaStencilOneFont,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        letterSpacing = 0.sp
    ),
    
    headlineMedium = TextStyle(
        fontFamily = SairaStencilOneFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    
    headlineSmall = TextStyle(
        fontFamily = SairaStencilOneFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    
    bodyLarge = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.32).sp
    ),
    
    bodyMedium = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.28).sp
    ),
    
    bodySmall = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.24).sp
    ),
    
    labelLarge = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.32).sp
    ),
    
    labelMedium = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.28).sp
    ),
    
    labelSmall = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = (-0.24).sp
    ),
    
    titleLarge = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.44).sp
    ),
    
    titleMedium = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.36).sp
    ),
    
    titleSmall = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.32).sp
    )
) 