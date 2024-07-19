package com.example.chatterly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatterly.Screen.SignIn
import com.example.chatterly.Screen.SignUp
import com.example.chatterly.Screen.chatRoomListScreen
import com.example.chatterly.Screen.chatScreen
import com.example.chatterly.Screen.screen
import com.example.chatterly.ViewModel.AuthViewModel
import com.example.chatterly.ui.theme.ChatterlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            ChatterlyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navigation(navController,authViewModel)
                }
            }
        }
    }
}

@Composable
fun navigation(navController: NavHostController,
               authViewModel: AuthViewModel
               ){

    NavHost(navController = navController, startDestination =  screen.signUpScreen.route ){
        composable(route = screen.signUpScreen.route){
            SignUp(
                authViewModel,
                onNavigateToSignIn = {navController.navigate(screen.signInScreen.route)}
            )
        }
        composable(route = screen.signInScreen.route){
            SignIn(
                authViewModel,
                onNavigateToSignUp = {navController.navigate(screen.signUpScreen.route)}
            ){
                navController.navigate(screen.chatScreen.route)
            }
        }
        composable(route = screen.chatScreen.route){
            chatRoomListScreen{
                navController.navigate("${screen.chatRoomScreen.route}/${it.id}")
            }
        }

        composable(route = "${screen.chatRoomScreen.route}/{roomId}"){
            val roomId: String = it.arguments?.getString("roomId")?:""
            chatScreen(roomId = roomId)
        }
    }
}
