package com.example.chatterly.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatterly.ViewModel.AuthViewModel

@Composable
fun SignUp(
    authViewModel: AuthViewModel,
    onNavigateToSignIn: ()->Unit
){

    var email by remember{ mutableStateOf("")}
    var password by remember{mutableStateOf("")}
    var firstName by remember{mutableStateOf("")}
    var lastName by remember{mutableStateOf("")}


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.Center
    ){

        OutlinedTextField(
            value = email ,
            onValueChange = {email = it},
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = password ,
            onValueChange = {password = it},
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = firstName ,
            onValueChange = {firstName = it},
            label = { Text(text = "First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = lastName ,
            onValueChange = {lastName = it},
            label = { Text(text = "Last Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        
        Button(onClick = {
//      function to be added
            authViewModel.signUp(email,password,firstName, lastName)
            email = "";
            password = ""
            firstName = "";
            lastName = "";

        }, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Text(text = "Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Already have an account? Sign in" ,
            modifier = Modifier.clickable { onNavigateToSignIn() }
            )

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview(){
    SignUp(viewModel()){}
}