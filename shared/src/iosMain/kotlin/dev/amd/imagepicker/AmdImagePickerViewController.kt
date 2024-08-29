package dev.amd.imagepicker

import platform.UIKit.UIViewController

import androidx.compose.ui.window.ComposeUIViewController

import dev.amd.imagepicker.content.AmdImagePickerContent

public fun AmdImagePickerViewController(): UIViewController = ComposeUIViewController {
    AmdImagePickerContent()
}