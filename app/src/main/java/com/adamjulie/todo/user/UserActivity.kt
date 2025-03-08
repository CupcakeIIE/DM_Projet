package com.adamjulie.todo.user

import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import coil3.Bitmap
import coil3.Uri
import coil3.compose.AsyncImage
import com.adamjulie.todo.data.Api.userWebService
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserUI()
        }
    }

    private fun android.net.Uri.toRequestBody(): MultipartBody.Part {
        val fileInputStream = contentResolver.openInputStream(this)!!
        val fileBody = fileInputStream.readBytes().toRequestBody()
        return MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "avatar.jpg",
            body = fileBody
        )
    }

    private fun android.graphics.Bitmap.toRequestBody(): MultipartBody.Part {
        val tmpFile = File.createTempFile("avatar", "jpg")
        tmpFile.outputStream().use { // *use*: open et close automatiquement
            this.compress(
                android.graphics.Bitmap.CompressFormat.JPEG,
                100,
                it
            ) // *this* est le bitmap ici
        }
        return MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "avatar.jpg",
            body = tmpFile.readBytes().toRequestBody()
        )
    }


    @Composable
    fun UserUI() {
        var bitmap: Bitmap? by remember { mutableStateOf(null) }
        var uri: Uri? by remember { mutableStateOf(null) }

        val composeScope = rememberCoroutineScope()
        val captureUri by lazy {
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
        }


        // Initialisation du launcher pour prendre une photo avec TakePicturePreview
        val takePicture =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { success ->
                if (success) {
                    captureUri?.let {
                        uri = coil3.Uri(it.toString())
                        composeScope.launch {
                            val avatarPart = it.toRequestBody()
                            userWebService.updateAvatar(avatarPart)
                        }
                        }
                } else {
                    return@rememberLauncherForActivityResult
                }
            }


        val pickMedia =  rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { newUri ->
            if (newUri != null) {
                uri = coil3.Uri(newUri.toString())
                composeScope.launch {
                    val avatarPart = newUri.toRequestBody()
                    userWebService.updateAvatar(avatarPart)
                }
            } else {
                return@rememberLauncherForActivityResult
            }
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

            Button(
                onClick = {
                    takePicture.launch(captureUri!!)
                },
                content = { Text("Take picture") }
            )

            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                content = { Text("Pick photo") }
            )
        }


    }
}


