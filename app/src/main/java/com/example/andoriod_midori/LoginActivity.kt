package com.example.andoriod_midori

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.andoriod_midori.R
import com.example.andoriod_midori.ui.theme.Andoriod_midoriTheme
import com.example.andoriod_midori.ui.theme.MidoriSpacing
import com.example.andoriod_midori.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MidoriSpacing.XXL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(MidoriSpacing.TopMargin))
        
        AsyncImage(
            model = Constants.Assets.MIDORI_ICON,
            contentDescription = "MIDORI Character",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = MidoriSpacing.LogoToTitle),
            contentScale = ContentScale.Fit
        )
        
        Text(
                            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = MidoriSpacing.Size40)
        )
        
        Text(
                            text = stringResource(R.string.login_message),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.padding(bottom = MidoriSpacing.M)
        )
        
        Text(
                            text = stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = MidoriSpacing.Size64)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        OutlinedButton(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as ComponentActivity).finish()
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
                horizontal = 76.dp,
                vertical = 9.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = Constants.Assets.MIRIM_LOGO,
                    contentDescription = "Mirim Logo",
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = stringResource(R.string.login_button),
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