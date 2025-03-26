package com.adamjulie.todo.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface UserWebService {
    @GET("/sync/v9/user/")
    suspend fun fetchUser(): Response<User>

    @Multipart
    @POST("sync/v9/update_avatar")
    suspend fun updateAvatar(@Part avatar: MultipartBody.Part): Response<User>

    @POST("sync/v9/sync")
    suspend fun update(@Body userUpdate: UserUpdate): Response<Unit>


}
/*
data class UserUpdate(
    val commands: List<Command>
)

data class Command(
    val type: String = "user_update",
    val args: UserUpdateArgs
)

data class UserUpdateArgs(
    val userId: String,
    val newName: String
)*/

