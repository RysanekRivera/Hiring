package io.github.rysanekrivera.common.utils

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <reified S> MutableStateFlow<S>.updateValue(block: S.() -> S) {
    while (true) {
        val prevValue = value
        val nextValue = block(prevValue)
        if (compareAndSet(prevValue, nextValue)) {
            return
        }
    }
}
