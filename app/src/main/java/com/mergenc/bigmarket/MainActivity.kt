package com.mergenc.bigmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.hms.ads.HwAds
import com.mergenc.bigmarket.screen.NavGraphs
import com.mergenc.bigmarket.ui.theme.BigMarketTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AGConnectAuth.getInstance().signOut()
        HwAds.init(this)

        setContent {
            BigMarketTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

/*@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_screen") {
        /*composable("login_screen") {
            LoginScreen(navController = navController, navigator = navigator)
        }*/
        composable("register_screen") {
            RegisterScreen(navController = navController)
        }
        /*composable("company_list_screen") {
            CompanyListScreen(navController = navController, navigator =)
        }*/
    }
}*/