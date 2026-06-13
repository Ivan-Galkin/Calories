package com.colorilens.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.colorilens.app.ui.ColoriLensApp
import com.colorilens.app.ui.theme.ColoriLensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColoriLensTheme {
                ColoriLensApp()
            }
        }
    }
}
