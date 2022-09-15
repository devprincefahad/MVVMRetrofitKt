package com.example.mvvmkotlin.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmkotlin.data.models.User
import com.example.mvvmkotlin.repos.GithubRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Response

class GithubViewModel : ViewModel() {
    val users = MutableLiveData<List<User>>()
    fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = withContext(Dispatchers.IO) { GithubRepository.getUsers() }
            if (response.isSuccessful) {
                response.body()?.let {
                    users.postValue(it)
                }
            }
        }

    }

    fun searchUsers(name: String) = liveData(Dispatchers.IO) {
        val response = withContext(Dispatchers.IO) { GithubRepository.searchUsers(name) }
        if (response.isSuccessful) {
            response.body()?.let {
                emit(it.items)
            }
        }
    }

}

fun ViewModel.runIO(
    dispatchers: CoroutineDispatcher = Dispatchers.IO,
    function: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(Dispatchers.IO) {
        function()
    }
}