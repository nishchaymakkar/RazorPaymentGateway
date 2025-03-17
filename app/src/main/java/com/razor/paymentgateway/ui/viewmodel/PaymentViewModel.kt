package com.razor.paymentgateway.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razor.paymentgateway.data.config.RazorpayConfig
import com.razor.paymentgateway.data.module.PaymentState
import com.razor.paymentgateway.ui.snackbar.SnackbarController
import com.razor.paymentgateway.ui.snackbar.SnackbarEvent
import com.razorpay.Checkout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class PaymentViewModel: ViewModel(){

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Idle)
    val paymentState = _paymentState.asStateFlow()

    fun startPayment(activity: Activity, amount: Int, description: String="test payment"){
        try {
            val options = JSONObject().apply {
                put("name","paymentGateway demo app")

                put("description",description)
                put("currency", "INR")
                put("amount", (amount*100).toLong())
                put("theme","#3399cc")
                put("method",JSONObject().apply {
                    put("upi", true)
                    put("qr",true)
                })
                put("upi",JSONObject().apply {
                    put("flow","intent")
                })
                put("readonly", JSONObject().apply {
                    put("contact",false)
                    put("email", false)
                    put("method",false)
                })

            }

            val checkout= Checkout()
            checkout.setKeyID(RazorpayConfig.KEY_ID)
            checkout.open(activity,options)
        }catch (e: Exception){
            _paymentState.value = PaymentState.Error(e.message.toString())
        }
    }

    fun handlePaymentSuccess(paymentId: String){
        _paymentState.value = PaymentState.Success(paymentId)
    }

    fun handelPaymentError(code: Int,message: String){
        _paymentState.value = PaymentState.Error(message)
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = "code: $code error:$message",
                )
            )
        }

    }

}