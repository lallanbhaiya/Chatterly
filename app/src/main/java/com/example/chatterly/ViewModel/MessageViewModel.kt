package com.example.chatterly.ViewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatterly.Data.Message
import com.example.chatterly.Data.MessageRepository
import com.example.chatterly.Data.user
import com.example.chatterly.Data.Result
import com.example.chatterly.Data.userRepository
import com.example.chatterly.Injection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MessageViewModel: ViewModel() {
    private val messageRepository: MessageRepository
    private val userRepository: userRepository

    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = userRepository(
            FirebaseAuth.getInstance()
            , Injection.instance())

        loadCurrentUser()
    }

    private val _messages = MutableLiveData<List<Message>> ()
    val messages: LiveData<List<Message>> get() = _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<user> ()

    val currentUser: LiveData<user> get() = _currentUser
//    val context = LocalContext.current

    private fun loadCurrentUser(){
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()){
                is com.example.chatterly.Data.Result.success -> {
                    _currentUser.value = result.data
//                    Log.e("userM", "mil gaya ${result.data.firstName}")

                }
                is com.example.chatterly.Data.Result.error -> {
//                    Log.e("userM", "Error getting current user", result.exception)
                }
            }
        }
    }

    fun sendMessage(text: String){
        if(_currentUser.value != null){
            val message = Message(
                senderFirstName = _currentUser.value!!.firstName,
                senderId = _currentUser.value!!.email,
                text = text
            )
            viewModelScope.launch {
                when(val res = _roomId.value?.let {messageRepository.sendMessage(it, message)
                }){
                    is Result.success -> {
//                        Log.d("AnotherMess","bc ${res} me bhej diya ${message.text}")
                    }
                    is Result.error -> {
//                        Log.d("AnotherMess","maa ki chu gaya hi nhi ")
                    }
                    else -> {
//                        Log.d("AnotherMess","bilkul gaand hi mar gyi")

                    }
                }
            }
        }
    }

    fun loadMessages(){
        viewModelScope.launch {
                if(_roomId != null){
                    messageRepository.getChatMessages(_roomId.value.toString()).collect {
                        _messages.value = it
                    }
                }
        }
    }

    fun setRoomId(roomId: String){
        _roomId.value = roomId
        loadMessages()
    }

    fun setCurrentUser(){
        loadCurrentUser()
    }

}