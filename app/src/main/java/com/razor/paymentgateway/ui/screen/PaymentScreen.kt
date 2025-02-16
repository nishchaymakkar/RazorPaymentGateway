package com.razor.paymentgateway.ui.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.razor.paymentgateway.data.module.PaymentState
import com.razor.paymentgateway.ui.viewmodel.PaymentViewModel

@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = viewModel()
) {
    var amount by remember { mutableStateOf("") }

    val context = LocalContext.current
    val paymentState by viewModel.paymentState.collectAsState()
  Scaffold   {padding->
        Column (
            modifier = modifier.padding(padding).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
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
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Pay with RazorPay")
            }
            when(val state: PaymentState = paymentState){
                is PaymentState.Success -> {
                    Text(text = "Payment Successful: ${state.paymentId}")
                }
                is PaymentState.Error -> {
                    Text(text = "Error: ${state.message}")
                }
                else -> {}
            }
        }


    }
}