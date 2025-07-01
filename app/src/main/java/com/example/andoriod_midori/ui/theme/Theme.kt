package com.example.andoriod_midori.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MidoriDarkColorScheme = darkColorScheme(
    primary = MidoriGreen,
    onPrimary = Gray100,
    secondary = MidoriGreenLight,
    onSecondary = Gray00,
    tertiary = MidoriGreenDark,
    onTertiary = Gray100,
    
    background = BackgroundPrimary,
    onBackground = TextPrimary,
    surface = BackgroundSecondary,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundTertiary,
    onSurfaceVariant = TextSecondary,
    
    outline = BorderPrimary,
    outlineVariant = BorderSecondary,
    
    error = Error,
    onError = Gray100
)

private val MidoriLightColorScheme = lightColorScheme(
    primary = MidoriGreen,
    onPrimary = Gray100,
    secondary = MidoriGreenLight,
    onSecondary = Gray00,
    tertiary = MidoriGreenDark,
    onTertiary = Gray100,
    
    background = Gray100,
    onBackground = Gray00,
    surface = Gray90,
    onSurface = Gray00,
    surfaceVariant = Gray80,
    onSurfaceVariant = Gray30,
    
    outline = Gray30,
    outlineVariant = Gray40,
    
    error = Error,
    onError = Gray100
)

@Composable
fun Andoriod_midoriTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> MidoriDarkColorScheme
        else -> MidoriLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MidoriTypography,
        content = content
    )
} 