package com.exam.personalbill.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.exam.personalbill.Database
import com.exam.personalbill.R
import com.exam.personalbill.activity.LoginActivity


class Account : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        val logoutButton = view.findViewById<Button>(R.id.logoutbutton)
        logoutButton.setOnClickListener {
            // 清除本地保存的用户信息
            clearUserData()

            // 跳转到登录界面
            startActivity(Intent(activity, LoginActivity::class.java))

            // 结束当前的 Activity
            activity?.finish()
        }

        return view
    }

    private fun clearUserData() {
        val database = Database(requireContext())
        database.clearUserData()
    }
}