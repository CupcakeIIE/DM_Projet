package com.adamjulie.todo.user

import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import coil3.Bitmap
import coil3.Uri
import coil3.compose.AsyncImage
import com.adamjulie.todo.user.ui.theme.TodoAdamJulieTheme
import okhttp3.MultipartBody

class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                UserUI()
        }
    }
}


@Composable
fun UserUI() {
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    var uri: Uri? by remember { mutableStateOf(null) }

    // Initialisation du launcher pour prendre une photo avec TakePicturePreview
    val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        bitmap = it
    }


    Column {
        AsyncImage(
            modifier = Modifier.fillMaxHeight(0.2f),
            model = bitmap ?: uri,
            contentDescription = null
        )

        Button(onClick = {
            // Action pour prendre une photo (implémentation à faire)
        }) {
            Text("Take picture")
        }

        Button(onClick = {
            // Action pour choisir une photo (implémentation à faire)
        }) {
            Text("Pick photo")
        }
    }
}
