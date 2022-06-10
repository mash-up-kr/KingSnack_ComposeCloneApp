package com.example.kingsnack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kingsnack.ui.theme.KingSnackTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KingSnackTheme {
                // A surface container using the 'background' color from the theme
                KingsnackApp()
            }
        }
    }
}
