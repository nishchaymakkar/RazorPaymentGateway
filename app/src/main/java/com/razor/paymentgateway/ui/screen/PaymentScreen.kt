package com.razor.paymentgateway.ui.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.razor.paymentgateway.R
import com.razor.paymentgateway.data.module.PaymentState
import com.razor.paymentgateway.ui.snackbar.ObserveAsEvents
import com.razor.paymentgateway.ui.snackbar.SnackbarController
import com.razor.paymentgateway.ui.viewmodel.PaymentViewModel
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = viewModel()
) {
    var amount by remember { mutableStateOf("") }

    val context = LocalContext.current
    val paymentState by viewModel.paymentState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = SnackbarController.events, snackbarHostState
    ) { event->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )
        }
    }
  Scaffold (
      snackbarHost = {
          SnackbarHost(
              hostState = snackbarHostState,
              snackbar = { snackbarData ->
                  Snackbar(
                      snackbarData = snackbarData,
                      containerColor = MaterialTheme.colorScheme.error,
                      contentColor = MaterialTheme.colorScheme.onError
                  )
              }
          )
      }
  )  {padding->
        Column (
            modifier = modifier.padding(padding).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                textAlign = TextAlign.Center,
                text = "Make a Payment",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    amount.toDoubleOrNull()?.let {
                        viewModel.startPayment(context as Activity, it.toInt())
                    }
                },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ) {
                Text("Pay")
                Spacer(Modifier.width(8.dp))
                Icon(painter = painterResource(R.drawable.paymenticon), modifier = Modifier.size(20.dp), contentDescription = "Pay")
            }
            when(val state: PaymentState = paymentState){
                is PaymentState.Success -> {
                    Text(text = "Payment Successful: ${state.paymentId}",
                        color = MaterialTheme.colorScheme.primary,
                        modifier =   modifier.padding(16.dp),
                        textAlign = TextAlign.Unspecified
                    )
                }
                is PaymentState.Error -> {
                    Text(text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                      modifier =   modifier.padding(16.dp),
                        textAlign = TextAlign.Unspecified)
                }
                else -> {}
            }
        }


    }
}