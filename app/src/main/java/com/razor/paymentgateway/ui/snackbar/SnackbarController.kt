package com.razor.paymentgateway.ui.snackbar

import androidx.annotation.StringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
  val message: String,
  val action: SnackbarAction? = null
)
data class SnackbarAction(
    val name: String,
    val action: ()-> Unit
)
object SnackbarController {
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

  suspend fun sendEvent(event: SnackbarEvent){
    _events.send(event)
  }
}