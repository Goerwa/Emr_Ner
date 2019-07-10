package com.example.testapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.example.testapp.util.Post_record

import kotlinx.android.synthetic.main.activity_result.*

class resultActivity : AppCompatActivity() {

    var r_code = ""
    var musr_post_socre = object : Post_record.Post_score{
        override fun get_usr_Result(ret: Boolean, tipText: String) {
            if (ret) {
                r_code = tipText
            } else {
                r_code = "wrong"
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
    // 答题结果
    var answer_result: IntArray = intArrayOf()
    var info = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        // 获取上一个界面传递的信息
        get_transfr()
        var show_result:String = "总共有" + need2do.toString() + "道题，你答对了" + info + "道！\n是否查看错题"
        info_str.setText(show_result)
//        println(answer_result.size)
//        for (i in 0..answer_result.size-1) {
//            println(i.toString() + answer_result[i].toString())
//        }
        // 上传用户答题结果
        Post_record(musr_post_socre).send_usr_result(server_url, usr_id,paper_info,info,look_up,r_str)
        if (r_code == "200") {
            AlertDialog.Builder(this).setTitle("上传成功").setMessage("已成功上传您的答题结果！")
                .setPositiveButton("确定", null).show()
        }
        bt_lookup.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            // 方位类型为查看做题结果
            transfr_next(intent)
            startActivity(intent)
        }

        bt_out.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,Menu_Activity::class.java)
            intent.putExtra("url",server_url)
            intent.putExtra("用户名",usr_id)
            startActivity(intent)
        }

    }

    // 获取上一个界面传递的数据
    fun get_transfr() {
        server_url = intent.getStringExtra("url")
        usr_id = intent.getStringExtra("用户名")
        visit_type = intent.getStringExtra("访问类型")
        paper_info = intent.getStringExtra("试卷信息")
        r_str = intent.getStringExtra("试卷内容")
        look_up = intent.getIntArrayExtra("查看错题")
//        is_paper = intent.getIntExtra("做试卷",0)
        need2do = intent.getIntExtra("题目数量",5)
        info = intent.getStringExtra("得分结果")
    }

    // 传递信息给下一个界面
    fun transfr_next(intent:Intent) {
        intent.putExtra("url",server_url)
        intent.putExtra("用户名",usr_id)
        intent.putExtra("访问类型",visit_type)
        intent.putExtra("试卷信息",paper_info)
        intent.putExtra("试卷内容",r_str)
        intent.putExtra("答题结果", answer_result)
        intent.putExtra("题目数量",need2do)
        intent.putExtra("得分结果", info)
        intent.putExtra("查看错题",look_up)
    }

}
