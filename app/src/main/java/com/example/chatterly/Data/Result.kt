package com.example.chatterly.Data

sealed class Result<out T>{
    data class success<out T> (val data: T): Result<T>()
    data class error(val exception: Exception) : Result<Nothing> ()
}

