package com.adamjulie.todo.list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
data class Task (val id: String, val title : String, val description : String = "") : java.io.Serializable
{


}
