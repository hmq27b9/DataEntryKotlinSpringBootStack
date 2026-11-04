package com.dataentrykotlinspringbootstack.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dataentrykotlinspringbootstack.frontend.controllers.AuthViewModel
import com.dataentrykotlinspringbootstack.frontend.controllers.EntryViewModel
import com.dataentrykotlinspringbootstack.frontend.network.RetrofitClient
import com.dataentrykotlinspringbootstack.frontend.storage.TokenStore
import com.dataentrykotlinspringbootstack.frontend.views.EntryScreen
import com.dataentrykotlinspringbootstack.frontend.views.LoginScreen

class MainActivity : ComponentActivity() {

    private lateinit var tokenStore: TokenStore
    private lateinit var authViewModel: AuthViewModel
    private lateinit var entryViewModel: EntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenStore = TokenStore(this)
        val apiService = RetrofitClient.create(tokenStore)

        authViewModel = AuthViewModel(apiService, tokenStore)
        entryViewModel = EntryViewModel(apiService)

        setContent {
            MaterialTheme {
                Surface { AppNav(authViewModel, entryViewModel) }
            }
        }
    }
}

@Composable
fun AppNav(authViewModel: AuthViewModel, entryViewModel: EntryViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("entries") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("entries") {
            EntryScreen(
                viewModel = entryViewModel,
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("entries") { inclusive = true }
                    }
                }
            )
        }
    }
}