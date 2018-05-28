package log;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import Gui.FileUi;

public class searchlog extends JFrame implements ActionListener {
  JButton jb1, jb2, jb3, jb4;
  JTextField j1, j2, j3, j4;
  JLabel l1, l2, l3, l4;
  JTextField j11, j12, j13, j14;
  JLabel l11, l12, l13, l14;
  JLabel jl1, jl2, jl3;
  JPanel jp1, jp2, jp3;

  public searchlog() {
    jb1 = new JButton("输出所有INFO信息");
    jb2 = new JButton("输出所有WARN信息");
    jb3 = new JButton("输出所有ERROR信息");
    jb4 = new JButton("按时间段查询");
    jl1 = new JLabel("按类型查询:");
    jl2 = new JLabel("起始时间：");
    jl2.setFont(new java.awt.Font("Dialog", 0, 20));
    jl3 = new JLabel("结束时间：");
    jl3.setFont(new java.awt.Font("Dialog", 0, 20));
    l1 = new JLabel("月:");
    l11 = new JLabel("月:");
    l2 = new JLabel("日:");
    l12 = new JLabel("日:");
    l3 = new JLabel("点:");
    l13 = new JLabel("点:");
    l4 = new JLabel("分:");
    l14 = new JLabel("分:");
    this.setSize(400, 400);
    jl1.setFont(new java.awt.Font("Dialog", 0, 20));
    // 设置监听
    jb1.addActionListener(this);
    jb2.addActionListener(this);
    jb3.addActionListener(this);
    jb4.addActionListener(this);
    jp1 = new JPanel();
    jp2 = new JPanel();
    jp3 = new JPanel();
    jp1.add(jl1);
    jp1.add(jb1);
    jp1.add(jb2);
    jp1.add(jb3);
    j1 = new JTextField(2);
    j2 = new JTextField(2);
    j3 = new JTextField(2);
    j4 = new JTextField(2);
    j11 = new JTextField(2);
    j12 = new JTextField(2);
    j13 = new JTextField(2);
    j14 = new JTextField(2);
    jp2.add(jl2);
    jp2.add(j1);
    jp2.add(l1);
    jp2.add(j2);
    jp2.add(l2);
    jp2.add(j3);
    jp2.add(l3);
    jp2.add(j4);
    jp2.add(l4);
    jp3.add(jl3);
    jp3.add(j11);
    jp3.add(l11);
    jp3.add(j12);
    jp3.add(l12);
    jp3.add(j13);
    jp3.add(l13);
    jp3.add(j14);
    jp3.add(l14);
    jp3.add(jb4);
    jp1.setLayout(new GridLayout(4, 1));
    jp2.setLayout(new FlowLayout());
    // jp2.setLayout(new GridLayout(5,2));

    this.add(jp1);
    // jp2.add(jl2);
    this.add(jp2);
    // jp3.add(jl3);
    this.add(jp3);
    this.setLayout(new GridLayout(4, 1));
    this.setTitle("searchlog");
    this.setLocation(400, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置当关闭窗口时，保证JVM也退出
    this.setVisible(true);
    this.setResizable(true);
  }

  private void dealtype(String filepath) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(filepath));
    String headline1;
    String headline2;
    String headline3;
    String headline4;
    while ((headline1 = br.readLine()) != null) {
      headline2 = br.readLine();
      headline3 = br.readLine();
      headline4 = br.readLine();
      String info1 = headline1.replace("时间：", "");
      // String info11 = headline1.replace("时间：", "").replaceAll("-", " ").replaceAll(":", " ");
      // String[] infos1 = info11.split(" ");
      String info2 = headline2.replace("类型：", "");
      String[] infos3 = headline3.split("\\(");
      String info3 = infos3[1].replaceAll("\\)", "");
      String info4 = headline4.replace("信息：", "");
      System.out.println(info1 + "  " + info2 + "  " + info3 + "  " + info4);
    }
  }

  private void dealtime(String filepath, int[] time) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(filepath));
    String headline1;
    String headline2;
    String headline3;
    String headline4;
    while ((headline1 = br.readLine()) != null) {
      headline2 = br.readLine();
      headline3 = br.readLine();
      headline4 = br.readLine();
      String info1 = headline1.replace("时间：", "");
      String info11 = headline1.replace("时间：", "").replaceAll("-", " ").replaceAll(":", " ");
      String[] infos1 = info11.split(" ");
      if (time[0] <= Integer.parseInt(infos1[0]) && Integer.parseInt(infos1[0]) <= time[4]) {
        if (time[1] < Integer.parseInt(infos1[1]) && Integer.parseInt(infos1[1]) < time[5]) {
          String info2 = headline2.replace("类型：", "");
          String[] infos3 = headline3.split("\\(");
          String info3 = infos3[1].replaceAll("\\)", "");
          String info4 = headline4.replace("信息：", "");
          System.out.println(info1 + "  " + info2 + "  " + info3 + "  " + info4);
        } else if (time[1] == Integer.parseInt(infos1[1])
            || Integer.parseInt(infos1[1]) == time[5]) {
          if (time[2] <= Integer.parseInt(infos1[2]) && Integer.parseInt(infos1[2]) <= time[6]) {
            if (time[3] <= Integer.parseInt(infos1[3]) && Integer.parseInt(infos1[3]) <= time[7]) {
              String info2 = headline2.replace("类型：", "");
              String[] infos3 = headline3.split("\\(");
              String info3 = infos3[1].replaceAll("\\)", "");
              String info4 = headline4.replace("信息：", "");
              System.out.println(info1 + "  " + info2 + "  " + info3 + "  " + info4);
            }
          }
        }
      } else {
        System.out.println("wrong time!");
        break;
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() == "输出所有INFO信息") {
      try {
        dealtype("src/log/log.log");
      } catch (IOException e1) {
        System.out.println(e1);
      }
    } else if (e.getActionCommand() == "输出所有WARN信息") {
      try {
        dealtype("src/log/warn.log");
      } catch (IOException e1) {
        System.out.println(e1);
      }
    } else if (e.getActionCommand() == "输出所有ERROR信息") {
      try {
        dealtype("src/log/error.log");
      } catch (IOException e1) {
        System.out.println(e1);
      }
    } else if (e.getActionCommand() == "按时间段查询") {
      try {
        int[] t = new int[8];
        t[0] = Integer.parseInt(j1.getText());
        t[1] = Integer.parseInt(j2.getText());
        t[2] = Integer.parseInt(j3.getText());
        t[3] = Integer.parseInt(j4.getText());
        t[4] = Integer.parseInt(j11.getText());
        t[5] = Integer.parseInt(j12.getText());
        t[6] = Integer.parseInt(j13.getText());
        t[7] = Integer.parseInt(j14.getText());
        dealtime("src/log/error.log", t);
      } catch (IOException e1) {
        System.out.println(e1);
      }
    }


  }

  public static void main(String[] args) {
    new searchlog();
  }

}
