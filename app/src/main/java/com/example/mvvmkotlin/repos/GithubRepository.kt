package com.example.mvvmkotlin.repos

import com.example.mvvmkotlin.data.api.Client

object GithubRepository {

    suspend fun getUsers() = Client.api.getUsers()

    suspend fun searchUsers(name:String) = Client.api.searchUsers(name)
}