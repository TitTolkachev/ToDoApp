package com.example.todoapp

import android.app.Application
import com.example.todoapp.di.AppContainer
import com.example.todoapp.di.DefaultAppContainer

class App : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}