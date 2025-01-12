package com.adamjulie.todo.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import coil3.Bitmap
import coil3.Uri
import coil3.compose.AsyncImage

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

    // Initialisation du launcher pour choisir une photo dans la galerie
//    val pickPhoto = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
//        uri = it // selectedUri est un Uri ou null
//    }


    Column {
        AsyncImage(
            modifier = Modifier.fillMaxHeight(0.2f),
            model = bitmap ?: uri,
            contentDescription = null
        )

        Button(onClick = {
            takePicture.launch()
        }) {
            Text("Take picture")
        }

        Button(onClick = {
           // pickPhoto.launch("image/*")
        }) {
            Text("Pick photo")
        }
    }



}

