package com.adamjulie.todo.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Arguments (

    @SerialName("full_name")
    val full_name : String,

) : java.io.Serializable