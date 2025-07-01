package com.example.andoriod_midori

import android.content.Intent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen {
                // 3초 후 메인 화면으로 이동
                navigateToMainScreen()
            }
        }
    }
    
    private fun navigateToMainScreen() {
        // 3초 후 메인 화면으로 이동
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // 스플래시 화면 종료
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val midoriGreen = Color(0xFF00C448)

    val sairaStencilOne = FontFamily(
        Font(R.font.saira_stencil_one_regular, FontWeight.Normal)
    )

    // 3초 후 onTimeout 실행
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(midoriGreen)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 352.dp)
        ) {
            // 최적화된 원본 SVG 사용
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("file:///android_asset/icons.svg")
                    .memoryCacheKey("midori_logo") // 메모리 캐시 키 설정
                    .diskCacheKey("midori_logo") // 디스크 캐시 키 설정
                    .crossfade(false) // 애니메이션 비활성화로 빠른 표시
                    .build(),
                contentDescription = "Midori SVG Icon",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Fit
            )

            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.size(16.dp)
            )
            
            Text(
                text = "MIDORI",
                color = Color.White,
                fontSize = 48.sp,
                fontFamily = sairaStencilOne,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen {}
} 