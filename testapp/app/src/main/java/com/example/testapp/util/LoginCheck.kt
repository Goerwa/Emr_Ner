package com.example.testapp.util

import com.example.testapp.GeneralInterface
import com.example.testapp.Http
import org.json.JSONObject

/**
 * 登录功能的实现
 */
class LoginCheck(pLoginResult: LoginResult): GeneralInterface {

    var mLoginCheck = pLoginResult

    override fun response(flag: Boolean, str: String?) {
        if (flag) {
            println(str)
            val mJSON = JSONObject(str!!)
            when (mJSON.optString("code", "400")) {
                "200" -> {
                    mLoginCheck.getLoginResult(true,mJSON.optString("right",""))
                }
                "201" -> {
                    mLoginCheck.getLoginResult(false,mJSON.optString("false",""))
                }
            }
        } else {
            println(str)
            println("Something Wrong!")
        }
    }


    /**
     * 接收两个参数，分别是用户名和密码，将用户名和密码发送到云端，判断，并返回结果
     */
    fun checkUserNameAndPassword(url:String, userName: String, userPassword: String = "") {
        //通过接收的参数，构造出JSON
        val mJson = "{\"username\":\"$userName\",\"userpassword\":\"$userPassword\"}"
        //访问网络
        Http.send(url + "/student/login", this, mJson)
        //新建协程

    }

    interface LoginResult{
        fun getLoginResult(ret:Boolean,tipText:String)
    }
}
//class LoginCheck {
//
//    val mCallBacks = object : GeneralInterface {
//        override fun response(flag: Boolean, str: String?) : String {
//            if (flag) {
//                println(str)
//                println("right")
//            } else {
//                println(str)
//                println("wrong")
//            }
//        }
//
//    }
//
//    /**
//     * 接收两个参数，分别是用户名和密码，将用户名和密码发送到云端，判断，并返回结果
//     */
//    fun checkUserNameAndPassword(userName:String,userPassword:String = "") : String{
//        val mJson = "{\"username\":\"$userName\",\"userpassword\":\"$userPassword\"}"
//        //访问网络
//        GlobalScope.launch {
//            println(mJson)
////            Http.send("http:/172.20.122.36:8080/student/login", mCallBacks, mJson)
//            Http.send("http:/172.20.122.36:8080/student/login", mCallBacks, mJson)
//            //新建协程
//            kotlinx.coroutines.delay(1000L)
//            //延迟1000毫秒
//            return mCallBacks
//        }
//
//    }
//
//    fun test_get(){
//        //访问网络
//        GlobalScope.launch {
//            Http.sendGet("http:/www.baidu.com", mCallBacks)
//            //新建协程
//            kotlinx.coroutines.delay(1000L)
//            //延迟1000毫秒
//        }
//
//    }
//}