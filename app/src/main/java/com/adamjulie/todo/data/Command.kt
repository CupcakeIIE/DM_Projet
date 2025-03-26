package com.adamjulie.todo.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Command (
    @SerialName("args")
    val args : Arguments,

    @SerialName("type")
    val type : String,

    @SerialName("uuid")
    val uuid : String
)