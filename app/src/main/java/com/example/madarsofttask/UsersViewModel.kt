package com.example.madarsofttask

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.madarsofttask.data.User
import com.example.madarsofttask.data.UserDao
import kotlinx.coroutines.launch


class UsersViewModel(private val userDao: UserDao) : ViewModel() {

    val allUsers: LiveData<List<User>> = userDao.getUsers().asLiveData()

    fun addNewUser(userName: String, age: String, jobTitle: String, gender: String) {
        val newUser = getNewUserEntry(userName, age, jobTitle,gender)
        insertUser(newUser)
    }

    private fun insertUser(user: User) {
        viewModelScope.launch {
            userDao.insert(user)
        }
    }


    fun isEntryValid(userName: String, age: String, jobTitle: String, gender: String): Boolean {
        return !(userName.isBlank() || age.isBlank() || jobTitle.isBlank() || gender.isBlank())
    }

    private fun getNewUserEntry(userName: String, age: String, jobTitle: String, gender: String): User {
        return User(
            userName = userName,
            age = age,
            jobTitle = jobTitle, gender = gender
        )
    }


}


class UserViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

