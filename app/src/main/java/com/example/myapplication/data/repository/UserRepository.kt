package com.example.myapplication.data.repository

import com.example.myapplication.data.Entities.User

object UserRepository {
    private val registeredUsers = mutableMapOf<String, User>()

    fun registerUser(user: User): Boolean {
        if (registeredUsers.containsKey(user.email)) return false
        registeredUsers[user.email] = user
        return true
    }
    fun isValidUser(email: String, password: String): Boolean {
        val user = registeredUsers[email]
        return user?.password == password
    }
    fun getUser(email: String): User? {
        return registeredUsers[email]
    }
}
