package com.exam.personalbill

data class ParaBean(
    private var userid: Int,
    private var username: String,
    private var password: String,
    private var timestamp: String,
    private var quantity: Float,
    private var purpose: String,
    private var classification: String,
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




