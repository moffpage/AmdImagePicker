package dev.amd.imagepicker.android

import android.os.Bundle
import android.graphics.Color

import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import dev.amd.imagepicker.content.AmdImagePickerContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = systemBarsStyle,
            navigationBarStyle = systemBarsStyle
        )

        setContent {
            AmdImagePickerContent()
        }
    }

    private companion object {
        val systemBarsStyle = SystemBarStyle.auto(
            lightScrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT
        )
    }
}
