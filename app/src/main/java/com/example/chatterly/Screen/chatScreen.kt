package com.example.chatterly.Screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatterly.Data.Message
import com.example.chatterly.Data.Room
import com.example.chatterly.ViewModel.MessageViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun chatScreen(roomId : String,
               messageViewModel : MessageViewModel = viewModel()
               ){

    messageViewModel.setCurrentUser()

    val messages by messageViewModel.messages.observeAsState(emptyList())
    messageViewModel.setRoomId(roomId)

    val text = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ){
            messageViewModel.setCurrentUser()
            for(it in messages){
                print(it)
            }
            items(messages){message->
                chatMessageItem(message = message.copy(isSentByCurrentUser = message.senderId == messageViewModel.currentUser.value?.email))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(value = text.value,
                onValueChange = { text.value = it },
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                )

            IconButton(onClick = {
                if(text.value.isNotEmpty()){
                    Log.d("Mess","BKL click ho gyi")
                    messageViewModel.sendMessage(text.value.trim())
                    Log.e("Mess","Ye bheja hai: ${text.value.trim()}")
                    text.value = ""
                }
                messageViewModel.loadMessages()
            }) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatTimeStamp(timeStamp: Long) : String{
    val messageDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())
    val now = LocalDateTime.now()

    return when{
        isSameDay(messageDateTime, now) -> "today ${formatTime(messageDateTime)}"
        isSameDay(messageDateTime.plusDays(1), now) -> "yesterday ${formatTime(messageDateTime)}"
        else -> "${formatData(messageDateTime)}"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isSameDay(dateTime1: LocalDateTime, dateTime2: LocalDateTime) : Boolean{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return dateTime1.format(formatter) == dateTime2.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatTime(dateTime: LocalDateTime) : String{
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return dateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatData(dateTime: LocalDateTime) : String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return dateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun chatMessageItem(message: Message){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ,
        horizontalAlignment = if(message.isSentByCurrentUser) Alignment.End else Alignment.Start
    ){

        Box(
            modifier = Modifier
                .background(
                    color = if (message.isSentByCurrentUser) Color.Blue else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ){
            Text(message.text, color = Color.White, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp) )
        Text(text = message.senderFirstName, style = TextStyle(fontSize = 12.sp, color = Color.Gray))
        Text(formatTimeStamp(message.timestamp), style = TextStyle(fontSize = 12.sp, color = Color.Gray))

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun chatScreenPreview(){
    chatScreen("")
}