package com.mergenc.bigmarket.screen.registerhuaweiauth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.EmailUser
import com.huawei.agconnect.auth.VerifyCodeSettings
import com.mergenc.bigmarket.R
import com.mergenc.bigmarket.common.CommonLoginButton
import com.mergenc.bigmarket.common.CommonText
import com.mergenc.bigmarket.common.CommonTextField
import com.mergenc.bigmarket.ui.theme.PinkColor
import com.mergenc.bigmarket.ui.theme.bigMarketGray
import com.mergenc.bigmarket.ui.theme.bigMarketOrange
import java.util.*

@Composable
fun RegisterScreen(navController: NavController) {
    var verifyCode by remember { mutableStateOf("") }
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
                    text = "Create Account,",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                ) {}
                Spacer(modifier = Modifier.height(5.dp))
                CommonText(
                    text = "Sign up to get started!",
                    fontSize = 28.sp,
                    color = bigMarketGray
                ) {}
            }
            Spacer(modifier = Modifier.height(16.dp))
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
            CommonTextField(
                text = verifyCode,
                placeholder = "Verify code",
                onValueChange = { verifyCode = it },
                isPasswordTextField = false
            )
            Spacer(modifier = Modifier.height(16.dp))

            CommonLoginButton(text = "Register", modifier = Modifier.fillMaxWidth()) {
                if (email.isNotBlank() && password.isNotBlank() && verifyCode.isNotBlank()) {
                    println("Kayit Basarili")
                    Log.d("info", "$email $password $verifyCode")

                    val emailUser = EmailUser.Builder()
                        .setEmail(email)
                        .setVerifyCode(verifyCode)
                        .setPassword(password)
                        .build()
                    AGConnectAuth.getInstance().createUser(emailUser).addOnSuccessListener {
                        Log.d("RegisterScreen", "createUser:success")
                        val user = AGConnectAuth.getInstance().currentUser
                    }.addOnFailureListener {
                        Log.d("RegisterScreen", "createUser:failure")
                    }
                } else {
                    println("Kayit Basarisiz")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val settings = VerifyCodeSettings.newBuilder()
                        .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
                        .locale(Locale.getDefault())
                        .sendInterval(30)
                        .build()
                    if (email != "") {
                        val task = AGConnectAuth.getInstance().requestVerifyCode(email, settings)
                        task.addOnSuccessListener {
                            Log.d("TAG", "requestVerifyCode:success")
                        }.addOnFailureListener {
                            Log.d("TAG", "requestVerifyCode:failure")
                        }
                    } else {
                        println("Email bos olamaz.")
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .height(58.dp)
                    .border(3.dp, Color.Black, RoundedCornerShape(25.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White, RoundedCornerShape(20.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_huawei_logo),
                        contentDescription = "Huawei Logo",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    Text(
                        text = "Verify e-mail",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Spacer(modifier = Modifier.weight(0.3f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonText(text = "I'm a new user,", fontSize = 18.sp) {}
                Spacer(modifier = Modifier.width(4.dp))
                CommonText(
                    text = "Sign In",
                    color = PinkColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                ) {
                    navController.navigate("login_screen") {
                        popUpTo("login_screen") { inclusive = true }
                    }
                }
            }
        }
    }
}