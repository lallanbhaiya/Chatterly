package com.example.chatterly.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatterly.Data.Result
import com.example.chatterly.ViewModel.AuthViewModel

@Composable
fun SignIn(
    authViewModel: AuthViewModel,
    onNavigateToSignUp: ()->Unit,
    onSignInSuccess: ()->Unit
){
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }

    val result by authViewModel.authResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        OutlinedTextField(value = email, onValueChange = {email = it}, label = {Text("Email")})
        OutlinedTextField(value = password, onValueChange = {password = it} , label = {Text("Password")} , visualTransformation = PasswordVisualTransformation())
        Button(
            onClick = {
                        authViewModel.signIn(email, password)
                      when(result){
                          is Result.success -> {
                              onSignInSuccess()
                          }
                          is Result.error ->{

                          }
                          else->{

                          }
                      }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text("Sign in")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "New user? Sign up" , modifier = Modifier.clickable { onNavigateToSignUp() })
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview(){
    SignIn(viewModel(),{}){}
}