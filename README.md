# Razorpay Payment Gateway Integration in Android

## Overview
This project demonstrates how to integrate Razorpay payment gateway in an Android application using Kotlin and Jetpack Compose. It covers setting up Razorpay SDK, handling payments, and managing payment success or failure callbacks.


## Screenshots
<img src="https://github.com/user-attachments/assets/cf3f515e-96ad-4921-a59c-69d0a590dca7" alt="Payment Screen" width="200"/>
<img src="https://github.com/user-attachments/assets/ca2b722c-770c-4b11-8ee1-837c42860c28" alt="Payment Options Screen" width="200"/>
<img src="https://github.com/user-attachments/assets/8e6ad676-3a95-4f4f-85e5-714b4bb86c2b" alt="Payment Successful Screen" width="200"/>



## Features
- Integrate Razorpay payment gateway
- Handle successful and failed transactions
- Customize payment options
- Implement callback handling in Jetpack Compose

## Prerequisites
- Android Studio (latest version recommended)
- Kotlin and Jetpack Compose setup
- Razorpay API Key (Sign up at [Razorpay](https://razorpay.com/) to get your API key)

## Getting Started
### 1. Add Razorpay Dependency
Add the following dependency in your `build.gradle` (Module) file:
```gradle
dependencies {
    implementation 'com.razorpay:checkout:1.6.26'
}
```

### 2. Add Internet Permission
Update your `AndroidManifest.xml` file to include internet permission:
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

## Running the App
1. Clone this repository
2. Replace `YOUR_RAZORPAY_KEY_ID` with your actual Razorpay Key ID
3. Run the app and click on "Pay Now" to test the integration

## Troubleshooting
- Ensure that your API key is correct.
- Make sure you have an active internet connection.
- Check logs for any detailed error messages.

## License
This project is open-source and available under the MIT License.

---

This README provides a step-by-step guide to integrating Razorpay with an Android Jetpack Compose application. Let me know if you need any modifications!

