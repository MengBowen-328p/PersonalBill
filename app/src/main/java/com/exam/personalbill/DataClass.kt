package com.exam.personalbill

data class User(
    private var userid: Int,
    private var username: String,
    private var password: String,
)

data class DepositEvery(
    val id: Int,
    val category: String,
    val amount: Double,
    val timestamp: String,
    )

data class DepositAll(
    val id: Int,
    val category: String,
    val amount: Double
)




