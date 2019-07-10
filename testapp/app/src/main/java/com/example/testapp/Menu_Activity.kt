package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.util.Get_test
import kotlinx.android.synthetic.main.activity_toolbar.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/***
 *  菜单界面
 */
class Menu_Activity : AppCompatActivity() {

    //数据库的名称
    private val DB_NAME = "test_app.db"
    //数据库的地址
    private val DB_PATH = "/data/data/com.example.testapp/databases/"

    // 服务端url
    var server_url:String = ""
    // 用户名
    var usr_id:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar_all)
        // 拷贝数据库的数据
//        initFile()
        // 接收传递的数据
        server_url = intent.getStringExtra("url")
        usr_id = intent.getStringExtra("用户名")

        // 练习模式
        val BT_type: Button = findViewById(R.id.bt_practice)
        BT_type.setOnClickListener{
            val intent = Intent()
            // 跳转至练习界面
            intent.setClass(this,practice_Activity::class.java)
            intent.putExtra("url",server_url)
            intent.putExtra("用户名",usr_id)
            intent.putExtra("访问类型","做题")
            startActivity(intent)
        }
        // 选择试卷
        val BT_paper_type: Button = findViewById(R.id.bt_paper)
        BT_paper_type.setOnClickListener{
            val intent2 = Intent()
            intent2.setClass(this,choose_paperActivity::class.java)
            intent2.putExtra("url",server_url)
            intent2.putExtra("用户名",usr_id)
            intent2.putExtra("访问类型","做题")
            startActivity(intent2)
        }
        // 查看分数
        val BT_score: Button = findViewById(R.id.bt_score)
        BT_score.setOnClickListener {
            val intent3 = Intent()
            intent3.setClass(this,lookup_scoreActivity::class.java)
            intent3.putExtra("url",server_url)
            intent3.putExtra("用户名",usr_id)
            startActivity(intent3)
        }

    }

    fun initFile() {
        println("加载文件")
        if (File(DB_PATH + DB_NAME).exists() == false) {
            var dir = File(DB_PATH)
            if (!dir.exists()) {
                dir.mkdir()
            }
            try {
                var is_file: InputStream = assets.open(DB_NAME)
                var os = FileOutputStream(DB_PATH + DB_NAME);

                //用来复制文件
                var buffe = ByteArray(512)
                //保存已经复制的长度
                var length = is_file.read(buffe)
                println("aaaaa" + length.toString())
                while (length != -1) {
                    os.write(buffe,0,length)
                    println("aaaaa" + length.toString())
                    length = is_file.read(buffe)
                }
                //刷新
                os.flush();
                //关闭
                os.close();
                is_file.close();
            } catch (e:IOException) {
                println("aaaaa")
                println(e.stackTrace)
            }

        }
    }


}