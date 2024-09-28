package io.github.rysanekrivera.common.state

sealed class RemoteRequestState {

    companion object {
        fun success() : RemoteRequestState = Success
        fun successNoContent(): RemoteRequestState = SuccessNoContent
        fun error(): RemoteRequestState = Error
        fun idle(): RemoteRequestState = Idle
    }

    data object Success : RemoteRequestState()
    data object Error : RemoteRequestState()
    data object SuccessNoContent : RemoteRequestState()
    data object Idle : RemoteRequestState()
}