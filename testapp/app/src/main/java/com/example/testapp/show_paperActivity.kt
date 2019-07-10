package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.INotificationSideChannel
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.testapp.DB_util.DB_connect
import com.example.testapp.DB_util.paper
import com.example.testapp.DB_util.question
import com.example.testapp.util.Get_test
import com.example.testapp.util.Post_test
import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.android.synthetic.main.activity_show_paper.*
import kotlinx.android.synthetic.main.choose_item.*

/**
 * 显示查询到的试卷,点击相应试卷进入考试界面
 */
class show_paperActivity : AppCompatActivity() {

    var all_bt:List<TextView> = listOf()
    var all_imag:List<LinearLayout> = listOf()
    // 返回试卷信息
    var r_str = ""
    var mGetpaper_Result = object : Post_test.Paper_result {
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
    // 试卷信息
    var paper_info = ""
    var p_list:List<String> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_paper)
        get_transfr()
//        println(paper_info)
        // 获取试卷信息
        p_list = paper_info.split("cut1")
        p_list -= ""
//        val rec_str = intent.getStringExtra("试卷信息")
//        var p_list = rec_str.split("cut1")
        // 添加按键功能
        add_function()
    }

    // 获取上一个界面传递的数据
    fun get_transfr() {
        server_url = intent.getStringExtra("url")
        usr_id = intent.getStringExtra("用户名")
        visit_type = intent.getStringExtra("访问类型")
        paper_info = intent.getStringExtra("试卷信息")
    }
    // 添加按键功能
    fun add_function() {
        // 初始化列表
        initBT(p_list.size)
        var name2id:Map<String,String> = mutableMapOf()
        for (j in 0.. p_list.size-1) {
            println(p_list[j])
            name2id += Pair(p_list[j].split("cut0")[1],p_list[j].split("cut0")[0])
        }
        for (j in name2id.keys)
            println(name2id[j] + " " + j)
        for (i in 0..7)
            if (i < p_list.size) {
                all_bt[i].setText(p_list[i].split("cut0")[1])
            } else {
                all_bt[i].setVisibility(View.INVISIBLE)
                all_imag[i].setVisibility(View.INVISIBLE)
            }
        all_bt[0].setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            println("str " + all_bt[0].text.toString())
            println(name2id[all_bt[0].text.toString()])
            Post_test(mGetpaper_Result).get_paper_byid(server_url,name2id[all_bt[0].text.toString()]!!)
            while(r_str == "");
            transfr_next(intent)
            startActivity(intent)
        }
        all_bt[1].setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            Post_test(mGetpaper_Result).get_paper_byid(server_url,name2id[all_bt[1].text.toString()]!!)
            while(r_str == "");
            transfr_next(intent)
            startActivity(intent)
        }
        all_bt[2].setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            Post_test(mGetpaper_Result).get_paper_byid(server_url,name2id[all_bt[2].text.toString()]!!)
            while(r_str == "");
            transfr_next(intent)
            startActivity(intent)
        }
        all_bt[3].setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            Post_test(mGetpaper_Result).get_paper_byid(server_url,name2id[all_bt[3].text.toString()]!!)
            while(r_str == "");
            transfr_next(intent)
            startActivity(intent)
        }
        all_bt[4].setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            Post_test(mGetpaper_Result).get_paper_byid(server_url,name2id[all_bt[4].text.toString()]!!)
            while(r_str == "");
            transfr_next(intent)
            startActivity(intent)
        }
        all_bt[5].setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            Post_test(mGetpaper_Result).get_paper_byid(server_url,name2id[all_bt[5].text.toString()]!!)
            while(r_str == "");
            transfr_next(intent)
            startActivity(intent)
        }
        all_bt[6].setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            Post_test(mGetpaper_Result).get_paper_byid(server_url,name2id[all_bt[6].text.toString()]!!)
            while(r_str == "");
            transfr_next(intent)
            startActivity(intent)
        }
        all_bt[7].setOnClickListener {
            val intent = Intent()
            intent.setClass(this,examActivity::class.java)
            Post_test(mGetpaper_Result).get_paper_byid(server_url,name2id[all_bt[7].text.toString()]!!)
            while(r_str == "");
            transfr_next(intent)
            startActivity(intent)
        }
        bt_paper_down.setOnClickListener {
            var page_now = bt_paper_num.text.split("/")[0].toInt()
            if (page_now < (p_list.size/8 + 1)) {
                for (i in 0..7)
                    if (i+page_now * 8 < p_list.size - 1)
                        all_bt[i].setText(p_list[i + page_now * 8].split("cut0")[0])
                    else {
                        all_bt[i].setVisibility(View.INVISIBLE)
                        all_imag[i].setVisibility(View.INVISIBLE)
                    }
                val page_str: String = (page_now + 1).toString() + "/" + (p_list.size / 8 + 1).toString()
                bt_paper_num.setText(page_str)
            }
        }
        bt_paper_up.setOnClickListener {
            var page_now = bt_paper_num.text.split("/")[0].toInt()-1
            if (page_now >= 0) {
                for (i in 0..7) {
                    if (i + (page_now - 1) * 8 < p_list.size - 1) {
                        all_bt[i].setVisibility(View.VISIBLE)
                        all_imag[i].setVisibility(View.VISIBLE)
                        all_bt[i].setText(p_list[i + (page_now - 1) * 8].split("cut0")[0])
                    } else {
                        all_bt[i].setVisibility(View.INVISIBLE)
                        all_imag[i].setVisibility(View.INVISIBLE)
                    }
                }
                val page_str:String = (page_now).toString() + "/" + (p_list.size/8 + 1).toString()
                bt_paper_num.setText(page_str)
            }
        }
    }
    // 初始化按键
    fun initBT(p_list_size:Int) {
        all_bt += paper1_text
        all_bt += paper2_text
        all_bt += paper3_text
        all_bt += paper4_text
        all_bt += paper5_text
        all_bt += paper6_text
        all_bt += paper7_text
        all_bt += paper8_text
        all_imag += p_layout1
        all_imag += p_layout2
        all_imag += p_layout3
        all_imag += p_layout4
        all_imag += p_layout5
        all_imag += p_layout6
        all_imag += p_layout7
        all_imag += p_layout8
        println("size:" + p_list_size.toString())
        bt_paper_num.setText("1/" + (p_list_size/8 + 1).toString())
    }

    // 传递信息给下一个界面
    fun transfr_next(intent:Intent) {
        intent.putExtra("url",server_url)
        intent.putExtra("用户名",usr_id)
        intent.putExtra("访问类型",visit_type)
        intent.putExtra("试卷信息",paper_info)
        intent.putExtra("试卷内容",r_str)
        intent.putExtra("查看错题", IntArray(0))
    }


}