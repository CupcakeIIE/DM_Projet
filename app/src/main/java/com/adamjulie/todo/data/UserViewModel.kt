package com.adamjulie.todo.data

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.MultipartBody
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    val webService = Api.userWebService

    val defaultUser = User(
        email = "truc@mail.com",
        name = "Machin",
        avatar = null
    )

    val user = MutableStateFlow<User>(defaultUser)


    fun updatePicture(avatarPart : MultipartBody.Part) {
        viewModelScope.launch {
            val response = webService.updateAvatar(avatarPart)
            if(!response.isSuccessful) {
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val updatedProfilePicture = response.body()!!
            user.value = updatedProfilePicture
        }
    }

}