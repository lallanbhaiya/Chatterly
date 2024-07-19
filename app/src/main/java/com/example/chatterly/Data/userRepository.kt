package com.example.chatterly.Data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class userRepository(private val auth: FirebaseAuth,
                     private val firestore: FirebaseFirestore
    ) {

    suspend fun signUp(email: String, password: String, firstName: String, lastName: String): Result<Boolean> =
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            if (authResult.user != null) {
                val user = user(email, firstName, lastName)
                saveUserToFirebase(user)
//                Log.d("UserSuccess", "User created and saved to Firestore")
                Result.success(true)
            } else {
//                Log.d("UserError", "Authentication succeeded but no user returned")
                Result.error(Exception("Authentication succeeded but no user returned"))
            }
        } catch (e: Exception) {
//            Log.d("UserError", "Error during sign up: ${e.message}")
            Result.error(e)
        }

    private suspend fun saveUserToFirebase(user: user){
        try {
            firestore.collection("users").document(user.email).set(user).await()
//            Log.d("FirestoreSuccess", "User data saved to Firestore")
        } catch (e: Exception) {
//            Log.d("FirestoreError", "Failed to save user data to Firestore: ${e.message}")
            throw e // Rethrow the exception to handle it in the calling function
        }
    }

    suspend fun signIn(email: String, password: String) : Result<Boolean> =
        try{
            auth.signInWithEmailAndPassword(email,password).await()
            Result.success(true)
        }
        catch (e: Exception){
            Result.error(e)
        }

    suspend fun getCurrentUser(): Result<user> = try {
        val uid = auth.currentUser?.email
        if (uid != null) {
            val userDocument = firestore.collection("users").document(uid).get().await()
            val user = userDocument.toObject(user::class.java)
            if (user != null) {
//                Log.d("user2","$uid")
                Result.success(user)
            } else {
                Result.error(Exception("User data not found"))
            }
        } else {
            Result.error(Exception("User not authenticated"))
        }
    } catch (e: Exception) {
        Result.error(e)
    }
}