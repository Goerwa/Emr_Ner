package com.example.testapp.util

import com.example.testapp.GeneralInterface
import com.example.testapp.Http
import org.json.JSONObject

/**
 * 获取所有试卷信息
 */
class Get_test(usr_papers_Result: Papers_Result) : GeneralInterface {

    val mGet_papers_Result = usr_papers_Result

    override fun response(flag: Boolean, str: String?) {
        if (flag) {
            println(str)
            mGet_papers_Result.get_paper_Result(true, str!!)
        } else {
            println(str)
            println("Something Wrong!")
        }
    }

    // GET方法获取所有试卷信息
    fun get_all_papers(url:String) {
        //访问网络
        Http.sendGet(url + "/student/test", this)
        //新建协程

    }
    interface Papers_Result{
        fun get_paper_Result(ret:Boolean,tipText:String)
    }
}