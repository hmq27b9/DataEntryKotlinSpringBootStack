package com.dataentrykotlinspringbootstack.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dataentrykotlinspringbootstack.frontend.controllers.AuthController
import com.dataentrykotlinspringbootstack.frontend.controllers.AuthInterceptor
import com.dataentrykotlinspringbootstack.frontend.controllers.EntryController
import com.dataentrykotlinspringbootstack.frontend.controllers.TokenStore
import com.dataentrykotlinspringbootstack.frontend.routers.AuthRouter
import com.dataentrykotlinspringbootstack.frontend.routers.EntryRouter
import com.dataentrykotlinspringbootstack.frontend.views.EntryScreen
import com.dataentrykotlinspringbootstack.frontend.views.LoginScreen

class MainActivity : ComponentActivity() {

    private lateinit var authController: AuthController
    private lateinit var entryController: EntryController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenStore = TokenStore(this)
        val interceptor = AuthInterceptor(tokenStore)

        val userRepository = AuthRouter.create(interceptor)
        val entryRepository = EntryRouter.create(interceptor)

        authController = AuthController(userRepository, tokenStore)
        entryController = EntryController(entryRepository)

        setContent {
            AppNav(authController, entryController)
        }
    }
}

@Composable
fun AppNav(authController: AuthController, entryController: EntryController) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                controller = authController,
                onLoginSuccess = {
                    navController.navigate("entries") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("entries") {
            EntryScreen(
                controller = entryController,
                onLogout = {
                    authController.logout()
                    navController.navigate("login") {
                        popUpTo("entries") { inclusive = true }
                    }
                }
            )
        }
    }
}