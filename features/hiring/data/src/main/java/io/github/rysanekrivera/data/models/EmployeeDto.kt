package io.github.rysanekrivera.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeDto(
    @SerialName("id") val id: Int,
    @SerialName("listId") val listId: Int,
    @SerialName("name") val name: String? = null
) {

    fun toEmployee() = Employee(id = id, listId = listId, name ?: "")

}

data class Employee(
    val id: Int,
    val listId: Int,
    val name: String
)