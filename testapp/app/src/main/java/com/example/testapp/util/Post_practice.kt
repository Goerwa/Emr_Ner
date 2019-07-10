package com.example.testapp.util

import com.example.testapp.GeneralInterface
import com.example.testapp.Http

class Post_practice(usr_practice_result:Practice_Result) : GeneralInterface {
    val musr_practice_result = usr_practice_result

    override fun response(flag: Boolean, str: String?) {
        if (flag) {
            println(str)
            musr_practice_result.get_practice_Result(true, str!!)
        } else {
            println(str)
            println("Something Wrong!")
        }
    }

    // 获取一点量的题目
    fun get_practice(url:String, problem_type:String, problem_num:Int) {
        println(problem_type)
        println(problem_num)
        val mJson = "{\"problem_type\":\"$problem_type\",\"number\":$problem_num}"
        //访问网络
        Http.send(url + "/student/practice/", this, mJson)
        //新建协程

    }
    interface Practice_Result{
        fun get_practice_Result(ret:Boolean,tipText:String)
    }
}