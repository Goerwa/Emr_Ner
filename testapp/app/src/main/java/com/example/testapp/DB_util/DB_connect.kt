package com.example.testapp.DB_util

import android.database.sqlite.SQLiteDatabase
import android.database.Cursor
//import com.sun.tools.corba.se.idl.Util.getAbsolutePath


class DB_connect {

    private var db:SQLiteDatabase
    //构造方法
    init{
        //连接数据库
        println("初始化db")
        db = SQLiteDatabase.openDatabase("/data/data/com.example.testapp/databases/test_app.db",
            null, SQLiteDatabase.OPEN_READWRITE)
    }
    //获取数据库的数据
    fun getqustion(table_name:String,need_do:Int): List<question> {
        println(table_name)
        var list:List<question> = arrayListOf()
        var cursor:Cursor = db.rawQuery("select * from " + table_name, null)

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            var count:Int = cursor.getCount();
            //遍历
//            for (i in 0..count-1) {
            for (i in 0..need_do-1) {
//                println("iiiiiiiiiiiiiiiiiii")
//                println(i)
                cursor.moveToPosition(i)
                var q_now: question = question()
                //ID
                q_now.question_id = cursor.getInt(cursor.getColumnIndex("q_id"));
                //问题
                q_now.question_str = cursor.getString(cursor.getColumnIndex("q_str"));
                //四个选择
                q_now.answer_a = cursor.getString(cursor.getColumnIndex("a_1"));
                q_now.answer_b = cursor.getString(cursor.getColumnIndex("a_2"));
                q_now.answer_c = cursor.getString(cursor.getColumnIndex("a_3"));
                q_now.answer_d = cursor.getString(cursor.getColumnIndex("a_4"));
                //答案
                q_now.answer_id = cursor.getInt(cursor.getColumnIndex("r_id"));
                //解析
                q_now.right_answer = cursor.getString(cursor.getColumnIndex("r_a"));
                list += q_now
            }
        }
        return list
    }

    fun get_paper(paper_type:String): List<paper> {
        var list:List<paper> = arrayListOf()
        var r_list:List<paper> = arrayListOf()
        var cursor:Cursor = db.rawQuery("select * from paper", null)

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            var count:Int = cursor.getCount();
            //遍历
            for (i in 0..count-1) {
                cursor.moveToPosition(i)
                var p_now: paper = paper()
                //ID
                p_now.paper_id = cursor.getInt(cursor.getColumnIndex("paper_id"))
                p_now.paper_name = cursor.getString(cursor.getColumnIndex("paper_name"))
                p_now.paper_q_id = cursor.getString(cursor.getColumnIndex("paper_q_id"))
                p_now.paper_q_type = cursor.getString(cursor.getColumnIndex("paper_q_type"))
                list += p_now
            }
        }
        for (i in 0..list.size-1) {
            if (list[i].paper_q_type == paper_type) {
                r_list += list[i]
            }
        }
        return r_list
    }

    fun get_qustion_byid(table_name:String,q_list:List<Int>): List<question> {
        println(table_name)
        var list:List<question> = arrayListOf<question>()
        var r_list:List<question> = arrayListOf<question>()
        var cursor:Cursor = db.rawQuery("select * from " + table_name, null)

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            var count:Int = cursor.getCount();
            //遍历
            for (i in 0..count-1) {
                cursor.moveToPosition(i)
                var q_now: question = question()
                //ID
                q_now.question_id = cursor.getInt(cursor.getColumnIndex("q_id"));
                //问题
                q_now.question_str = cursor.getString(cursor.getColumnIndex("q_str"));
                //四个选择
                q_now.answer_a = cursor.getString(cursor.getColumnIndex("a_1"));
                q_now.answer_b = cursor.getString(cursor.getColumnIndex("a_2"));
                q_now.answer_c = cursor.getString(cursor.getColumnIndex("a_3"));
                q_now.answer_d = cursor.getString(cursor.getColumnIndex("a_4"));
                //答案
                q_now.answer_id = cursor.getInt(cursor.getColumnIndex("r_id"));
                //解析
                q_now.right_answer = cursor.getString(cursor.getColumnIndex("r_a"));
                list += q_now
            }
        }
        for (i in 0..list.size-1) {
            if (q_list.contains(list[i].question_id)) {
                r_list += list[i]
            }
        }
        return r_list
    }

}