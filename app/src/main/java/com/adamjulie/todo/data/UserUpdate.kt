package com.adamjulie.todo.data

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdate (

    val commands : List<Command>
)