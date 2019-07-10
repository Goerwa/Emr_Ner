package com.example.testapp.util

import com.example.testapp.GeneralInterface
import com.example.testapp.Http

class Get_record(usr_score_result: Score_result) : GeneralInterface{

    val mGet_papers_Result = usr_score_result

    override fun response(flag: Boolean, str: String?) {
        if (flag) {
            mGet_papers_Result.get_score_info(true, str!!)
        } else {
            println(str)
            println("Something Wrong!")
        }
    }

    // 返回用户分数
    fun get_all_score(url:String, id:String) {
        var mJson = "{\"username\":\"$id\"}"
        //访问网络
        Http.send(url + "/student/score", this, mJson)

    }
    interface Score_result{
        fun get_score_info(ret:Boolean,tipText:String)
    }
}