package com.example.testapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.DB_util.DB_connect
import com.example.testapp.DB_util.paper
import com.example.testapp.Data_type.Paper_test
import com.example.testapp.util.Get_test
import com.example.testapp.util.Post_test
import com.example.testapp.util.json_util
import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.android.synthetic.main.activity_choose_paper.*

/**
 * 根据类型选择试卷
 */
class choose_paperActivity : AppCompatActivity() {
    // 返回试卷相关信息
    var r_str = ""
    var r_list: List<Paper_test> = listOf()
    var musr_papers_Result = object : Get_test.Papers_Result {
        override fun get_paper_Result(ret: Boolean, tipText: String){
            if (ret) {
                r_str = tipText
            } else {
                r_str = "wrong"
            }
        }
    }
    // 服务端url
    var server_url:String = ""
    // 用户名
    var usr_id:String = ""
    // 访问类型
    var visit_type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_paper)
        // 接收传递的信息
        get_transfr()
        // 获取所有试卷信息
        Get_test(musr_papers_Result).get_all_papers(server_url)
        while(r_str == "");
        r_list = json_util().Analysis_paper_test(r_str)
//        for (i in r_list) {
//            println(i.test_id)
//            println(i.test_name)
//            println(i.test_type)
//        }
        // 选择数据库卷子 传递一个记录所有试卷信息的长字符串
        add_function()
    }
    // 设置按键功能
    fun add_function() {
        // 选择数据库
        text_paper_db.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,show_paperActivity::class.java)
            intent.putExtra("试卷信息",sort_by_type("数据库"))
            transfer(intent)
            startActivity(intent)
        }
        // 选择操作系统
        text_paper_os.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,show_paperActivity::class.java)
            intent.putExtra("试卷信息",sort_by_type("操作系统"))
            transfer(intent)
            startActivity(intent)
        }
        // 选择计算机网络
        text_paper_cn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,show_paperActivity::class.java)
            intent.putExtra("试卷信息",sort_by_type("计算机网络"))
            transfer(intent)
            startActivity(intent)
        }
        // 该按钮暂不使用
        text_paper_ds.setOnClickListener {
        }
    }
    // 按类型对返回的试卷信息分类
    fun sort_by_type(paper_type:String) : String {
        var send_str:String = ""
        for (i in r_list)
            if (i.test_type == paper_type)
                send_str += (i.test_id.toString() + "cut0" + i.test_name + "cut1")
        return send_str
    }
    // 获取上一个界面传递的数据
    fun get_transfr() {
        server_url = intent.getStringExtra("url")
        usr_id = intent.getStringExtra("用户名")
        visit_type = intent.getStringExtra("访问类型")
    }
    // 传递数据给下一个界面
    fun transfer(intent:Intent){
        intent.putExtra("url",server_url)
        intent.putExtra("用户名",usr_id)
        intent.putExtra("访问类型",visit_type)
    }
}