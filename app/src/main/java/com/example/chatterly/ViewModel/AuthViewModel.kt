package com.example.chatterly.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatterly.Data.Result
import com.example.chatterly.Data.userRepository
import com.example.chatterly.Injection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel:ViewModel() {
    private val userRepo : userRepository

    init{
        userRepo= userRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    private val _authResult = MutableLiveData<Result<Boolean>> ()
    val authResult : LiveData<Result<Boolean>> get() = _authResult

    fun signUp(email: String, password: String, firstName:String, lastName: String){
        viewModelScope.launch {
            _authResult.value = userRepo.signUp(email,password,firstName,lastName)
        }
    }

    fun signIn(email: String, password: String){
        viewModelScope.launch {
            _authResult.value = userRepo.signIn(email,password)
        }
    }
}