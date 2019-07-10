package com.example.testapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.RadioButton
import com.example.testapp.util.Post_practice
import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

/**
 * 练习题目界面
 */
class practice_Activity : AppCompatActivity() {

    // 题目的默认值
    var default_q_num:Int = 5
    // 返回题目
    var r_result = ""
    var musr_practice_result = object:Post_practice.Practice_Result{
        override fun get_practice_Result(ret: Boolean, tipText: String) {
            if (ret) {
                r_result = tipText
            } else {
                r_result = "wrong"
            }
        }
    }
    // 服务端url
    var server_url:String = ""
    // 用户名
    var usr_id:String = ""
    // 访问类型
    var visit_type = ""
    // 试卷信息
    var paper_info = ""
    // 试卷具体内容
    var r_str = ""
    // 做题结果
    var look_up = intArrayOf()
    // 题目数量
    var need2do = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        get_transfr()
        // 选择数据库
        text_db.setOnClickListener {
            Post_practice(musr_practice_result).get_practice(server_url,"数据库",get_q_num())
            while (r_result ==  "");
            val intent = Intent()
            intent.setClass(this, examActivity::class.java)
            transfr_next(intent)
            startActivity(intent)
        }
        // 选择计算机网络
        text_cn.setOnClickListener {
            Post_practice(musr_practice_result).get_practice(server_url,"计算机网络",get_q_num())
            while (r_result ==  "");
            val intent = Intent()
            intent.setClass(this, examActivity::class.java)
            transfr_next(intent)
            startActivity(intent)
        }
        // 选择操作系统
        text_os.setOnClickListener {
            Post_practice(musr_practice_result).get_practice(server_url,"操作系统",get_q_num())
            while (r_result ==  "");
            val intent = Intent()
            intent.setClass(this, examActivity::class.java)
            transfr_next(intent)
            startActivity(intent)
        }


    }
    // 获取单选按钮的题目数量
    fun get_q_num(): Int {
        if (bt_pn1.isChecked) {
            return 1
        }
        else if (bt_pn2.isChecked) {
            return 2
        }
        else if (bt_pn3.isChecked) {
            return 3
        }
        return default_q_num
    }
    // 获取上一个界面传递的数据
    fun get_transfr() {
        server_url = intent.getStringExtra("url")
        usr_id = intent.getStringExtra("用户名")
        visit_type = intent.getStringExtra("访问类型")
    }

    // 传递信息给下一个界面
    fun transfr_next(intent:Intent) {
        intent.putExtra("url",server_url)
        intent.putExtra("用户名",usr_id)
        intent.putExtra("访问类型",visit_type)
        intent.putExtra("试卷信息",paper_info)
        intent.putExtra("试卷内容",r_str)
        intent.putExtra("试卷内容",r_result)
        intent.putExtra("题目数量",need2do)
        intent.putExtra("查看错题",IntArray(0))
        intent.putExtra("练习","true")
    }


}
