package com.exam.personalbill.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.exam.personalbill.fragment.DashBoard
import com.exam.personalbill.fragment.Home
import com.exam.personalbill.R
import com.exam.personalbill.fragment.Account
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    replaceFragment(DashBoard())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_home -> {
                    replaceFragment(Home())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_account -> {
                    replaceFragment(Account())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottomNavMenu)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // 设置默认Fragment
        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.nav_home
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}