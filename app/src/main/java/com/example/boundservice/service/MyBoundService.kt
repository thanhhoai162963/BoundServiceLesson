package com.example.boundservice.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.Random

class MyBoundService : Service() {
    // Binder được sử dụng để trả về instance của service
    private val binder = LocalBinder()

    // Lớp LocalBinder để trả về service instance
    inner class LocalBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }

    // Phương thức trả về đối tượng Binder để liên kết với client
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }


    fun getRandomNumber(demo :Int): Int {
        val a = 10 + demo
        return a
    }


}