package com.example.andoriod_midori

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.andoriod_midori.R
import com.example.andoriod_midori.ui.theme.Andoriod_midoriTheme
import com.example.andoriod_midori.ui.theme.MidoriGreen
import com.example.andoriod_midori.ui.theme.SairaStencilOneFont
import com.example.andoriod_midori.utils.Constants
import kotlinx.coroutines.delay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            
            splashScreen.setKeepOnScreenCondition { 
                false
            }
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.remove()
            }
        }
        
        super.onCreate(savedInstanceState)
        setContent {
            Andoriod_midoriTheme {
                SplashScreen {
                    navigateToMainScreen()
                }
            }
        }
    }
    
    private fun navigateToMainScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(Constants.Timing.CUSTOM_SPLASH_DELAY)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MidoriGreen)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Constants.Dimensions.SPLASH_BOTTOM_PADDING.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.Assets.MIDORI_ICON)
                    .memoryCacheKey("midori_logo")
                    .diskCacheKey("midori_logo")
                    .crossfade(false)
                    .build(),
                contentDescription = "Midori SVG Icon",
                modifier = Modifier.size(Constants.Dimensions.SPLASH_LOGO_SIZE.dp),
                contentScale = ContentScale.Fit
            )

            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.size(Constants.Dimensions.SPLASH_LOGO_TEXT_SPACING.dp)
            )
            
            Text(
                text = LocalContext.current.getString(R.string.app_name),
                style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen {}
} 