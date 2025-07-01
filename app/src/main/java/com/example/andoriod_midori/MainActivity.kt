package com.example.andoriod_midori

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.andoriod_midori.ui.theme.Andoriod_midoriTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Andoriod_midoriTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMenuScreen()
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MIDORI 메인 메뉴",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Button(
            onClick = { /* TODO: 기능 추가 */ },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("시작하기")
        }
        
        Button(
            onClick = { /* TODO: 기능 추가 */ },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("설정")
        }
        
        Button(
            onClick = { /* TODO: 기능 추가 */ },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("정보")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    Andoriod_midoriTheme {
        MainMenuScreen()
    }
} 