package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.content.*
import com.example.testapp.util.LoginCheck

import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.login.*
import androidx.appcompat.app.AlertDialog
import com.example.testapp.util.Post_test
import com.example.testapp.util.json_util


/**
 * 登录界面
 */
class MainActivity : AppCompatActivity() {
    // 服务端url
    var server_url: String = "http://192.168.43.52:8080"
    // 登录成功返回right，失败返回wrong
    var r_str: String = ""
    val mLoginCheck = object : LoginCheck.LoginResult {
        override fun getLoginResult(ret: Boolean, tipText: String) {
            if (ret) {
                r_str = "right"
            } else {
                r_str = "wrong"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        setSupportActionBar(toolbar_all)

        // 登录按钮
        bt_login.setOnClickListener {
            LoginCheck(mLoginCheck).checkUserNameAndPassword(server_url,usr_name.text.toString(), usr_password.text.toString())
            while (r_str == "");
            if (r_str == "right") {
                val intent = Intent()
                // 跳转至菜单 传递url和用户id
                intent.setClass(this, Menu_Activity::class.java)
                intent.putExtra("url", server_url)
                intent.putExtra("用户名", usr_name.text.toString())
                startActivity(intent)
            } else {
                AlertDialog.Builder(this).setTitle("登录失败").setMessage("请确认用户名和密码！")
                    .setPositiveButton("确定", null).show()
            }
        }

//        var mGetpaper_Result = object : Post_test.Paper_result {
//            override fun get_paper_Result(ret: Boolean, tipText: String) {
//                if (ret) {
//                    println("aaaaaaaaaa")
//                    r_str = tipText
//                    println(r_str)
//                } else {
//                    println("bbbbbbb")
//                    r_str = "wrong"
//                    println(r_str)
//                }
//            }
//        }
        // 测试功能
//        bt_test.setOnClickListener {
//        }

    }

}
