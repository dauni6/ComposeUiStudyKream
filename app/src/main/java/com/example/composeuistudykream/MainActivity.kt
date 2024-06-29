@file:OptIn(ExperimentalFoundationApi::class)

package com.example.composeuistudykream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeuistudykream.ui.product.ProductScreen
import com.example.composeuistudykream.ui.theme.ComposeUiStudyKreamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeUiStudyKreamTheme {
                ProductScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeUiStudyKreamTheme {
        ProductScreen()
    }
}