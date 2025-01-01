package com.adamjulie.todo.user

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private fun android.graphics.Bitmap.toRequestBody(): MultipartBody.Part {
    val tmpFile = File.createTempFile("avatar", ".jpg")
    tmpFile.outputStream().use { outputStream ->
        this.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, outputStream)
    }
    return MultipartBody.Part.createFormData(
        name = "avatar",
        filename = "avatar.jpg",
        body = tmpFile.readBytes().toRequestBody() // Transforme les bytes du fichier en RequestBody
    )
}