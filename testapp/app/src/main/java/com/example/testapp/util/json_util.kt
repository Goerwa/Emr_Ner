package com.example.testapp.util

import android.util.JsonReader
import com.example.testapp.Data_type.Paper_test
import com.example.testapp.Data_type.problem
import org.json.JSONObject
import java.io.StringReader

class json_util {
    /**
     * 解析问题集，返回一个prolem的list
     */
    fun Analysis_post_paper(paper:String) : List<problem>{
        println("aaa"+paper)
        val mJSON = JSONObject(paper)
        var problem = mJSON.optString("problems")
        var r_list:List<problem> = listOf()
        println(mJSON)
        println(problem)
        try {
            val reader = JsonReader(StringReader(problem))
            reader.beginArray()
            while (reader.hasNext()) {
                reader.beginObject()
                var new_problem = problem()
                while (reader.hasNext()) {
                    val tagName = reader.nextName()
                    println(tagName)
                    if (tagName == "problem_id") {
                        new_problem.problem_id = reader.nextString().toInt()
//                        System.out.println(reader.nextString())
                    } else if (tagName == "problem_type") {
                        new_problem.problem_type = reader.nextString()
//                        System.out.println(reader.nextString())
                    } else if (tagName == "duration") {
                        new_problem.problem_duration = reader.nextString()
//                        System.out.println(reader.nextString())
                    } else if (tagName == "content") {
                        new_problem.problem_content = reader.nextString()
//                        System.out.println(reader.nextString())
                    } else if (tagName == "options") {
                        reader.beginArray().toString()
//                        System.out.println(reader.beginArray().toString())
//                        println(reader.nextString()) * 4
                        new_problem.problem_option_a = reader.nextString()
                        new_problem.problem_option_b = reader.nextString()
                        new_problem.problem_option_c = reader.nextString()
                        new_problem.problem_option_d = reader.nextString()
//                        println(reader.endArray())
                        reader.endArray()
                    } else if (tagName == "correct_answer") {
                        new_problem.problem_correct_answer = reader.nextString()
//                        System.out.println(reader.nextString())
                    }
                }
                reader.endObject()
                r_list += new_problem
            }
            reader.endArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return r_list
    }
    /**
     * 解析所有试卷信息，返回Paper_test的list
     */
    fun Analysis_paper_test(paper:String) : List<Paper_test>{
        val mJSON = JSONObject(paper)
        var problem = mJSON.optString("tests")
        var r_list:List<Paper_test> = listOf()
        println(mJSON)
        println(problem)
        try {
            val reader = JsonReader(StringReader(problem))
            reader.beginArray()
            while (reader.hasNext()) {
                reader.beginObject()
                var new_paper_test = Paper_test()
                while (reader.hasNext()) {
                    val tagName = reader.nextName()
                    println(tagName)
                    if (tagName == "test_id") {
                        new_paper_test.test_id = reader.nextString().toInt()
//                        System.out.println(reader.nextString())
                    } else if (tagName == "test_name") {
                        new_paper_test.test_name = reader.nextString()
//                        System.out.println(reader.nextString())
                    } else if (tagName == "test_type") {
                        new_paper_test.test_type = reader.nextString()
//                        System.out.println(reader.nextString())
                    }
                }
                reader.endObject()
                r_list += new_paper_test
            }
            reader.endArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return r_list
    }
    /**
     * 解析所有分数信息，返回一个字符串list
     */
    fun Analysis_score(paper:String) : List<Paper_test>{
        println(paper)
        val mJSON = JSONObject(paper)
        var problem = mJSON.optString("tests")
        var r_list:List<Paper_test> = listOf()
        println(mJSON)
        println(problem)
        try {
            val reader = JsonReader(StringReader(problem))
            reader.beginArray()
            while (reader.hasNext()) {
                reader.beginObject()
                var new_paper_test = Paper_test()
                while (reader.hasNext()) {
                    val tagName = reader.nextName()
                    println(tagName)
//                    if (tagName == "test_id") {
//                        new_paper_test.test_id = reader.nextString().toInt()
////                        System.out.println(reader.nextString())
//                    } else
                    if (tagName == "test_name") {
                        new_paper_test.test_name = reader.nextString()
//                        System.out.println(reader.nextString())
                    } else if (tagName == "score") {
                        new_paper_test.score = reader.nextInt()
//                        System.out.println(reader.nextString())
                    }
                }
                reader.endObject()
                r_list += new_paper_test
            }
            reader.endArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return r_list
    }

}