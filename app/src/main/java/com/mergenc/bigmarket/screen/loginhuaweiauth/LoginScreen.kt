package com.mergenc.bigmarket.screen.loginhuaweiauth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.EmailAuthProvider
import com.mergenc.bigmarket.common.*
import com.mergenc.bigmarket.screen.destinations.CompanyListScreenDestination
import com.mergenc.bigmarket.screen.destinations.RegisterScreenDestination
import com.mergenc.bigmarket.screen.registerhuaweiauth.RegisterScreen
import com.mergenc.bigmarket.ui.theme.PinkColor
import com.mergenc.bigmarket.ui.theme.bigMarketGray
import com.mergenc.bigmarket.ui.theme.bigMarketOrange
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun LoginScreen(
    navController: NavController,
    navigator: DestinationsNavigator
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(
                    text = "Welcome to Big Market,",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                ) {}
                Spacer(modifier = Modifier.height(5.dp))
                CommonText(
                    text = "Sign in to see stocks!",
                    fontSize = 28.sp,
                    color = bigMarketGray
                ) {}
            }
            Spacer(modifier = Modifier.height(60.dp))
            CommonTextField(
                text = email,
                placeholder = "Email",
                onValueChange = { email = it },
                isPasswordTextField = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            CommonTextField(
                text = password,
                placeholder = "Password",
                onValueChange = { password = it },
                isPasswordTextField = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(24.dp))

            val toastContext = LocalContext.current

            CommonLoginButton(text = "Login", modifier = Modifier.fillMaxWidth()) {
                Log.d("LoginScreen", "Login button clicked")

                if (email.isNotBlank() && password.isNotBlank()) {

                    Log.d("login info", "email: $email, password: $password")
                    val credential =
                        EmailAuthProvider.credentialWithPassword(email, password)

                    AGConnectAuth.getInstance().signIn(credential).addOnSuccessListener {
                        makeToast(toastContext, "Login successful")
                        navigator.navigate(CompanyListScreenDestination)
                    }.addOnFailureListener {
                        makeToast(toastContext, "Login failed")
                    }
                }
            }

            /*CommonLoginButton(text = "log-out", modifier = Modifier.fillMaxWidth()) {
                AGConnectAuth.getInstance().signOut()
                makeToast(toastContext, "Logged out")
            }*/

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.weight(0.4f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(text = "I'm a new user,", fontSize = 18.sp) {}
                Spacer(modifier = Modifier.width(4.dp))
                CommonText(
                    text = "Sign Up",
                    color = PinkColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                ) {
                    navigator.navigate(RegisterScreenDestination)
                }
            }
        }
    }
}

private fun makeToast(context: Context, msg: String) {
    Toast.makeText(context, "$msg", Toast.LENGTH_LONG).show()
}

@Composable
fun CircularProgressIndicator() {
    CircularProgressIndicator(
        color = bigMarketOrange,
        strokeWidth = 4.dp
    )
}