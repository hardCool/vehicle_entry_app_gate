package com.example.test1_dash_room

import android.app.Application

class MyApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ProductRepository(database) }
}
