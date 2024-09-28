package io.github.rysanekrivera.common.uiEvents

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    data class NavigateToEmployeeDetails(val employeeId: Int): UiEvent()
    data class ShowAlertDialog(
        val message: String,
        val positiveText: String? = null,
        val negativeText: String? = null,
        val neutralText: String? = null,
        val positiveAction: (() -> Unit)? = null,
        val negativeAction: (() -> Unit)? = null,
        val neutralAction: (() -> Unit)? = null
    ): UiEvent()
}