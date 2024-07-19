package com.example.chatterly.Screen

sealed class screen(val route : String) {
    object signUpScreen : screen("SignUp")
    object signInScreen: screen("SignIn")
    object chatRoomScreen: screen("ChatRoomScreen")
    object chatScreen: screen("ChatScreen")
}