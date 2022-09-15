package com.example.mvvmkotlin.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.data.models.User
import com.example.mvvmkotlin.ui.adapter.UsersAdapter
import com.example.mvvmkotlin.ui.viewmodel.GithubViewModel

class MainActivity : AppCompatActivity() {

    val vm by lazy {
        ViewModelProvider(this).get(GithubViewModel::class.java)
    }

    val list = arrayListOf<User>()
    val originalList = arrayListOf<User>()
    val adapter = UsersAdapter(list)

    lateinit var userRv: RecyclerView
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        userRv = findViewById(R.id.usersRv)
        searchView = findViewById(R.id.searchView)

        userRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    findUsers(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        searchView.setOnCloseListener {
            list.clear()
            list.addAll(originalList)
            adapter.notifyDataSetChanged()
            return@setOnCloseListener true
        }

        vm.fetchUsers()
        vm.users.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                list.addAll(it)
                originalList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

    }

    private fun findUsers(query: String) {
        vm.searchUsers(query).observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }
}