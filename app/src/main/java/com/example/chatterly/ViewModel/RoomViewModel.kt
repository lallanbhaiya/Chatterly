package com.example.chatterly.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatterly.Data.Room
import com.example.chatterly.Data.RoomRepository
import com.example.chatterly.Data.Result
import com.example.chatterly.Injection
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>> ()
    val rooms : LiveData<List<Room>> get() = _rooms
    private val roomRepo: RoomRepository

    init{
        roomRepo = RoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name: String){
        viewModelScope.launch {
            roomRepo.createRoom(name)
            loadRooms()
        }
    }

    fun loadRooms(){
        viewModelScope.launch {
            when (val result = roomRepo.getRooms()){
                is Result.success -> _rooms.value = result.data
                is Result.error -> {

                }
            }
        }
    }

}