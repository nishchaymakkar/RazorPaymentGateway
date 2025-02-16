package com.razor.paymentgateway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.razor.paymentgateway.ui.screen.PaymentScreen
import com.razor.paymentgateway.ui.theme.RazorPaymentGatewayTheme
import com.razor.paymentgateway.ui.viewmodel.PaymentViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener

class MainActivity : ComponentActivity() , PaymentResultWithDataListener{

    private val viewModel: PaymentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Checkout.preload(applicationContext)
        setContent {
            RazorPaymentGatewayTheme {
                PaymentScreen(viewModel = viewModel)
            }
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData?) {
        if (razorpayPaymentId != null) {
            viewModel.handlePaymentSuccess(razorpayPaymentId)
        }
    }

    override fun onPaymentError(code: Int, response: String?, paymentData: PaymentData?) {
        viewModel.handelPaymentError(code,response?: "Payment failed")
    }
}
