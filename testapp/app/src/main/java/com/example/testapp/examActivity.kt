package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.content.Intent
import android.widget.*
import kotlinx.android.synthetic.main.choose_item.*
import android.os.*
import com.example.testapp.DB_util.DB_connect
import com.example.testapp.DB_util.question
import com.example.testapp.Data_type.problem
import com.example.testapp.util.Post_test
import com.example.testapp.util.json_util

/**
 * 考试选择界面
 */
class examActivity : AppCompatActivity() {

    // 选项对应数字
    private var id2answer: List<String> = listOf("N", "A", "B", "C", "D")
    // 问题list
    private var q_list: List<problem> = listOf()
    // 选择按钮
    var radio_bts: List<RadioButton> = listOf()
    // 倒计时设置
    var time = Timecount(10000, 1000)

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
        setContentView(R.layout.choose_item)
        // 获取上一个界面传递的信息
        get_transfr()

//        var q_type = intent.getStringExtra("题目类型")
//        var visit_type = intent.getStringExtra("访问类型")
//        var look_up = intent.getIntArrayExtra("查看错题")
//        var is_paper = intent.getIntExtra("做试卷",0)
//        var need2do = intent.getIntExtra("题目数量",5)
//        val db_server = DB_connect()
        // 记录用户答题结果
        var paper_q_list:List<Int> = listOf()
        // 解析返回信息，得到问题的list
//        if (intent.getStringExtra("练习") == "true") {
//            println("1111")
//        } else {
//            println(intent.getStringExtra("练习"))
            println(r_str)
            q_list = json_util().Analysis_post_paper(r_str)
//        }

        println(q_list.size)
        for (i in 0..q_list.size - 1)
            println(q_list[i].toString())

        // 界面的初始化
        initView(q_list)
        need2do = q_list.size
        // 若是查看，则显示用户结果
        if (visit_type == "查看") {
            show_right_answer(q_list[0].problem_correct_answer, look_up[0])
            time_text.setText("")
        }
        else {
            // 开始倒计时
            time.start()
        }
        // 下一题
        bt_down.setOnClickListener {
            var info_num = bt_num.text
            var num_list = info_num.split("/")
            // 更新问题
            if (num_list[0].toInt() < num_list[1].toInt()) {
                info_num = (num_list[0].toInt() + 1).toString() + "/" + num_list[1]
//                println(info_num)
                bt_num.setText(info_num)
            }
            if (visit_type == "做题") {
                if (num_list[0].toInt() == num_list[1].toInt() - 1) {
                    bt_down.setText("提交")
                }
                if (num_list[0].toInt() == num_list[1].toInt()) {
                    time.cancel()
                    record_answer(num_list[0].toInt() - 1)
                    val intent = Intent()
                    intent.setClass(this, resultActivity::class.java)
                    // 得分结果
                    info = get_score(q_list.size)
                    get_look_up()
                    for (i in 0..q_list.size - 1)
                        answer_result += q_list[i].usr_answer_id
                    transfr_next(intent)
                    startActivity(intent)
                } else {
                    record_answer(num_list[0].toInt() - 1)
                    adjust_qustion(num_list[0].toInt())
                    time.start()
                }
            } else {
                if (num_list[0].toInt() == num_list[1].toInt() - 1) {
                    bt_down.setText("返回")
                }
                if (num_list[0].toInt() == num_list[1].toInt()) {
                    val intent = Intent()
                    intent.setClass(this,Menu_Activity::class.java)
                    intent.putExtra("url",server_url)
                    intent.putExtra("用户名",usr_id)
                    startActivity(intent)
                } else {
                    adjust_qustion(num_list[0].toInt())
                    show_right_answer(q_list[num_list[0].toInt()].usr_answer, look_up[num_list[0].toInt()])
                }
            }

        }
        // 上一题
        bt_up.setOnClickListener {
            var info_num = bt_num.text
            println(info_num)
            var num_list = info_num.split("/")
            if (num_list[0].toInt() > 1) {
                info_num = (num_list[0].toInt() - 1).toString() + "/" + num_list[1]
                bt_num.setText(info_num)
            }
            if (num_list[0].toInt() != 1) {
                bt_down.setText("下一题")
                if (visit_type == "做题") {
                    record_answer(num_list[0].toInt() - 1)
                    adjust_qustion(num_list[0].toInt() - 2)
                } else {
                    adjust_qustion(num_list[0].toInt() - 2)
                    show_right_answer(q_list[num_list[0].toInt() - 1].problem_correct_answer, look_up[num_list[0].toInt() - 1])
                }
            }
        }

    }

    fun initView(list: List<problem>) {
        radio_bts += bt_a
        radio_bts += bt_b
        radio_bts += bt_c
        radio_bts += bt_d
        text_q.setText(list[0].problem_content)
        radio_bts[0].setText(list[0].problem_option_a)
        radio_bts[1].setText(list[0].problem_option_b)
        radio_bts[2].setText(list[0].problem_option_c)
        radio_bts[3].setText(list[0].problem_option_d)
        answer_text.setText("")
        bt_num.setText("1/" + q_list.size.toString())
    }

    fun adjust_qustion(num: Int) {
        text_q.setText(q_list[num].problem_content)
        bt_a.setText(q_list[num].problem_option_a)
        bt_b.setText(q_list[num].problem_option_b)
        bt_c.setText(q_list[num].problem_option_c)
        bt_d.setText(q_list[num].problem_option_d)
//        time = Timecount(q_list[num].problem_duration.toLong(),1000)
    }

    fun show_right_answer(answer_correct: String, usr_id: Int) {
        var prompt: String = "正确答案："
        prompt = prompt.plus(answer_correct)
        if (answer_correct == id2answer[usr_id])
            prompt = prompt.plus(" 你选对了")
        else
            prompt = prompt.plus(" 你错选为 " + id2answer[usr_id])
        println(prompt)
        answer_text.setText(prompt)
    }

    fun record_answer(id: Int) {
        q_list[id].usr_answer_id = 0
        for (i in 0..3) {
            if (radio_bts[i].isChecked == true) {
//                println(radio_bts[i].text)
                q_list[id].usr_answer = radio_bts[i].text.toString()
                q_list[id].usr_answer_id = i + 1
            }
        }
    }

    fun get_score(q_num: Int): String {
        var right: Int = 0
        for (i in 0..q_num - 1) {
            println(id2answer[q_list[i].usr_answer_id])
            println(q_list[i].problem_correct_answer)
            if (id2answer[q_list[i].usr_answer_id] == q_list[i].problem_correct_answer)
                right += 1
        }
        return right.toString()
    }

    fun get_look_up() {
        for (i in 0..q_list.size-1) {
            look_up += q_list[i].usr_answer_id
        }
    }

    inner class Timecount constructor(end: Long, per: Long) : CountDownTimer(end, per) {
        override fun onTick(p0: Long) {
            time_text.setText("剩余时间：" + (p0 / 1000).toString() + "S")
//            toolbar_time.setText("剩余时间：" + (p0 / 1000).toString() + "S")
        }

        override fun onFinish() {
            time_text.setText("结束")
            bt_down.callOnClick()
        }

    }
    // 禁用返回键
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event != null) {
            if(event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
                //do something.
                return true;
            }else {
                return super.dispatchKeyEvent(event);
            }
        }
        return false
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
    }

    // 传递信息给下一个界面
    fun transfr_next(intent:Intent) {
        visit_type = "查看"
        println("needtod" + need2do.toString())
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