package com.ndc.neostore.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.ndc.neostore.data.source.network.firebase.dto.User
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val databaseRef = Firebase.database.reference
    private val auth = Firebase.auth

    fun getUser(
        uid: String = auth.currentUser?.uid ?: "",
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        databaseRef
            .child("users")
            .child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) onSuccess(user)
                    else onFailure("Data not found")
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

            })
    }

    fun checkPersonalization(
        onSuccess: (Boolean) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        databaseRef
            .child("users")
            .child(auth.currentUser?.uid ?: "")
            .child("name")
            .get()
            .addOnSuccessListener { snapshot ->
                val name = snapshot.getValue(String::class.java)
                if (name != null) onSuccess(true)
                else onSuccess(false)

            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }
    }

    fun setUserName(
        name: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val initData = mapOf(
            "uid" to (auth.currentUser?.uid ?: ""),
            "name" to name,
            "email" to (auth.currentUser?.email ?: ""),
            "balance" to 3000000
        )
        databaseRef
            .child("users")
            .child(auth.currentUser?.uid ?: "")
            .setValue(initData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }
    }
}