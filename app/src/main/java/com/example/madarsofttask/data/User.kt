package com.example.madarsofttask.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val userName: String,
    @ColumnInfo(name = "age")
    val age: String,
    @ColumnInfo(name = "job_title")
    val jobTitle: String,
    @ColumnInfo(name = "gender")
    val gender: String
)
