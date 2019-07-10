package com.example.testapp.util

import com.example.testapp.GeneralInterface
import com.example.testapp.Http
import org.json.JSONObject

class Post_test(usr_paper_result: Paper_result) : GeneralInterface {

    var mLoginCheck = usr_paper_result

    override fun response(flag: Boolean, str: String?) {
        if (flag) {
            println(str)
            mLoginCheck.get_paper_Result(true,str!!)
        } else {
            println(str)
            println("Something Wrong!")
        }
    }


    //  提交试卷id 得到题目信息
    fun get_paper_byid(url:String,test_id:String) {
        //通过接收的参数，构造出JSON
        val mJson = "{\"test_id\":${test_id.toInt()}}"
        //访问网络
        Http.send(url + "/student/test", this, mJson)
        //新建协程

    }

    interface Paper_result{
        fun get_paper_Result(ret:Boolean,tipText:String)
    }
}