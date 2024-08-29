package dev.amd.imagepicker.content

import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.TextButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done

import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import com.preat.peekaboo.image.picker.toImageBitmap
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher

@Composable
public fun AmdImagePickerContent() {
    val scope = rememberCoroutineScope()

    val images = remember { mutableStateListOf<ImageBitmap>() }

    val cameraState = rememberPeekabooCameraState(
        onCapture = { bytes ->
            bytes?.toImageBitmap()?.let {
                images += it
            }
        }
    )

    val singleImagePicker = rememberImagePickerLauncher(
        scope = scope,
        selectionMode = SelectionMode.Single,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                images += it.toImageBitmap()
            }
        }
    )

    val multipleImagePicker = rememberImagePickerLauncher(
        scope = scope,
        selectionMode = SelectionMode.Multiple(),
        onResult = { byteArrays ->
            images += byteArrays.map {
                it.toImageBitmap()
            }
        }
    )

    var showCamera by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Spacer(modifier = Modifier.weight(weight = 1f))

                        TextButton(
                            onClick = {
                                singleImagePicker.launch()
                            }
                        ) {
                            Text("1️⃣")
                        }

                        TextButton(
                            onClick = {
                                multipleImagePicker.launch()
                            }
                        ) {
                            Text("🎨")
                        }

                        TextButton(
                            onClick = {
                                showCamera = true
                            }
                        ) {
                            Text("📸")
                        }
                    }
                }
            )
        },
        content = {
            if (showCamera) {
                Box {
                    PeekabooCamera(
                        modifier = Modifier.fillMaxSize(),
                        state = cameraState
                    )
                    IconButton(
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .padding(bottom = 100.dp),
                        onClick = {
                            cameraState.capture()
                            showCamera = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(count = 2),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        top = 12.dp,
                        bottom = WindowInsets.navigationBars
                            .getBottom(density = LocalDensity.current)
                            .dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    items(images) { image ->
                        Image(
                            modifier = Modifier.aspectRatio(ratio = 1f),
                            bitmap = image,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}