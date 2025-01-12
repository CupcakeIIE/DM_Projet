package com.adamjulie.todo.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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

//
//    private fun Uri.toRequestBody(): MultipartBody.Part {
//        val fileInputStream = contentResolver.openInputStream(this)!!
//        val fileBody = fileInputStream.readBytes().toRequestBody()
//        return MultipartBody.Part.createFormData(
//            name = "avatar",
//            filename = "avatar.jpg",
//            body = fileBody
//        )
//    }
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

