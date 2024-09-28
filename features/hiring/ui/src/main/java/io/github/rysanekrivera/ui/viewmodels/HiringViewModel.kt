package io.github.rysanekrivera.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.rysanekrivera.common.uistate.UiState
import io.github.rysanekrivera.common.utils.updateValue
import io.github.rysanekrivera.domain.repositories.HiringRepository
import io.github.rysanekrivera.common.uiEvents.UiEvent
import io.github.rysanekrivera.data.models.Employee
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiringViewModel @Inject constructor(
    private val hiringRepository: HiringRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _state.value)

    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents = _uiEvents

    fun getAllEmployees() = viewModelScope.launch {
        _state.updateValue { markLoading() }

        runCatching {
            hiringRepository.getAllEmployees()
        }.onSuccess { response ->
            _state.updateValue { successResponse(response) }
        }.onFailure { e ->
            _state.updateValue { errorResponse(e) }
        }

    }

    fun navigateToEmployeeDetails(employeeId: Int) = viewModelScope.launch {
        _uiEvents.emit(UiEvent.NavigateToEmployeeDetails(employeeId))
    }

    fun onShowEmployeeSnackBar(message: String) = viewModelScope.launch {
        _uiEvents.emit(UiEvent.ShowSnackBar(message))
    }

    fun onShowAlertDialog(
        message: String,
        positiveText: String? = null,
        negativeText: String? = null,
        neutralText: String? = null,
        positiveAction: (() -> Unit)? = null,
        negativeAction: (() -> Unit)? = null,
        neutralAction: (() -> Unit)? = null
    ) = viewModelScope.launch {

        _uiEvents.emit(
            UiEvent.ShowAlertDialog(
                message,
                positiveText,
                negativeText,
                neutralText,
                positiveAction,
                negativeAction,
                neutralAction
            )
        )

    }

}