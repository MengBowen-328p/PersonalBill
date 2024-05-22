package com.exam.personalbill

data class ParaBean(
    private var userid: Int,
    private var username: String,
    private var password: String,
    private var timestamp: String,
    private var quantity: Float,
    private var purpose: String,
    private var classification: String,
) {
    fun getUserId(): Int {
        return userid;
    }
    fun getUserName(): String{
        return username
    }
    fun getPassword(): String{
        return password
    }
    fun getTimeStamp(): String{
        return timestamp
    }
    fun getQuantity(): Float{
        return quantity
    }
    fun getPurpose(): String{
        return purpose
    }
    fun getClassification(): String{
        return classification
    }
    fun setUserId(newuserid: Int){
        requireNotNull(newuserid)
        userid = newuserid
    }
    fun setUserName(newusername: String){
        require(newusername.isNotBlank())
        username = newusername
    }
    fun setPassword(newpassword: String){
        require((newpassword.isNotBlank()))
        password = newpassword
    }
    fun setTimeStamp(newtimestamp: String){
        require(newtimestamp.isNotBlank())
        timestamp = newtimestamp
    }
    fun setQuantity(newquantity: Float){
        requireNotNull(newquantity)
        quantity = newquantity
    }
    fun setPurpose(newpurpose: String){
        purpose = newpurpose
    }
    fun setClassification(newclassification: String)
    {
        classification = newclassification
    }
}

data class Deposit(val id: Int, val category: String, val amount: Double)

