package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.Data_type.Paper_test
import com.example.testapp.R
import com.example.testapp.util.*
import kotlinx.android.synthetic.main.activity_lookup_score.*
import kotlinx.android.synthetic.main.activity_show_paper.*
import kotlinx.android.synthetic.main.activity_show_paper.bt_paper_down
import kotlinx.android.synthetic.main.activity_show_paper.bt_paper_num
import kotlinx.android.synthetic.main.activity_show_paper.bt_paper_up
import kotlinx.android.synthetic.main.activity_show_paper.p_layout1
import kotlinx.android.synthetic.main.activity_show_paper.p_layout2
import kotlinx.android.synthetic.main.activity_show_paper.p_layout3
import kotlinx.android.synthetic.main.activity_show_paper.p_layout4
import kotlinx.android.synthetic.main.activity_show_paper.p_layout5
import kotlinx.android.synthetic.main.activity_show_paper.p_layout6
import kotlinx.android.synthetic.main.activity_show_paper.p_layout7
import kotlinx.android.synthetic.main.activity_show_paper.p_layout8
import kotlinx.android.synthetic.main.activity_show_paper.paper1_text
import kotlinx.android.synthetic.main.activity_show_paper.paper2_text
import kotlinx.android.synthetic.main.activity_show_paper.paper3_text
import kotlinx.android.synthetic.main.activity_show_paper.paper4_text
import kotlinx.android.synthetic.main.activity_show_paper.paper5_text
import kotlinx.android.synthetic.main.activity_show_paper.paper6_text
import kotlinx.android.synthetic.main.activity_show_paper.paper7_text
import kotlinx.android.synthetic.main.activity_show_paper.paper8_text
import kotlinx.android.synthetic.main.activity_toolbar.*

class lookup_scoreActivity : AppCompatActivity(){
    var all_bt:List<TextView> = listOf()
    var all_score:List<TextView> = listOf()
    var all_imag:List<LinearLayout> = listOf()
    var r_str = ""
    var musr_score_result = object : Get_record.Score_result {
        override fun get_score_info(ret: Boolean, tipText: String){
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
    // 分数信息
    var p_list:List<Paper_test> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup_score)

        server_url = intent.getStringExtra("url")
        usr_id = intent.getStringExtra("用户名")
        Get_record(musr_score_result).get_all_score(server_url,usr_id)
        while(r_str == "");
        p_list = json_util().Analysis_score(r_str.toString())
        add_function()
    }

    // 添加按键功能
    fun add_function() {
        // 初始化列表
        initBT(p_list.size)
        for (i in 0..7){
            if (i < p_list.size) {
                all_bt[i].setText(p_list[i].test_name)
                all_score[i].setText(p_list[i].score.toString())
            } else {
                all_bt[i].setVisibility(View.INVISIBLE)
                all_score[i].setVisibility(View.INVISIBLE)
                all_imag[i].setVisibility(View.INVISIBLE)
            }
        }
        bt_paper_down.setOnClickListener {
            var page_now = bt_paper_num.text.split("/")[0].toInt()
            if (page_now < (p_list.size/8 + 1)) {
                for (i in 0..7)
                    if (i+page_now * 8 < p_list.size - 1) {
                        all_bt[i].setText(p_list[i + page_now * 8].test_name)
                        all_score[i].setText(p_list[i + page_now * 8].score.toString())
                    } else {
                        all_bt[i].setVisibility(View.INVISIBLE)
                        all_score[i].setVisibility(View.INVISIBLE)
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
                        all_score[i].setVisibility(View.VISIBLE)
                        all_imag[i].setVisibility(View.VISIBLE)
                        all_bt[i].setText(p_list[i + (page_now - 1) * 8].test_name)
                        all_score[i].setText(p_list[i + (page_now - 1) * 8].score.toString())
                    } else {
                        all_bt[i].setVisibility(View.INVISIBLE)
                        all_score[i].setVisibility(View.INVISIBLE)
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
        all_bt += paper1_text1
        all_bt += paper2_text1
        all_bt += paper3_text1
        all_bt += paper4_text1
        all_bt += paper5_text1
        all_bt += paper6_text1
        all_bt += paper7_text1
        all_bt += paper8_text1
        all_score += paper1_score
        all_score += paper2_score
        all_score += paper3_score
        all_score += paper4_score
        all_score += paper5_score
        all_score += paper6_score
        all_score += paper7_score
        all_score += paper8_score
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
}