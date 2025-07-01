package com.example.andoriod_midori

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.andoriod_midori.ui.theme.Andoriod_midoriTheme
import com.example.andoriod_midori.ui.theme.MidoriSpacing

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Andoriod_midoriTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MidoriSpacing.XXL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(MidoriSpacing.TopMargin))
        
        AsyncImage(
            model = "file:///android_asset/icons.svg",
            contentDescription = "MIDORI Character",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = MidoriSpacing.LogoToTitle),
            contentScale = ContentScale.Fit
        )
        
        Text(
            text = "MIDORI",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = MidoriSpacing.XXXL)
        )
        
        Text(
            text = "기숙사 일정부터 세탁실 현황, 외출 신청까지!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.padding(bottom = MidoriSpacing.M)
        )
        
        Text(
            text = "스마트한 기숙사 생활을 경험해보세요.",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = MidoriSpacing.XXXXXL)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        OutlinedButton(
            onClick = {
                // TODO: 로그인 로직 구현
            },
            modifier = Modifier
                .width(325.dp)
                .height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline
            ),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(
                horizontal = MidoriSpacing.ButtonPaddingHorizontal, 
                vertical = MidoriSpacing.ButtonPaddingVertical
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = "file:///android_asset/mirimlogo.svg",
                    contentDescription = "Mirim Logo",
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(MidoriSpacing.IconToText))
                
                Text(
                    text = "미림마고 계정으로 로그인",
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1
                )
            }
        }
        
        Spacer(modifier = Modifier.height(MidoriSpacing.BottomMargin))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Andoriod_midoriTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen()
        }
    }
} 