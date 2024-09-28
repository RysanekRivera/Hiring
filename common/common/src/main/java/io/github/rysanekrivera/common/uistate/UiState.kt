package io.github.rysanekrivera.common.uistate

import io.github.rysanekrivera.common.state.RemoteRequestState
import io.github.rysanekrivera.data.models.Employee
import io.github.rysanekrivera.data.models.EmployeeDto
import retrofit2.Response

data class UiState(
    val allEmployeesRequestState: RemoteRequestState = RemoteRequestState.idle(),
    val allEmployeesList: List<Employee> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val errorCode: Int? = null
) {
    fun markLoading() = copy(isLoading = true)

    fun successResponse(response: Response<List<EmployeeDto>>) = when {

        response.isSuccessful && response.body() != null -> copy(
            allEmployeesRequestState = RemoteRequestState.success(),
            allEmployeesList = response.body()?.filterNot{ it.name.isNullOrBlank() }?.sortedWith(compareBy<EmployeeDto> { it.listId }.thenBy { it.name })?.map { it.toEmployee() } ?: emptyList(),
            isLoading = false
        )

        else -> noContentResponse()
    }

    fun noContentResponse() = copy(
        allEmployeesRequestState = RemoteRequestState.successNoContent(),
        isLoading = false,
    )

    fun errorResponse(error: Throwable?) = copy(
        allEmployeesRequestState = RemoteRequestState.error(),
        error = error,
        isLoading = false
    )

}