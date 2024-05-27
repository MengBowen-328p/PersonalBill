package com.exam.personalbill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent
import com.exam.personalbill.Database
import com.exam.personalbill.R

class SplashActivity : AppCompatActivity() {
    private val splash_time: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            if (isUserLoggedIn()) {
                // 用户已经登录，跳转到主界面
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // 用户未登录，跳转到登录界面
                startActivity(Intent(this, LoginActivity::class.java))
            }
            // 结束当前的 SplashActivity
            finish()
        }, splash_time)
    }
    private fun isUserLoggedIn(): Boolean {
        // 初始化数据库帮助类
        val database = Database(this)
        // 检查用户是否已经登录
        return database.isUserLoggedIn()
    }
}