package com.example.madarsofttask

import android.app.Application
import com.example.madarsofttask.data.UserRoomDatabase


class UsersApplication : Application() {

    val database: UserRoomDatabase by lazy { UserRoomDatabase.getDatabase(this) }
}
