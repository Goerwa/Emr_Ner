package com.example.testapp.util

import com.example.testapp.GeneralInterface
import com.example.testapp.Http
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class Post_record(usr_post_socre:Post_score) : GeneralInterface{
    var mLoginCheck = usr_post_socre
    private var id2answer: List<String> = listOf("N", "A", "B", "C", "D")
    override fun response(flag: Boolean, str: String?) {
        if (flag) {
            println(str)
            mLoginCheck.get_usr_Result(true,str!!)
        } else {
            println(str)
            println("Something Wrong!")
        }
    }


    // 上传用户答题结果
    fun send_usr_result(url:String ,usr_name:String, test_info:String, score_info:String, answer_info:IntArray, problem_info:String) {
        println("usr_name " + usr_name)
        println("test_info " + test_info)
        println("score_info " + score_info)
        for (i in 0..answer_info.size-1)
            println(answer_info[i])
        println("problem_info " + problem_info)
        var score:Float = (score_info.toFloat() / answer_info.size.toFloat()) * 100
        var p_list = json_util().Analysis_post_paper(problem_info)
        //通过接收的参数，构造出JSON
        var mJson = ""
        var mjson1 = "{\"username\":\"$usr_name\",\"test_id\":${test_info.split("cut0")[0]},\"score\":${score.toInt()},"
        var mjosn2 = "\"student_answers\":["
        for (i in 0..p_list.size - 1) {
            var p_str = "{\"problem_id\":${p_list[i].problem_id},\"correct_answer\":\"${p_list[i].problem_correct_answer}\"," +
                    "\"student_answer\":\"${id2answer[answer_info[i]]}\"}"
            mjosn2 += p_str
        }
        mjosn2 += "]}"
        mJson = mjson1 + mjosn2
        println(mJson)
        //访问网络
        Http.send(url+"/student/record", this, mJson)
        //新建协程

    }

    interface Post_score{
        fun get_usr_Result(ret:Boolean,tipText:String)
    }
}